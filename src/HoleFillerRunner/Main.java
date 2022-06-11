package HoleFillerRunner;

import Lib.HoleFiller;
import Lib.Models.ConnectivityType;

public class Main {

    public static void main(String[] args) throws  IllegalArgumentException{
        // Parse command line arguments
        System.out.println("Parsing command line arguments");
        System.out.println(args.length);

        if (args.length != 5) {
            throw new IllegalArgumentException("Invalid number of arguments -" +
                " Usage: <input_image_file> <mask_image_file> <connectivity> <epsilon> <weight_function>");
        }
        var inputImageFile = args[0];
        var maskImageFile = args[1];
        var connectivity = ConnectivityType.fromString(args[2]);
        var epsilon = Double.parseDouble(args[3]);
        var normDegree = Integer.parseInt(args[4]);

        //Create a mergedImage from image and maskedImage
        var image = ImageUtils.loadImage(inputImageFile);
        var mask = ImageUtils.loadImage(maskImageFile);
        var maskedImage = ImageUtils.mergeImages(image, mask);

        //Create a weightFunction instance
        var weightFunction = new DefaultWeightFunction(epsilon,normDegree);

        //Call lib func
        var outputImage = new HoleFiller(maskedImage, weightFunction, connectivity).fillHole();

        //Write the output image to a file
        ImageUtils.writeImage(outputImage, inputImageFile+"_output.png");
        System.out.println("Done");

    }

}
