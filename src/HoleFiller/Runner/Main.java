package HoleFiller.Runner;

import HoleFiller.Lib.HoleFiller;
import HoleFiller.Lib.Models.ConnectivityType;
import static HoleFiller.Runner.ImageUtils.*;

public class Main {

    ///Usage: <input_image> <mask_image_file> <connectivity_type (4 or 8)>  <epsilon> <norm_degree>
    public static void main(String[] args) throws IllegalArgumentException {
        // Parse command line arguments
        System.out.println("Parsing command line arguments");

        if (args.length != 5) {
            throw new IllegalArgumentException("Invalid number of arguments -" +
                    " Usage: <input_image_file> <mask_image_file> <connectivity> <epsilon> <normDegree>");
        }
        var inputImageFile = args[0];
        var maskImageFile = args[1];
        var connectivity = ConnectivityType.fromString(args[2]);
        var epsilon = Double.parseDouble(args[3]);
        var normDegree = Integer.parseInt(args[4]);

        //Create a mergedImage from image and maskedImage
        var image = loadImage(inputImageFile);
        var mask = loadImage(maskImageFile);
        var maskedImage = mergeImages(image, mask);

        //Create a weightFunction instance
        var weightFunction = new DefaultWeightFunction(epsilon, normDegree);

        //Call lib func
        var holeFiller = new HoleFiller(maskedImage, weightFunction, connectivity);
        var outputImage = holeFiller.fillHole();

        //Write the output image to a file
        writeImage(outputImage, inputImageFile + "_output.png");
        System.out.println("Done");

    }

}
