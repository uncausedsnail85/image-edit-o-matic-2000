package script;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The concrete Image View class that uses a text output stream as a this.
 */
public class TextBasedConcreteImageView implements ImageView {
  private Appendable out;
  private Readable in;

  /**
   * Constructor for the this.
   *
   * @param out the output to use
   * @throws IllegalArgumentException for invalid parameter values
   */
  public TextBasedConcreteImageView(Appendable out, Readable input)
          throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Invalid output passed to this.");
    }
    this.out = out;
    this.in = input;
  }

  /**
   * Show a message to the user.
   *
   * @param msg the message to show
   */
  @Override
  public void showMessage(String msg) {
    try {
      this.out.append(msg);
      this.out.append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Error occurred writing to view");
    }
  }

  /**
   * Updates or refreshes the view and await further input.
   */
  @Override
  public void update(BufferedImage image) {
    try {
      // showAllOptions();
      this.out.append(String.format("Waiting for a command: "));
      this.out.append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Error occurred writing to view");
    }
  }

  /**
   * Prompts a user to load an image to start.
   */
  @Override
  public void promptForImage() {
    try {
      Map<String, String> optionsMap = new HashMap<>();
      this.out.append(String.format("Please load an image by typing 'load', then followed by the "
              + "image name to begin."));
      this.out.append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Error occurred writing to view");
    }
  }

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(Features f) {
    f.executeBatchCommands(in);
  }

  /**
   * Private method to display all available options to the user.
   */
  private void showAllOptions() {
    Map<String, String> optionsMap = new HashMap<>();
    optionsMap.put("load", "load an image");
    optionsMap.put("save", "save current image");
    optionsMap.put("blur", "blurs current image");
    optionsMap.put("sharpen", "sharpen current image");
    optionsMap.put("greyscale", "applies greyscale to current image");
    optionsMap.put("sepia", "applies a sepia to current image");
    optionsMap.put("dither", "dithers current image in grayscale");
    optionsMap.put("mosaic", "applies a mosaic effect to current image");
    try {
      this.out.append(System.lineSeparator() + "Options:" + System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalStateException("Error occurred writing to view");
    }
    optionsMap.forEach((k, v) -> {
      try {
        this.out.append(String.format("%s - %s", k, v) + System.lineSeparator());
      } catch (IOException e) {
        throw new IllegalStateException("Error occurred writing to view");
      }
    });
  }
}
