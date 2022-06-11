package Lib.Models;

@FunctionalInterface
public interface IWeightFunction {
    double getWeight(Pixel p1, Pixel p2);
}
