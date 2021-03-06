import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyStyleSelectionListener implements ListSelectionListener, ActionListener {

    private final DPGenGUI genGUI;
    private boolean showedConfirmDialogOnce;

    public MyStyleSelectionListener(DPGenGUI genGUI) {
        this.genGUI = genGUI;
        showedConfirmDialogOnce = false;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int index = genGUI.styleSelectionList.getSelectedIndex();
            if (index < 0) return;
            int sure = JOptionPane.OK_OPTION;
            if (genGUI.materialBlocks.size() > 0 && !showedConfirmDialogOnce) {
                sure = JOptionPane.showConfirmDialog(genGUI, "Выбранный стиль подействует на все элементы поста. Продолжить?", "Выбор стиля", JOptionPane.OK_CANCEL_OPTION);
                showedConfirmDialogOnce = true;
            }
            if (sure != JOptionPane.OK_OPTION) {
                genGUI.styleSelectionList.clearSelection();
                return;
            }
            String nameOfStyle = genGUI.listOfStyles.get(index);
            genGUI.setStyleByString(nameOfStyle);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        genGUI.setStyleOfSelectedBlockByString(e.getActionCommand());
    }
}
