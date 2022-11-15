package imageview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import script.Features;
import script.ImageView;

/**
 * A View for the image program producing an interactive GUI using JFrame.
 */
public class SwingImageView extends JFrame implements ImageView {

  private static int width = 1290;
  private static int height = 720;
  private static int locationX = 200;
  private static int locationY = 200;


  private JMenuItem saveMenuItem;
  private JMenuItem loadMenuItem;

  private JMenuItem blurMenuItem;
  private JMenuItem sharpenMenuItem;
  private JMenuItem grayscaleMenuItem;
  private JMenuItem sepiaMenuItem;
  private JMenuItem ditherMenuItem;
  private JMenuItem mosaicMenuItem;
  private JMenuItem edgeDetectionMenuItem;
  private JMenuItem createScriptMenuItem;
  private JMenuItem executeScriptMenuItem;
  private JMenuItem cropMenuItem;
  private JMenuItem histogramEqualizationMenuItem;
  private JMenuBar toolbar;

  private JFileChooser imageFileChooser;
  private JFileChooser scriptFileChooser;

  private JScrollPane scrollPane;
  private JLabel imageLabel;

  private JTextArea statusArea;

  // New frame for script editor.
  private JFrame editorFrame;

  // New Frame for crop previewer
  private JFrame cropPreviewFrame;
  private JLabel cropLabel;

  /**
   * Constructor for the primary frame of the view.
   */
  public SwingImageView() {
    // Title bar for the frame
    super("Image-Filter-o-Matic-2000");

    setSize(width, height);
    setLocation(locationX, locationY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    /*
     * MENU
     */
    // File Menu
    saveMenuItem = new JMenuItem("Save");
    loadMenuItem = new JMenuItem("Open");

    loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));

    JMenu fileMenu = new JMenu("File");
    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);

    saveMenuItem.setEnabled(false);

    // Filter menu
    blurMenuItem = new JMenuItem("Blur");
    sharpenMenuItem = new JMenuItem("Sharpen");
    grayscaleMenuItem = new JMenuItem("Grayscale");
    sepiaMenuItem = new JMenuItem("Sepia");
    ditherMenuItem = new JMenuItem("Dither");
    mosaicMenuItem = new JMenuItem("Mosaic");
    edgeDetectionMenuItem = new JMenuItem("Edge Detection Effect");
    histogramEqualizationMenuItem = new JMenuItem("Histogram Equalization");

    blurMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
    sharpenMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
    grayscaleMenuItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_G,
            KeyEvent.CTRL_DOWN_MASK));
    sepiaMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
    ditherMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
    mosaicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
    edgeDetectionMenuItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
    histogramEqualizationMenuItem.setAccelerator((KeyStroke.getKeyStroke(
            KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK)));

    blurMenuItem.setEnabled(false);
    sharpenMenuItem.setEnabled(false);
    grayscaleMenuItem.setEnabled(false);
    sepiaMenuItem.setEnabled(false);
    ditherMenuItem.setEnabled(false);
    mosaicMenuItem.setEnabled(false);
    edgeDetectionMenuItem.setEnabled(false);
    histogramEqualizationMenuItem.setEnabled(false);

    JMenu filterMenu = new JMenu("Filter");
    filterMenu.add(blurMenuItem);
    filterMenu.add(sharpenMenuItem);
    filterMenu.add(grayscaleMenuItem);
    filterMenu.add(sepiaMenuItem);
    filterMenu.add(ditherMenuItem);
    filterMenu.add(mosaicMenuItem);
    filterMenu.add(edgeDetectionMenuItem);
    filterMenu.add(histogramEqualizationMenuItem);

    // Tools Menu
    cropMenuItem = new JMenuItem("Crop");
    cropMenuItem.setEnabled(false);
    cropMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));

    JMenu toolsMenu = new JMenu("Tools");
    toolsMenu.add(cropMenuItem);

    // Script Menu
    createScriptMenuItem = new JMenuItem("Create");
    executeScriptMenuItem = new JMenuItem("Run");

    JMenu scriptMenu = new JMenu("Script");
    scriptMenu.add(createScriptMenuItem);
    scriptMenu.add(executeScriptMenuItem);

    // Menu Bar
    this.toolbar = new JMenuBar();
    this.toolbar.add(fileMenu);
    this.toolbar.add(filterMenu);
    this.toolbar.add(toolsMenu);
    this.toolbar.add(scriptMenu);

    this.add(this.toolbar, BorderLayout.PAGE_START);


    /*
     * File choosers
     */
    imageFileChooser = new JFileChooser();
    imageFileChooser.addChoosableFileFilter(
            new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
    imageFileChooser.setAcceptAllFileFilterUsed(false);
    scriptFileChooser = new JFileChooser();
    scriptFileChooser.addChoosableFileFilter(
            new FileNameExtensionFilter("txt files", "txt", "text"));
    scriptFileChooser.setAcceptAllFileFilterUsed(false);

    /*
     * Image field
     */
    imageLabel = new JLabel("", JLabel.CENTER);
    imageLabel.setText("Please load an image (File -> Open)");
    scrollPane = new JScrollPane(imageLabel);

    this.add(scrollPane, BorderLayout.CENTER);

    /*
     * Status Area
     */
    statusArea = new JTextArea();
    JScrollPane statusScrollPane = new JScrollPane(statusArea);
    statusScrollPane.setPreferredSize(new Dimension(150, 120));
    statusScrollPane.setBorder(BorderFactory.createTitledBorder("Log"));
    statusScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    statusArea.setEditable(false);

    showMessage("Welcome");
    this.add(statusScrollPane, BorderLayout.PAGE_END);
  }

  /**
   * Show a message to the user.
   *
   * @param msg the message to show
   */
  @Override
  public void showMessage(String msg) {
    statusArea.append(msg + System.lineSeparator());
    statusArea.update(statusArea.getGraphics());
  }

  /**
   * Updates the view with new image and await further input.
   */
  @Override
  public void update(BufferedImage image) {
    imageLabel.setText("");
    imageLabel.setIcon(new ImageIcon(image));
    blurMenuItem.setEnabled(true);
    sharpenMenuItem.setEnabled(true);
    grayscaleMenuItem.setEnabled(true);
    sepiaMenuItem.setEnabled(true);
    ditherMenuItem.setEnabled(true);
    mosaicMenuItem.setEnabled(true);
    edgeDetectionMenuItem.setEnabled(true);
    saveMenuItem.setEnabled(true);
    cropMenuItem.setEnabled(true);
    histogramEqualizationMenuItem.setEnabled(true);
  }

  /**
   * Prompts a user to load an image to start.
   */
  @Override
  public void promptForImage() {
    this.showMessage("Please load next image.");
  }

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(Features f) {

    // Show view to start
    this.setVisible(true);

    // Action listeners for load and save
    loadMenuItem.addActionListener(l -> loadProcedure(f));
    saveMenuItem.addActionListener(l -> saveProcedure(f));

    /*
     * Filter menu item
     */
    blurMenuItem.addActionListener(l -> {
      f.applyBlur();
      this.update(f.getBufferedImage());
      this.showMessage("Image blurred");
    });
    sharpenMenuItem.addActionListener(l -> {
      f.applySharpen();
      this.update(f.getBufferedImage());
      this.showMessage("Image sharpened");
    });
    grayscaleMenuItem.addActionListener(l -> {
      f.applyGrayscale();
      this.update(f.getBufferedImage());
      this.showMessage("Grayscale applied");
    });
    sepiaMenuItem.addActionListener(l -> {
      f.applySepia();
      this.update(f.getBufferedImage());
      this.showMessage("Sepia applied");
    });
    ditherMenuItem.addActionListener(l -> {
      f.applyDither();
      this.update(f.getBufferedImage());
      this.showMessage("Dither applied");
    });
    mosaicMenuItem.addActionListener(l -> {
      JSlider seedSlider = createSeedSlider();
      int sliderDialogResponse = seedSliderDialog(seedSlider, this);
      if (!(sliderDialogResponse == JOptionPane.CLOSED_OPTION
              || sliderDialogResponse == JOptionPane.CANCEL_OPTION)) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        f.applyMosaic(seedSlider.getValue());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        this.update(f.getBufferedImage());
        this.showMessage(String.format("Mosaic applied with %d seeds", seedSlider.getValue()));
      }
    });
    edgeDetectionMenuItem.addActionListener(l -> {
      f.applyEdgeDetection();
      this.update(f.getBufferedImage());
      this.showMessage("Edge detection effect applied");
    });
    histogramEqualizationMenuItem.addActionListener(l -> {
      JOptionPane.showMessageDialog(this,
              "Histogram equalization works best with grayscale images, but can still be"
                      + "applied to color images with varying results.");
      f.applyHistogramEqualization();
      this.update(f.getBufferedImage());
      this.showMessage("Histogram Equalization applied");
    });

    /*
     * Script menu items
     */
    executeScriptMenuItem.addActionListener(l -> {
      try {
        int fileReturnVal = scriptFileChooser.showOpenDialog(this);
        if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
          File inputFile = scriptFileChooser.getSelectedFile();

          this.showMessage("Script loaded: " + inputFile.getPath());
          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          Readable input = new FileReader(inputFile);
          f.executeBatchCommands(input);
          this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      } catch (IllegalArgumentException | FileNotFoundException e) {
        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Load Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });

    createScriptMenuItem.addActionListener(l -> {
      createScriptEditor();
    });

    cropMenuItem.addActionListener(l -> {
      new CropPreviewFrame(f, this);
    });
  }


  /**
   * Private method for executing a load procedure.
   *
   * @param f the set of callback functions that the load procedure requires
   */
  private void loadProcedure(Features f) {
    try {
      // Show file chooser
      int fileReturnVal = imageFileChooser.showOpenDialog(this);
      // If file selected
      if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
        File file = imageFileChooser.getSelectedFile();
        // Load image and update
        f.loadImage(file.getPath());
        this.showMessage("Image loaded.");
        this.update(f.getBufferedImage());
      }
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Private method for executing a save procedure.
   *
   * @param f the set of callback functions that the save procedure requires
   */
  private void saveProcedure(Features f) {
    try {
      // Show file chooser
      int fileReturnVal = imageFileChooser.showSaveDialog(this);
      if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
        File file = imageFileChooser.getSelectedFile();
        // if saving without extension, add extension
        if (!file.toString().endsWith(".jpg") && !file.toString().endsWith(".png")) {
          file = new File(file + ".jpg");
        }
        // Save file
        f.saveImage(file.getPath());
        this.showMessage("Image saved: " + file.getPath());
      }
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Private method for creating and a JSlider specifically to get number of mosaic
   * seeds.
   *
   * @return JSlider specifically to get number of mosaic
   */
  private JSlider createSeedSlider() {
    JSlider seedSlider = new JSlider(JSlider.HORIZONTAL, 100, 20100, 10100);
    seedSlider.setMajorTickSpacing(5000);
    seedSlider.setMinorTickSpacing(1000);
    seedSlider.setPaintTicks(true);
    seedSlider.setPaintLabels(true);
    return seedSlider;
  }

  /**
   * Private method to create a pop-up dialog to display a JSlider, specifically to get number of
   * mosaic seeds.
   *
   * @param seedSlider JSlider to display
   * @param parent     container to hold dialog
   * @return the response of the dialog, i.e. whether it was clicked, closed, etc.
   */
  private int seedSliderDialog(JSlider seedSlider, Component parent) {
    // Create text label
    JLabel sliderLabel = new JLabel("Number of Seeds", JLabel.CENTER);
    sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Create Panel
    JPanel sliderPanel = new JPanel(new BorderLayout());
    sliderPanel.add(sliderLabel, BorderLayout.PAGE_START);
    sliderPanel.add(seedSlider, BorderLayout.CENTER);

    // Create dialog
    int sliderDialogResponse = JOptionPane.showOptionDialog(
            parent,
            sliderPanel,
            "Apply Mosaic",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null, null, null);
    return sliderDialogResponse;
  }

  /**
   * Private method to create a whole new JFrame that hosts components to create and save batch
   * script files.
   */
  private void createScriptEditor() {
    // Frame
    editorFrame = new JFrame("Script Editor");
    editorFrame.setSize(width, height);
    editorFrame.setLocation(locationX + 50, locationY + 50);
    editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    editorFrame.setLayout(new BorderLayout());

    // Text area
    JTextArea editorTextArea = new JTextArea();
    editorTextArea.setEditable(true);
    JScrollPane editorScrollPane = new JScrollPane(editorTextArea);
    editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    editorFrame.add(editorScrollPane, BorderLayout.CENTER);

    // Toolbar
    JMenuItem editorSaveMenuItem = new JMenuItem("Save Script");
    JMenu fileMenu = new JMenu("File");
    fileMenu.add(editorSaveMenuItem);
    JMenuBar scriptToolBar = new JMenuBar();
    scriptToolBar.add(fileMenu);
    editorFrame.add(scriptToolBar, BorderLayout.PAGE_START);
    editorSaveMenuItem.addActionListener(l -> {
      try {
        int fileReturnVal = scriptFileChooser.showSaveDialog(editorFrame);
        if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
          File file = scriptFileChooser.getSelectedFile();
          if (!file.toString().endsWith(".txt")) {
            file = new File(file + ".txt");
          }
          String text = editorTextArea.getText();
          BufferedWriter writer = new BufferedWriter(new FileWriter(file));
          writer.write(text);
          writer.close();
          this.showMessage("Script saved: " + file.getPath());
        }
      } catch (IllegalArgumentException | IOException e) {
        JOptionPane.showMessageDialog(
                editorFrame,
                e.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
      }
    });

    // Buttons
    JPanel editorButtonPanel = new JPanel(new FlowLayout());

    Map<JButton, String> editorButtonMap = new LinkedHashMap<>();
    editorButtonMap.put(new JButton("Blur"), "blur");
    editorButtonMap.put(new JButton("Sharpen"), "sharpen");
    editorButtonMap.put(new JButton("Grayscale"), "grayscale");
    editorButtonMap.put(new JButton("Sepia"), "sepia");
    editorButtonMap.put(new JButton("Dither"), "dither");
    editorButtonMap.put(new JButton("Mosaic"), "mosaic");
    editorButtonMap.put(new JButton("Edge Detection"), "edgedetection");
    editorButtonMap.put(new JButton("Histogram Equalization"), "histogramequalization");
    editorButtonMap.put(new JButton("Save"), "save");
    editorButtonMap.put(new JButton("Load"), "load");
    for (Map.Entry<JButton, String> entry : editorButtonMap.entrySet()) {
      editorButtonPanel.add(entry.getKey());
      entry.getKey().setEnabled(false);
      switch (entry.getValue()) {
        case "save":
          entry.getKey().addActionListener(l -> {
            int fileReturnVal = imageFileChooser.showSaveDialog(editorFrame);
            if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
              File file = imageFileChooser.getSelectedFile();
              if (!file.toString().endsWith(".jpg") && !file.toString().endsWith(".png")) {
                file = new File(file + ".jpg");
              }
              editorTextArea.append(
                      entry.getValue() + " " + file.getPath() + System.lineSeparator());
            }
          });
          break;
        case "load":
          entry.getKey().setEnabled(true);
          entry.getKey().addActionListener(l -> {
            int fileReturnVal = imageFileChooser.showOpenDialog(editorFrame);
            if (fileReturnVal == JFileChooser.APPROVE_OPTION) {
              File file = imageFileChooser.getSelectedFile();
              editorTextArea.append(
                      entry.getValue() + " " + file.getPath() + System.lineSeparator());
              for (JButton key : editorButtonMap.keySet()) {
                key.setEnabled(true);
              }
            }
          });
          break;
        case "mosaic":
          entry.getKey().addActionListener(l -> {
            JSlider editorSeedSlider = createSeedSlider();
            int sliderDialogResponse = seedSliderDialog(editorSeedSlider, editorFrame);
            if (!(sliderDialogResponse == JOptionPane.CLOSED_OPTION
                    || sliderDialogResponse == JOptionPane.CANCEL_OPTION)) {
              editorTextArea.append(
                      entry.getValue() + " " + editorSeedSlider.getValue()
                              + System.lineSeparator());
            }
          });
          break;
        default:
          entry.getKey().addActionListener(l -> {
            editorTextArea.append(entry.getValue() + System.lineSeparator());
          });
      }
    }
    editorFrame.add(editorButtonPanel, BorderLayout.PAGE_END);

    editorFrame.setVisible(true);
  }
}
