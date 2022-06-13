package HoleFiller.Lib.Algos;

import HoleFiller.Lib.HoleFillerAlgo;
import HoleFiller.Lib.Models.ConnectivityType;
import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.ArrayList;
import java.util.Collection;

public class FirstAlgoThread extends HoleFillerAlgo implements Runnable {

    public FirstAlgoThread(ArrayList<Pixel> pixels, Collection<Pixel> border, Image image, IWeightFunction weightFunction, ConnectivityType connectivityType) {
        super(pixels, border, image, weightFunction, connectivityType);
    }

    @Override
    public void run() {
        fillHole();
    }


    @Override
    public void fillHole() {
        for (var pixel : super.holePixels) {
            var newColor = super.getNewColor(pixel, border);
            image.setPixel(pixel.x, pixel.y, newColor);
        }
    }
}
