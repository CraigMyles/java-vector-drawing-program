package shapes;

import java.awt.geom.Ellipse2D;


/**
 * Extend shape to draw an ellipse from a drawn rectangle.
 */
public class ShapeEllipse extends Shape {

    public ShapeEllipse() {
        shape = new Ellipse2D.Double();
    }


    @Override
    public void construct(int x1, int y1, int x2, int y2) {
        ((Ellipse2D.Double) shape).setFrameFromDiagonal(x1, y1, x2, y2);
        updateCoordinates(x1, y1, x2, y2);
    }
}
