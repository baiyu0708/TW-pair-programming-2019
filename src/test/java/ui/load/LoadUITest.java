package ui.load;

import util.file.CodeDir;

import javax.swing.*;
import java.io.IOException;

public class LoadUITest {

    public static void main(String[] args) throws IOException {
        CodeDir root = new CodeDir(CodeDir.MAVEN_TEST);
        LoadUI loadUI = new LoadUI(root.child("boards"));

        JFrame window = new JFrame();
        window.setSize(640, 480);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.add(loadUI);
        window.setVisible(true);
    }
}
