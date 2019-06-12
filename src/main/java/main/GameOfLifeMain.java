package main;

import app.GameOfLifeApp;

import java.io.File;
import java.io.IOException;

public class GameOfLifeMain {

    public static void main(String[] args) throws IOException {
        new GameOfLifeApp(new File("boards"));
    }
}
