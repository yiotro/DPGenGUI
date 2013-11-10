import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyListSelectionListener implements ListSelectionListener {

    DPGenGUI genGUI;

    MyListSelectionListener(DPGenGUI genGUI) {
        this.genGUI = genGUI;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int index = genGUI.elementList.getSelectedIndex();
            if (index < 0) return;
            genGUI.selectedBlock = genGUI.materialBlocks.get(index);
            genGUI.selectedIndex = index;
            genGUI.updateResultSelectionWithoutComposing();
            //switching panel
            if (genGUI.selectedBlock instanceof PictureBlock) {
                genGUI.mainCardLayout.show(genGUI.mainCardPanel, "picture");
                genGUI.currentImage = ((PictureBlock) genGUI.selectedBlock).picture;
                genGUI.imageOnEditLabel.setIcon(new ImageIcon(DPGenGUI.getResizedImageByWidth(genGUI.currentImage, 300)));
                genGUI.imageTextField.setText(((PictureBlock) genGUI.selectedBlock).description);
                genGUI.imageTextField.requestFocus();
            } else if (genGUI.selectedBlock instanceof Article) {
                genGUI.mainCardLayout.show(genGUI.mainCardPanel, "article");
                genGUI.articleArea.setText(DPGenGUI.getStringFromStringVector(((Article)genGUI.selectedBlock).article, true));
                genGUI.articleArea.requestFocus();
            } else if (genGUI.selectedBlock instanceof Title) {
                genGUI.mainCardLayout.show(genGUI.mainCardPanel, "title");
                genGUI.titleField.setText(((Title)genGUI.selectedBlock).header);
                genGUI.titleField.requestFocus();
            }
        }
    }
}
