package guiDelegate;

import model.Model;
import shapes.Shape;
import shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

/**
 * The Class ProgWindow draws all the shapes of the shapes list on a JPanel. It is also responsible for displaying changes regarding resizing and moving.
 */
public class ProgWindow extends JPanel {

    private Model model;
    private int initialXPos;
    private int initialYPos;
    private Shape drawShape;
    private Shape selectedShape;
    private Color colour;
    private int shapeSelection = 1;
    private int mode = 1;
    private boolean setFill = false;
    private boolean resizing = false;
    private boolean dragging = false;
    private boolean export = false;
    private int resizeBoundry = 4;
    private Rectangle2D.Double[] resizeBoxes;

    /**
     * init new program window.
     */
    public ProgWindow(Model model) {

        this.model = model;
        resizeBoxes = new Rectangle2D.Double[4];
        //window size
        this.setSize(1440, 1080);
        setBackground(Color.WHITE);
        setVisible(true);
        colour = Color.BLACK;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShape = null;
                if (mode == 2) {
                    selectedShape = model.getShapeAtPos(e.getX(), e.getY());
                    if (selectedShape != null) {
                        resizeBoxes = getSelections(selectedShape);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //dragging not true when mouse not clicked
                dragging = false;
                //resizing not true when mouse not clicked
                resizing = false;
                //boundry box
                resizeBoundry = 4;

                //draw
                if (mode == 1) {
                    if (drawShape != null) {
                        model.addShape(drawShape);
                    }
                    drawShape = null;
                }
                //select
                else if (mode == 2) {
                    if (selectedShape != null) {
                        selectedShape.updateBounds();
                        resizeBoxes = getSelections(selectedShape);
                    }
                }


                repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                initialXPos = e.getX();
                initialYPos = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                // shapes and controls for drawing shapes based on selected jbutton
                if (mode == 1) {
                    switch (shapeSelection) {
                        case 1:
                            drawShape = new ShapeLine();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 2:
                            drawShape = new ShapeRectangle();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 3:
                            drawShape = new ShapeSquare();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 4:
                            drawShape = new ShapeCircle();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 5:
                            drawShape = new ShapeEllipse();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 6:
                            drawShape = new ShapeStar();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        case 7:
                            drawShape = new ShapeTriangle();
                            drawShape.setColor(colour);
                            drawShape.setFill(setFill);
                            drawShape.construct(initialXPos, initialYPos, e.getX(), e.getY());
                            break;

                        default:
                            break;

                    }
                }
                //if on selection mode and an object is selected,
                else if ((mode == 2) && (selectedShape != null)) {
                    //if dragging, resize.
                    if (!resizing && (getResizeBoxNumber(e.getX(), e.getY()) >= 0) && (getResizeBoxNumber(e.getX(), e.getY()) < 4)) {
                        model.addUndoAction();
                        resizeBoundry = getResizeBoxNumber(e.getX(), e.getY());
                        resizing = true;
                    }
                    else if (resizing) {
                        resizeBoxes = getSelections(selectedShape);
                        selectedShape.resize(e.getX(), e.getY(), resizeBoundry);
                    }
                    else if (selectedShape.isClicked(e.getX(), e.getY()) && !dragging) {
                        model.addUndoAction();
                        dragging = true;
                    }
                    else if (dragging) {
                        selectedShape.dragTo(e.getX(), e.getY());
                        resizeBoxes = getSelections(selectedShape);
                    }
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

    }

    /**
     * Paint shapes from list
     */
    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);

        //paint from list
        for (int i = 0; i < model.getShapeList().size(); i++) {
            Shape shape = model.getShapeList().get(i);
            shape.paint(g);
        }

        //paint drawn shape
        if (drawShape != null) {
            g.setColor(colour);
            if (drawShape.isFill()) {
                g.fill(drawShape.getShape());
            }
            g.draw(drawShape.getShape());
        }

        //resize markers
        if (!export && (resizeBoxes != null) && (selectedShape != null)) {
            for (Rectangle2D.Double resizeBox : resizeBoxes) {
                if (resizeBox != null) {
                    g.setColor(Color.BLACK);
                    g.draw(resizeBox);

                }
            }
        }
    }

    /**
     * Gets colour.
     */
    Color getColour() {
        return colour;
    }

    /**
     * Sets colour.
     */
    void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Sets draw mode.
     */
    public void setDrawMode(int drawMode) {
        shapeSelection = drawMode;
    }

    /**
     * Set mode.
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Get selected shapes.
     */
    public Shape getSelectedShape() {
        return selectedShape;
    }

    /**
     * Set selected shapes.
     */
    public void setSelectedShape(Shape selectedShape) {
        this.selectedShape = selectedShape;
    }

    /**
     * Gets the number of the resize box that was clicked.
     */
    private int getResizeBoxNumber(int x, int y) {
        if (resizeBoxes != null) {
            for (int i = 0; i < resizeBoxes.length; i++) {
                if (resizeBoxes[i] != null) {
                    if (resizeBoxes[i].contains(x, y)) {
                        return i;
                    }
                }
            }
        }
        return 4;
    }

    /**
     * Gets an array of the resize boxes.
     */
    public Rectangle2D.Double[] getSelections(Shape shape) {

        //box
        Rectangle2D.Double[] selections = new Rectangle2D.Double[4];

        int boxSize = 5;

        if (shape != null) {

            selections[0] = new Rectangle2D.Double(shape.getShape().getBounds().getMinX() - boxSize,
                    shape.getShape().getBounds().getMinY() - boxSize, 2 * boxSize, 2 * boxSize);
            selections[1] = new Rectangle2D.Double(shape.getShape().getBounds().getMaxX() - boxSize,
                    shape.getShape().getBounds().getMinY() - boxSize, 2 * boxSize, 2 * boxSize);
            selections[2] = new Rectangle2D.Double(
                    shape.getShape().getBounds().getMinX() - boxSize,
                    shape.getShape().getBounds().getMaxY() - boxSize, 2 * boxSize, 2 * boxSize);
            selections[3] = new Rectangle2D.Double(
                    shape.getShape().getBounds().getMaxX() - boxSize,
                    shape.getShape().getBounds().getMaxY() - boxSize, 2 * boxSize, 2 * boxSize);

            return selections;
        } else {
            return null;
        }

    }

    /**
     * Draw shape filled if ticked.
     */
    public void setSetFill(boolean setFill) {
        this.setFill = setFill;
    }

    /**
     * Currently exporting.
     */
    public void setExporting(boolean export) {
        this.export = export;
    }

    /**
     * Sets the model.
     */
    public void setModel(Model model) {
        this.model = model;
    }

}
