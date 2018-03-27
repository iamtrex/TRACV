package com.tracv.util;


import com.tracv.directional.PointToPointDistance;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import javax.swing.*;
import java.awt.*;

public class MouseHooker {
    private JFrame window;
    private Robot mouseRobot;
    private GameBinder gb;

    private boolean active = false;
    private boolean registered = false;


    public MouseHooker(JFrame frame) {
        window = frame;

        try {

            mouseRobot = new Robot();
            gb = new GameBinder();

            GlobalScreen.registerNativeHook();
            //GlobalScreen.getInstance().addNativeMouseMotionListener(gb);

            System.out.println("Reg successfully");
            active = true;
            registered = true;

        } catch (NativeHookException e) {
            System.out.println("Cannot properly register hook");
            e.printStackTrace();
        } catch (AWTException e) {
            System.out.println("Cannot properly register hook");
            e.printStackTrace();
        }
    }

    public void setActive(boolean active) {
        boolean wasActive = this.active;
        this.active = active;

        if(registered){
            if(active){
                if(!wasActive) {
                    System.out.println("REG!!!!");
                    GlobalScreen.getInstance().addNativeMouseMotionListener(gb);
                }
            }else{
                if(wasActive) {
                    GlobalScreen.getInstance().removeNativeMouseMotionListener(gb);
                }
            }
        }
    }

    private class GameBinder implements NativeMouseMotionListener {

        private void handle(NativeMouseEvent e) {
            System.out.println("Camer here");
            if (!active) {
                System.out.println("bugged");
                return;
            }
            Point p = window.getLocationOnScreen();

            Rectangle r = new Rectangle((int) p.getX(), (int) p.getY(),
                    window.getWidth(), window.getHeight());

            Point mouse = e.getPoint();
            if (PointToPointDistance.isPointInRegion(mouse, r)) {
                return;
            }

            double x = mouse.getX();
            double y = mouse.getY();

            //move it back to the edge...
            if (mouse.getX() <= r.getX()) {
                x = r.getX();
            } else if (mouse.getX() >= r.getX() + r.getWidth()) {
                x = r.getX() + r.getWidth();
            }

            if (mouse.getY() <= r.getY()) {
                y = r.getY();
            } else if (mouse.getY() >= r.getY() + r.getHeight()) {
                y = r.getY() + r.getHeight();
            }

            mouseRobot.mouseMove((int) x, (int) y);

        }

        @Override
        public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
            handle(nativeMouseEvent);
        }

        @Override
        public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
            handle(nativeMouseEvent);
        }
    }

}
