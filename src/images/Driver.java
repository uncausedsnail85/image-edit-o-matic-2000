package images;

/**
 * Driver class to load, save and filter images.
 */
public class Driver {

  /**
   * Main.
   *
   * @param args main
   */
  public static void main(String[] args) {

    // String filename = "manhattan-small";
    String filename = "C:\\Users\\bryan\\Documents\\cs5004\\hw09\\manhattan-small-grayscale";

    ImageModel model = new ConcreteImageModel();

    model.loadImage(filename + ".png");
    model.applyEdgeDetection();
    model.saveImage(filename + "-edge-detection.png");

    model.loadImage(filename + ".png");
    model.applyHistogramEqualization();
    model.saveImage(filename + "-equalized.png");

    model.loadImage(filename + ".png");
    model.saveImage(filename + "-2.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred-2.png");

    model.loadImage(filename + ".png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen.png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen-2.png");

    model.loadImage(filename + ".png");
    model.applyGrayscale();
    model.saveImage(filename + "-grayscale.png");

    model.loadImage(filename + ".png");
    model.applySepia();
    model.saveImage(filename + "-sepia.png");

    model.loadImage(filename + ".png");
    model.applyDither();
    model.saveImage(filename + "-dither.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(15000);
    model.saveImage(filename + "manhattan-small-mosaic-15000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(8000);
    model.saveImage(filename + "manhattan-small-mosaic-8000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(4000);
    model.saveImage(filename + "manhattan-small-mosaic-4000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(1000);
    model.saveImage(filename + "manhattan-small-mosaic-1000.png");

    filename = "C:\\Users\\bryan\\Documents\\cs5004-hw09\\res\\code-review";
    // "C:\\Users\\bryan\\Documents\\cs5004\\hw09\\manhattan-small";

    model = new ConcreteImageModel();

    model.loadImage(filename + ".png");
    model.saveImage(filename + "-2.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred-2.png");

    model.loadImage(filename + ".png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen.png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen-2.png");

    model.loadImage(filename + ".png");
    model.applyGrayscale();
    model.saveImage(filename + "-grayscale.png");

    model.loadImage(filename + ".png");
    model.applySepia();
    model.saveImage(filename + "-sepia.png");

    model.loadImage(filename + ".png");
    model.applyDither();
    model.saveImage(filename + "-dither.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(15000);
    model.saveImage(filename + "-mosaic-15000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(8000);
    model.saveImage(filename + "-mosaic-8000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(4000);
    model.saveImage(filename + "-mosaic-4000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(1000);
    model.saveImage(filename + "-mosaic-1000.png");

    filename = "C:\\Users\\bryan\\Documents\\cs5004-hw09\\res\\mondays";

    model = new ConcreteImageModel();

    model.loadImage(filename + ".png");
    model.saveImage(filename + "-2.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred.png");
    model.applyBlur();
    model.saveImage(filename + "-blurred-2.png");

    model.loadImage(filename + ".png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen.png");
    model.applySharpen();
    model.saveImage(filename + "-sharpen-2.png");

    model.loadImage(filename + ".png");
    model.applyGrayscale();
    model.saveImage(filename + "-grayscale.png");

    model.loadImage(filename + ".png");
    model.applySepia();
    model.saveImage(filename + "-sepia.png");

    model.loadImage(filename + ".png");
    model.applyDither();
    model.saveImage(filename + "-dither.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(15000);
    model.saveImage(filename + "-mosaic-15000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(8000);
    model.saveImage(filename + "-mosaic-8000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(4000);
    model.saveImage(filename + "-mosaic-4000.png");

    model.loadImage(filename + ".png");
    model.applyMosaic(1000);
    model.saveImage(filename + "-mosaic-1000.png");

  }
}
