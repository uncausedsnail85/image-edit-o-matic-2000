package script;

/**
 * Mock image model that throws error when applyingMosiac.
 */
public class MockImageModelIllegalMosaicSeed extends MockImageModelParent {

  /**
   * Constructor for MockImageModelIllegalMosaicSeed.
   *
   * @param modelLog log to write to
   */
  public MockImageModelIllegalMosaicSeed(StringBuilder modelLog) {
    super(modelLog);
  }

  /**
   * Apply the mosaic effect to the data in the image model.
   *
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) {
    if (seeds <= 0) {
      super.modelLog.append("MODEL: mosaic seeds <=0" + System.lineSeparator());
      throw new IllegalArgumentException("Mosaic seeds can't be <= 0");
    }
  }
}
