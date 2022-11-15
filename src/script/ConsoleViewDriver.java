package script;

import images.ConcreteImageModel;
import images.ImageModel;
import java.io.InputStreamReader;

/**
 * Driver class to start the MVC model for the image program, using console view.
 */
public class ConsoleViewDriver {
  /**
   * Main class to start the mvc model.
   *
   * @param args null
   */
  public static void main(String[] args) {
    // Console view
    // Create the model
    ImageModel model = new ConcreteImageModel();

    // Create the view
    Readable input = new InputStreamReader(System.in);
    ImageView view = new TextBasedConcreteImageView(System.out, input);
    // Create the controller with the model
    ImageController controller = new ConcreteImageController(model, view);
    controller.go();
  }
}
