package Lib;

import Lib.Models.IWeightFunction;
import Lib.Models.Image;
import Lib.Models.Pixel;
import java.util.Collection;

public class HoleFillerThread implements Runnable {
    private final Collection<Pixel> holePixels;
    private final Collection<Pixel> border;
    private final Image image;
    private final IWeightFunction weightFunction;

    public HoleFillerThread(Collection<Pixel> pixels, Collection<Pixel> border, Image image, IWeightFunction weightFunction) {
        this.holePixels = pixels;
        this.border = border;
        this.image = image;
        this.weightFunction = weightFunction;
    }

    @Override
    public void run() {
        for (var pixel : holePixels) {
            var newColor = getNewColor(pixel);
            image.setPixel(pixel.x, pixel.y, newColor);
        }
    }

    private double getNewColor(Pixel pixel) {
        double sumWeights = 0f;
        double sumColors = 0f;
        for (var neighbor : border) {
            if(neighbor.getColor() != -1) {
                var weight = weightFunction.getWeight(pixel, neighbor);
                sumColors += neighbor.getColor() * weight;
                sumWeights += weight;
            }
        }
        return sumColors / sumWeights;
    }
}
