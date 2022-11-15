package script;

/**
 * Mock image model that throws error when applying blur.
 */
public class MockImageModelBlurBeforeLoad extends MockImageModelParent {

  /**
   * Constructor for MockImageModelBlurBeforeLoad.
   * @param modelLog log to write to
   */
  public MockImageModelBlurBeforeLoad(StringBuilder modelLog) {
    super(modelLog);
  }


  /**
   * Apply the blur filter to the data in the image model.
   */
  @Override
  public void applyBlur() {
    super.modelLog.append("MODEL: Error applying blur.");
    throw new IllegalStateException("Can't apply blur before loading.");
  }
}
