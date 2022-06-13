package HoleFiller.Lib;

import HoleFiller.Lib.Models.ConnectivityType;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.Collection;

public class Utils {
    // update given neighbors collection with all neighbors of the given pixel (in the given image)
    public static void updateNeighbors(Pixel pixel, Collection<Pixel> neighbors, Image image, ConnectivityType connectivityType) {
        neighbors.clear();
        if (connectivityType == ConnectivityType.Eight) {
            neighbors.add(image.getPixel(pixel.x - 1, pixel.y - 1));
            neighbors.add(image.getPixel(pixel.x + 1, pixel.y - 1));
            neighbors.add(image.getPixel(pixel.x - 1, pixel.y + 1));
            neighbors.add(image.getPixel(pixel.x + 1, pixel.y + 1));
        }
        neighbors.add(image.getPixel(pixel.x, pixel.y + 1));
        neighbors.add(image.getPixel(pixel.x, pixel.y - 1));
        neighbors.add(image.getPixel(pixel.x - 1, pixel.y));
        neighbors.add(image.getPixel(pixel.x + 1, pixel.y));
    }

}
