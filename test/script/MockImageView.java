package script;

import java.awt.image.BufferedImage;

/**
 * Mock view that implements ImageView to use for test.
 */
public class MockImageView implements ImageView {

  private StringBuilder viewLog;

  /**
   * Constructor for the mock view.
   */
  public MockImageView(StringBuilder viewLog) {
    this.viewLog = viewLog;
  }

  /**
   * Show a message to the user.
   *
   * @param msg the message to show
   */
  @Override
  public void showMessage(String msg) {
    viewLog.append("show message: " + msg + System.lineSeparator());
  }

  /**
   * Shows users a list of options after an image is loaded and prompts the user for give an input.
   */
  @Override
  public void update(BufferedImage image) {
    viewLog.append("Prompting for input." + System.lineSeparator());
  }

  /**
   * Prompts a user to load an image to start.
   */
  @Override
  public void promptForImage() {
    viewLog.append("Prompting for an image." + System.lineSeparator());
  }

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(Features f) {
    viewLog.append("Seeting features" + System.lineSeparator());
  }
}
