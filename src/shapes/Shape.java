package shapes;

import java.awt.*;
import java.io.Serializable;

/**
 * Shape describes and defines a shape. Is abstract for specific shapes to use and override.
 */
public abstract class Shape implements Serializable {

    protected java.awt.Shape shape;
    protected int x1 = 0;
    protected int y1 = 0;
    protected int x2 = 0;
    protected int y2 = 0;
    protected int w = 0;
    protected int h = 0;
    private Color color;
    private boolean fill = false;
    public boolean isClicked(int x, int y) {
        return (shape.contains(x, y));
    }
    public abstract void construct(int x1, int y1, int x2, int y2);

    /**
     * Move a shape by dragging.
     */
    public void dragTo(int x, int y) {
        construct(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
    }

    /**
     * Resize a given shape.
     */
    public void resize(int x, int y, int corner) {
        if (corner == 0) {
            construct(x, y, x2, y2);
        } else if (corner == 1) {
            construct(x1, y, x, y2);
        } else if (corner == 2) {
            construct(x, y1, x2, y);
        }else if (corner == 3) {
            construct(x1, y1, x, y);
        }
    }

    /**
     * Update bounds after resizing.
     */
    public void updateBounds() {
        x1 = (int) shape.getBounds().getMinX();
        y1 = (int) shape.getBounds().getMinY();
        x2 = (int) shape.getBounds().getMaxX();
        y2 = (int) shape.getBounds().getMaxY();
        w = x2 - x1;
        h = y2 - y1;
    }

    /**
     * Update the coordinates after drawing.
     */
    protected void updateCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        w = x2 - x1;
        h = y2 - y1;
    }

    /**
     * Paint shape.
     */
    public void paint(Graphics2D g) {
        if (color != null) {
            g.setColor(color);
        }
        if (fill) {
            g.fill(shape);
        }
        g.draw(shape);
    }

    /**
     * Gets shape.
     */
    public java.awt.Shape getShape() {
        return shape;
    }

    /**
     * Gets colour.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Checks fill (boolean) for filling with colour.
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * Set fill.
     */
    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
