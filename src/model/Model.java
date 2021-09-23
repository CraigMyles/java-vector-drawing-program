package model;

import shapes.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * Model for managing and controlling.
 */
public class Model extends Observable {

    //stack for all undo bytes
    private final Stack<byte[]> undoStack;
    //stack for all redo bytes
    private final Stack<byte[]> redoStack;
    //array list for holding all shapes
    private ArrayList<Shape> shapeList;


    /**
     * Init Model with shapes list, undo, and redo stacks.
     */
    public Model() {
        shapeList = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Action an undo. Moves undo to the redo stack so it can be reversed.
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public void undo() {
        //if stack exists;
        if (undoStack.size() > 0) {
            try {
                // put the current list on the redoStack
                redoStack.push(serialize(shapeList));
                // make the last item of the undoStack the list
                shapeList = new ArrayList<>(deserialize((undoStack.pop())));
                setChanged();
                notifyObservers();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Action a redo and update stack.
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public void redo() {
        if (redoStack.size() > 0) {
            try {
                undoStack.push(serialize(shapeList));
                shapeList = deserialize(redoStack.pop());
                setChanged();
                notifyObservers();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add previous action to undo stack.
     */
    public void addUndoAction() {
        try {
            redoStack.clear();
            undoStack.push(serialize(shapeList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get recent shape at x,y position
     */
    public Shape getShapeAtPos(int x, int y) {
        Shape selectedShape = null;
        for (Shape shape : shapeList) {
            if (shape.isClicked(x, y)) {
                selectedShape = shape;
            }
        }
        return selectedShape;
    }

    /**
     * Add new shape.
     */
    public void addShape(Shape shape) {
        addUndoAction();
        shapeList.add(shape);
        setChanged();
        notifyObservers();
    }

    /**
     * Remove a given shape.
     */
    public void removeShape(Shape shape) {
        addUndoAction();
        shapeList.remove(shape);
        setChanged();
        notifyObservers();
    }

    /**
     * Clear the shape list.
     */
    public void clearShapeList() {
        addUndoAction();
        shapeList.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Serialise shape list.
     */
    private byte[] serialize(ArrayList<Shape> shapeList) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(shapeList);
        oos.close();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * Deserialize shape list.
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Shape> deserialize(byte[] serializedShapeList) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bain = new ByteArrayInputStream(serializedShapeList);
        ObjectInputStream ois = new ObjectInputStream(bain);
        ois.close();
        bain.close();
        return (ArrayList<Shape>) ois.readObject();
    }

    /**
     * Gets the shapes list.
     *
     * @return the shapes list
     */
    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    /**
     * Save the shapes list to file.
     */
    public void saveToFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(shapeList);
        oos.close();
        fos.close();
    }

    /**
     * Read shapes list from file.
     */
    @SuppressWarnings("unchecked")
    public void readFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        shapeList = (ArrayList<Shape>) ois.readObject();
        ois.close();
        fis.close();
    }

    /**
     * Clears the undo and the redo stack.
     */
    public void clearUndoRedoStack() {
        undoStack.clear();
        redoStack.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Boolean return for undo stack status
     */
    public boolean undoIsEmpty() {
        return (undoStack.size() >= 1);
    }

    /**
     * Boolean return for redo stack status.
     */
    public boolean redoIsEmpty() {
        return (redoStack.size() >= 1);
    }

}
