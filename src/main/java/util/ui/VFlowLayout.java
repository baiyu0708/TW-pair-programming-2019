package util.ui;

import java.awt.*;

/**
 * 和原生{@code FlowLayout}类似，但是沿垂直方向排布
 * <p>
 * 第三方代码，来源：https://blog.csdn.net/csdn_lqr/article/details/51068395
 * 注：原代码非常烂，因此没有重构到完美，仅保证功能可用
 */
public class VFlowLayout extends FlowLayout {

    private static final long serialVersionUID = 1L;

    private int hgap;
    private int vgap;
    private boolean hfill;

    public VFlowLayout() {
        setAlignment(FlowLayout.LEFT);
        this.hgap = 5;
        this.vgap = 5;
        this.hfill = true;
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        Dimension tarsiz = new Dimension(0, 0);
        for (int i = 0; i < target.getComponentCount(); i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                tarsiz.width = Math.max(tarsiz.width, d.width);
                if (i > 0) {
                    tarsiz.height += hgap;
                }
                tarsiz.height += d.height;
            }
        }
        Insets insets = target.getInsets();
        tarsiz.width += insets.left + insets.right + hgap * 2;
        tarsiz.height += insets.top + insets.bottom + vgap * 2;
        return tarsiz;
    }

    public Dimension minimumLayoutSize(Container target) {
        Dimension tarsiz = new Dimension(0, 0);
        for (int i = 0; i < target.getComponentCount(); i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getMinimumSize();
                tarsiz.width = Math.max(tarsiz.width, d.width);
                if (i > 0) {
                    tarsiz.height += vgap;
                }
                tarsiz.height += d.height;
            }
        }
        Insets insets = target.getInsets();
        tarsiz.width += insets.left + insets.right + hgap * 2;
        tarsiz.height += insets.top + insets.bottom + vgap * 2;
        return tarsiz;
    }

    /**
     * places the components defined by first to last within the target	 * container using the bounds box defined.	 * 	 * @param target	 *            the container.	 * @param x	 *            the x coordinate of the area.	 * @param y	 *            the y coordinate of the area.	 * @param width	 *            the width of the area.	 * @param height	 *            the height of the area.	 * @param first	 *            the first component of the container to place.	 * @param last	 *            the last component of the container to place.
     */
    private void placethem(Container target, int x, int y, int width, int first, int last) {
        for (int i = first; i < last; i++) {
            Component m = target.getComponent(i);
            Dimension md = m.getSize();
            if (m.isVisible()) {
                int px = x + (width - md.width) / 2;
                m.setLocation(px, y);
                y += vgap + md.height;
            }
        }
    }

    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        int maxheight = target.getSize().height - (insets.top + insets.bottom + vgap * 2);
        int maxwidth = target.getSize().width - (insets.left + insets.right + hgap * 2);
        int numcomp = target.getComponentCount();
        int x = insets.left + hgap, y = 0;
        int colw = 0, start = 0;
        for (int i = 0; i < numcomp; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();                                // fit last component to remaining height				if ((this.vfill) && (i == (numcomp - 1))) 				{					d.height = Math.max((maxheight - y), m.getPreferredSize().height);				}

                if (this.hfill) {
                    m.setSize(maxwidth, d.height);
                    d.width = maxwidth;
                } else {
                    m.setSize(d.width, d.height);
                }
                if (y + d.height > maxheight) {
                    placethem(target, x, insets.top + vgap, colw, start, i);
                    y = d.height;
                    x += hgap + colw;
                    colw = d.width;
                    start = i;
                } else {
                    if (y > 0) {
                        y += vgap;
                    }
                    y += d.height;
                    colw = Math.max(colw, d.width);
                }
            }
        }
        placethem(target, x, insets.top + vgap, colw, start, numcomp);
    }
}