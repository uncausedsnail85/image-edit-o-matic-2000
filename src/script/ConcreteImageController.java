package script;

import images.ImageModel;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * The controller class for the image program. Provides a controller to handle user inputs to handle
 * images, including loading, saving and filtering the images.
 */
public class ConcreteImageController implements ImageController, Features {

  private ImageView view;
  private ImageModel model;

  /**
   * Constructor for the ConcreteImageController.
   *
   * @param m  model that the controller uses
   * @param v  view that the controller uses
   */
  public ConcreteImageController(ImageModel m, ImageView v) {
    this.model = m;
    this.view = v;
  }

  /**
   * Gives the control over to the controller and executes the program flow.
   */
  @Override
  public void go() {
    this.view.setFeatures(this);
  }

  /**
   * Loads an image from a local directory into the program.
   *
   * @param inputPath the full file path to the image to be loaded
   */
  @Override
  public void loadImage(String inputPath) {
    this.model.loadImage(inputPath);
  }

  /**
   * Saves the image the program is holding to a local directory.
   *
   * @param outputPath the full file path of the image to be saved
   */
  @Override
  public void saveImage(String outputPath) {
    this.model.saveImage(outputPath);
  }

  /**
   * Applies a blur filter to the program's image.
   */
  @Override
  public void applyBlur() {
    this.model.applyBlur();
  }

  /**
   * Applies a sharpening filter to the program's image.
   */
  @Override
  public void applySharpen() {
    this.model.applySharpen();
  }

  /**
   * Apply the grayscale color transformation to the program's image.
   */
  @Override
  public void applyGrayscale() {
    this.model.applyGrayscale();
  }

  /**
   * Apply the sepia color transformation to the program's image.
   */
  @Override
  public void applySepia() {
    this.model.applySepia();
  }

  /**
   * Apply the dithering effect to the program's image.
   */
  @Override
  public void applyDither() {
    this.model.applyDither();
  }

  /**
   * Apply the mosaic effect to the program's image.
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) throws IllegalArgumentException {
    view.showMessage("Applying a mosaic with " + seeds + " seeds. (this might take a while)");
    this.model.applyMosaic(seeds);
  }

  /**
   * Produces a cropped image.
   */
  @Override
  public void crop(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    this.model.crop(startIndexX, startIndexY, endIndexX, endIndexY);
  }

  /**
   * Returns a cropped BufferedImage for preview.
   *
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX   end index of the crop for the x-axis
   * @param endIndexY   end index of the crop for the y-axis
   * @return cropped image of the stored image to preview
   */
  @Override
  public BufferedImage cropPreview(int startIndexX, int startIndexY, int endIndexX, int endIndexY) {
    return this.model.cropPreview(startIndexX, startIndexY, endIndexX, endIndexY);
  }

  /**
   * Produces a grayscale image where edges (areas of high contrast) are highlighted.
   */
  @Override
  public void applyEdgeDetection() {
    this.model.applyEdgeDetection();
  }

  /**
   * Equalizes the images' histogram. Best on greyscale images but will work on color images with
   * by equalizing each channel separately.
   */
  @Override
  public void applyHistogramEqualization() {
    this.model.applyHistogramEqualization();
  }

  /**
   * Executes a sequence of features.
   *
   * @param in a command separated by whitespace
   */
  @Override
  public void executeBatchCommands(Readable in) {
    view.showMessage("Program has started.");
    view.promptForImage();

    boolean isImageLoaded = false;

    Scanner scanner = new Scanner(in);
    while (scanner.hasNext()) {
      String option = scanner.next();
      // First try-catch to catch IllegalStates when filters are applied before image is loaded
      try {
        // based on command give, try different actions.
        switch (option.toLowerCase()) {
          case "load":
            try {
              view.showMessage("Load the image:");
              String imageIn = scanner.next();
              view.showMessage("Loading " + imageIn);
              this.loadImage(imageIn);
              view.showMessage("Loaded " + imageIn);
              isImageLoaded = true;
              break;
            } catch (IllegalArgumentException e) {
              view.showMessage(e.getMessage());
              break;
            }
          case "save":
            try {
              view.showMessage("Save the image:");
              String imageOut = scanner.next();
              view.showMessage("Saving " + imageOut);
              this.saveImage(imageOut);
              view.showMessage("Image saved as: " + imageOut);
              break;
            } catch (IllegalArgumentException e) {
              view.showMessage(e.getMessage());
              break;
            }

          case "blur":
            view.showMessage("Applying blur.");
            this.applyBlur();
            view.showMessage("Applied blur.");
            break;

          case "sharpen":
            view.showMessage("Applying sharpen.");
            this.applySharpen();
            view.showMessage("Applied sharpen.");
            break;

          case "grayscale":
            view.showMessage("Applying grayscale.");
            this.applyGrayscale();
            view.showMessage("Applied grayscale.");
            break;

          case "sepia":
            view.showMessage("Applying sepia.");
            this.applySepia();
            view.showMessage("Applied sepia.");
            break;

          case "dither":
            view.showMessage("Applying dithering.");
            this.applyDither();
            view.showMessage("Applied dithering.");
            break;

          case "mosaic":
            if (scanner.hasNextInt()) {
              try {
                int seeds = scanner.nextInt();
                this.applyMosaic(seeds);
                view.showMessage(String.format("Mosaic applied: %d seeds", seeds));
                break;
              } catch (IllegalArgumentException e) {
                view.showMessage(e.getMessage());
                break;
              }
            } else {
              String mosaicSeed = scanner.next();
              view.showMessage(String.format(
                      "Error applying Mosaic, \"%s\" not a integer.", mosaicSeed));
              break;
            }

          case "edgedetection":
            view.showMessage("Applying edge detection.");
            this.applyEdgeDetection();
            view.showMessage("Applied edge detection.");
            break;

          case "histogramequalization":
            view.showMessage("Applying histogram equalization.");
            this.applyHistogramEqualization();
            view.showMessage("Applied histogram equalization.");
            break;

          case "quit":
          case "exit":
            view.showMessage("Exiting program.");
            return;

          default:
            view.showMessage("Command not recognized: " + option);
        }
      } catch (IllegalStateException e) {
        view.showMessage(e.getMessage());
      }

      if (isImageLoaded) {
        view.update(this.getBufferedImage());
      } else {
        view.promptForImage();
      }
    }
  }

  /**
   * Returns a BufferedImage from the model.
   *
   * @return a BufferedImage from the model
   */
  @Override
  public BufferedImage getBufferedImage() {
    return this.model.getBufferedImage();
  }

}
