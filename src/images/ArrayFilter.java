package images;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A utility class for filtering and transforming 3-dimensional RGB image arrays.
 */
public final class ArrayFilter {

  // Filter variables
  private static double[][] blurKernel = {
          {0.0625, 0.125, 0.0625},
          {0.125, 0.25, 0.125},
          {0.0625, 0.125, 0.0625}
  };
  private static double[][] sharpenKernel = {
          {-0.125, -0.125, -0.125, -0.125, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, 0.25, 1, 0.25, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, -0.125, -0.125, -0.125, -0.125}
  };

  // Color transformation variables
  private static double[][] greyscaleMatrix = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  };
  private static double[][] sepiaMatrix = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };

  private static double[][] sobelEdgeX = {
          {1, 0, -1},
          {2, 0, -2},
          {1, 0, -1}
  };

  private static double[][] sobelEdgeY = {
          {1, 2, 1},
          {0, 0, 0},
          {-1, -2, -1}
  };


  // Private constructor
  private ArrayFilter() {
  }

  /*
   * ###################################### Filter methods ######################################
   */

  /**
   * Filters a 3D RGB image array to create a blur effect.
   *
   * @param rgbArray the RGB image array to be transformed
   * @return a 3D array of the transformed image
   */
  public static int[][][] applyBlur(int[][][] rgbArray) {
    return applyKernel(rgbArray, blurKernel);
  }

  /**
   * Filters a 3D RGB image array to create a sharpening effect.
   *
   * @param rgbArray the RGB image array to be transformed
   * @return a 3D array of the transformed image
   */
  public static int[][][] applySharpen(int[][][] rgbArray) {
    return applyKernel(rgbArray, sharpenKernel);
  }

  /**
   * Private method for applying a filter to each pixel in a 3D RGB image array. Filters each pixel
   * based on the values of pixels around it's location in the same color channel.
   *
   * @param rgbArray the RGB image array to be transformed
   * @param kernel   the filter kernel to use
   * @return a 3D array of the transformed image
   */
  private static int[][][] applyKernel(int[][][] rgbArray, double[][] kernel) {

    int[][][] finalArray = deepCopy3dArray(rgbArray);

    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {

          int distanceToCenterX;
          int distanceToCenterY;
          double filterResult = 0;

          for (int kernelR = 0; kernelR < kernel.length; kernelR++) {
            for (int kernelC = 0; kernelC < kernel.length; kernelC++) {

              // Relative distance to center of kernel. Where the center is (kernel.length - 1) / 2
              distanceToCenterX = kernelR - ((kernel.length - 1) / 2);
              distanceToCenterY = kernelC - ((kernel.length - 1) / 2);

              // Check for out of bounds, if so do not apply
              if (r + distanceToCenterX >= rgbArray.length
                      || c + distanceToCenterY >= rgbArray[r].length
                      || r + distanceToCenterX < 0
                      || c + distanceToCenterY < 0) {
                continue;
              }
              filterResult = filterResult
                      + (rgbArray[r + distanceToCenterX][c + distanceToCenterY][z]
                      * kernel[kernelR][kernelC]);
            }
          }
          // Collect sum as a double then round at the end for more accurate results.
          finalArray[r][c][z] = (int) Math.round(filterResult);
        }
      }
    }
    return finalArray;
  }

  /*
   * ################################ Color transformation methods ################################
   */

  /**
   * Applies a greyscale color transformation to an 3D RGB image array.
   *
   * @param rgbArray the RGB image array to be transformed
   * @return a 3D array of the transformed image
   */
  public static int[][][] applyGreyscale(int[][][] rgbArray) {
    return applyColorTransformation(rgbArray, greyscaleMatrix);
  }

  /**
   * Applies a sepia color transformation to an 3D RGB image array.
   *
   * @param rgbArray the RGB image array to be transformed
   * @return a 3D array of the transformed image
   */
  public static int[][][] applySepia(int[][][] rgbArray) {
    return applyColorTransformation(rgbArray, sepiaMatrix);
  }

  /**
   * Private method to apply a color transformation matrix to an image array. Transforms each pixel
   * based on RGB values at the same coordinate.
   *
   * @param rgbArray             the RGB image array to be transformed
   * @param transformationMatrix the 3x3 color transformation matrix to apply to each pixel
   * @return a 3D array of the transformed image
   */
  private static int[][][] applyColorTransformation(int[][][] rgbArray,
                                                    double[][] transformationMatrix) {

    int[][][] finalArray = deepCopy3dArray(rgbArray);
    int[] pixelRgbValues = new int[3];
    int[] transformedPixelRgbValues = new int[3];

    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {

        // Reset values
        Arrays.fill(pixelRgbValues, 0);
        Arrays.fill(transformedPixelRgbValues, 0);

        // Get matrix of rgb values at each r and c position
        for (int z = 0; z < pixelRgbValues.length; z++) {
          pixelRgbValues[z] = rgbArray[r][c][z];
        }
        // Generate new rgb matrix at each r and c position
        transformedPixelRgbValues = dotProductRounded(transformationMatrix, pixelRgbValues);

        // Assign values from new rgb matrix to rgb picture array in each r and c position
        for (int z = 0; z < transformedPixelRgbValues.length; z++) {
          finalArray[r][c][z] = transformedPixelRgbValues[z];
        }
      }
    }
    return finalArray;
  }

  /**
   * Private method for calculating dot product between 2 matrixes. Does not check if given array is
   * a matrix nor whether row length of matrix 1 is equal column length of matrix 2. Returned matrix
   * is rounded and cast to int.
   *
   * @param transformationMatrix the first matrix of the dot product
   * @param rgbValues            the second matrix of the dot product
   * @return result matrix of the dot product
   */
  private static int[] dotProductRounded(double[][] transformationMatrix, int[] rgbValues) {
    int[] returnArray = new int[transformationMatrix.length];
    double sum;

    for (int r = 0; r < transformationMatrix.length; r++) {
      sum = 0;
      for (int c = 0; c < transformationMatrix[r].length; c++) {
        sum = sum + transformationMatrix[r][c] * rgbValues[c];
      }
      returnArray[r] = (int) Math.round(sum);
    }
    return returnArray;
  }

  /*
   * ################################ Dithering methods ################################
   */

  /**
   * Creates a greyscasle dither of the image.
   *
   * @param rgbArray the RGB image array to be dithered
   * @return a 3D array of a dithered image
   */
  public static int[][][] applyFloydSteinbergDither(int[][][] rgbArray) {

    int[][][] finalArray = deepCopy3dArray(rgbArray);
    finalArray = applyGreyscale(finalArray);

    for (int r = 0; r < finalArray.length; r++) {
      for (int c = 0; c < finalArray[r].length; c++) {

        int oldColor = finalArray[r][c][0];
        int newColor = Math.abs(oldColor - 255) < Math.abs(oldColor) ? 255 : 0;
        int error = oldColor - newColor;

        for (int z = 0; z < finalArray[r][c].length; z++) {
          finalArray[r][c][z] = newColor;

          // If not on right edge, add error to col to right
          if (!(c == finalArray[r].length - 1)) {
            finalArray[r][c + 1][z] = finalArray[r][c + 1][z] + (7 * error / 16);
          }
          // If not on bottom edge, add error to row below
          if (!(r == finalArray.length - 1)) {
            finalArray[r + 1][c][z] = finalArray[r + 1][c][z] + (5 * error / 16);
          }
          // If not on left edge or bottom edge, add error to next-row-left
          if (!(r == finalArray.length - 1 || c == 0)) {
            finalArray[r + 1][c - 1][z] = finalArray[r + 1][c - 1][z] + (3 * error / 16);
          }
          // If not on right edge or bottom edge, add error to next-row-right
          if (!(r == finalArray.length - 1 || c == finalArray[r].length - 1)) {
            finalArray[r + 1][c + 1][z] = finalArray[r + 1][c + 1][z] + (1 * error / 16);
          }

        }
      }
    }

    return finalArray;

  }
  /*
   * ################################ Mosaic methods ################################
   */

  /**
   * Creates a mosaic effect of the image.
   *
   * @param rgbArray the RGB image array to be dithered
   * @param seeds    the number of starting seeds
   * @return a 3D array of a dithered image
   */
  public static int[][][] applyMosaic(int[][][] rgbArray, int seeds) {

    // Array of seeds. Each seed is an array of r and c points
    int[][] seedPixels = new int[seeds][2];
    Random random = new Random();
    for (int i = 0; i < seeds; i++) {
      seedPixels[i][0] = random.nextInt(rgbArray.length);
      seedPixels[i][1] = random.nextInt(rgbArray[0].length);
    }

    int[][][] finalArray = deepCopy3dArray(rgbArray);
    // For each seed in the array of seeds, map a new list to that seed.
    Map<int[], List<int[]>> seedMap = new HashMap<>();
    for (int seedIndex = 0; seedIndex < seedPixels.length; seedIndex++) {
      seedMap.put(seedPixels[seedIndex], new ArrayList<>());
    }
    // For each pixel in image, check against all seeds in seed map, add closest
    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        // The distance to the closest seed
        double minDistance = Double.MAX_VALUE;
        // The closest seed to the pixel
        int[] closestSeed = new int[2];

        for (Map.Entry<int[], List<int[]>> seed : seedMap.entrySet()) {
          // Calculate the distance to each seed
          double distanceToSeed = calculateEculideanDistace(r, c,
                  seed.getKey()[0], seed.getKey()[1]);

          if (distanceToSeed < minDistance) {
            minDistance = distanceToSeed;
            closestSeed = seed.getKey();
          }
        }
        seedMap.get(closestSeed).add(new int[]{r, c});
      }
    }

    // Calculate the average RGB value for each seed
    seedMap.forEach((seed, pixelList) -> {
      int redSum = 0;
      int blueSum = 0;
      int greenSum = 0;
      for (int i = 0; i < pixelList.size(); i++) {
        int rowPixel = pixelList.get(i)[0];
        int colPixel = pixelList.get(i)[1];
        redSum += rgbArray[rowPixel][colPixel][0];
        blueSum += rgbArray[rowPixel][colPixel][1];
        greenSum += rgbArray[rowPixel][colPixel][2];
      }
      int averageRed = pixelList.size() == 0 ? redSum : redSum / pixelList.size();
      int averageBlue = pixelList.size() == 0 ? blueSum : blueSum / pixelList.size();
      int averageGreen = pixelList.size() == 0 ? greenSum : greenSum / pixelList.size();

      // Assign the average RGB value to each pixel associated with the seed
      pixelList.forEach(pixelArray -> {
        int rowPixel = pixelArray[0];
        int colPixel = pixelArray[1];
        finalArray[rowPixel][colPixel][0] = averageRed;
        finalArray[rowPixel][colPixel][1] = averageBlue;
        finalArray[rowPixel][colPixel][2] = averageGreen;
      });
    });
    return finalArray;
  }

  private static double calculateEculideanDistace(int x1, int y1, int x2, int y2) {
    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
  }

  /*
   * ################################ Sobel Edge Detection methods ################################
   */

  /**
   * Produces a grayscale image where edges (areas of high contrast) are highlighted.
   *
   * @param rgbArray the RGB image array to be highlighted
   * @return a 3D array of a highlighted image
   */
  public static int[][][] applySobelEdgeDetection(int[][][] rgbArray) {
    int[][][] finalArray = deepCopy3dArray(rgbArray);

    int maximum = Integer.MIN_VALUE;
    int minimum = Integer.MAX_VALUE;

    for (int r = 0; r < rgbArray.length; r++) {
      for (int c = 0; c < rgbArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {
          int distanceToCenterX;
          int distanceToCenterY;
          double gx = 0;
          double gy = 0;

          for (int kernelR = 0; kernelR < sobelEdgeX.length; kernelR++) {
            for (int kernelC = 0; kernelC < sobelEdgeX.length; kernelC++) {
              // Relative distance to center of kernel. Where the center is (kernel.length - 1) / 2
              // Assumes both sorbel kernels are same length
              distanceToCenterX = kernelR - ((sobelEdgeX.length - 1) / 2);
              distanceToCenterY = kernelC - ((sobelEdgeX.length - 1) / 2);

              // Check for out of bounds, if so do not apply
              if (r + distanceToCenterX >= rgbArray.length
                      || c + distanceToCenterY >= rgbArray[r].length
                      || r + distanceToCenterX < 0
                      || c + distanceToCenterY < 0) {
                continue;
              }
              gx = gx + (rgbArray[r + distanceToCenterX][c + distanceToCenterY][z]
                      * sobelEdgeX[kernelR][kernelC]);
              gy = gy + (rgbArray[r + distanceToCenterX][c + distanceToCenterY][z]
                      * sobelEdgeY[kernelR][kernelC]);
            }
          }
          int finalResult = (int) Math.round(Math.sqrt(gx * gx + gy * gy));
          finalArray[r][c][z] = finalResult;
          maximum = Math.max(maximum, finalResult);
          minimum = Math.min(minimum, finalResult);
        }
      }
    }

    System.out.println("max = " + maximum);
    System.out.println("min = " + minimum);
    for (int r = 0; r < finalArray.length; r++) {
      for (int c = 0; c < finalArray[r].length; c++) {
        for (int z = 0; z < rgbArray[r][c].length; z++) {
          int rescaleValue;
          rescaleValue = (finalArray[r][c][z] - minimum) * 255
                  / (maximum - minimum);
          finalArray[r][c][z] = rescaleValue;
        }
      }
    }
    return applyGreyscale(finalArray);
  }

  /*
   * ################################ Crop methods ################################
   */

  /**
   * Crops the image by discarding pixels before the lower bounds and after the upper bounds.
   *
   * @param rgbArray    array to be cropped
   * @param startIndexX start index of the crop for the x-axis
   * @param startIndexY start index of the crop for the y-axis
   * @param endIndexX   end index of the crop for the x-axis
   * @param endIndexY   end index of the crop for the y-axis
   *
   * @return a 3D array of a cropped image
   */
  public static int[][][] crop(int[][][] rgbArray, int startIndexX, int startIndexY, int endIndexX,
                               int endIndexY) {

    int[][][] finalArray = new int [endIndexY - startIndexY + 1][endIndexX - startIndexX + 1][3];
    int i;
    int j;
    for (int r = startIndexY; r < endIndexY; r++) {
      for (int c = startIndexX; c < endIndexX; c++) {
        for (int z = 0; z < 3; z++) {
          i = r - startIndexY;
          j = c - startIndexX;
          finalArray[i][j][z] = rgbArray[r][c][z];
        }
      }
    }
    return finalArray;
  }

  /*
   * ############################### Histogram Equalization methods ###############################
   */

  /**
   * Equalizes the images' histogram. Best on greyscale images but will work on color images with
   * by equalizing each channel separately.
   *
   * @param rgbArray rgb image to apply
   * @return a 3D array of a equalized image
   */
  public static int[][][] histogramEqualization(int[][][] rgbArray) {
    int[][][] finalArray = deepCopy3dArray(rgbArray);

    for (int z = 0; z < rgbArray[0][0].length; z++) {
      // in one channel
      double totalPixels = rgbArray.length * rgbArray[0].length;
      double bins = 255 / totalPixels;
      int[] originalHistogram = new int[256];

      for (int r = 0; r < rgbArray.length; r++) {
        for (int c = 0; c < rgbArray[r].length; c++) {
          originalHistogram[rgbArray[r][c][z]]++;
        }
      }

      int[] originalCumfd = new int[256];
      originalCumfd[0] = originalHistogram[0];
      for (int i = 1; i < 256; i++) {
        originalCumfd[i] = originalCumfd[i - 1] + originalHistogram[i];
      }

      double[] idealCumfd = new double[256];
      for (int i = 0; i < 256; i++) {
        idealCumfd[i] = originalCumfd[i] * bins;
      }

      for (int r = 0; r < rgbArray.length; r++) {
        for (int c = 0; c < rgbArray[r].length; c++) {
          int newValue = (int) idealCumfd[rgbArray[r][c][z]];
          finalArray[r][c][z] = newValue;
        }
      }
    }
    return finalArray;
  }

  /*
   * ################################ Helper methods ################################
   */

  /**
   * Private method for creating a deep copy of a 3D array. Assumes regular dimensions.
   *
   * @param array to be deep copied
   * @return new array
   */
  private static int[][][] deepCopy3dArray(int[][][] array) {
    int[][][] finalArray = new int[array.length][array[0].length][array[0][0].length];

    for (int r = 0; r < array.length; r++) {
      for (int c = 0; c < array[r].length; c++) {
        finalArray[r][c] = Arrays.copyOf(array[r][c], array[r][c].length);
      }
    }
    return finalArray;
  }

}
