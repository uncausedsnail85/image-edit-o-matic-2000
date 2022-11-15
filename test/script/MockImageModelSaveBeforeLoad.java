package script;

/**
 * Mock image model that throws error when saving.
 */
public class MockImageModelSaveBeforeLoad extends MockImageModelParent {

  /**
   * Constructor for MockImageModelSaveBeforeLoad.
   * @param modelLog log to write to
   */
  public MockImageModelSaveBeforeLoad(StringBuilder modelLog) {
    super(modelLog);
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
    super.modelLog.append("MODEL: Saving image before loading.");
    throw new IllegalStateException("Can't save image before loading.");
  }
}
