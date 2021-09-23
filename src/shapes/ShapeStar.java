package shapes;

import java.awt.geom.Path2D;

/**
 * Defines star and extends Shape.
 */
public class ShapeStar extends Shape {

    /**
     * Instantiates a new star.
     */
    public ShapeStar() {
        shape = new Path2D.Double();
    }

    /**
     * Construct the star according to its definition.
     */
    @Override
    public void construct(int x1, int y1, int x2, int y2) {
        double width = x2 - x1;
        double height = y2 - y1;
        double min = Math.min(width, height);
        double half = min / 2;
        Path2D.Double path = new Path2D.Double();

        path.moveTo(x1 + half * 0.5f, y1 + half * 0.84f);
        path.lineTo(x1 + half * 1.5f, y1 + half * 0.84f);
        path.lineTo(x1 + half * 0.68f, y1 + half * 1.45f);
        path.lineTo(x1 + half * 1.0f, y1 + half * 0.5f);
        path.lineTo(x1 + half * 1.32f, y1 + half * 1.45f);
        path.lineTo(x1 + half * 0.5f, y1 + half * 0.84f);
        path.closePath();

        updateCoordinates(x1, y1, x2, y2);
        shape = path;

    }
}
