package HoleFiller.Lib;

import HoleFiller.Lib.Algos.SecondAlgo;
import HoleFiller.Lib.Algos.neighborDirection;
import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.ArrayList;

import static HoleFiller.Lib.Utils.updateNeighbors;

public class HoleFiller {

    private final Image image;
    private final ArrayList<Pixel> border;
    private final ArrayList<Pixel> hole;
    public final IWeightFunction weightFunction;
    public final HoleFiller.Lib.Algos.neighborDirection neighborDirection;

    public HoleFiller(Image image, IWeightFunction weightFunction, neighborDirection neighborDirection) {
        this.image = image.clone();
        this.weightFunction = weightFunction;
        this.neighborDirection = neighborDirection;
        this.border = new ArrayList<>();
        this.hole = new ArrayList<>();
        initBorderAndHole();
    }

    ///<returns> a new <see cref="Image"/> with filled hole</returns>
    public Image fillHole() {
        var algo = new SecondAlgo(hole, border, image, weightFunction,neighborDirection);
//        var algo2 = new FirstAlgo(hole, border, image, weightFunction, connectivityType);
        algo.fillHole();
        return image;
    }

    ///<summary>
    /// Finds the border and hole pixels and adds them to the border and hole arrays
    ///</summary>
    private void initBorderAndHole() {
        var currentNeighbors = new ArrayList<Pixel>();
        for (var pixel : image) {
            if (pixel.getColor() == -1) {
                hole.add(pixel);
                updateNeighbors(pixel, currentNeighbors, image, neighborDirection);
                currentNeighbors.forEach(neighbor -> {
                    if (neighbor.getColor() != -1) {
                        border.add(neighbor);
                    }
                });
            }
        }
    }

}