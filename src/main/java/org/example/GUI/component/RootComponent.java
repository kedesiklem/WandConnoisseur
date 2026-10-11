package org.example.GUI.component;

import org.example.GUI.Fraction;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class RootComponent extends AdaptiveComponent{
    private final RootPaneContainer rootContainer;
    private final Component contentPane;
    private boolean isReady = false;

    public RootComponent(RootPaneContainer rootContainer){
        super(new Fraction(0), new Fraction(0), new Fraction((int)rootContainer.getContentPane().getPreferredSize().getWidth()), new Fraction((int)rootContainer.getContentPane().getPreferredSize().getHeight()), 0, 0, new Fraction(0), new Fraction(0), new Fraction(1), new Fraction(1));
        this.rootContainer = rootContainer;
        this.contentPane = this.rootContainer.getContentPane();
        this.rootContainer.getContentPane().addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                RootComponent.this.updateLayout();
                RootComponent.this.updateBounds();
            }
        });
    }

    // getters
    public RootPaneContainer getRootContainer(){
        return this.rootContainer;
    }

    public Component getRootComponent(){
        return this.contentPane;
    }

    public void ready(){
        this.isReady = true;
        this.updateLayout(true);
        this.updateBounds();
    }

    @Override
    public void addPanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = this;
        this.children.add(adaptiveComponent);
        if(adaptiveComponent.component != null){
            this.rootContainer.getContentPane().add(adaptiveComponent.component);
            this.childUpdated = true;
        }
    }

    @Override
    public void removePanel(AdaptiveComponent adaptiveComponent){
        adaptiveComponent.parent = null;
        this.children.remove(adaptiveComponent);
        if(adaptiveComponent.component != null){
            this.rootContainer.getContentPane().remove(adaptiveComponent.component);
            this.childUpdated = true;
        }
    }

    @Override
    public void updateLayout(boolean forceUpdate){
        if(!this.isReady){
            return;
        }

        if(this.layoutConstraints.update(new Fraction(this.rootContainer.getContentPane().getWidth()), new Fraction(this.rootContainer.getContentPane().getHeight()), this.layoutConstraints.getStartWidth(), this.layoutConstraints.getStartHeight()) || forceUpdate){
            this.layoutUpdated = true;
            for(AdaptiveComponent child : this.children){
                child.updateLayout(forceUpdate);
            }
        }
    }
}