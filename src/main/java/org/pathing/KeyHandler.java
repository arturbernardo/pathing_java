package org.pathing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    DemoPanel dp;

    public KeyHandler(DemoPanel dp) {
        this.dp = dp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        System.out.println(code);

        if (code == KeyEvent.VK_ENTER) {
            dp.search();
        }

        if (code == KeyEvent.VK_F1) {
            dp.autoSearch();
        }

        if (code == KeyEvent.VK_ESCAPE) {
            dp.restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
