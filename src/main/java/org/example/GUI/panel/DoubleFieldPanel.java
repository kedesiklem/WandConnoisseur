package org.example.GUI.panel;

import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

class DoubleFilter extends DocumentFilter{
    private static final Pattern PATTERN = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]*$");
    private static final Pattern PATTERN_UNSIGNED = Pattern.compile("^[0-9]*\\.?[0-9]*$$");
    private final boolean unsigned;

    public DoubleFilter(boolean unsigned){
        this.unsigned = unsigned;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException{
        replace(fb, offset, 0, text, attrs);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException{
        Document document = fb.getDocument();
        String current = document.getText(0, document.getLength());
        String replacement = text == null ? "" : text;
        String result = current.substring(0, offset) + replacement + current.substring(offset + length);

        if(this.unsigned){
            if(PATTERN_UNSIGNED.matcher(result).matches()){
                fb.replace(offset, length, text, attrs);
            }
        }else{
            if(PATTERN.matcher(result).matches()){
                fb.replace(offset, length, text, attrs);
            }
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
        replace(fb, offset, length, "", null);
    }
}

public class DoubleFieldPanel extends JTextField{
    private double value;
    private final double initialValue;

    public DoubleFieldPanel(double value, boolean unsigned){
        super(Double.toString(value));
        this.value = value;
        this.initialValue = value;

        ((AbstractDocument) getDocument()).setDocumentFilter(new DoubleFilter(unsigned));
        this.getDocument().addDocumentListener(new DocumentListener(){
            private void update(){
                String text = getText();

                if(text.equals("")){
                    DoubleFieldPanel.this.value = 0.0;
                    return;
                }
                try{DoubleFieldPanel.this.value = Double.parseDouble(text);}catch(NumberFormatException ignored){}
            }

            @Override
            public void insertUpdate(DocumentEvent e){
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                update();
            }
        });
    }

    public DoubleFieldPanel(boolean unsigned){
        this(0.0, unsigned);
    }

    public DoubleFieldPanel(){
        this(0.0, false);
    }

    public double getValue(){
        return this.value;
    }

    public double getInitialValue(){
        return this.initialValue;
    }

    public void setValue(double value){
        this.value = value;
        this.setText(Double.toString(this.value));
    }
}