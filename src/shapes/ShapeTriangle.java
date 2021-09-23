package shapes;

import java.awt.geom.Path2D;

/**
 * Defines Triangle and extends Shape.
 */
public class ShapeTriangle extends Shape {

    /**
     * Instantiates a new XHexagon.
     */
    public ShapeTriangle() {
        shape = new Path2D.Double();
    }

    /**
     * Construct the triangle according to its definition.
     */
    @Override
    public void construct(int x1, int y1, int x2, int y2) {
        double width = x2 - x1;
        Path2D.Double path = new Path2D.Double();
        path.moveTo(x1 + width * 0.5, y1);
        path.lineTo(x2, y2);
        path.lineTo(x1, y2);
        path.lineTo(x1 + width * 0.5, y1);
        path.closePath();
        updateCoordinates(x1, y1, x2, y2);
        shape = path;

    }
}
