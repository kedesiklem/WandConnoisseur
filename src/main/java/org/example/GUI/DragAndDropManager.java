package org.example.GUI;

import org.example.GUI.component.AdaptiveComponent;
import org.example.GUI.component.RootComponent;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public final class DragAndDropManager{
    private final RootComponent root;
    private final Window rootWindow;
    private final AWTEventListener mouseListener;
    private AdaptiveComponent draggedComponent;
    private AdaptiveComponent hoveredComponent;
    private AdaptiveComponent clickedComponent;
    private boolean isDragging = false;

    public DragAndDropManager(RootComponent root){
        this.root = root;
        this.rootWindow = SwingUtilities.getWindowAncestor(this.root.getRootComponent());

        this.mouseListener = event -> {
            if(!(event instanceof MouseEvent mouseEvent)){
                return;
            }
            switch(mouseEvent.getID()){
                case MouseEvent.MOUSE_PRESSED -> {
                    if(mouseEvent.getButton() != MouseEvent.BUTTON1){
                        return;
                    }
                    startClick(mouseEvent);
                    startDrag(mouseEvent);
                }
                case MouseEvent.MOUSE_RELEASED -> {
                    if(mouseEvent.getButton() != MouseEvent.BUTTON1){
                        return;
                    }
                    this.isDragging = false;
                    endClick(mouseEvent);
                    stopStartDrag();
                    finishDrag(mouseEvent);
                    this.clickedComponent = null;
                    this.draggedComponent = null;
                }
                case MouseEvent.MOUSE_DRAGGED, MouseEvent.MOUSE_MOVED -> {
                    if(this.isDragging){
                        updateDragHover(mouseEvent);
                    }
                }
            }
        };

        Toolkit.getDefaultToolkit().addAWTEventListener(this.mouseListener, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        this.rootWindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowActivated(WindowEvent e){
                Toolkit.getDefaultToolkit().addAWTEventListener(DragAndDropManager.this.mouseListener, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
            }

            @Override
            public void windowDeactivated(WindowEvent e){
                Toolkit.getDefaultToolkit().removeAWTEventListener(DragAndDropManager.this.mouseListener);
            }
        });
    }

    private AdaptiveComponent eventToClickableComponent(MouseEvent event){
        Point point = SwingUtilities.convertPoint(event.getComponent(), event.getPoint(), this.root.getRootComponent());

        return this.root.findClickableComponentAt(point.x, point.y);
    }

    private AdaptiveComponent eventToDraggableComponent(MouseEvent event){
        Point point = SwingUtilities.convertPoint(event.getComponent(), event.getPoint(), this.root.getRootComponent());

        return this.root.findDraggableComponentAt(point.x, point.y);
    }

    private AdaptiveComponent eventToDroppableComponent(MouseEvent event){
        Point point = SwingUtilities.convertPoint(event.getComponent(), event.getPoint(), this.root.getRootComponent());

        return this.root.findDroppableComponentAt(point.x, point.y);
    }

    private void startClick(MouseEvent event){
        AdaptiveComponent component = eventToClickableComponent(event);

        if(component == null){
            return;
        }

        this.clickedComponent = component;
    }

    private void endClick(MouseEvent event){
        AdaptiveComponent component = eventToClickableComponent(event);

        if(component == null){
            return;
        }

        if(component == this.clickedComponent){
            component.onClick();
        }
    }

    private void startDrag(MouseEvent event){
        AdaptiveComponent component = eventToDraggableComponent(event);

        if(component == null){
            return;
        }

        this.isDragging = true;
        this.draggedComponent = component;
        component.startOnDrag();
    }

    private void stopStartDrag(){
        if(this.draggedComponent == null){
            return;
        }

        this.draggedComponent.stopOnDrag();
    }

    private void finishDrag(MouseEvent event){
        if(this.draggedComponent == null){
            return;
        }

        AdaptiveComponent fromComponent = this.draggedComponent;
        AdaptiveComponent toComponent = eventToDroppableComponent(event);

        this.draggedComponent = null;

        if(toComponent == fromComponent){
            return;
        }

        fromComponent.onDrop(fromComponent, toComponent);
    }

    private void updateDragHover(MouseEvent event){
        AdaptiveComponent newHoveredComponent = eventToDroppableComponent(event);

        if(this.hoveredComponent == newHoveredComponent){
            return;
        }

        if(this.hoveredComponent != null){
            this.hoveredComponent.onDragExit(this.draggedComponent);
        }

        if(newHoveredComponent == this.draggedComponent){
            return;
        }
        this.hoveredComponent = newHoveredComponent;

        if(this.hoveredComponent != null){
            this.hoveredComponent.onDragEnter(this.draggedComponent);
        }
    }
}