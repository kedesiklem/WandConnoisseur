package org.example.GUI.panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BooleanFieldPanel extends JLabel{
    private boolean value;
    private final boolean initialValue;

    public BooleanFieldPanel(boolean value){
        super(Boolean.toString(value));
        this.value = value;
        this.initialValue = value;

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                BooleanFieldPanel.this.value = !BooleanFieldPanel.this.value;
                BooleanFieldPanel.this.setText(Boolean.toString(BooleanFieldPanel.this.value));
            }
        });
    }

    public boolean getValue(){
        return this.value;
    }

    public boolean getInitialValue(){
        return this.initialValue;
    }

    public void setValue(boolean value){
        this.value = value;
        this.setText(Boolean.toString(this.value));
    }
}