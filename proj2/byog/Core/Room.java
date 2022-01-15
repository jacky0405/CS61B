package byog.Core;

import java.util.HashSet;

public class Room {
    private Position position; // represent left bottom position
    private int width;
    private int height;

    public Room(Position p, int width, int height) {
        this.position = p;
        this.width = width;
        this.height = height;
    }

    public Position getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position centerPosition() {
        int centerX = position.x + width/2;
        int centerY = position.y + height/2;

        return new Position(centerX, centerY);
    }

    public HashSet<Position> roomPositions() {
        HashSet<Position> roomPositions = new HashSet<>();

        for (int i=0; i < width-1; i++) {
            for (int j=0; j < height-1; j++) {
                roomPositions.add(new Position(position.x + i, position.y + j));
            }
        }
        return roomPositions;
    }
}
