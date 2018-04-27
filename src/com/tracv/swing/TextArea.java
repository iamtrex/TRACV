package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;

public class TextArea extends JTextArea {

    public TextArea(){
        super();
        this.setEditable(false);

        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFont(Constants.LOGGER_FONT);
    }
}
