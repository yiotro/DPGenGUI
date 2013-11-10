import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MyImageMouseListener implements MouseListener {

    private final DPGenGUI genGUI;

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
