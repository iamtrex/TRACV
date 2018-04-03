package com.tracv.util;

import com.tracv.swing.Pane;
import com.tracv.swing.TextArea;
import com.tracv.swing.ScrollPane;
import com.tracv.swing.Label;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Logger extends JFrame {



    private static Logger logger;

    private List<LogMessage> logs;



    public void log(String message, LoggerLevel level){

        LogMessage logMsg = new LogMessage(message, level);
        logs.add(logMsg);
        if(this.isVisible()){
            SwingUtilities.invokeLater(()->{
                logWindow.append(logMsg.getText() + "\n");
                scroll.scrollDown();
                this.validate();
            });
        }
    }

    private TextArea logWindow;
    private ScrollPane scroll;
    private Pane panel;

    private Label delay, loadDelay;

    private Logger(){
        logs = new ArrayList<>();

        logWindow = new TextArea();
        scroll = new ScrollPane();
        scroll.setViewportView(logWindow);

        panel = new Pane();

        delay = new Label("Delay Time " , Label.SMALL);
        loadDelay = new Label("Load Time " , Label.SMALL);

        Comp.add(scroll, panel, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(delay, panel, 0, 1, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(loadDelay, panel, 1, 1, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);



        this.setContentPane(panel);

        this.setSize(300, 600);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
    }

    public void showLogWindow(){
        this.setVisible(true);
    }

    public void updateLoadDelay(long time) {
        loadDelay.setText("Load Time " + time + "ms");
        loadDelay.repaint();
    }

    public void updateDelay(long time) {
        delay.setText("Frame Time " + time + "ms");
        delay.repaint();
    }

    private class LogMessage{
        private String message;
        private LoggerLevel level;

        public LogMessage(String message, LoggerLevel level){
            this.message = message;
            this.level = level;
        }
        //TODO ADD TIME
        public String getText(){
            return "[" + level.getName() + "]" + "- " + message;

        }
    }

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }
}
