package Lib.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class Image implements Iterable<Pixel> {

    private final ArrayList<ArrayList<Pixel>> data;
    public final int width;
    public final int height;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;

        // Initialize data
        data = new ArrayList<>(height);
        for(int row=0; row<height; row++) {
            data.add(new ArrayList<>(width));
            for(int col=0; col<width; col++) {
                var newPixel = new Pixel(col, row, -1);
                data.get(row).add(newPixel);
            }
        }
    }

    public void fillImage(Consumer<ArrayList<ArrayList<Pixel>>> fillFunction) {
        fillFunction.accept(data);
    }


    public Pixel getPixel(int x, int y) {
        return data.get(y).get(x);
    }

    public void setPixel(int x, int y, double grayscale) {
        getPixel(x, y).setColor(grayscale);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return new Iterator<>() {

            private int row = 0;
            private int col = 0;
            private final int rowsNum = data.size();

            @Override
            public boolean hasNext() {
                return row != rowsNum;
            }

            @Override
            public Pixel next() {
                var pixel = data.get(row).get(col);
                col++;
                if(col == data.get(row).size()) {
                    col = 0;
                    row++;
                }
                return pixel;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
