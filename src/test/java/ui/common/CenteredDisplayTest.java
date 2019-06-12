package ui.common;

import org.junit.Test;
import ui.common.CenteredDisplay;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class CenteredDisplayTest {

    @Test
    public void image_width_height_ratio_is_smaller_than_panel_then_fill_height_and_horizontally_centralized() {
        Dimension imageSize = new Dimension(3, 3);
        Dimension panelSize = new Dimension(1024, 768);

        Rectangle displayArea = CenteredDisplay.getDisplayArea(imageSize, panelSize);

        assertEquals(new Rectangle(128, 0, 768, 768), displayArea);
    }

    @Test
    public void image_width_height_ratio_is_larger_than_panel_then_fill_width_and_vertically_centralized() {
        Dimension imageSize = new Dimension(3, 3);
        Dimension panelSize = new Dimension(768, 1024);

        Rectangle displayArea = CenteredDisplay.getDisplayArea(imageSize, panelSize);

        assertEquals(new Rectangle(0, 128, 768, 768), displayArea);
    }
}