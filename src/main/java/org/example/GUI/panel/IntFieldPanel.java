package org.example.GUI.panel;

import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

class IntFilter extends DocumentFilter{
    private static final Pattern PATTERN = Pattern.compile("^[-+]?[0-9]*$");
    private static final Pattern PATTERN_UNSIGNED = Pattern.compile("^[0-9]*$");
    private final boolean unsigned;

    public IntFilter(boolean unsigned){
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

public class IntFieldPanel extends JTextField{
    private int value;
    private final int initialValue;

    public IntFieldPanel(int value, boolean unsigned){
        super(Integer.toString(value));
        this.value = value;
        this.initialValue = value;

        ((AbstractDocument) getDocument()).setDocumentFilter(new IntFilter(unsigned));
        this.getDocument().addDocumentListener(new DocumentListener(){
            private void update(){
                String text = getText();

                if(text.equals("")){
                    IntFieldPanel.this.value = 0;
                    return;
                }

                try{IntFieldPanel.this.value = Integer.parseInt(text);}catch(NumberFormatException ignored){}
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

    public IntFieldPanel(boolean unsigned){
        this(0, unsigned);
    }

    public IntFieldPanel(){
        this(0, false);
    }

    public int getValue(){
        return this.value;
    }

    public int getInitialValue(){
        return this.initialValue;
    }

    public void setValue(int value){
        this.value = value;
        this.setText(Integer.toString(this.value));
    }
}