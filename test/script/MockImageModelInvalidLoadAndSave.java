package script;

/**
 * Mock image model that throws error when loading and saving.
 */
public class MockImageModelInvalidLoadAndSave extends MockImageModelParent {

  /**
   * Constructor for MockImageModelInvalidLoadAndSave.
   *
   * @param modelLog log to write to
   */
  public MockImageModelInvalidLoadAndSave(StringBuilder modelLog) {
    super(modelLog);
  }

  /**
   * Load an image into the image model.
   *
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong loading the image
   */
  @Override
  public void loadImage(String filename) {
    super.modelLog.append("MODEL: Reading an invalid file.");
    throw new IllegalArgumentException("Error reading image");
  }

  /**
   * Save the data in the image model to a file.
   *
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something
   *                                  goes wrong saving the file
   */
  @Override
  public void saveImage(String filename) {
    super.modelLog.append("MODEL: Saving an invalid file.");
    throw new IllegalArgumentException("Error saving image");

  }
}
