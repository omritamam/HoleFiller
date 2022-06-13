package HoleFiller.Lib.Models;

public class Pixel {
    public final int x;
    public final int y;
    private double color;

    public Pixel(int x, int y, double grayscale) {
        this.x = x;
        this.y = y;
        this.color = grayscale;
    }

    public double getColor() {
        return this.color;
    }

    public void setColor(double grayscale) {
        this.color = grayscale;
    }
}
