package org.example.main;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.ImageHandlerBase64Encoder;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

sealed interface DrawAction permits ImageAction, PathAction, RectAction, TextAction{}
record ImageAction(String id, int x, int y) implements DrawAction{}
record PathAction(Path2D path, Color color, boolean fill) implements DrawAction{}
record RectAction(int x1, int y1, int x2, int y2, Color color, boolean fill) implements DrawAction{}
record TextAction(String text, Font font, int x, int y, Color color) implements DrawAction{}
record Edge(int x1, int y1, int x2, int y2){}
record Vertex(int x, int y){}

public class ImageBuilder{
    private int margin = 20;
    private int canvasWidth = 1;
    private int canvasHeight = 1;
    private final Color backgroundColor;
    private int borderThickness = 1;
    private Color borderColor = null;
    private final Deque<PathAction> arrowStack = new ArrayDeque<>();
    private final LinkedList<DrawAction> drawActions = new LinkedList<>();
    private final Map<String, BufferedImage> imageIds = new HashMap<>();
    private static final Font defaultFont = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFont();
    private Font currentFont = defaultFont;
    private FontMetrics currentFontMetrics = new Canvas().getFontMetrics(this.currentFont);

    public ImageBuilder(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    // getters
    public int getMargin(){
        return this.margin;
    }

    public int getBorderThickness(){
        return this.borderThickness;
    }

    public int getCanvasWidth(){
        return this.canvasWidth;
    }

    public int getCanvasHeight(){
        return this.canvasHeight;
    }

    // setters
    public void setMargin(int margin){
        this.margin = margin;
    }

    public void setBorderThickness(int borderThickness){
        this.borderThickness = borderThickness;
    }

    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }

    public void setCanvasWidth(int canvasWidth){
        this.canvasWidth = Math.max(this.canvasWidth, canvasWidth);
    }

    public void setCanvasHeight(int canvasHeight){
        this.canvasHeight = Math.max(this.canvasHeight, canvasHeight);
    }

    public void addImage(BufferedImage image, int x, int y, String id){
        this.canvasWidth = Math.max(this.canvasWidth, x + image.getWidth());
        this.canvasHeight = Math.max(this.canvasHeight, y + image.getHeight());
        this.imageIds.putIfAbsent(id, image);
        this.drawActions.add(new ImageAction(id, x, y));
    }

    private Path2D drawArrowhead(int x, int y, boolean invertX, boolean invertY){
        int arrowLength = 8;
        int arrowWidth = 4;
        int x1 = x + (invertY ? (invertX ? 1 : -1)*arrowLength : arrowWidth);
        int y1 = y + (invertY ? arrowWidth : (invertX ? 1 : -1)*arrowLength);
        int x2 = x - (invertY ? (invertX ? -1 : 1)*arrowLength : arrowWidth);
        int y2 = y - (invertY ? arrowWidth : (invertX ? -1 : 1)*arrowLength);

        if(!invertX && invertY){
            x += 1;
            x1 += 1;
            x2 += 1;
        }

        Path2D arrowhead = new Path2D.Double();
        arrowhead.moveTo(x, y);
        arrowhead.lineTo(x1 + (invertY ? 0 : 0.1), y1);
        arrowhead.lineTo(x2, y2);
        arrowhead.closePath();

        return arrowhead;
    }

    public void drawArrow(int x1, int y1, int x2, int y2, Color color, boolean priorityX, boolean drawUnder){
        this.canvasWidth = Math.max(this.canvasWidth, Math.max(x1, x2));
        this.canvasHeight = Math.max(this.canvasHeight, Math.max(y1, y2));

        Path2D path = new Path2D.Double();
        boolean invertX = x1 > x2;
        boolean invertY = y1 > y2;
        int middleWidth = (x2 + x1)/2;
        int middleHeight = (y2 + y1)/2;
        int radius = Math.max(Math.min(Math.abs(y2 - y1)/2, Math.abs((x2 - x1)/2)), 0);
        int[][] points = new int[][]{
            {(priorityX ? middleWidth : x1) + (invertX == priorityX ? 0 : -2*radius), (priorityX ? y1 : middleHeight) + (invertY == priorityX ? -2*radius : 0), priorityX ? (invertY ? 270 : 90) : (invertX ? 0 : 180), (invertX ^ invertY) ^ !priorityX ? 90 : -90},
            {(priorityX ? middleWidth : x2) + (invertX == priorityX ? -2*radius : 0), (priorityX ? y2 : middleHeight) + (invertY == priorityX ? 0 : -2*radius), priorityX ? (invertX ? 0 : 180) : (invertY ? 270 : 90), (invertX ^ invertY) ^ !priorityX ? -90 : 90}
        };

        path.moveTo(x1, y1);
        for(int[] point : points){
            path.append(new Arc2D.Double(point[0], point[1], 2*radius, 2*radius, point[2], point[3], Arc2D.OPEN), true);
        }
        path.lineTo(x2, y2);

        this.drawActions.add(new PathAction(this.drawArrowhead(x2, y2, priorityX ? invertX : invertY, priorityX), color, true));
        if(drawUnder){
            this.arrowStack.push(new PathAction(path, color, false));
        }else{
            this.drawActions.add(new PathAction(path, color, false));
        }
    }

    public void drawSingleTurnArrow(int x1, int y1, int x2, int y2, Color color, boolean priorityX, boolean drawUnder){
        this.canvasWidth = Math.max(this.canvasWidth, Math.max(x1, x2));
        this.canvasHeight = Math.max(this.canvasHeight, Math.max(y1, y2));

        Path2D path = new Path2D.Double();
        boolean invertX = x1 > x2;
        boolean invertY = y1 > y2;
        int radius = Math.max(Math.min(Math.abs(y2 - y1)/2, Math.abs((x2 - x1)/2)), 0);
        int[][] points = new int[][]{
            {(priorityX ? x2 : x1) + (invertX == priorityX ? 0 : -2*radius), (priorityX ? y1 : y2) + (invertY == priorityX ? -2*radius : 0), priorityX ? (invertY ? 270 : 90) : (invertX ? 0 : 180), (invertX ^ invertY) ^ !priorityX ? 90 : -90}
        };

        path.moveTo(x1, y1);
        for(int[] point : points){
            path.append(new Arc2D.Double(point[0], point[1], 2*radius, 2*radius, point[2], point[3], Arc2D.OPEN), true);
        }
        path.lineTo(x2, y2);

        this.drawActions.add(new PathAction(this.drawArrowhead(x2, y2, priorityX ? invertY : invertX, !priorityX), color, true));
        if(drawUnder){
            this.arrowStack.push(new PathAction(path, color, false));
        }else{
            this.drawActions.add(new PathAction(path, color, false));
        }
    }

    public Point drawCurlyBrackets(int x1, int y1, int x2, int y2, String text, Color color, boolean invertX, boolean invertY){
        int textWidth = this.currentFontMetrics.stringWidth(text);
        int textHeight = this.currentFontMetrics.getHeight();
        Path2D path = new Path2D.Double();
        int tmp;
        if(y1 > y2){
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        if(x1 > x2){
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        this.canvasWidth = Math.max(this.canvasWidth, x2);
        this.canvasHeight = Math.max(this.canvasHeight, y2);

        int middleWidth = (x1 + x2)/2;
        int middleHeight = (y1 + y2)/2;
        int radius = Math.max(Math.min((y2 - y1)/(invertY ? 4 : 2), (x2 - x1)/(invertY ? 2 : 4)), 0);
        int[][] points = new int[][]{
            {invertY ? (middleWidth + (invertX ? 0 : -2*radius)) : x1, invertY ? y1 : (middleHeight + (invertX ? 0 : -2*radius)), invertY ? 90 : 180, invertX ^ invertY ? -90 : 90},
            {middleWidth + (invertX || !invertY ? -2*radius : 0), middleHeight + (invertX || invertY ? -2*radius : 0), invertX ? (invertY ? 0 : 270) : (invertY ? 180 : 90), invertX ^ invertY ? 90 : -90}
        };

        path.moveTo(invertY && invertX ? x2 : x1, invertY || !invertX ? y1 : y2);
        for(int[] point : points){
            path.append(new Arc2D.Double(point[0], point[1], 2*radius, 2*radius, point[2], point[3], Arc2D.OPEN), true);
        }
        path.lineTo(invertY ? (invertX ? x1 : x2) : middleWidth, invertY ? middleHeight : (invertX ? y1 : y2));

        AffineTransform transform = new AffineTransform();
        transform.translate(invertY ? 0 : path.getCurrentPoint().getX(), invertY ? path.getCurrentPoint().getY() : 0);
        transform.scale(invertY ? 1 : -1, invertY ? -1 : 1);
        transform.translate(invertY ? 0 : -path.getCurrentPoint().getX(), invertY ? -path.getCurrentPoint().getY() : 0);
        path.append(path.createTransformedShape(transform), false);

        this.drawActions.add(new PathAction(path, color, false));
        this.drawText(text, invertY ? (invertX ? x1 - textWidth : x2) : (x1 + x2 - textWidth)/2, invertY ? (y1 + y2 - textHeight)/2 : (invertX ? y1 - textHeight : y2), color);

        return new Point(x2 + (invertX || !invertY ? 0 : textWidth), y2 + (invertX || invertY ? 0 : textHeight));
    }

    public void setFont(Font font){
        if(this.currentFont.equals(font)){
            return;
        }
        this.currentFont = font;
        this.currentFontMetrics = new Canvas().getFontMetrics(currentFont);
    }

    public Point drawText(String text, int x, int y, Color color){
        int textWidth = this.currentFontMetrics.stringWidth(text);
        int textHeight = this.currentFontMetrics.getHeight();

        this.canvasWidth = Math.max(this.canvasWidth, x + textWidth);
        this.canvasHeight = Math.max(this.canvasHeight, y + textHeight);
        this.drawActions.add(new TextAction(text, this.currentFont, x, y + this.currentFontMetrics.getMaxAscent(), color));

        return new Point(x + textWidth, y + textHeight);
    }

    public Point drawText(String[] text, int x, int y, int insideMargin, int outsideMargin, Color color){
        int textHeight = this.currentFontMetrics.getHeight();
        Point nextPoint = new Point(x + outsideMargin, y + outsideMargin);

        for(String str : text){
            nextPoint.x = Math.max(nextPoint.x, this.drawText(str, x + outsideMargin, nextPoint.y, color).x);
            nextPoint.y += textHeight + insideMargin;
        }
        nextPoint.x += outsideMargin;
        nextPoint.y += outsideMargin;

        this.canvasWidth = Math.max(this.canvasWidth, nextPoint.x);
        this.canvasHeight = Math.max(this.canvasHeight, nextPoint.y);

        return new Point(nextPoint.x, nextPoint.y);
    }

    public Point drawTextFixedSpacing(String[] text, int x, int y, int margin, int spacing, Color color){
        Point nextPoint = new Point(x + margin, y + margin);

        for(int i=0; i < text.length; i++){
            if(i < text.length - 1){
                nextPoint.x = Math.max(nextPoint.x, this.drawText(text[i], x + margin, nextPoint.y, color).x);
                nextPoint.y += spacing;
            }else{
                Point point = this.drawText(text[i], x + margin, nextPoint.y, color);
                nextPoint.x = Math.max(nextPoint.x, point.x);
                nextPoint.y = Math.max(nextPoint.y, point.y);
            }
        }
        nextPoint.x += margin;
        nextPoint.y += margin;

        this.canvasWidth = Math.max(this.canvasWidth, nextPoint.x);
        this.canvasHeight = Math.max(this.canvasHeight, nextPoint.y);

        return new Point(nextPoint.x, nextPoint.y);
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, Color color, boolean fill){
        int xMin = Math.min(x1, x2);
        int yMin = Math.min(y1, y2);
        int xMax = Math.max(x1, x2);
        int yMax = Math.max(y1, y2);

        this.canvasWidth = Math.max(this.canvasWidth, xMax);
        this.canvasHeight = Math.max(this.canvasHeight, yMax);
        this.drawActions.add(new RectAction(xMin, yMin, xMax, yMax, color, fill));
    }

    public Point drawTextRectangle(String[] text, int x, int y, int rectangleMargin, Color fontColor, Color rectangleColor){
        int textHeight = this.currentFontMetrics.getHeight();
        Point nextPoint = new Point(x + rectangleMargin, y + rectangleMargin);

        for(String str : text){
            nextPoint.x = Math.max(nextPoint.x, this.drawText(str, x + rectangleMargin, nextPoint.y, fontColor).x);
            nextPoint.y += textHeight;
        }
        nextPoint.x += rectangleMargin;
        nextPoint.y += rectangleMargin;

        this.drawActions.add(new RectAction(x, y, nextPoint.x, nextPoint.y, rectangleColor, false));
        this.canvasWidth = Math.max(this.canvasWidth, nextPoint.x);
        this.canvasHeight = Math.max(this.canvasHeight, nextPoint.y);

        return new Point(nextPoint.x, nextPoint.y);
    }

    public BufferedImage toPNG(){
        BufferedImage canvas = new BufferedImage(this.canvasWidth + 2*this.margin, this.canvasHeight + 2*this.margin, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = canvas.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        g2d.setColor(this.backgroundColor);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.translate(this.margin, this.margin);

        Iterator<PathAction> it = this.arrowStack.descendingIterator();
        while(it.hasNext()){
            PathAction action = it.next();
            g2d.setColor(action.color());
            if(action.fill()){
                g2d.fill(action.path());
            }else{
                g2d.draw(action.path());
            }
        }

        for(DrawAction currentAction : this.drawActions){
            switch(currentAction){
                case ImageAction action -> {
                    BufferedImage img = imageIds.get(action.id());
                    if(img != null){
                        g2d.drawImage(img, action.x(), action.y(), null);
                    }
                }
                case PathAction action -> {
                    g2d.setColor(action.color());
                    if(action.fill()){
                        g2d.fill(action.path());
                    }else{
                        g2d.draw(action.path());
                    }
                }
                case RectAction action -> {
                    g2d.setColor(action.color());
                    if(action.fill()){
                        g2d.fillRect(action.x1(), action.y1(), action.x2() - action.x1(), action.y2() - action.y1());
                    }else{
                        g2d.drawRect(action.x1(), action.y1(), action.x2() - action.x1(), action.y2() - action.y1());
                    }
                }
                case TextAction action -> {
                    g2d.setColor(action.color());
                    g2d.setFont(action.font());
                    g2d.drawString(action.text(), action.x(), action.y());
                }
            }
        }

        if(this.borderColor != null && this.borderThickness > 0){
            g2d.translate(-this.margin, -this.margin);
            g2d.setStroke(new BasicStroke(this.borderThickness));
            g2d.setColor(this.borderColor);
            g2d.drawRect(this.borderThickness/2, this.borderThickness/2, this.canvasWidth + 2*this.margin - this.borderThickness, this.canvasHeight + 2*this.margin - this.borderThickness);
        }

        return canvas;
    }

    public boolean saveAsPNG(String filePath){
        try{
            ImageIO.write(this.toPNG(), "png", new File(filePath));
            return true;
        }catch(IOException e){
            return false;
        }
    }

    private static Element imageToSVG(BufferedImage image, Document document, SVGGeneratorContext ctx){
        int width = image.getWidth();
        int height = image.getHeight();
        int colorInt;
        int alpha;
        boolean[][] visited = new boolean[width][height];
        Map<Integer, LinkedList<Path2D>> pathMap = new HashMap<>();

        for(int imgX = 0; imgX < width; imgX++){
            for(int imgY = 0; imgY < height; imgY++){
                if(visited[imgX][imgY]){
                    continue;
                }

                colorInt = image.getRGB(imgX, imgY);
                alpha = (colorInt >>> 24) & 0xFF;

                if(alpha == 0){
                    visited[imgX][imgY] = true;
                    continue;
                }

                Path2D path = buildPath(floodFill(image, imgX, imgY, visited));
                if(path != null){
                    pathMap.computeIfAbsent(colorInt, k -> new LinkedList<>()).add(path);
                }
            }
        }

        Element imageGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        Element colorGroup;
        Element pathElement;
        for(Map.Entry<Integer, LinkedList<Path2D>> entry : pathMap.entrySet()){
            LinkedList<Path2D> list = entry.getValue();
            colorInt = entry.getKey();
            alpha = (colorInt >>> 24) & 0xFF;
            String colorString = String.format("#%02X%02X%02X", (colorInt >>> 16) & 0xFF, (colorInt >>> 8) & 0xFF, colorInt & 0xFF);

            if(list.size() == 1){
                pathElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "path");
                pathElement.setAttribute("fill", colorString);
                if(alpha != 255){
                    pathElement.setAttribute("fill-opacity", String.valueOf(alpha/255.0));
                }
                pathElement.setAttribute("d", SVGPath.toSVGPathData(list.element(), ctx));
                imageGroup.appendChild(pathElement);
            }else{
                colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
                colorGroup.setAttribute("fill", colorString);
                if(alpha != 255){
                    colorGroup.setAttribute("fill-opacity", String.valueOf(alpha/255.0));
                }
                for(Path2D path : entry.getValue()){
                    pathElement = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "path");
                    pathElement.setAttribute("d", SVGPath.toSVGPathData(path, ctx));
                    colorGroup.appendChild(pathElement);
                }
                imageGroup.appendChild(colorGroup);
            }
        }

        return imageGroup;
    }

    private static Path2D buildPath(Set<Edge> edges){
        if(edges.isEmpty()){
            return null;
        }

        Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
        Map<Vertex, List<Edge>> adjacency = new HashMap<>();
        Set<Edge> used = new HashSet<>();
        List<Edge> nextEdges;
        Edge found;

        for(Edge e : edges){
            Vertex v = new Vertex(e.x1(), e.y1());
            adjacency.computeIfAbsent(v, k -> new ArrayList<>()).add(e);
        }

        for(Edge start : edges){
            if(used.contains(start)){
                continue;
            }

            int startX = start.x1();
            int startY = start.y1();

            int currentX = start.x1();
            int currentY = start.y1();

            int dirX = start.x2() - start.x1();
            int dirY = start.y2() - start.y1();

            path.moveTo(currentX, currentY);

            Edge current = start;

            while(true){
                used.add(current);

                int nextX = current.x2();
                int nextY = current.y2();

                int nextDirX = nextX - currentX;
                int nextDirY = nextY - currentY;

                if(nextDirX != dirX || nextDirY != dirY){
                    path.lineTo(currentX, currentY);
                    dirX = nextDirX;
                    dirY = nextDirY;
                }

                currentX = nextX;
                currentY = nextY;

                if(currentX == startX && currentY == startY){
                    break;
                }

                nextEdges = adjacency.get(new Vertex(currentX, currentY));
                found = null;

                for(Edge e : nextEdges){
                    if(used.contains(e)){
                        continue;
                    }

                    found = e;
                    break;
                }
                if(found == null){
                    break;
                }
                current = found;
            }
            path.closePath();
        }

        return path;
    }

    private static void toggleEdge(Set<Edge> edges, Edge e){
        Edge reverse = new Edge(e.x2(), e.y2(), e.x1(), e.y1());

        if(edges.contains(reverse)){
            edges.remove(reverse);
        }else{
            edges.add(e);
        }
    }

    private static Set<Edge> floodFill(BufferedImage image, int startX, int startY, boolean[][] visited){
        int width = image.getWidth();
        int height = image.getHeight();
        int color = image.getRGB(startX, startY);
        Set<Edge> edges = new HashSet<>();

        Deque<int[]> stack = new ArrayDeque<>();

        stack.push(new int[]{startX, startY});

        visited[startX][startY] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while(!stack.isEmpty()){
            int[] p = stack.pop();
            int x = p[0];
            int y = p[1];

            toggleEdge(edges, new Edge(x, y, x + 1, y));
            toggleEdge(edges, new Edge(x + 1, y, x + 1, y + 1));
            toggleEdge(edges, new Edge(x + 1, y + 1, x, y + 1));
            toggleEdge(edges, new Edge(x, y + 1, x, y));

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx < 0 || ny < 0 || nx >= width || ny >= height || visited[nx][ny] || image.getRGB(nx, ny) != color){
                    continue;
                }

                visited[nx][ny] = true;
                stack.push(new int[]{nx, ny});
            }
        }

        return edges;
    }

    public Document toSVGDocument(boolean embed){
        Document document;
        SVGGeneratorContext ctx;
        Element root;
        Element marginTranslation;
        Element defs;
        Element currentGroup;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        FontRenderContext frc = new FontRenderContext(null, true, true);
        ImageHandlerBase64Encoder handler = embed ? null : new ImageHandlerBase64Encoder();

        factory.setNamespaceAware(true);

        try{
            document = factory.newDocumentBuilder().getDOMImplementation().createDocument("http://www.w3.org/2000/svg", "svg", null);
            root = document.getDocumentElement();
            ctx = SVGGeneratorContext.createDefault(document);
        }catch(Exception e){
            System.out.println("Error creating SVG document: " + e.getMessage());
            return null;
        }

        Map<String, Float> charDefMap = new HashMap<>();
        LinkedList<ImageAction> images = new LinkedList<>();
        Map<Integer, LinkedList<TextAction>> textMap = new HashMap<>();
        Map<Long, LinkedList<RectAction>> rectMap = new HashMap<>();
        Map<Long, LinkedList<PathAction>> pathMap = new HashMap<>();

        marginTranslation = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        marginTranslation.setAttribute("transform", "translate(" + this.margin + "," + this.margin + ")");

        defs = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "defs");

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "image_def");
        currentGroup.setAttribute("fill-rule", "evenodd");
        currentGroup.setAttribute("stroke", "none");
        for(Map.Entry<String, BufferedImage> entry : imageIds.entrySet()){
            BufferedImage image = entry.getValue();

            Element img = embed ? imageToSVG(image, document, ctx) : document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "image");
            img.setAttribute("id", entry.getKey());
            if(!embed){
                handler.handleImage((Image)image, img, ctx);
            }
            currentGroup.appendChild(img);
        }
        if(currentGroup.hasChildNodes()){
            defs.appendChild(currentGroup);
        }

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "char_def");
        currentGroup.setAttribute("stroke", "none");
        for(DrawAction currentAction : drawActions){
            switch(currentAction){
                case ImageAction action -> images.add(action);
                case TextAction action -> {
                    if(embed){
                        for(char c : action.text().toCharArray()){
                            String charId = "ch_" + action.font().getFamily() + "_" + action.font().getSize() + "_" + (int)c;
                            if(!charDefMap.containsKey(charId)){
                                GlyphVector glyphVector = action.font().createGlyphVector(frc, String.valueOf(c));
                                charDefMap.put(charId, glyphVector.getGlyphMetrics(0).getAdvance());
                                Element ch = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "path");
                                ch.setAttribute("id", charId);
                                ch.setAttribute("d", SVGPath.toSVGPathData(glyphVector.getOutline(0, 0), ctx));
                                currentGroup.appendChild(ch);
                            }
                        }
                    }
                    textMap.computeIfAbsent(action.color().getRGB(), k -> new LinkedList<>()).add(action);
                }
                case RectAction action -> rectMap.computeIfAbsent(((long)action.color().getRGB() << 1) | (action.fill() ? 1L : 0L), k -> new LinkedList<>()).add(action);
                case PathAction action -> pathMap.computeIfAbsent(((long)action.color().getRGB() << 1) | (action.fill() ? 1L : 0L), k -> new LinkedList<>()).add(action);
            }
        }
        if(currentGroup.hasChildNodes()){
            defs.appendChild(currentGroup);
        }
        if(defs.hasChildNodes()){
            root.appendChild(defs);
        }

        if(this.backgroundColor.getAlpha() != 0){
            currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
            currentGroup.setAttribute("x", "0");
            currentGroup.setAttribute("y", "0");
            currentGroup.setAttribute("width", String.valueOf(this.canvasWidth + 2*this.margin));
            currentGroup.setAttribute("height", String.valueOf(this.canvasHeight + 2*this.margin));
            currentGroup.setAttribute("fill", String.format("#%02X%02X%02X", backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
            if(this.backgroundColor.getAlpha() != 255){
                currentGroup.setAttribute("fill-opacity", String.valueOf(backgroundColor.getAlpha()/255));
            }
            root.appendChild(currentGroup);
        }

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "images");
        for(ImageAction action : images){
            Element use = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "use");
            use.setAttribute("href", "#" + action.id());
            use.setAttribute("x", String.valueOf(action.x()));
            use.setAttribute("y", String.valueOf(action.y()));
            currentGroup.appendChild(use);
        }
        if(currentGroup.hasChildNodes()){
            marginTranslation.appendChild(currentGroup);
        }

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "texts");
        for(Map.Entry<Integer, LinkedList<TextAction>> entry : textMap.entrySet()){
            int colorInt = entry.getKey();
            int alpha = (colorInt >>> 24) & 0xFF;
            String colorString = String.format("#%02X%02X%02X", (colorInt >>> 16) & 0xFF, (colorInt >>> 8) & 0xFF, colorInt & 0xFF);

            Element colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
            colorGroup.setAttribute("fill", colorString);
            if(alpha != 255){
                colorGroup.setAttribute("fill-opacity", String.valueOf(alpha/255.0));
            }

            currentGroup.appendChild(colorGroup);
            for(TextAction action : entry.getValue()){
                if(embed){
                    float x = action.x();
                    for(char c : action.text().toCharArray()){
                        String charId = "ch_" + action.font().getFamily() + "_" + action.font().getSize() + "_" + (int)c;

                        Element use = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "use");
                        use.setAttribute("href", "#" + charId);
                        use.setAttribute("x", String.valueOf(x));
                        use.setAttribute("y", String.valueOf(action.y()));
                        colorGroup.appendChild(use);

                        x += charDefMap.getOrDefault(charId, 0F);
                    }
                }else{
                    Element text = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
                    text.setTextContent(action.text());
                    text.setAttribute("x", String.valueOf(action.x()));
                    text.setAttribute("y", String.valueOf(action.y()));
                    if(!colorGroup.hasAttribute("font-family")){
                        colorGroup.setAttribute("font-family", action.font().getFamily());
                        colorGroup.setAttribute("font-size", String.valueOf(action.font().getSize()));
                    }else if(!colorGroup.getAttribute("font-family").equals(action.font().getFamily())){
                        colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
                        colorGroup.setAttribute("fill", colorString);
                        if(alpha != 255){
                            colorGroup.setAttribute("fill-opacity", String.valueOf(alpha/255.0));
                        }
                        colorGroup.setAttribute("font-family", action.font().getFamily());
                        colorGroup.setAttribute("font-size", String.valueOf(action.font().getSize()));
                        currentGroup.appendChild(colorGroup);
                    }
                    colorGroup.appendChild(text);
                }
            }
        }
        if(currentGroup.hasChildNodes()){
            marginTranslation.appendChild(currentGroup);
        }

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "rectangles");
        for(Map.Entry<Long, LinkedList<RectAction>> entry : rectMap.entrySet()){
            int colorInt = (int)(entry.getKey() >> 1);
            boolean fill = (entry.getKey() & 1L) != 0;
            int alpha = (colorInt >>> 24) & 0xFF;

            Element colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
            colorGroup.setAttribute(fill ? "fill" : "stroke", String.format("#%02X%02X%02X", (colorInt >>> 16) & 0xFF, (colorInt >>> 8) & 0xFF, colorInt & 0xFF));
            colorGroup.setAttribute(fill ? "stroke" : "fill", "none");
            if(alpha != 255){
                colorGroup.setAttribute(fill ? "fill-opacity" : "stroke-opacity", String.valueOf(alpha/255.0));
            }
            currentGroup.appendChild(colorGroup);
            for(RectAction action : entry.getValue()){
                Element rect = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
                rect.setAttribute("x", String.valueOf(action.x1()));
                rect.setAttribute("y", String.valueOf(action.y1()));
                rect.setAttribute("width", String.valueOf(action.x2() - action.x1()));
                rect.setAttribute("height", String.valueOf(action.y2() - action.y1()));
                colorGroup.appendChild(rect);
            }
        }
        if(currentGroup.hasChildNodes()){
            marginTranslation.appendChild(currentGroup);
        }

        currentGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
        currentGroup.setAttribute("id", "paths");
        currentGroup.setAttribute("stroke-width", "1");

        Element colorGroup = null;
        Iterator<PathAction> it = this.arrowStack.descendingIterator();
        while(it.hasNext()){
            PathAction action = it.next();
            int colorInt = action.color().getRGB();
            boolean fill = action.fill();
            int alpha = action.color().getAlpha();
            String colorString = String.format("#%02X%02X%02X", (colorInt >>> 16) & 0xFF, (colorInt >>> 8) & 0xFF, colorInt & 0xFF);
            String opacityString = alpha == 255 ? "" : String.valueOf(alpha/255.0);

            if(colorGroup == null || !colorGroup.getAttribute(fill ? "fill-opacity" : "stroke-opacity").equals(opacityString) || !colorGroup.getAttribute(fill ? "fill" : "stroke").equals(colorString)){
                colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
                colorGroup.setAttribute(fill ? "fill" : "stroke", colorString);
                colorGroup.setAttribute(fill ? "stroke" : "fill", "none");
                if(alpha != 255){
                    colorGroup.setAttribute(fill ? "fill-opacity" : "stroke-opacity", opacityString);
                }
                currentGroup.appendChild(colorGroup);
            }

            Element path = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "path");
            path.setAttribute("d", SVGPath.toSVGPathData(action.path(), ctx));
            colorGroup.appendChild(path);
        }

        for(Map.Entry<Long, LinkedList<PathAction>> entry : pathMap.entrySet()){
            int colorInt = (int)(entry.getKey() >> 1);
            boolean fill = (entry.getKey() & 1L) != 0;
            int alpha = (colorInt >>> 24) & 0xFF;

            colorGroup = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "g");
            colorGroup.setAttribute(fill ? "fill" : "stroke", String.format("#%02X%02X%02X", (colorInt >>> 16) & 0xFF, (colorInt >>> 8) & 0xFF, colorInt & 0xFF));
            colorGroup.setAttribute(fill ? "stroke" : "fill", "none");
            if(alpha != 255){
                colorGroup.setAttribute(fill ? "fill-opacity" : "stroke-opacity", String.valueOf(alpha/255.0));
            }
            currentGroup.appendChild(colorGroup);
            for(PathAction action : entry.getValue()){
                Element path = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "path");
                path.setAttribute("d", SVGPath.toSVGPathData(action.path(), ctx));
                colorGroup.appendChild(path);
            }
        }
        if(currentGroup.hasChildNodes()){
            marginTranslation.appendChild(currentGroup);
        }

        root.appendChild(marginTranslation);
        if(this.borderColor != null && this.borderThickness > 0){
            Element rect = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");

            rect.setAttribute("x", String.valueOf(this.borderThickness/2.0));
            rect.setAttribute("y", String.valueOf(this.borderThickness/2.0));
            rect.setAttribute("width", String.valueOf(this.canvasWidth + 2*this.margin - this.borderThickness));
            rect.setAttribute("height", String.valueOf(this.canvasHeight + 2*this.margin - this.borderThickness));
            rect.setAttribute("fill", "none");
            rect.setAttribute("stroke", String.format("#%02x%02x%02x", this.borderColor.getRed(), this.borderColor.getGreen(), this.borderColor.getBlue()));
            rect.setAttribute("stroke-width", String.valueOf(this.borderThickness));

            if(this.borderColor.getAlpha() < 255){
                rect.setAttribute("stroke-opacity", String.valueOf(this.borderColor.getAlpha()/255.0));
            }

            root.appendChild(rect);
        }


        return document;
    }

    public byte[] toBytes(boolean embed){
        Document document = this.toSVGDocument(embed);

        if(document == null){
            return null;
        }

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(this.toSVGDocument(embed)), new StreamResult(baos));

            return baos.toByteArray();
        }catch(Exception e){
            System.out.println("Error writing document: " + e.getMessage());
            return null;
        }
    }

    public boolean saveAsSVG(String filePath, boolean embed){
        try{
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(this.toSVGDocument(embed)), new StreamResult(new File(filePath)));
        }catch(Exception e){
            System.out.println("Error creating file \"" + filePath + "\": " + e.getMessage());
            return false;
        }

        return true;
    }
}