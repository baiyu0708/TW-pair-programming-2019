package ui;

import board.Board;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * 居中显示{@link Board}的状态
 */
public class BoardDisplayPanel extends JPanel {

    private static final Color DEAD_CELL_COLOR = Color.white;
    private static final Color LIVING_CELL_COLOR = Color.black;

    @Nullable
    private volatile BufferedImage image;

    public void display(@Nullable Board board) {
        this.image = board != null ? createImage(board) : null;
        repaint();
    }

    private BufferedImage createImage(Board board) {
        BufferedImage image = new BufferedImage(board.columnCount(), board.rowCount(), TYPE_INT_RGB);
        for (int row = 0; row < board.rowCount(); row++) {
            for (int column = 0; column < board.columnCount(); column++) {
                Color color = board.isLiving(row, column) ? LIVING_CELL_COLOR : DEAD_CELL_COLOR;
                image.setRGB(column, row, color.getRGB());
            }
        }
        return image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (image == null) {
            return;
        }

        @SuppressWarnings("ConstantConditions")
        Rectangle displayArea = CenteredDisplay.getDisplayArea(
                new Dimension(image.getWidth(), image.getHeight()),
                new Dimension(getWidth(), getHeight())
        );
        g.drawImage(image, displayArea.x, displayArea.y, displayArea.width, displayArea.height, null);
    }
}
