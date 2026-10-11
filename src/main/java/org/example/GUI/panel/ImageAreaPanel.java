package org.example.GUI.panel;

import org.example.main.Global;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ImageAreaPanel extends JComponent{
    private BufferedImage image;
    private double zoom = 1.0;
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    private double minZoom;
    private double maxZoom = 10.0;
    private Point lastMousePosition;

    public ImageAreaPanel(BufferedImage image){
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
        this.image = image;
        this.updateMinZoom();

        JMenuItem menuSaveImage = new JMenuItem("Save image");
        JMenuItem menuImageClipboard = new JMenuItem("Copy to clipboard");

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(menuSaveImage);
        popupMenu.add(menuImageClipboard);

        menuSaveImage.addActionListener(e -> {
            if(this.image == null){
                return;
            }
            Global.saveBufferedImage(this.image, Global.getPathOutput() + "test.png");
        });
        menuImageClipboard.addActionListener(e -> {
            if(this.image == null){
                return;
            }
            Global.toClipboard(this.image);
        });

        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                if(ImageAreaPanel.this.updateMinZoom()){
                    ImageAreaPanel.this.repaint();
                }
            }
        });

        MouseAdapter mouseHandler = new MouseAdapter(){
            private void showPopup(MouseEvent e){
                if(e.isPopupTrigger()){
                    popupMenu.show(ImageAreaPanel.this, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(MouseEvent e){
                if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2){
                    ImageAreaPanel.this.resetZoom();
                }
            }

            @Override
            public void mousePressed(MouseEvent e){
                if(e.isPopupTrigger()){
                    showPopup(e);
                    return;
                }

                if(SwingUtilities.isLeftMouseButton(e)){
                    ImageAreaPanel.this.lastMousePosition = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                if(e.isPopupTrigger()){
                    showPopup(e);
                    return;
                }

                if(SwingUtilities.isLeftMouseButton(e)){
                    ImageAreaPanel.this.lastMousePosition = null;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e){
                if(ImageAreaPanel.this.lastMousePosition == null){
                    return;
                }

                ImageAreaPanel.this.offsetX += e.getX() - ImageAreaPanel.this.lastMousePosition.x;
                ImageAreaPanel.this.offsetY += e.getY() - ImageAreaPanel.this.lastMousePosition.y;

                ImageAreaPanel.this.lastMousePosition = e.getPoint();
                ImageAreaPanel.this.clampPosition();
                ImageAreaPanel.this.repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                ImageAreaPanel.this.zoomAt(e.getX(), e.getY(), e.getWheelRotation());
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
    }

    // getters
    public BufferedImage getImage(){
        return image;
    }

    // setters
    public void setImage(BufferedImage image){
        this.image = image;
        this.updateMinZoom();
        this.clampPosition();
        this.repaint();
    }

    public void setMaxZoom(double maxZoom){
        this.maxZoom = maxZoom;
        this.zoom = Math.min(this.zoom, maxZoom);
        this.repaint();
    }

    private void zoomAt(int mouseX, int mouseY, int wheelRotation){
        double newZoom = this.zoom*Math.pow(1.1, -wheelRotation);

        newZoom = Math.max(this.minZoom, Math.min(this.maxZoom, newZoom));

        if(newZoom == this.zoom){
            return;
        }

        this.offsetX = mouseX - newZoom*(mouseX - this.offsetX)/this.zoom;
        this.offsetY = mouseY - newZoom*(mouseY - this.offsetY)/this.zoom;
        this.zoom = newZoom;

        this.clampPosition();
        this.repaint();
    }

    private boolean clampPosition(){
        double widthDiff = this.getWidth() - this.image.getWidth()*this.zoom;
        double heightDiff = this.getHeight() - this.image.getHeight()*this.zoom;

        double oldOffsetX = this.offsetX;
        double oldOffsetY = this.offsetY;

        this.offsetX = Math.max(Math.min(this.offsetX, Math.max(widthDiff, 0.0)), Math.min(widthDiff, 0.0));
        this.offsetY = Math.max(Math.min(this.offsetY, Math.max(heightDiff, 0.0)), Math.min(heightDiff, 0.0));

        return oldOffsetX != this.offsetX || oldOffsetY != this.offsetY;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(this.image == null){
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        try{
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(this.getBackground());
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.drawImage(this.image, (int)this.offsetX, (int)this.offsetY, (int)(this.image.getWidth()*this.zoom), (int)(this.image.getHeight()*this.zoom), null);
        }finally{
            g2d.dispose();
        }
    }

    private boolean updateMinZoom(){
        double newMinZoom = Double.MAX_VALUE;
        if(this.getWidth() != 0){
            newMinZoom = Math.min((double)this.getWidth()/this.image.getWidth(), newMinZoom);
        }
        if(this.getHeight() != 0){
            newMinZoom = Math.min((double)this.getHeight()/this.image.getHeight(), newMinZoom);
        }
        newMinZoom = Math.min(newMinZoom, 1.0);

        if(this.minZoom != newMinZoom){
            this.minZoom = newMinZoom;
            double oldZoom = this.zoom;
            this.zoom = Math.max(Math.min(this.zoom, this.maxZoom), this.minZoom);
            return this.clampPosition() || oldZoom != this.zoom;
        }

        return false;
    }

    public void resetZoom(){
        double oldZoom = this.zoom;

        this.zoom = Math.min(Math.max(1.0, this.minZoom), this.maxZoom);
        if(this.zoom != oldZoom){
            this.clampPosition();
            this.repaint();
        }
    }
}