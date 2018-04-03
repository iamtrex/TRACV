package com.tracv.util;

import com.tracv.swing.TextArea;
import com.tracv.swing.ScrollPane;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Logger extends JFrame {



    private static Logger logger;

    private List<LogMessage> logs;



    public void log(String message, LoggerLevel level){
        LogMessage logMsg = new LogMessage(message, level);
        logs.add(logMsg);
        if(this.isVisible()){
            logWindow.append(logMsg.getText() + "\n");
            scroll.scrollDown();
            this.validate();
        }
    }

    private TextArea logWindow;
    private ScrollPane scroll;

    private Logger(){
        logs = new ArrayList<>();

        logWindow = new TextArea();
        scroll = new ScrollPane();
        scroll.setViewportView(logWindow);


        this.setContentPane(scroll);

        this.setSize(300, 600);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
    }

    public void showLogWindow(){
        this.setVisible(true);
    }

    private class LogMessage{
        private String message;
        private LoggerLevel level;

        public LogMessage(String message, LoggerLevel level){
            this.message = message;
            this.level = level;
        }
        public String getText(){
            return level.getName() + "- " + message;

        }
    }

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }
}
