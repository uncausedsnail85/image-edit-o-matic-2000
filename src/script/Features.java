package script;

import java.awt.image.BufferedImage;

/**
 * The interface for the features that the program offers to the user. Each feature is a callback
 * function used by the view to pass control to the controller.
 */
public interface Features {
  /**
   * Loads an image from a local directory into the program.
   *
   * @param inputPath the full file path to the image to be loaded
   */
  void loadImage(String inputPath);

  /**
   * Saves the image the program is holding to a local directory.
   *
   * @param outputPath the full file path of the image to be saved
   */
  void saveImage(String outputPath);

  /**
   * Applies a blur filter to the program's image.
   */
  void applyBlur();

  /**
   * Applies a sharpening filter to the program's image.
   */
  void applySharpen();

  /**
   * Apply the grayscale color transformation to the program's image.
   */
  public void applyGrayscale();

  /**
   * Apply the sepia color transformation to the program's image.
   */
  public void applySepia();

  /**
   * Apply the dithering effect to the program's image.
   */
  public void applyDither();

  /**
   * Apply the mosaic effect to the program's image.
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  public void applyMosaic(int seeds) throws IllegalArgumentException;

  /**
   * Crops the stored image.
   */
  public void crop(int startIndexX, int startIndexY, int endIndexX, int endIndexY);

  /**
   * Returns a cropped BufferedImage for preview.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX   end index of the crop for the x-axis
   * @param endIndexY   end index of the crop for the y-axis
   * @return cropped image of the stored image to preview
   */
  public BufferedImage cropPreview(int startIndexX, int startIndexY, int endIndexX, int endIndexY);

  /**
   * Produces a grayscale image where edges (areas of high contrast) are highlighted.
   */
  public void applyEdgeDetection();

  /**
   * Equalizes the images' histogram. Best on greyscale images but will work on color images with
   * by equalizing each channel separately.
   */
  public void applyHistogramEqualization();

  /**
   * Executes a sequence of features.
   *
   * @param in a command separated by whitespace
   */
  public void executeBatchCommands(Readable in);

  /**
   * Returns a BufferedImage from the model.
   *
   * @return a BufferedImage from the model
   */
  public BufferedImage getBufferedImage();

}
