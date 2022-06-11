package HoleFillerRunner;

import Lib.HoleFiller;
import Lib.Models.ConnectivityType;
import HoleFillerRunner.Models.DefaultWeightFunction;

import java.io.IOException;

public class Main {
//Write a command line utility that accepts an input image file(s), and connectivity 𝑧, ϵ type, fills
//the hole and writes the result to an image file.
    // Usage <input_image_file> <mask_image_file> <connectivity> <epsilon> <weight_function>
    public static void main(String[] args) throws IOException, IllegalArgumentException{
        // Parse command line arguments
        System.out.println("Parsing command line arguments");
        System.out.println(args.length);

        if (args.length != 5) {
            System.out.println("Usage: <input_image_file> <mask_image_file> <connectivity> <epsilon> <weight_function>");
            System.exit(1);
        }
        //Create a mergedImage from image and maskedImage
        String inputImageFile = args[0];
        String maskImageFile = args[1];
        var image = ImageUtils.loadImage(inputImageFile);
        var mask = ImageUtils.loadImage(maskImageFile);
        var mergedImage = ImageUtils.mergeImages(image, mask);

        //Create a weightFunction instance
        var epsilon = Double.parseDouble(args[3]);
        var normDegree = Integer.parseInt(args[4]);
        var weightFunction = new DefaultWeightFunction(epsilon,normDegree);

        //Create a connectivityType instance
        var connectivity = ConnectivityType.fromString(args[2]);

        var holeFiller = new HoleFiller(mergedImage, weightFunction, connectivity);
        var outputImage = holeFiller.fillHole();

        //Write the output image to a file
        ImageUtils.writeImage(outputImage);

    }
}
