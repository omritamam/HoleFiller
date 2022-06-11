package Lib;

import Lib.Models.ConnectivityType;
import Lib.Models.IWeightFunction;
import Lib.Models.Image;
import Lib.Models.Pixel;
import java.util.ArrayList;

public class HoleFiller {

    private final Image image;
    private final ArrayList<Pixel> border;
    private final ArrayList<Pixel> hole;
    private final IWeightFunction weightFunction;
    private final ConnectivityType connectivityType;


    public HoleFiller(Image image,IWeightFunction weightFunction, ConnectivityType connectivityType) {
        this.image = image;
        this.weightFunction = weightFunction;
        this.connectivityType = connectivityType;
        this.border = new ArrayList<>();
        this.hole = new ArrayList<>();
        initBorderAndHole();
    }

    public Image fillHole() {

        //split the holePixels into cores parts
        var cores = Runtime.getRuntime().availableProcessors();
        var threads = new Thread[cores];
        for(int i=0; i<cores; i++) {
            var start = i*hole.size()/cores;
            var end = (i+1)*hole.size()/cores;
            var thread = new HoleFillerThread(hole.subList(start, end), border, image, weightFunction);
            threads[i] = new Thread(thread);
            threads[i].start();
        }
        // Wait for all threads to finish
        for(int i=0; i<cores; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
    private void updateNeighbors(Pixel pixel, ArrayList<Pixel> neighbors) {
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


    void initBorderAndHole() {
        var currentNeighbors = new ArrayList<Pixel>();
        for (var pixel : image) {
            if (pixel.getColor() == -1) {
                hole.add(pixel);
                updateNeighbors(pixel, currentNeighbors);
                currentNeighbors.forEach(neighbor -> {
                    if (neighbor.getColor() != -1) {
                        border.add(neighbor);
                    }
                });
            }
        }
    }
}