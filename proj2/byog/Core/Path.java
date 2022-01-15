package byog.Core;

import java.util.HashSet;
import java.util.Random;

public class Path {
    public static HashSet<Position> connectPath(Position p1, Position p2, Random random) {
        HashSet<Position> path = new HashSet<>();

        int x_distance = p2.x - p1.x;
        int y_distance = p2.y - p1.y;
        int x_direction = x_distance == 0 ? 0 : x_distance / Math.abs(x_distance);
        int y_direction = y_distance == 0 ? 0 : y_distance / Math.abs(y_distance);

        // decide the bend axis
        int state = random.nextInt(2);

        int x = p1.x;
        int y = p1.y;
        if (state == 0 || Math.abs(y_distance) > Math.abs(x_distance)) {
            int x_bend = p1.x + (RandomUtils.uniform(random, 0, Math.abs(x_distance) + 1) * x_direction);
            while (x != x_bend) {
                x += x_direction;
                path.add(new Position(x,y));
            }
            while (y != p2.y) {
                y += y_direction;
                path.add(new Position(x,y));
            }
            while (x != p2.x) {
                x += x_direction;
                path.add(new Position(x,y));
            }
        } else {
            int y_bend = p1.y + (RandomUtils.uniform(random, 0, Math.abs(y_distance) + 1) * y_direction);
            while (y != y_bend) {
                y += y_direction;
                path.add(new Position(x,y));
            }
            while (x != p2.x) {
                x += x_direction;
                path.add(new Position(x,y));
            }
            while (y != p2.y) {
                y += y_direction;
                path.add(new Position(x,y));
            }
        }

        return path;
    }
}
