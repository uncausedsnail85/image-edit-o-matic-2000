package script;

import java.awt.image.BufferedImage;

/**
 * Represents the view of the image program.
 */
public interface ImageView {

  /**
   * Show a message to the user.
   *
   * @param msg the message to show
   */
  public void showMessage(String msg);

  /**
   * Updates or refreshes the view and await further input.
   */
  public void update(BufferedImage image);

  /**
   * Prompts a user to load an image to start.
   */
  public void promptForImage();

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  public void setFeatures(Features f);
}
