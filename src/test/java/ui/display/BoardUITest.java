package ui.display;

import board.Board;
import ui.display.BoardUI;

import javax.swing.*;
import java.time.Duration;

public class BoardUITest {

    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(new int[][]{
                {0, 0, 0, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0},
                {0, 0, 0, 0}
        });
        BoardUI boardUI = new BoardUI();

        JFrame window = new JFrame();
        window.setSize(640, 480);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.add(boardUI);
        window.setVisible(true);

        boardUI.setBoard(board);

        Thread.sleep(5000);
        boardUI.setUpdateInterval(Duration.ofMillis(2000)); // 允许动态变更间隔
    }
}