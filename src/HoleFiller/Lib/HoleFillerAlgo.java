package HoleFiller.Lib;

import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.ArrayList;
import java.util.Collection;

public abstract class HoleFillerAlgo {
    protected final ArrayList<Pixel> holePixels;
    protected final Collection<Pixel> border;
    protected final Image image;
    protected final IWeightFunction weightFunction;
    protected final HoleFiller.Lib.Algos.neighborDirection neighborDirection;

    public abstract void fillHole();

    protected HoleFillerAlgo(ArrayList<Pixel> holePixels, Collection<Pixel> border, Image image, IWeightFunction weightFunction, HoleFiller.Lib.Algos.neighborDirection neighborDirection) {
        this.holePixels = holePixels;
        this.border = border;
        this.image = image;
        this.weightFunction = weightFunction;
        this.neighborDirection = neighborDirection;

    }

    protected double getNewColor(Pixel pixel, Collection<Pixel> border) {
        var sumWeights = 0f;
        var sumColors = 0f;
        for (var neighbor : border) {
            if (neighbor.getColor() != -1) {
                var weight = weightFunction.getWeight(pixel, neighbor);
                sumColors += neighbor.getColor() * weight;
                sumWeights += weight;
            }
        }
        return sumColors / sumWeights;
    }

}

