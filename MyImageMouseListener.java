import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyImageMouseListener implements MouseListener {

    DPGenGUI genGUI;

    MyImageMouseListener(DPGenGUI genGUI) {
        this.genGUI = genGUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        genGUI.mousePressedOnImage(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
