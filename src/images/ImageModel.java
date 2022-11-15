package images;

import java.awt.image.BufferedImage;

/**
 * Interface for the image model.
 */
public interface ImageModel {

  /**
   * Load an image into the image model.
   *
   *
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong loading the image
   */
  public void loadImage(String filename) throws IllegalArgumentException;

  /**
   * Save the data in the image model to a file.
   *
   *
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong saving the file
   */
  public void saveImage(String filename) throws IllegalArgumentException;

  /**
   * Apply the blur filter to the data in the image model.
   */
  public void applyBlur();

  /**
   * Apply the sharpen filter to the data in the image model.
   */
  public void applySharpen();

  /**
   * Apply the grayscale color transformation to the data in the image model.
   */
  public void applyGrayscale();

  /**
   * Apply the sepia color transformation to the data in the image model.
   */
  public void applySepia();

  /**
   * Apply the dithering effect to the data in the image model.
   */
  public void applyDither();

  /**
   * Apply the mosaic effect to the data in the image model.
   *
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  public void applyMosaic(int seeds) throws IllegalArgumentException;

  /**
   * Produces a grayscale image where edges (areas of high contrast) are highlighted.
   */
  public void applyEdgeDetection();

  /**
   * Crops the image by discarding pixels before the lower bounds and after the upper bounds.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX end index of the crop for the x-axis
   * @param endIndexY end index of the crop for the y-axis
   */
  public void crop(int startIndexX, int startIndexY, int endIndexX, int endIndexY);

  /**
   * Returns a BufferedImage of the image cropped for preview.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX end index of the crop for the x-axis
   * @param endIndexY end index of the crop for the y-axis
   *
   * @return cropped image of the stored image to preview
   */
  public BufferedImage cropPreview(int startIndexX, int startIndexY, int endIndexX, int endIndexY);

  /**
   * Equalizes the images' histogram. Best on greyscale images but will work on color images with
   *  by equalizing each channel separately.
   */
  public void applyHistogramEqualization();

  /**
   * Returns the image stored in the model as a BufferedImage.
   *
   * @return the image stored in the model as a BufferedImage.
   */
  public BufferedImage getBufferedImage();
}
