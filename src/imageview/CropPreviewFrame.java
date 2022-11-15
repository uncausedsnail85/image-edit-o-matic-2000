package imageview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import script.Features;
import script.ImageView;

/**
 * JFrame for the cropping window.
 */
public class CropPreviewFrame extends JFrame {

  private static int locationX = 250;
  private static int locationY = 250;

  private Features features;

  private JLabel cropLabel;
  private JButton resetButton;
  private JButton acceptButton;
  private ImageView mainFrame;

  private int cropStartX;
  private int cropStartY;
  private int cropCurrentX;
  private int cropCurrentY;
  private boolean toDraw;

  /**
   * Constructs and displays the crop window.
   *
   * @param f controller features required
   * @param mainFrame the main JFrame that this window is connected to
   */
  public CropPreviewFrame(Features f, ImageView mainFrame) {
    super("Crop");
    this.features = f;
    this.mainFrame = mainFrame;
    BufferedImage img = f.getBufferedImage();

    // Setup frame
    setSize(img.getWidth(), img.getHeight());
    setLocation(locationX, locationY);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create label with custom paint method. Paint is called when repaint() is called.
    // Override paint method in JLabel to draw a cropping rectangle
    cropLabel = new JLabel("", JLabel.CENTER) {
      @Override
      public void paint(Graphics g) {
        super.paint(g);
        drawRectangle(g);
      }
    };

    cropLabel.setIcon(new ImageIcon(img));

    addCropMouseListeners();
    JScrollPane cropScrollPane = new JScrollPane(cropLabel);
    add(cropScrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    resetButton = new JButton("Reset");
    acceptButton = new JButton("Accept");
    buttonPanel.add(resetButton);
    buttonPanel.add(acceptButton);
    add(buttonPanel, BorderLayout.PAGE_END);
    addButtonListeners(f);

    pack();
    setResizable(false);
    setVisible(true);
  }

  /**
   * Adds mouse listener to the JLabel to track mouse movements.
   */
  private void addCropMouseListeners() {

    cropLabel.addMouseListener(new MouseListener() {
      /**
       * Invoked when the mouse button has been clicked (pressed
       * and released) on a component.
       *
       * @param e the event to be processed
       */
      @Override
      public void mouseClicked(MouseEvent e) {

      }

      /**
       * Invoked when a mouse button has been pressed on a component.
       *
       * @param e the event to be processed
       */
      @Override
      public void mousePressed(MouseEvent e) {
        // Set rectangle flag to true and grab location of press
        toggleToDraw(true);
        updateCropStartCoordinates(e.getX(), e.getY());
      }

      /**
       * Invoked when a mouse button has been released on a component.
       *
       * @param e the event to be processed
       */
      @Override
      public void mouseReleased(MouseEvent e) {
        // Set rectangle flag to false and update window with crop preview
        toggleToDraw(false);
        cropPreviewWindowUpdate();
      }

      /**
       * Invoked when the mouse enters a component.
       *
       * @param e the event to be processed
       */
      @Override
      public void mouseEntered(MouseEvent e) {

      }

      /**
       * Invoked when the mouse exits a component.
       *
       * @param e the event to be processed
       */
      @Override
      public void mouseExited(MouseEvent e) {
      }
    });

    cropLabel.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {
        // If cursor out of bounds of component, use bounds of component
        int currentX = e.getX() < 0 ? 0 : e.getX();
        currentX = e.getX() > cropLabel.getWidth() ? cropLabel.getWidth() : currentX;
        int currentY = e.getY() < 0 ? 0 : e.getY();
        currentY = e.getY() > cropLabel.getHeight() ? cropLabel.getHeight() : currentY;
        // save coordinates to fields and repaint JLabel
        updateCropCurrentCoordinates(currentX, currentY);
        cropLabel.repaint();
      }

      @Override
      public void mouseMoved(MouseEvent e) {

      }
    });
  }

  /**
   * Adds listeners to the accept and reset buttons.
   *
   * @param f controller features required to obtain image from model
   */
  private void addButtonListeners(Features f) {
    resetButton.addActionListener(l -> {
      this.cropLabel.setIcon(new ImageIcon(f.getBufferedImage()));
      pack();
    });
    acceptButton.addActionListener(l -> {
      // orders start and end indexes
      int cropStartX = Math.min(this.cropStartX, this.cropCurrentX);
      int cropEndX = Math.max(this.cropStartX, this.cropCurrentX);
      int cropStartY = Math.min(this.cropStartY, this.cropCurrentY);
      int cropEndY = Math.max(this.cropStartY, this.cropCurrentY);
      f.crop(cropStartX, cropStartY, cropEndX, cropEndY);
      // update main frame
      this.mainFrame.showMessage("Cropped");
      this.mainFrame.update(f.getBufferedImage());
      // closes frame
      dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    });
  }

  /**
   * Updates the crop window with a preview of the crop.
   */
  private void cropPreviewWindowUpdate() {
    // orders start and end indexes
    int cropStartX = Math.min(this.cropStartX, this.cropCurrentX);
    int cropEndX = Math.max(this.cropStartX, this.cropCurrentX);
    int cropStartY = Math.min(this.cropStartY, this.cropCurrentY);
    int cropEndY = Math.max(this.cropStartY, this.cropCurrentY);
    BufferedImage img = this.features.cropPreview(cropStartX, cropStartY, cropEndX, cropEndY);
    this.cropLabel.setIcon(new ImageIcon(img));
    pack();
  }

  /**
   * Draws the rectangle.
   *
   * @param g Graphics
   */
  private void drawRectangle(Graphics g) {
    Graphics2D graphics = (Graphics2D) g;
    // If rect flag is true, draw rect. otherwise, draw rect of 0 width and height.
    if (this.toDraw) {
      int drawStartX;
      int drawStartY;
      int drawHeight;
      int drawWidth;
      // keeps values positive for rectangle
      if (this.cropCurrentX - this.cropStartX > 0) {
        drawStartX = cropStartX;
        drawWidth = this.cropCurrentX - this.cropStartX;
      } else {
        drawStartX = cropCurrentX;
        drawWidth = this.cropStartX - this.cropCurrentX;
      }
      if (this.cropCurrentY - this.cropStartY > 0) {
        drawStartY = cropStartY;
        drawHeight = this.cropCurrentY - this.cropStartY;
      } else {
        drawStartY = cropCurrentY;
        drawHeight = this.cropStartY - this.cropCurrentY;
      }
      graphics.drawRect(drawStartX, drawStartY, drawWidth, drawHeight);
    } else {
      graphics.drawRect(0, 0, 0, 0);
    }
  }

  // Updates starting coordinates for the rect and image crop
  private void updateCropStartCoordinates(int startX, int startY) {
    this.cropStartX = startX;
    this.cropStartY = startY;
  }

  // Updates end coordinates for rect and image crop
  private void updateCropCurrentCoordinates(int currentX, int currentY) {
    this.cropCurrentX = currentX;
    this.cropCurrentY = currentY;
  }

  // Updates whether the rectangle should be drawn or not
  private void toggleToDraw(Boolean bool) {
    this.toDraw = bool;
  }
}
