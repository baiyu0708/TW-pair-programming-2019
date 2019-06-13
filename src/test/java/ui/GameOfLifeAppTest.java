package ui;

import ui.GameOfLifeApp;
import util.file.CodeDir;

import java.io.IOException;

public class GameOfLifeAppTest {

    public static void main(String[] args) throws IOException {
        CodeDir root = new CodeDir(CodeDir.MAVEN_TEST);

        new GameOfLifeApp(root.child("boards"));
    }
}