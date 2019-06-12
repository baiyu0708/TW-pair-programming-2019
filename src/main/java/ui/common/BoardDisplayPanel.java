package ui.common;

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
    private static final Color GRID_COLOR = Color.GRAY;

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
        g.setColor(GRID_COLOR);
        drawGrid(g, displayArea);
    }

    @SuppressWarnings("ConstantConditions")
    private void drawGrid(Graphics g, Rectangle displayArea) {
        assert image != null;

        for (int column = 0; column < image.getWidth(); column++) {
            drawVerticalLine(g, displayArea, displayArea.x + displayArea.width * column / image.getWidth());
        }
        drawVerticalLine(g, displayArea, displayArea.x + displayArea.width - 1);

        /* 绘制水平线*/
        for (int row = 0; row < image.getHeight(); row++) {
            drawHorizontalLine(g, displayArea, displayArea.y + displayArea.height * row / image.getHeight());
        }
        drawHorizontalLine(g, displayArea, displayArea.y + displayArea.height - 1);
    }

    private void drawHorizontalLine(Graphics g, Rectangle displayArea, int y) {
        g.drawLine(displayArea.x, y, displayArea.x + displayArea.width, y);
    }

    private void drawVerticalLine(Graphics g, Rectangle displayArea, int x) {
        g.drawLine(x, displayArea.y, x, displayArea.y + displayArea.height);
    }
}
