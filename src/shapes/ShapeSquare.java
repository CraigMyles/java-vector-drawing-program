package shapes;

/**
 * Defines square and extends Shape.
 */
public class ShapeSquare extends ShapeRectangle {

    /**
     * Construct a square.
     */
    @Override
    public void construct(int x1, int y1, int x2, int y2) {

        //find shortest side
        int shortestSide = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
        int newX = x1 > x2 ? x1 - shortestSide : x1;
        int newY = y1 > y2 ? y1 - shortestSide : y1;
        super.construct(newX, newY, newX + shortestSide, newY + shortestSide);
    }

    /**
     * Resize square
     */
    @SuppressWarnings({"ConstantConditions", "DuplicateCondition"})
    @Override
    public void resize(int x, int y, int corner) {
        int shortSide;
        if (corner == 3) {
            shortSide = Math.min(Math.abs(x - x1), Math.abs(y - y1));
            super.construct(x1, y1, x1 + shortSide, y1 + shortSide);
        } else if (corner == 0) {
            shortSide = Math.min(Math.abs(x - x2), Math.abs(y - y2));
            super.construct(x2 - shortSide, y2 - shortSide, x2, y2);
        }
    }

}
