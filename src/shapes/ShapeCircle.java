package shapes;

/**
 * Definitions of a circle extended from ellipse. Uses shortest side to enforce equal l+h.
 */
public class ShapeCircle extends ShapeEllipse {

    /**
     * Build a circle from the shortest side of the drawn rectangle.
     */
    @Override
    public void construct(int x1, int y1, int x2, int y2) {

        //find shortest side
        int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));

        //ellipse method for drawing
        int newX = x1 > x2 ? x1 - radius : x1;
        int newY = y1 > y2 ? y1 - radius : y1;

        super.construct(newX, newY, newX + radius, newY + radius);

    }

    /**
     * Resize the circle. The shortest side of the rectangle is taken as for the x and y components of the radius.
     */
    @Override
    public void resize(int x, int y, int corner) {
        int s;
        if (corner == 3) {
            s = Math.min(Math.abs(x - x1), Math.abs(y - y1));
            super.construct(x1, y1, x1 + s, y1 + s);
        } else if (corner == 0) {
            s = Math.min(Math.abs(x - x2), Math.abs(y - y2));
            super.construct(x2 - s, y2 - s, x2, y2);
        } else if (corner == 1) {
            s = Math.min(Math.abs(x - x1), Math.abs(y - y2));
            super.construct(x1, y2 - s, x1 + s, y2);
        } else if (corner == 2) {
            s = Math.min(Math.abs(x - x2), Math.abs(y - y1));
            super.construct(x2 - s, y1, x2, y1 + s);
        }
    }

}
