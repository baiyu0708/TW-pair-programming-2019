package ui.board;

import util.ui.VFlowLayout;

import javax.swing.*;

class EditModePanel extends JPanel {

    static final int FLIP = 0;
    static final int PENCIL = 1;
    static final int ERASER = 2;

    private final JRadioButton flip = new JRadioButton("转换");
    private final JRadioButton pencil = new JRadioButton("铅笔");
    private final JRadioButton eraser = new JRadioButton("橡皮");

    EditModePanel() {
        flip.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(flip);
        group.add(pencil);
        group.add(eraser);

        setLayout(new VFlowLayout());
        add(flip);
        add(pencil);
        add(eraser);
    }

    int mode() {
        if (flip.isSelected()) {
            return FLIP;
        }

        if (pencil.isSelected()) {
            return PENCIL;
        }

        if (eraser.isSelected()) {
            return ERASER;
        }

        throw new IllegalStateException();
    }
}
