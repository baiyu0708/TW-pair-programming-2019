package ui.common;

import board.Board;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

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
    @Nullable
    private Consumer<Point> cellClickListener; // Point.x为单元格所在列（从0开始计），Point.y为单元格所在行（从0开始计）

    public BoardDisplayPanel() {
        addMouseListener(new ClickListener());
    }

    public void setCellClickListener(@Nullable Consumer<Point> listener) {
        this.cellClickListener = listener;
    }

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

        g.drawImage(image, displayArea().x, displayArea().y, displayArea().width, displayArea().height, null);
        g.setColor(GRID_COLOR);
        drawGrid(g, displayArea());
    }

    @SuppressWarnings("ConstantConditions")
    private Rectangle displayArea() {
        assert image != null;
        return CenteredDisplay.getDisplayArea(
                new Dimension(image.getWidth(), image.getHeight()),
                new Dimension(getWidth(), getHeight())
        );
    }

    @SuppressWarnings("ConstantConditions")
    private void drawGrid(Graphics g, Rectangle displayArea) {
        assert image != null;

        /* 绘制垂直线*/
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

    private class ClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void mousePressed(MouseEvent e) {
            if (image == null || cellClickListener == null) {
                return;
            }

            if (!displayArea().contains(e.getPoint())) {
                return;
            }

            int cellX = ((int) e.getPoint().getX() - displayArea().x) * image.getWidth() / displayArea().width;
            int cellY = ((int) e.getPoint().getY() - displayArea().y) * image.getHeight() / displayArea().height;
            cellClickListener.accept(new Point(cellX, cellY));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
