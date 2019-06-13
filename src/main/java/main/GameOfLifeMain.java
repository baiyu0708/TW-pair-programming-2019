package main;

import ui.GameOfLifeApp;
import util.ui.UIFonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOfLifeMain {

    public static void main(String[] args) throws IOException {
        UIFonts.setAll(new Font("微软雅黑", Font.PLAIN, 24));
        new GameOfLifeApp(new File("boards"));
    }
}
