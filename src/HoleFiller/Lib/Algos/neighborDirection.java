package HoleFiller.Lib.Algos;

import java.util.HashSet;

public class neighborDirection {
    HashSet<Coordinates> directions = new HashSet<>();

    public static class Coordinates{
        final int x;
        final int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void addDirection(int x, int y){
        directions.add(new Coordinates(x, y));
    }
}
