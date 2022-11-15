package script;

import static org.junit.Assert.assertEquals;

import images.ImageModel;
import java.io.FileNotFoundException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Contains all the unit tests for the ConcreteImageController.
 */
public class ConcreteImageControllerTest {

  private StringBuilder viewLog;
  private ImageView v;

  /**
   * Setup for tests.
   */
  @Before
  public void setup() throws FileNotFoundException {
    // Create view
    this.viewLog = new StringBuilder();
    v = new MockImageView(viewLog);
  }

  /**
   * Tests all commands work as expected.
   */
  @Test
  public void testAllCommandsWorkingScript() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load.jpg blur sharpen grayscale sepia"
            + " dither mosaic 8000 save mock-save.jpg quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying blur." + System.lineSeparator()
            + "show message: Applied blur." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying sharpen." + System.lineSeparator()
            + "show message: Applied sharpen." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying grayscale." + System.lineSeparator()
            + "show message: Applied grayscale." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying sepia." + System.lineSeparator()
            + "show message: Applied sepia." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying dithering." + System.lineSeparator()
            + "show message: Applied dithering." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying a mosaic with 8000 seeds. (this might take a while)"
            + System.lineSeparator()
            + "show message: Mosaic applied: 8000 seeds" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Save the image:" + System.lineSeparator()
            + "show message: Saving mock-save.jpg" + System.lineSeparator()
            + "show message: Image saved as: mock-save.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator()
            + "applying blur" + System.lineSeparator()
            + "applying sharpen" + System.lineSeparator()
            + "applying grayscale" + System.lineSeparator()
            + "applying sepia" + System.lineSeparator()
            + "applying dither" + System.lineSeparator()
            + "applying mosaic, seeds: 8000" + System.lineSeparator()
            + "saving image: mock-save.jpg" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());

  }

  /**
   * Test that the controller prompts for an image before it is loaded, and prompts for options
   * after an image are loaded.
   */
  @Test
  public void testPrompts() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load.jpg quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Test that the controller quits with quit command even with inputs after.
   */
  @Test
  public void testQuit() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load.jpg quit blur");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests invalid commands give the view proper user-friendly messages.
   */
  @Test
  public void testInvalidOptions() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("a A 123 !!! \n 3.149 quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Command not recognized: a" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Command not recognized: A" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Command not recognized: 123" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Command not recognized: !!!" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Command not recognized: 3.149" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "";
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Test that the commands are case-insensitive and works as expected.
   */
  @Test
  public void testCaseInsensitiveValidOptions() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("LOAD mock-load.jpg BLUR ShArPeN exit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying blur." + System.lineSeparator()
            + "show message: Applied blur." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying sharpen." + System.lineSeparator()
            + "show message: Applied sharpen." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator()
            + "applying blur" + System.lineSeparator()
            + "applying sharpen" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that the readable still works without a quit command.
   */
  @Test
  public void testInputWithNoExitCommand() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelParent(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load.jpg blur");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying blur." + System.lineSeparator()
            + "show message: Applied blur." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator()
            + "applying blur" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that applying a filter before loading an image gives error message to user.
   */
  @Test
  public void testFilterBeforeLoad() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelBlurBeforeLoad(modelLog);

    // Create controller
    StringReader input = new StringReader("blur quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Applying blur." + System.lineSeparator()
            + "show message: Can't apply blur before loading." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "MODEL: Error applying blur.";
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that saving before loading an image gives error message to user.
   */
  @Test
  public void testSaveBeforeLoading() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelSaveBeforeLoad(modelLog);

    // Create controller
    StringReader input = new StringReader("save mock-save.jpg quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Save the image:" + System.lineSeparator()
            + "show message: Saving mock-save.jpg" + System.lineSeparator()
            + "show message: Can't save image before loading." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "MODEL: Saving image before loading.";
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that providing negative, 0 or String as a mosaic seed gives error message to user.
   */
  @Test
  public void testIllegalMosaicSeed() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelIllegalMosaicSeed(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load.jpg "
            + "mosaic a "
            + "mosaic -1 "
            + "mosaic 0 quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load.jpg" + System.lineSeparator()
            + "show message: Loaded mock-load.jpg" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Error applying Mosaic, \"a\" not a integer." + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying a mosaic with -1 seeds. (this might take a while)"
            + System.lineSeparator()
            + "show message: Mosaic seeds can't be <= 0" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Applying a mosaic with 0 seeds. (this might take a while)"
            + System.lineSeparator()
            + "show message: Mosaic seeds can't be <= 0" + System.lineSeparator()
            + "Prompting for input." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "loading image: mock-load.jpg" + System.lineSeparator()
            + "MODEL: mosaic seeds <=0" + System.lineSeparator()
            + "MODEL: mosaic seeds <=0" + System.lineSeparator();
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that loading an invalid image gives error message to user.
   */
  @Test
  public void testLoadInvalidFile() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelInvalidLoadAndSave(modelLog);

    // Create controller
    StringReader input = new StringReader("load mock-load-doesnt-exist.file quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Load the image:" + System.lineSeparator()
            + "show message: Loading mock-load-doesnt-exist.file" + System.lineSeparator()
            + "show message: Error reading image" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "MODEL: Reading an invalid file.";
    assertEquals(expectedModel, modelLog.toString());
  }

  /**
   * Tests that saving an invalid image gives error message to user.
   */
  @Test
  public void testSaveInvalidFile() {
    //Create model
    StringBuilder modelLog = new StringBuilder();
    ImageModel m = new MockImageModelInvalidLoadAndSave(modelLog);

    // Create controller
    StringReader input = new StringReader("save bad-save.file quit");
    ConcreteImageController c = new ConcreteImageController(m, v);

    c.executeBatchCommands(input);

    // Check view log
    String expectedView = "show message: Program has started." + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Save the image:" + System.lineSeparator()
            + "show message: Saving bad-save.file" + System.lineSeparator()
            + "show message: Error saving image" + System.lineSeparator()
            + "Prompting for an image." + System.lineSeparator()
            + "show message: Exiting program." + System.lineSeparator();
    assertEquals(expectedView, this.viewLog.toString());

    // Check model log
    String expectedModel = "MODEL: Saving an invalid file.";
    assertEquals(expectedModel, modelLog.toString());
  }
}