package org.example.GUI;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

public class HoleBorder implements Border{
    public enum Side{TOP, RIGHT, BOTTOM, LEFT}
    public static class Hole{
        private Side side;
        private int from;
        private int to;

        private Hole(Side side, int from, int to){
            this.side = side;
            this.from = Math.min(from, to);
            this.to = Math.max(from, to);
        }

        // getters
        public Side getSide(){
            return this.side;
        }

        public int getFrom(){
            return this.from;
        }

        public int getTo(){
            return this.to;
        }

        // setters
        public void setSide(Side side){
            this.side = side;
        }

        public void setFrom(int from){
            this.from = from;
        }

        public void setTo(int to){
            this.to = to;
        }

        public void setRange(int from, int to){
            this.from = from;
            this.to = to;
        }
    }

    private final Color color;
    private final int thickness;
    private final List<Hole> holes = new ArrayList<>();

    public HoleBorder(Color color, int thickness){
        this.color = color;
        this.thickness = thickness;
    }

    public Hole addHole(Side side, int from, int to){
        Hole hole = new Hole(side, from, to);
        this.holes.add(hole);
        return hole;
    }

    public void removeHole(Hole hole){
        this.holes.remove(hole);
    }

    public void clearHoles(){
        this.holes.clear();
    }

    public Hole[] getHoles(){
        return this.holes.toArray(new Hole[0]);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
        Graphics2D g2d = (Graphics2D) g.create();
        Rectangle topRectangle = new Rectangle(0, 0, width, this.thickness);
        Rectangle bottomRectangle = new Rectangle(0, height - this.thickness, width, this.thickness);
        Rectangle leftRectangle = new Rectangle(0, 0, this.thickness, height);
        Rectangle rightRectangle = new Rectangle(width - this.thickness, 0, this.thickness, height);

        if(this.holes.size() > 0){
            Color background = c.getBackground();
            if(c instanceof JScrollPane scrollPane){
                Component view = scrollPane.getViewport().getView();
                if(view != null){
                    background = view.getBackground();
                }
            }
            g2d.setColor(background);
            g2d.fill(topRectangle);
            g2d.fill(bottomRectangle);
            g2d.fill(leftRectangle);
            g2d.fill(rightRectangle);
        }

        Area topBorder = new Area(topRectangle);
        Area bottomBorder = new Area(bottomRectangle);
        Area leftBorder = new Area(leftRectangle);
        Area rightBorder = new Area(rightRectangle);

        g2d.setColor(this.color);
        for(Hole hole : this.holes){
            switch(hole.side){
                case TOP -> topBorder.subtract(new Area(new Rectangle(x + hole.from, y, hole.to - hole.from, this.thickness)));
                case BOTTOM -> bottomBorder.subtract(new Area(new Rectangle(x + hole.from, y + height - this.thickness, hole.to - hole.from, this.thickness)));
                case LEFT -> leftBorder.subtract(new Area(new Rectangle(x, y + hole.from, this.thickness, hole.to - hole.from)));
                case RIGHT -> rightBorder.subtract(new Area(new Rectangle(x + width - this.thickness, y + hole.from, this.thickness, hole.to - hole.from)));
            }
        }

        g2d.fill(topBorder);
        g2d.fill(bottomBorder);
        g2d.fill(leftBorder);
        g2d.fill(rightBorder);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c){
        return new Insets(this.thickness, this.thickness, this.thickness, this.thickness);
    }

    @Override
    public boolean isBorderOpaque(){
        return false;
    }
}