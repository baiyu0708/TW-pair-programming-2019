package ui;

import java.awt.*;

class CenteredDisplay {

    static Rectangle getDisplayArea(Dimension imageSize, Dimension panelSize) {

        if (getWidthHeightRatio(imageSize) <= getWidthHeightRatio(panelSize)) {
            int imageNewHeight = panelSize.height;
            // imageSize.width * panelSize.height不会越界
            // imageSize.width：图像由用户创建，尺寸一般不超过几百
            // panelSize.height：屏幕尺寸，高分屏的分辨率一般为1920*1280
            int imageNewWidth = imageSize.width * panelSize.height / imageSize.height;
            return new Rectangle((panelSize.width - imageNewWidth) / 2, 0, imageNewWidth, imageNewHeight);
        }else  {
            // imageSize.height * panelSize.width不会越界，原因同上
            int imageNewHeight = imageSize.height * panelSize.width / imageSize.width;
            int imageNewWidth = panelSize.width;
            return new Rectangle(0, (panelSize.height - imageNewHeight) / 2, imageNewWidth, imageNewHeight);
        }
    }

    private static float getWidthHeightRatio(Dimension imageSize) {
        return (float) imageSize.width / imageSize.height;
    }
}
