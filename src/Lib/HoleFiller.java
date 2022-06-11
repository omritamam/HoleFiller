package Lib;

import Lib.Models.ConnectivityType;
import Lib.Models.IWeightFunction;
import Lib.Models.Image;
import Lib.Models.Pixel;
import java.util.HashSet;
import java.util.Set;

public class HoleFiller {

    private final ConnectivityType connectivityType;
    private final IWeightFunction weightFunction;
    private final Image image;

    public HoleFiller(Image image,IWeightFunction weightFunction, ConnectivityType connectivityType) {
        this.image = image;
        this.weightFunction = weightFunction;
        this.connectivityType = connectivityType;
    }

    public Image fillHole() {
        for (int i = 0; i < image.width; i++) {
            for (int j = 0; j < image.height; j++) {
                var curPixel = image.getPixel(i, j);
                if (curPixel.color == -1) {
                    var neighbors = getNeighbors(image.getPixel(i, j));
                    var newColor = getNewColor(curPixel, neighbors);
                    image.setPixel(i, j, newColor);
                }
            }
        }
        return image;
    }

    private Set<Pixel> getNeighbors(Pixel pixel) {
        if (connectivityType == ConnectivityType.Four) {
            return getNeighborsByDistance(pixel, 1);
        } else {
            return getNeighborsByDistance(pixel, 2);
        }
    }

    private Set<Pixel> getNeighborsByDistance(Pixel center, int distance) {
        var neighbors = new HashSet<Pixel>();
        for(int xOffset = -distance; xOffset < distance; xOffset++) {
            int distanceRemainder = distance - Math.abs(xOffset);
            for (int yOffset = -distanceRemainder; yOffset <= distanceRemainder; yOffset++) {
                neighbors.add(image.getPixel(center.x + xOffset, center.y + yOffset));
            }
        }
        return neighbors;
    }

    private float getNewColor(Pixel pixel,Set<Pixel> neighbors) {
        var sumWeights = 0f;
        var sumColors = 0f;
        for (var neighbor : neighbors) {
            if(neighbor.color != -1) {
                var weight = weightFunction.getWeight(pixel, neighbor);
                sumColors += neighbor.color * weight;
                sumWeights += weight;
            }
        }
        return sumColors / sumWeights;
    }
}