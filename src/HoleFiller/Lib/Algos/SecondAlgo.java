package HoleFiller.Lib.Algos;

import HoleFiller.Lib.HoleFillerAlgo;
import HoleFiller.Lib.Models.ConnectivityType;
import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Image;
import HoleFiller.Lib.Models.Pixel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static HoleFiller.Lib.Utils.updateNeighbors;

public class SecondAlgo extends HoleFillerAlgo {

    // PRECISION_LEVEL is the minimum number of neighbors pixels for filling a hole pixel
    // higher values will result in more accurate filling but will take longer to fill
    private static final int PRECISION_LEVEL = 0;

    public SecondAlgo(ArrayList<Pixel> holePixels, Collection<Pixel> border, Image image, IWeightFunction weightFunction, ConnectivityType connectivityType) {
        super(holePixels, border, image, weightFunction, connectivityType);
    }

    @Override
    public void fillHole() {
        var visited = new HashSet<Pixel>();
        var holeEdge = getHoleEdge(super.holePixels);
        var innerHoleEdge = new HashSet<Pixel>();
        var currentNeighbors = new ArrayList<Pixel>();
        while(!holeEdge.isEmpty()) {
            for (var pixel : holeEdge) {
                // skip if already visited
                if(visited.contains(pixel)) {
                    continue;
                }
                //find all neighbors of the current pixel which are not in the hole
                updateNeighbors(pixel, currentNeighbors, image, connectivityType);
                var borderNeighbors = currentNeighbors.stream().filter(neighbor -> neighbor.getColor() != -1).collect(Collectors.toList());
                var holeNeighbors = currentNeighbors.stream().filter(neighbor -> neighbor.getColor() == -1).collect(Collectors.toList());
                if(borderNeighbors.size() <= PRECISION_LEVEL) {
                    // not enough neighbors to fill the hole
                    continue;
                }
                //set the color of the current pixel to the average color of its neighbors
                var newColor = getNewColor(pixel, borderNeighbors);
                image.setPixel(pixel.x, pixel.y, newColor);
                innerHoleEdge.addAll(holeNeighbors);
                visited.add(pixel);
            }
            // move to inner edge of the hole
            holeEdge.clear();
            holeEdge.addAll(innerHoleEdge);
            innerHoleEdge.clear();
        }
    }
    //// <summary>
    //// Finds the edge of the hole
    //// </summary>
    //// <param name="holePixels">The hole pixels</param>
    //// <returns>The edge of the hole</returns>
    private Set<Pixel> getHoleEdge(Collection<Pixel> hole) {
        return hole.stream().filter(pixel -> {
            var currentNeighbors = new ArrayList<Pixel>();
            updateNeighbors(pixel, currentNeighbors, image, connectivityType);
            if (currentNeighbors.stream().anyMatch(neighbor -> neighbor.getColor() == -1)) {
                return true;
            }
            return false;
        }).collect(Collectors.toSet());
    }
}


