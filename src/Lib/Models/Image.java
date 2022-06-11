package Lib.Models;

import java.util.ArrayList;

public class Image {
    private final ArrayList<ArrayList<Pixel>> data;
    public final int width;
    public final int height;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new ArrayList<>(height);
        for(int i=0; i<height; i++) {
            this.data.add(new ArrayList<>(width));
            for(int j=0; j<width; j++) {
                this.data.get(i).add(new Pixel(j, i, -1));
            }
        }
    }


    public Pixel getPixel(int i, int j) {
        return data.get(i).get(j);
    }

    public void setPixel(int x, int y, double grayscale) {
        if(data.get(x).get(y) == null) {
            data.get(x).add(y, new Pixel(x, y, grayscale));
        }
        data.get(x).get(y).color = grayscale;
    }
}
