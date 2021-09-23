package shapes;

import java.awt.geom.Path2D;

/**
 * Defines Triangle and extends abstract Shape.
 */
public class ShapeRectangle extends Shape {

    /**
     * New Rectangle
     */
    public ShapeRectangle() {
        shape = new Path2D.Double();
    }

    @Override
    public void construct(int x1, int y1, int x2, int y2) {
        Path2D.Double rectangle = new Path2D.Double();
        rectangle.moveTo(x1, y1);
        rectangle.lineTo(x2, y1);
        rectangle.lineTo(x2, y2);
        rectangle.lineTo(x1, y2);
        rectangle.lineTo(x1, y1);
        rectangle.closePath();
        updateCoordinates(x1, y1, x2, y2);
        shape = rectangle;

    }
}
