# Image-Filter-o-Matic-2000

This is a java program that allows the filtering or manipulation of images, either with the provided
GUI or a txt script file.

## List of features

This program contains multiple high-level features which can be used to manipulate images

- Loading
- Saving
- Blur
- Sharpen
- Grayscale
- Sepia
- Dithering
- Mosaic
- Edge Detection
- Histogram Equalization
- Cropping
- Creating and executing scripted commands
- A GUI for performing all the above functions

[//]: # (## Other lower level features)

[//]: # ()

[//]: # (In addition to the above high level features, a few other features have been implemented &#40;)

[//]: # (non-exhaustive&#41;:)

[//]: # ()

[//]: # (- Case-insensitive options)

[//]: # (- Invalid file loading and saving handling)

[//]: # (- Invalid command handling)

[//]: # (- State handling for before and after an image is loaded)

[//]: # (- Quit functionality)

[//]: # (- Input and out abstraction, ability to use a script file and a console view &#40;different files&#41;)

## How to Run

### Using the GUI

Executing the given JAR file (named hw10.jar tentatively) will launch the program.

Executing the program will show the main pane. The main pane offers menus for performing all
actions. In addition to that, each action has a corresponding short-cut associated to it.
![mainpage](/res/README images/mainpage.png)
Clicking on `Script->Create` will bring up the script editor page. Here we can directly edit a
script file or use the built-in buttons to populate the script file. After creating, we can save the
file directly from the editor page to be executed later.
![scriptpage](/res/README images/scriptpage.png)
A few things of note:
- All image filters and the save function is disabled until an image is loaded.
- You can view a history of actions in the log section beneath.
- Applying a mosaic requires you to select the number of seeds in a pop-up dialog.

### Using the text-based script driver

To use a script independent of the GUI, a jar file needs to be created from the main method
in `src\script\Driver.java`. The jar needs to be given a command argument when executing. This
command argument is the filepath to the intended script, relative to the root level folder.

To use a console view, simply create a jar file from the `ConsoleViewDriver.java` file and execute.

## How to use the program

### Using the GUI

### Writing scripts

To write a script, multiple commands must be written, separated by whitespace. Simply write the
desired commands in a text file or use the editor provided. The file must then be saved to be
executed For example:

```
load /res/mondays.png
blur blur
grayscale
sharpen
save /res/new.png
```
See the `.txt` files in `\res\` for further example.


## List of image filters

The following is a list of image filters, an example of their effect and the corresponding script
command:

### Loading Images

```
load some\imagepath.png
```

Loads the specified image into the program to be manipulated. The path specified is relative to the
working directory.

### Saving images

```
save some\impagepath.png
```

Saves the loaded image into a specified directory. The path specified is relative to the working
directory.

### Blurring images

![blur](/res/code-review-blurred.png)

```
blur
```

Applies a blur to the image.

### Sharpen images

![sharpen](/res/code-review-sharpen.png)

```
sharpen
```

Applies a sharpening to the image.

### Grayscale

![grayscale](/res/code-review-grayscale.png)

```
grayscale
```

Applies a grayscale filter to the image.

### Sepia

![sepia](/res/code-review-sepia.png)

```
sepia
```

Applies a sepia filter to the image.

### Dither Images

![dither](/res/code-review-dither.png)

```
dither
```

Applies a grayscale dithering to the image using
the [Floyd-Steinberg](https://en.wikipedia.org/wiki/Floyd%E2%80%93Steinberg_dithering) algorithm.

### Mosaic


![mosaic](/res/code-review-mosaic-1000.png)
```
mosaic 8000
```

Applies a mosaic filter to the image. The number of seeds provided indicates how many "window panes"
are in the image.

### Edge detection
![edgedetection](/res/code-review-edgedetection.png)
```
edgedetection
```
Applies a Sobel edge detection by converting the image to grayscale and highlighting edges.

### Histgoram equalization
![histogramequalization](/res/code-review-grayscale-equalized.png)
```
histogramequalization
```
Histogram equalization (Links to an external site.) is an image enhancement technique that uses 
image intensity to increase the contrast of an image.



## Examples

Here are the various example script files that are given under the `\res` folder.

| Name                                    | Description                                                                                                          |
|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------|
| `script.txt`                            | A complete example showing all commands being used as intended.                                                      |
| `script-invalid-commands.txt`           | Contains examples of invalid commands that the program will handle.                                                  |
| `script-invalid-mosaic-seed.txt`        | Contains examples of when an invalid input is given as the mosaic   paramter, where the program handles to continue. |
| `script-invalid-save.txt`               | Shows an example of the program handling when an invalid save occurs, i.e. before an image is loaded.              |
| `script-loading-invalid-image.txt`      | Shows an example of the program handling when an image can't be loaded.                                              |
| `script-quitting.txt`                   | Shows the command for quitting the program.                                                                          |
| `script-using-filters-without-load.txt` | Shows the program handling errors when filters are applied before an   image is loaded.                              |