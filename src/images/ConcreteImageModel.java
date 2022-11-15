package images;

import java.awt.image.BufferedImage;

/**
 * The model class acting as a facade for the entire model.
 */
public class ConcreteImageModel implements ImageModel {

  private RgbArray24Bit rgbData;
  private int width;
  private int height;

  /**
   * Constructor for ConcreteImageModel.
   */
  public ConcreteImageModel() {
    this.rgbData = null;
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
    this.rgbData = new RgbArray24Bit(ImageUtilities.readImage(filename));
  }

  /**
   * Save the data in the image model to a file.
   *
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong saving the file
   */
  @Override
  public void saveImage(String filename) throws IllegalArgumentException, IllegalStateException {
    checkForImage("saving");
    ImageUtilities.writeImage(this.rgbData.toArray(), filename);
  }

  /**
   * Apply the blur filter to the data in the image model.
   */
  @Override
  public void applyBlur() throws IllegalStateException {
    checkForImage("applying blur");
    this.rgbData.updateArray(ArrayFilter.applyBlur(this.rgbData.toArray()));
  }

  /**
   * Apply the sharpen filter to the data in the image model.
   */
  @Override
  public void applySharpen() throws IllegalStateException {
    checkForImage("applying sharpen");
    this.rgbData.updateArray(
            ArrayFilter.applySharpen(this.rgbData.toArray()));
  }

  /**
   * Apply the grayscale color transformation to the data in the image model.
   */
  @Override
  public void applyGrayscale() throws IllegalStateException {
    checkForImage("applying grayscale");
    this.rgbData.updateArray(
            ArrayFilter.applyGreyscale(this.rgbData.toArray())
    );
  }

  /**
   * Apply the sepia color transformation to the data in the image model.
   */
  @Override
  public void applySepia() throws IllegalStateException {
    checkForImage("applying sepia");
    this.rgbData.updateArray(
            ArrayFilter.applySepia(this.rgbData.toArray())
    );
  }

  /**
   * Apply the dithering effect to the data in the image model.
   */
  @Override
  public void applyDither() throws IllegalStateException {
    checkForImage("applying dithering");
    this.rgbData.updateArray(
            ArrayFilter.applyFloydSteinbergDither(this.rgbData.toArray())
    );
  }

  /**
   * Apply the mosaic effect to the data in the image model. Not yet implemented
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) throws IllegalArgumentException, IllegalStateException {
    checkForImage("applying mosaic");
    this.rgbData.updateArray(
            ArrayFilter.applyMosaic(this.rgbData.toArray(), seeds)
    );
  }

  /**
   * Produces a grayscale image where edges (areas of high contrast) are highlighted.
   */
  @Override
  public void applyEdgeDetection() {
    checkForImage("applying edge detection");
    this.rgbData.updateArray(
            ArrayFilter.applySobelEdgeDetection(this.rgbData.toArray())
    );
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
    checkForImage("cropping");
    this.rgbData.updateArray(
            ArrayFilter.crop(this.rgbData.toArray(), startIndexX, startIndexY, endIndexX, endIndexY)
    );
  }

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
  @Override
  public BufferedImage cropPreview(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    return ImageUtilities.arrayToBufferedImage(
            ArrayFilter.crop(this.rgbData.toArray(), startIndexX, startIndexY, endIndexX, endIndexY)
    );
  }

  /**
   * Equalizes the images' histogram.
   */
  @Override
  public void applyHistogramEqualization() {
    checkForImage("applying histogram equalization");
    this.rgbData.updateArray(
            ArrayFilter.histogramEqualization(this.rgbData.toArray())
    );
  }

  @Override
  public BufferedImage getBufferedImage() {
    return ImageUtilities.arrayToBufferedImage(this.rgbData.toArray());
  }

  /**
   * Returns the image array as a String.
   *
   * @return the image array as a String
   */
  @Override
  public String toString() {
    return this.rgbData.toString();
  }

  private void checkForImage(String s) {
    if (this.rgbData == null) {
      throw new IllegalStateException(String.format("Image must be loaded before %s.", s));
    }
  }

}
