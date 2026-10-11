package org.example.GUI.dialog;

import org.example.GUI.component.AdaptiveComponent;
import org.example.GUI.component.RootComponent;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.*;

public abstract class AdaptiveDialog<T> extends JDialog{
    protected T result;
    protected RootComponent root;
    protected Dimension startDimension = new Dimension(0, 0);

    protected AdaptiveDialog(Window owner, String title){
        super(owner, title, ModalityType.APPLICATION_MODAL);
    }

    protected AdaptiveDialog(Frame owner){
        this(owner, "");
    }

    protected final void close(T result){
        this.result = result;
        this.dispose();
    }

    protected final void close(){
        this.result = null;
        this.dispose();
    }

    protected final T endRun(LinkedList<AdaptiveComponent> componentList){
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                AdaptiveDialog.this.close();
            }
        });
        this.getContentPane().setLayout(null);
        this.getContentPane().setPreferredSize(this.startDimension);
        this.getContentPane().setMinimumSize(this.startDimension);
        this.root = new RootComponent(this);
        for(AdaptiveComponent component : componentList){
            this.root.addPanel(component);
        }

        this.pack();
        this.setLocationRelativeTo(null);
        //this.setMinimumSize(this.getSize());
        this.setMinimumSize(this.startDimension);
        this.root.ready();
        this.setVisible(true);

        return this.result;
    }
}