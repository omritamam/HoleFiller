package HoleFillerRunner;

import Lib.Models.Image;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;

class ImageUtils {

    public static final double BLACK_THRESHOLD = 0.05 * 255;

    public static Image mergeImages(Image image, Image maskedImage) {
         validateImages(image, maskedImage);
         Image mergedImage = new Image(image.width, image.height);
         for (int x = 0; x < image.width; x++) {
             for (int y = 0; y < image.height; y++) {
                 // check if masked image pixel is black
                 if (maskedImage.getPixel(x, y).getColor() < BLACK_THRESHOLD) {
                     mergedImage.setPixel(x, y, -1);
                 } else {
                     mergedImage.setPixel(x, y, image.getPixel(x, y).getColor());
                 }
             }
         }
        return mergedImage;
    }

    private static void validateImages(Image image, Image maskedImage) throws IllegalArgumentException {
        if (image.width != maskedImage.width || image.height != maskedImage.height) {
            throw new IllegalArgumentException("Images must be the same size");
        }
    }

    public static void writeImage(Image outputImage, String fileName) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file and storing it in to a Matrix object
        Mat mat = new Mat(outputImage.height, outputImage.width, CvType.CV_8UC1);
        for(int y=0;y<outputImage.height;y++){
            for(int x=0;x<outputImage.width;x++){
                mat.put(y, x, outputImage.getPixel(x, y).getColor());
            }
        }
        imageCodecs.imwrite(fileName, mat);

    }

    public static Image loadImage(String filePath) {
        //Check if files are images
        if (!(filePath.endsWith(".png") || filePath.endsWith(".jpg"))) {
            System.out.println("Input image file must be a png or jpg");
            System.exit(1);
        }
        System.loadLibrary(NATIVE_LIBRARY_NAME);
        Mat rgb = new Imgcodecs().imread(filePath);
        Mat rawImg = new Mat();
        Imgproc.cvtColor(rgb, rawImg, Imgproc.COLOR_RGB2GRAY);
        Image image = new Image(rawImg.width(), rawImg.height());
        for (int x = 0; x < rawImg.width(); x++) {
            for (int y = 0; y < rawImg.height(); y++) {
                var grayscale = rawImg.get(y, x)[0];
                image.setPixel(x, y, grayscale);
            }
        }
        return image;
    }
}
