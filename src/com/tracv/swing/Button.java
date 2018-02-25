package com.tracv.swing;

import com.tracv.util.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
    Customized version of JButton to keep with design.
 */
public class Button extends JButton {

    public Button() {
        super();
        setLook();
    }

    public Button(String s) {
        super(s);
        setLook();
    }

    public Button(Image sprite) {
        super(new ImageIcon(sprite));
        setLook();
    }

    private void setLook() {
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);

        this.setFont(Constants.DEFAULT_FONT);
        this.addMouseListener(new MyMouseListener());

        this.setFocusPainted(false);

        //TODO make proper selected border.
        this.setBorder(new LineBorder(Color.BLACK, 1));
    }

    @Override
    protected void paintComponent(Graphics g){
        if(this.isSelected()){
            g.setColor(Constants.BUTTON_SELECTED_COLOR);
        }else{
            g.setColor(Constants.BUTTON_UNSELECTED_COLOR);
        }

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        super.paintComponent(g);

    }

    private class MyMouseListener implements MouseListener {

        private void refresh(){
            SwingUtilities.invokeLater(()->Button.this.validate());
        }
        private void paintBorder(boolean b){
            if(Button.this.isBorderPainted() != b) {
                Button.this.setBorderPainted(b);
                refresh();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            paintBorder(false);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Button.this.setSelected(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Button.this.setSelected(false);
            paintBorder(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           paintBorder(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            paintBorder(false);
        }
    }
}

