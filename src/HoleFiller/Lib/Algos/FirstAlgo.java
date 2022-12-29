package HoleFiller.Lib.Algos;

import HoleFiller.Lib.HoleFillerAlgo;
import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;
import java.util.ArrayList;
import java.util.Collection;

public class FirstAlgo extends HoleFillerAlgo {

    public FirstAlgo(ArrayList<Pixel> holePixels, Collection<Pixel> border, Image image, IWeightFunction weightFunction, neighborDirection neighborDirection) {
        super(holePixels, border, image, weightFunction, neighborDirection);
    }

    public void fillHole() {
        //split the holePixels to separated threads
        var cores = Runtime.getRuntime().availableProcessors();
        var threads = new Thread[cores];
        for (int i = 0; i < cores; i++) {
            var startPixelInd = i * super.holePixels.size() / cores;
            var endPixelInd = (i + 1) * holePixels.size() / cores;
            var thread = new FirstAlgoThread(new ArrayList<>(holePixels.subList(startPixelInd, endPixelInd)), border, image, weightFunction, neighborDirection);
            threads[i] = new Thread(thread);
            threads[i].start();
        }
        // Wait for all threads to finish
        for (int i = 0; i < cores; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
