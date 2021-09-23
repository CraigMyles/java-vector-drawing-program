package guiDelegate;


import model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * Details gui of program. Defines aspects of whas is displayed including controls and options.
 */
public class GuiDelegate implements Observer {

    private final Model model;
    private final JFrame frame;
    private final ProgWindow drawPanel;
    private final JToolBar actionBar;
    private final JColorChooser jColorChooser;
    private JMenuBar menuBar;
    private JMenuItem undo;
    private JMenuItem redo;
    private JButton undoButton;
    private JButton redoButton;
    private JMenuItem deleteSelected;

    /**
     * Init gui delegate.
     */
    public GuiDelegate(Model model) {
        this.model = model;
        frame = new JFrame("Vector Drawing Program - 200034578");
        menuBar = new JMenuBar();
        actionBar = new JToolBar();
        menuBar = new JMenuBar();
        drawPanel = new ProgWindow(model);
        jColorChooser = new JColorChooser();

        setupComponents();
        model.addObserver(this);
    }

    /**
     * Setup components.
     */
    private void setupComponents() {
        setupMenu();
        setupActionBar();
        Container borderPane = frame.getContentPane();
        borderPane.setLayout(new BorderLayout());

        borderPane.add(drawPanel, BorderLayout.CENTER);
        borderPane.add(actionBar, BorderLayout.WEST);

        frame.setSize(1440, 1080);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Setup menu of the application.
     */
    private void setupMenu() {
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Options");
        JMenu about = new JMenu("About");

        JMenuItem newProjectWithoutSaving = new JMenuItem("New Project Without Saving");
        JMenuItem load = new JMenuItem("Load from .draw file");
        JMenuItem save = new JMenuItem("Save as .draw file");
        JMenuItem export = new JMenuItem("Save as .png");

        JMenuItem author = new JMenuItem("Crated By 200034578");
        JMenuItem fileType = new JMenuItem(".draw will preserve serialised vector data");


        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        deleteSelected = new JMenuItem("Delete Shape");
        JMenuItem deleteAll = new JMenuItem("Delete All");

        file.add(newProjectWithoutSaving);
        file.add(load);
        file.add(save);
        file.add(export);
        edit.add(undo);
        edit.add(redo);
        edit.add(deleteSelected);
        edit.add(deleteAll);
        about.add(author);
        about.add(fileType);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(about);
        author.setEnabled(false);
        fileType.setEnabled(false);

        undo.setEnabled(false);
        redo.setEnabled(false);
        deleteSelected.setEnabled(false);

        newProjectWithoutSaving.addActionListener(e -> {
            model.clearUndoRedoStack();
            model.getShapeList().clear();
            model.notifyObservers();
            drawPanel.setSelectedShape(null);
            drawPanel.repaint();
        });
        load.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(fc);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File myFile = fc.getSelectedFile();
                    model.readFromFile(myFile.toString());
                    model.clearUndoRedoStack();
                    model.notifyObservers();
                    drawPanel.setSelectedShape(null);
                    drawPanel.repaint();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Could not load file.", "Error:", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(fc);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File myFile = fc.getSelectedFile();
                    model.saveToFile(myFile.toString() + ".draw");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Could not save file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            drawPanel.repaint();
        });
        export.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(fc);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage exportImg = new BufferedImage(drawPanel.getWidth(), drawPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D exportGraphics = exportImg.createGraphics();
                    drawPanel.setExporting(true);
                    drawPanel.paintComponent(exportGraphics);
                    File myFile = fc.getSelectedFile();
                    ImageIO.write(exportImg, "png", myFile);
                    drawPanel.setExporting(false);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Could not safe file.", "Error:", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteSelected.addActionListener(e -> {
            if (drawPanel.getSelectedShape() != null) {
                model.removeShape(drawPanel.getSelectedShape());
                drawPanel.setSelectedShape(null);
                drawPanel.repaint();
            }
        });
        deleteAll.addActionListener(e -> {
            model.clearShapeList();
            drawPanel.setSelectedShape(null);
            drawPanel.repaint();
            model.clearShapeList();
        });
        undo.addActionListener(e -> {
            drawPanel.setSelectedShape(null);
            model.undo();
        });
        redo.addActionListener(e -> {
            drawPanel.setSelectedShape(null);
            model.redo();
        });
        frame.setJMenuBar(menuBar);
    }

    /**
     * Setup left bar of the application.
     */
    private void setupActionBar() {


        actionBar.setLayout(new GridLayout(20, 1));

        JLabel modeLabel = new JLabel("<html><i><u>Mode:</u></i></html>");


        JButton drawButton = null;
        JButton selectButton = null;


        JLabel label = new JLabel("<html><i><u>Shapes:</u></i></html>");

        JButton rectButton = null;
        JButton lineButton = null;
        JButton squareButton = null;
        JButton circleButton = null;
        JButton ellipseButton = null;
        JButton starButton = null;
        JButton triangleButton = null;

        try {
            //draw/select
            drawButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/pencil.png")));
            selectButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/pointer.png")));

            //shapes
            rectButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/rectangle.png")));
            lineButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/line.png")));
            squareButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/square.png")));
            ellipseButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/ellipse.png")));
            starButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/star.png")));
            triangleButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/triangle.png")));
            circleButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/circle.png")));

            //large undo/redo buttons
            undoButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/undo.png")));
            redoButton = new JButton("", new ImageIcon(new URL("https://craig.im/vector/redo.png")));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        JCheckBox fillBox = new JCheckBox("Fill");
        JButton deleteButton = new JButton("<html><center>Delete<br />Selected</brShape</center></html>");
        deleteButton.setEnabled(false);
        JLabel actionLabel = new JLabel("<html><i><u>Controls:</u></i></html>");
        JButton deleteAll = new JButton("<html><center>Clear<br />All</center></html>");
        //actions
        JButton finalDrawButton = drawButton;
        JButton finalSelectButton = selectButton;
        //shapes
        JButton finalLineButton = lineButton;
        JButton finalSquareButton = squareButton;
        JButton finalRectButton = rectButton;
        JButton finalCircleButton = circleButton;
        JButton finalEllipseButton = ellipseButton;
        JButton finalStarButton = starButton;
        JButton finalTriangleButton = triangleButton;

        drawButton.addActionListener(e -> {
            drawPanel.setMode(1);
            drawPanel.setSelectedShape(null);
            drawPanel.repaint();
            finalDrawButton.setEnabled(false);
            finalSelectButton.setEnabled(true);

            finalLineButton.setEnabled(true);
            finalSquareButton.setEnabled(true);
            finalRectButton.setEnabled(true);
            finalCircleButton.setEnabled(true);
            finalEllipseButton.setEnabled(true);
            finalStarButton.setEnabled(true);

            deleteSelected.setEnabled(false);
            deleteButton.setEnabled(false);
        });

        JButton finalSelectButton1 = selectButton;
        JButton finalDrawButton1 = drawButton;

        selectButton.addActionListener(e -> {
            drawPanel.setMode(2);
            finalSelectButton1.setEnabled(false);
            finalDrawButton1.setEnabled(true);
            finalLineButton.setEnabled(false);
            finalSquareButton.setEnabled(false);
            finalRectButton.setEnabled(false);
            finalCircleButton.setEnabled(false);
            finalEllipseButton.setEnabled(false);
            finalStarButton.setEnabled(false);
            finalTriangleButton.setEnabled(false);

            deleteSelected.setEnabled(true);
            deleteButton.setEnabled(true);
        });

        //shapes and corresponding shape/drawing mode.
        lineButton.addActionListener(e -> drawPanel.setDrawMode(1));
        squareButton.addActionListener(e -> drawPanel.setDrawMode(3));
        rectButton.addActionListener(e -> drawPanel.setDrawMode(2));
        circleButton.addActionListener(e -> drawPanel.setDrawMode(4));
        ellipseButton.addActionListener(e -> drawPanel.setDrawMode(5));
        starButton.addActionListener(e -> drawPanel.setDrawMode(6));
        triangleButton.addActionListener(e -> drawPanel.setDrawMode(7));

        undoButton.addActionListener(e -> {
            drawPanel.setSelectedShape(null);
            model.undo();
        });

        redoButton.addActionListener(e -> {
            drawPanel.setSelectedShape(null);
            model.redo();
        });

        deleteButton.addActionListener(e -> {
            if (drawPanel.getSelectedShape() != null) {
                model.removeShape(drawPanel.getSelectedShape());
                drawPanel.setSelectedShape(null);
                drawPanel.repaint();
            }
        });

        //tick box for filling next drawn shape.
        fillBox.addItemListener(e -> drawPanel.setSetFill(e.getStateChange() == ItemEvent.SELECTED));

        deleteAll.addActionListener(e -> {
            model.clearShapeList();
            drawPanel.setSelectedShape(null);
            drawPanel.repaint();
        });

        JLabel colourLabel = new JLabel("<html><i><u>Colour:</u></i></html>");
        JButton colourButton = new JButton();
        //default colour
        colourButton.setBackground(Color.BLACK);
        colourButton.addActionListener(e -> {
            Color colour = JColorChooser.showDialog(jColorChooser, "Colour Selection", drawPanel.getColour());
            drawPanel.setColour(colour);
            colourButton.setBackground(drawPanel.getColour());
        });

        //mode settings
        actionBar.add(modeLabel);
        actionBar.add(drawButton);
        actionBar.add(selectButton);
        //shape options
        actionBar.add(label);
        actionBar.add(lineButton);
        actionBar.add(squareButton);
        actionBar.add(rectButton);
        actionBar.add(circleButton);
        actionBar.add(ellipseButton);
        actionBar.add(starButton);
        actionBar.add(triangleButton);
        actionBar.add(actionLabel);
        actionBar.add(undoButton);
        actionBar.add(redoButton);
        actionBar.add(deleteButton);
        actionBar.add(deleteAll);
        //fill option
        actionBar.add(fillBox);
        //colour options
        actionBar.add(colourLabel);
        actionBar.add(colourButton);
    }

    /**
     * Updates the model reference and repaints the shapes.
     */
    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(() -> {
            drawPanel.setModel(model);
            drawPanel.repaint();
            //enable/disable redo based on stack status
            redo.setEnabled(model.redoIsEmpty());
            redoButton.setEnabled(model.redoIsEmpty());
            //enable/disable undo based on stack status
            undo.setEnabled(model.undoIsEmpty());
            undoButton.setEnabled(model.undoIsEmpty());
        });

    }

}
