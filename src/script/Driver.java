package script;

import images.ConcreteImageModel;
import images.ImageModel;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Driver class to start the MVC model for the image program,using script files.
 */
public class Driver {

  /**
   * Main class that reads a script for running the image program.
   *
   * @param args First parameter must the file path to the script to load
   */
  public static void main(String[] args) {

    System.out.println("Starting program.");

    // Script view
    if (args.length < 1) {
      System.out.println(
              "Invalid run, please include a script file as the first command-line argument.");
      return;
    }
    String inputFile = args[0];

    System.out.println("Attempting to use: " + inputFile);
    try {
      Readable input = new FileReader(inputFile);

      ImageModel model = new ConcreteImageModel();
      ImageView view = new TextBasedConcreteImageView(System.out, input);
      ImageController controller = new ConcreteImageController(model, view);

      controller.go();
    } catch (FileNotFoundException e) {
      System.out.println("Script file not found.");
      return;
    }


  }
}
