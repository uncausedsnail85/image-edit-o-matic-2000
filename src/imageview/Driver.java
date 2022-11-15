package imageview;

import images.ConcreteImageModel;
import images.ImageModel;
import script.ConcreteImageController;
import script.ImageView;


/**
 * Driver class for the Swing GUI view. Creates and launches the Swing GUI.
 */
public class Driver {
  /**
   * Starting point of the program.
   *
   * @param args Not used
   */
  public static void main(String[] args) {
    // Create the model
    ImageModel model = new ConcreteImageModel();
    // Create the view
    ImageView view = new SwingImageView();

    // Create the controller with the model and the view
    ConcreteImageController controller = new ConcreteImageController(model, view);
    controller.go();
  }
}
