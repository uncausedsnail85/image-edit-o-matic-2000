package script;

import images.ImageModel;
import java.awt.image.BufferedImage;

/**
 * The main mock model for the controller unit tests. Returns all methods as successful.
 */
public class MockImageModelParent implements ImageModel {

  protected StringBuilder modelLog;

  /**
   * Constructor for the mock image model.
   * @param modelLog the log to write to
   */
  public MockImageModelParent(StringBuilder modelLog) {
    this.modelLog = modelLog;
  }

  /**
   * Load an image into the image model.
   *
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong loading the image
   */
  @Override
  public void loadImage(String filename) throws IllegalArgumentException {
    this.modelLog.append("loading image: " + filename + System.lineSeparator());
  }

  /**
   * Save the data in the image model to a file.
   *
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong saving the file
   */
  @Override
  public void saveImage(String filename) throws IllegalArgumentException {
    this.modelLog.append("saving image: " + filename + System.lineSeparator());
  }

  /**
   * Apply the blur filter to the data in the image model.
   */
  @Override
  public void applyBlur() {
    this.modelLog.append("applying blur" + System.lineSeparator());
  }

  /**
   * Apply the sharpen filter to the data in the image model.
   */
  @Override
  public void applySharpen() {
    this.modelLog.append("applying sharpen" + System.lineSeparator());
  }

  /**
   * Apply the grayscale color transformation to the data in the image model.
   */
  @Override
  public void applyGrayscale() {
    this.modelLog.append("applying grayscale" + System.lineSeparator());
  }

  /**
   * Apply the sepia color transformation to the data in the image model.
   */
  @Override
  public void applySepia() {
    this.modelLog.append("applying sepia" + System.lineSeparator());
  }

  /**
   * Apply the dithering effect to the data in the image model.
   */
  @Override
  public void applyDither() {
    this.modelLog.append("applying dither" + System.lineSeparator());
  }

  /**
   * Apply the mosaic effect to the data in the image model.
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) throws IllegalArgumentException {
    this.modelLog.append("applying mosaic, seeds: " + seeds + System.lineSeparator());
  }

  /**
   *
   * Apply the sepia color transformation to the data in the image model.
   */
  @Override
  public void applyEdgeDetection() {
    this.modelLog.append("applying edge detection" + System.lineSeparator());
  }

  /**
   * Crops the image by discarding pixels before the lower bounds and after the upper bounds.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX   end index of the crop for the x-axis
   * @param endIndexY   end index of the crop for the y-axis
   */
  @Override
  public void crop(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    this.modelLog.append("cropping" + System.lineSeparator());
  }

  /**
   * Returns a BufferedImage of the image cropped for preview.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX   end index of the crop for the x-axis
   * @param endIndexY   end index of the crop for the y-axis
   * @return cropped image of the stored image to preview
   */
  @Override
  public BufferedImage cropPreview(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    this.modelLog.append("previewing crop" + System.lineSeparator());
    return null;
  }

  /**
   * Equalizes the images' histogram.
   */
  @Override
  public void applyHistogramEqualization() {
    this.modelLog.append("applying histogram equalization" + System.lineSeparator());
  }

  @Override
  public BufferedImage getBufferedImage() {
    return null;
  }


}
