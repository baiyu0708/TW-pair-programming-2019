package ui;

import board.Board;

import javax.swing.*;

public class BoardDisplayPanelTest {

    public static void main(String[] args) {
        Board board = new Board(new int[][]{
                {0, 0, 1, 0},
                {0, 1, 1, 0},
                {1, 0, 1, 1}
        });
        BoardDisplayPanel boardDisplayPanel = new BoardDisplayPanel();

        JFrame window = new JFrame();
        window.setSize(640, 480);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.add(boardDisplayPanel);
        window.setVisible(true);

        boardDisplayPanel.display(board);
    }
}