package HoleFiller.Lib.Models;

import java.util.ArrayList;
import java.util.Iterator;

public class Image implements Iterable<Pixel>, Cloneable {

    public final int width;
    public final int height;
    private final ArrayList<ArrayList<Pixel>> data;

    /// <summary>
    /// Initializes a new empty instance of the <see cref="Image"/> class.
    /// </summary>
    /// <param name="width">The width.</param>
    /// <param name="height">The height.</param>
    public Image(int width, int height) {
        this.width = width;
        this.height = height;

        // Initialize data
        data = new ArrayList<>(height);
        for (int row = 0; row < height; row++) {
            data.add(new ArrayList<>(width));
            for (int col = 0; col < width; col++) {
                var newPixel = new Pixel(col, row, -1);
                data.get(row).add(newPixel);
            }
        }
    }
    ///<summary>
    /// Creates a new <see cref="Image"/> from 2D pixel array
    ///</summary>
    public Image(ArrayList<ArrayList<Pixel>> data) {
        this.data = data;
        this.width = data.get(0).size();
        this.height = data.size();
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

            private final int rowsNum = data.size();
            private int row = 0;
            private int col = 0;

            @Override
            public boolean hasNext() {
                return row != rowsNum;
            }

            @Override
            public Pixel next() {
                var pixel = data.get(row).get(col);
                col++;
                if (col == data.get(row).size()) {
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
    @Override
    public Image clone() {
        var clone = new Image(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                clone.setPixel(x, y, getPixel(x, y).getColor());
            }
        }
        return clone;
    }
}
