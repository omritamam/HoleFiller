package HoleFiller.Lib;

import HoleFiller.Lib.Algos.neighborDirection;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.Collection;

public class Utils {
    // update given neighbors collection with all neighbors of the given pixel (in the given image)
    public static void updateNeighbors(Pixel pixel, Collection<Pixel> neighbors, Image image, neighborDirection neighborDirection ) {
        neighbors.clear();

    }
}
