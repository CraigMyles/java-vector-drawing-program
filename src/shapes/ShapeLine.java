package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Defines a line and extends abstract Shape, cannot use same rules as rectangle shape.
 */
public class ShapeLine extends Shape {

    /**
     * New line
     */
    public ShapeLine() {
        shape = new Line2D.Double();
    }

    /**
     * Start and finish points.
     */
    @Override
    public void construct(int x1, int y1, int x2, int y2) {
        ((Line2D.Double) shape).setLine(x1, y1, x2, y2);
        updateCoordinates(x1, y1, x2, y2);

    }

    /**
     * overridden method to detect clicking on lines.
     */
    @Override
    public boolean isClicked(int x, int y) {
        return shape.intersects(x - 1, y - 1, 5, 5);
    }

    /**
     * Paint the line.
     */
    @Override
    public void paint(Graphics2D g) {
        if (getColor() != null) {
            g.setColor(getColor());
        }
        g.draw(getShape());
    }

}
