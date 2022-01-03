package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {
    public boolean clicked = false;
    public Point mousePos = new Point(0, 0);

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        clicked = true;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mousePos = new Point(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}
    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {}
}
