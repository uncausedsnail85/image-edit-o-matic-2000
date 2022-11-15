package images;

/**
 * Class that represents a valid 3D array containing a 24bit color image.
 */
public class RgbArray24Bit {

  private static int max = 255;
  private int[][][] rgbArray;


  /**
   * Constructor for the 24 bit rgb array that will clamp values when relevant.
   *
   * @param rgbArray a 3D array with depth of 3, one for each color channel
   */
  public RgbArray24Bit(int[][][] rgbArray) {
    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {
          if (rgbArray[r][c][z] > max) {
            rgbArray[r][c][z] = max;
          }
          if (rgbArray[r][c][z] < 0) {
            rgbArray[r][c][z] = 0;
          }
        }
      }
    }
    this.rgbArray = rgbArray;
  }

  /**
   * Returns the 3D array of RGB values of this image.
   *
   * @return the 3D array of RGB values of this image
   */
  public int[][][] toArray() {
    return this.rgbArray;
  }

  /**
   * Updates the current image by updating with a new array.
   *
   * @param rgbArray new array to replace the previous array
   */
  public void updateArray(int[][][] rgbArray) {
    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {
          if (rgbArray[r][c][z] > max) {
            rgbArray[r][c][z] = max;
          }
          if (rgbArray[r][c][z] < 0) {
            rgbArray[r][c][z] = 0;
          }
        }
      }
    }
    this.rgbArray = rgbArray;
  }

  /**
   * Returns the string representation of the 3D image. Row and column arranged based on pixel
   * coordinate containing all 3 channels.
   *
   * @return the string representation of the 3D image
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {
          if (z == 0) {
            result.append("R: ");
          } else if (z == 1) {
            result.append("G: ");
          } else if (z == 2) {
            result.append("B: ");
          }
          result.append(String.format("%03d", rgbArray[r][c][z]));
          result.append(' ');
        }
      }
      result.append("\n");
    }
    return result.toString();
  }
}
