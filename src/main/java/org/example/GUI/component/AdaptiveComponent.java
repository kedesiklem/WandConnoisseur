package org.example.GUI.component;

import org.example.GUI.Fraction;
import org.example.GUI.LayoutConstraints;
import org.example.main.Global;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class AdaptiveComponent{
    protected final LayoutConstraints layoutConstraints;
    protected int scaleFactor = Global.getScaleFactor();
    protected AdaptiveComponent parent;
    protected final ArrayList<AdaptiveComponent> children = new ArrayList<>();
    protected boolean layoutUpdated = false;
    protected boolean childUpdated = false;
    protected JComponent component;
    protected boolean draggable = false;
    protected boolean droppable = false;
    protected boolean clickable = false;

    public AdaptiveComponent(JComponent component, LayoutConstraints layoutConstraints){
        this.component = component;
        this.parent = null;
        this.layoutConstraints = layoutConstraints;
        if(this.component != null){
            this.component.setLayout(null);
        }
    }

    public AdaptiveComponent(LayoutConstraints layoutConstraints){
        this(null, layoutConstraints);
    }

    public AdaptiveComponent(JComponent component, Fraction x, Fraction y, Fraction width, Fraction height, int minimumWidth, int minimumHeight, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this(component, new LayoutConstraints(x, y, width, height, minimumWidth, minimumHeight, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate));
    }

    public AdaptiveComponent(Fraction x, Fraction y, Fraction width, Fraction height, int minimumWidth, int minimumHeight, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this(null, new LayoutConstraints(x, y, width, height, minimumWidth, minimumHeight, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate));
    }

    public AdaptiveComponent(JComponent component, Fraction x, Fraction y, Fraction width, Fraction height, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this(component, new LayoutConstraints(x, y, width, height, 0, 0, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate));
    }

    public AdaptiveComponent(Fraction x, Fraction y, Fraction width, Fraction height, Fraction xGrowthRate, Fraction yGrowthRate, Fraction widthGrowthRate, Fraction heightGrowthRate){
        this(null, new LayoutConstraints(x, y, width, height, 0, 0, xGrowthRate, yGrowthRate, widthGrowthRate, heightGrowthRate));
    }

    public AdaptiveComponent(JComponent component, Fraction x, Fraction y, Fraction width, Fraction height){
        this(component, new LayoutConstraints(x, y, width, height, 0, 0, new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(0)));
    }

    public AdaptiveComponent(Fraction x, Fraction y, Fraction width, Fraction height){
        this(null, new LayoutConstraints(x, y, width, height, 0, 0, new Fraction(0), new Fraction(0), new Fraction(0), new Fraction(0)));
    }

    // getters
    private AdaptiveComponent getFirstParentWithComponent(){
        if(this.component != null){
            return this;
        }
        if(this.parent == null){
            return null;
        }
        return this.parent.getFirstParentWithComponent();
    }

    public LayoutConstraints getLayoutConstraints(){
        return this.layoutConstraints;
    }
    public int getScaleFactor(){
        return this.scaleFactor;
    }

    public JComponent getComponent(){
        return this.component;
    }

    public boolean isDraggable(){
        return draggable;
    }

    public boolean isDroppable(){
        return droppable;
    }

    public boolean isClickable(){
        return clickable;
    }

    // setters
    public void setScaleFactor(int scaleFactor){
        scaleFactor = Math.max(scaleFactor, 1);

        if(this.scaleFactor == scaleFactor){
            return;
        }

        this.scaleFactor = scaleFactor;
        this.updateScaleFactor();
    }

    public void setBackground(Color color){
        if(this.component == null){
            return;
        }
        this.component.setBackground(color);
    }

    public void setBorder(Border border){
        if(this.component == null){
            return;
        }
        this.component.setBorder(border);
    }

    protected void updateScaleFactor(){}

    public void setDraggable(boolean draggable){
        this.draggable = draggable;
    }

    public void setDroppable(boolean droppable){
        this.droppable = droppable;
    }

    public void setDragAndDrop(boolean enabled){
        this.draggable = enabled;
        this.droppable = enabled;
    }

    public void setClickable(boolean clickable){
        this.clickable = clickable;
    }

    public AdaptiveComponent findClickableComponentAt(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        if(this.layoutConstraints.isInside(x, y)){
            AdaptiveComponent component = this.findClickableComponentAtRec(x, y);
            if(component != null && component.isClickable()){
                return component;
            }
            if(this.component != null && this.isClickable()){
                return this;
            }
        }

        return null;
    }

    private AdaptiveComponent findClickableComponentAtRec(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        for(int i=this.children.size() - 1; i >= 0; i--){
            AdaptiveComponent child = children.get(i);
            if(child.layoutConstraints.isInside(x, y)){
                int newX = x - child.layoutConstraints.getXInt();
                int newY = y - child.layoutConstraints.getYInt();

                if(child.component instanceof JScrollPane scrollPane){
                    Point viewPos = scrollPane.getViewport().getViewPosition();
                    newX += viewPos.x;
                    newY += viewPos.y;
                }

                AdaptiveComponent component = child.findClickableComponentAtRec(newX, newY);
                if(component != null && component.isClickable()){
                    return component;
                }
                if(child.component != null && child.isClickable()){
                    return child;
                }
            }
        }
        if(this.component != null && this.isClickable()){
            return this;
        }
        return null;
    }

    public AdaptiveComponent findDraggableComponentAt(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        if(this.layoutConstraints.isInside(x, y)){
            AdaptiveComponent component = this.findDraggableComponentAtRec(x, y);
            if(component != null && component.isDraggable()){
                return component;
            }
            if(this.component != null && this.isDraggable()){
                return this;
            }
        }

        return null;
    }

    private AdaptiveComponent findDraggableComponentAtRec(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        for(int i=this.children.size() - 1; i >= 0; i--){
            AdaptiveComponent child = children.get(i);
            if(child.layoutConstraints.isInside(x, y)){
                int newX = x - child.layoutConstraints.getXInt();
                int newY = y - child.layoutConstraints.getYInt();

                if(child.component instanceof JScrollPane scrollPane){
                    Point viewPos = scrollPane.getViewport().getViewPosition();
                    newX += viewPos.x;
                    newY += viewPos.y;
                }

                AdaptiveComponent component = child.findDraggableComponentAtRec(newX, newY);
                if(component != null && component.isDraggable()){
                    return component;
                }
                if(child.component != null && child.isDraggable()){
                    return child;
                }
            }
        }
        if(this.component != null && this.isDraggable()){
            return this;
        }
        return null;
    }

    public AdaptiveComponent findDroppableComponentAt(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        if(this.layoutConstraints.isInside(x, y)){
            AdaptiveComponent component = this.findDroppableComponentAtRec(x, y);
            if(component != null && component.isDroppable()){
                return component;
            }
            if(this.component != null && this.isDroppable()){
                return this;
            }
        }

        return null;
    }

    private AdaptiveComponent findDroppableComponentAtRec(int x, int y){
        if(this.component != null && !this.component.isVisible()){
            return null;
        }
        for(int i=this.children.size() - 1; i >= 0; i--){
            AdaptiveComponent child = children.get(i);
            if(child.layoutConstraints.isInside(x, y)){
                int newX = x - child.layoutConstraints.getXInt();
                int newY = y - child.layoutConstraints.getYInt();

                if(child.component instanceof JScrollPane scrollPane){
                    Point viewPos = scrollPane.getViewport().getViewPosition();
                    newX += viewPos.x;
                    newY += viewPos.y;
                }

                AdaptiveComponent component = child.findDroppableComponentAtRec(newX, newY);

                if(component != null && component.isDroppable()){
                    return component;
                }
                if(child.component != null && child.isDroppable()){
                    return child;
                }
            }
        }
        if(this.component != null && this.isDroppable()){
            return this;
        }

        return null;
    }

    public void addPanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = this;
        this.children.add(adaptiveComponent);

        if(adaptiveComponent.component != null){
            AdaptiveComponent currentAComponent = this.getFirstParentWithComponent();
            if(currentAComponent != null){
                currentAComponent.component.add(adaptiveComponent.component);
                currentAComponent.childUpdated = true;
            }
        }
    }

    public void addPanel(AdaptiveComponent[] adaptiveComponents){
        AdaptiveComponent currentAComponent = this.getFirstParentWithComponent();

        for(AdaptiveComponent adaptiveComponent : adaptiveComponents){
            adaptiveComponent.parent = this;
            this.children.add(adaptiveComponent);
            if(currentAComponent != null && adaptiveComponent.component != null){
                currentAComponent.component.add(adaptiveComponent.component);
                currentAComponent.childUpdated = true;
            }
        }
    }

    public void removePanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = null;
        this.children.remove(adaptiveComponent);

        if(adaptiveComponent.component != null){
            AdaptiveComponent currentAComponent = this.getFirstParentWithComponent();
            if(currentAComponent != null){
                currentAComponent.component.remove(adaptiveComponent.component);
                currentAComponent.childUpdated = true;
            }
        }
    }

    public void removePanel(AdaptiveComponent[] adaptiveComponents){
        AdaptiveComponent currentAComponent = this.getFirstParentWithComponent();

        for(AdaptiveComponent adaptiveComponent : adaptiveComponents){
            adaptiveComponent.parent = null;
            this.children.remove(adaptiveComponent);
            if(currentAComponent != null && adaptiveComponent.component != null){
                currentAComponent.component.remove(adaptiveComponent.component);
                currentAComponent.childUpdated = true;
            }
        }
    }

    public void updateLayout(){
        this.updateLayout(false);
    }

    public void updateLayout(boolean forceUpdate){
        if(!forceUpdate && this.component != null && !this.component.isVisible()){
            return;
        }
        if(this.layoutConstraints.update(this.parent == null ? null : this.parent.layoutConstraints) || forceUpdate){
            this.layoutUpdated = true;
            for(AdaptiveComponent child : this.children){
                child.updateLayout(forceUpdate);
            }
        }
    }

    public void updateBounds(){
        if(this.layoutUpdated){
            this.layoutUpdated = false;
            if(this.component != null){
                this.component.setBounds(this.layoutConstraints.getXInt(), this.layoutConstraints.getYInt(), this.layoutConstraints.getWidthInt(), this.layoutConstraints.getHeightInt());
                this.component.repaint();
            }
            for(AdaptiveComponent child : this.children){
                child.updateBounds();
            }
        }
        if(this.childUpdated && this.component != null){
            this.childUpdated = false;
            this.component.revalidate();
            this.component.repaint();
        }
    }

    // to override
    public void onDrop(AdaptiveComponent from, AdaptiveComponent to){}
    public void startOnDrag(){}
    public void stopOnDrag(){}
    public void onDragEnter(AdaptiveComponent draggedComponent){}
    public void onDragExit(AdaptiveComponent draggedComponent){}
    public void onClick(){}
}