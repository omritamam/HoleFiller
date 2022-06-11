package HoleFillerRunner;

import Lib.Models.Image;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;

class ImageUtils {

    public static Image mergeImages(Image image, Image maskedImage) {
         validateImages(image, maskedImage);
         Image mergedImage = new Image(image.width, image.height);
         for (int x = 0; x < image.width; x++) {
             for (int y = 0; y < image.height; y++) {
                 if (maskedImage.getPixel(x, y).color == -1) {
                     mergedImage.setPixel(x, y, -1);
                 } else {
                     mergedImage.setPixel(x, y, image.getPixel(x, y).color);
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

    public static void writeImage(Image outputImage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();

        //Reading the Image from the file and storing it in to a Matrix object
        Mat mat = new Mat(outputImage.height, outputImage.width, CvType.CV_8UC1);
        for(int row=0;row<outputImage.height;row++){
            for(int col=0;col<outputImage.width;col++)
                mat.put(row, col, outputImage.getPixel(col, row).color);
        }

        String file2 = "sample_resaved.jpg";
        imageCodecs.imwrite(file2, mat);

    }

    public static Image loadImage(String filePath) {
        System.loadLibrary(NATIVE_LIBRARY_NAME);
        Mat rgb = new Imgcodecs().imread(filePath);
        Mat rawImg = new Mat();
        Imgproc.cvtColor(rgb, rawImg, Imgproc.COLOR_RGB2GRAY);
        Image image = new Image(rawImg.width(), rawImg.height());
        for (int x = 0; x < rawImg.width(); x++) {
            for (int y = 0; y < rawImg.height(); y++) {
                double grayscale = rawImg.get(y, x)[0];
                image.setPixel(x, y, grayscale);
            }
        }
        return image;
    }
}
