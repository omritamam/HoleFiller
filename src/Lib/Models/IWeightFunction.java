package Lib.Models;

@FunctionalInterface
public interface IWeightFunction {
    double getWeight(Pixel x, Pixel y);
}
