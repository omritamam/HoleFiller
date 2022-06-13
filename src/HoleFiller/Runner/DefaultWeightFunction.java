package HoleFiller.Runner;

import HoleFiller.Lib.Models.IWeightFunction;
import HoleFiller.Lib.Models.Pixel;

public class DefaultWeightFunction implements IWeightFunction {

    private final double epsilon;
    private final int normDegree;

    public DefaultWeightFunction(double epsilon, int normDegree) {
        this.epsilon = epsilon;
        this.normDegree = normDegree;
    }


    @Override
    public double getWeight(Pixel p1, Pixel p2) {
        var distance = Math.pow(Math.pow(Math.abs(p1.x - p2.x), normDegree) + Math.pow(Math.abs(p1.y - p2.y),
                normDegree), 1.0 / normDegree);
        return 1 / (this.epsilon + distance);
    }

}
