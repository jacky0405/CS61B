package byog.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BPSpace {
    private static final int MIN_SIZE = 3;
    private static final int MIN_FROM_SIDE = 1;
    private int width;
    private int height;
    Position LB; // left bottom point in BPSpace
    BPSpace leftChild;
    BPSpace rightChild;
    Room room;
    ArrayList<Position> path;
    Position keyPoint;

    public BPSpace(Position LB, int width, int height) {
        this.width = width;
        this.height = height;
        this.LB = LB;
        this.leftChild = null;
        this.rightChild = null;
        this.room = null;
        this.path = null;
    }

    // check if the space is enough to split
    public boolean doPartition(Random random) {
        if (this.leftChild != null) {
            return false;
        }

        // decide vertical split or horizontal split
        int direction = -1;
        if (width < height) {
            direction = 0;
        } else if (width > height) {
            direction = 1;
        } else {
            direction = random.nextInt(2);
        }

        //decide how much do we split
        int splitSize = 0;
        if (direction == 0 && height > 6) {
            splitSize = RandomUtils.uniform(random, MIN_SIZE, height - MIN_SIZE);
            leftChild = new BPSpace(LB, width, splitSize);
            rightChild = new BPSpace(new Position(LB.x,LB.y+splitSize), width, height-splitSize);
        } else if (direction == 1 && width > 6) {
            splitSize = RandomUtils.uniform(random, MIN_SIZE, width - MIN_SIZE);
            leftChild = new BPSpace(LB, splitSize, height);
            rightChild = new BPSpace(new Position(LB.x+splitSize, LB.y), width-splitSize, height);
        } else {
            return false;
        }
        return true;
    }

    public void createRoom(Random random) {
        if (leftChild != null && rightChild != null) {
            leftChild.createRoom(random);
            rightChild.createRoom(random);
        } else {
            int offsetX = width <= MIN_SIZE ? MIN_FROM_SIDE : random.nextInt(width-MIN_SIZE) + MIN_FROM_SIDE;
            int offsetY = height <= MIN_SIZE ? MIN_FROM_SIDE : random.nextInt(height-MIN_SIZE) + MIN_FROM_SIDE;
            int roomWidth = Math.max(MIN_SIZE, RandomUtils.uniform(random, width - offsetX));
            int roomHeight = Math.max(MIN_SIZE, RandomUtils.uniform(random, height - offsetY));

            this.room = new Room(new Position(LB.x+ offsetX, LB.y + offsetY), roomWidth, roomHeight);
        }
    }

    public Position createConnectPoint(Random random) {
        if (path != null) {
            int len = path.size();
            int pathPoint = RandomUtils.uniform(random, len);
            keyPoint = path.get(pathPoint);
        } else if (room != null) {
            int point_x = room.getPosition().x + RandomUtils.uniform(random, 0, room.getWidth() - 1);
            int point_y = room.getPosition().y + RandomUtils.uniform(random, 0, room.getHeight() - 1);
            keyPoint = new Position(point_x, point_y);
        }
        return keyPoint;
    }

    public void  createPathBetweenChildren(Random random) {
        if (room != null) {
            return;
        }
        if (leftChild != null && leftChild.room == null) {
            leftChild.createPathBetweenChildren(random);
        }
        if (rightChild != null && rightChild.room == null) {
            rightChild.createPathBetweenChildren(random);
        }
        if (leftChild != null && leftChild.room != null) {
            leftChild.createConnectPoint(random);
        }
        if (rightChild != null && rightChild.room != null) {
            rightChild.createConnectPoint(random);
        }

        HashSet<Position> p = Path.connectPath(new Position(leftChild.keyPoint.x,leftChild.keyPoint.y),
                new Position(rightChild.keyPoint.x, rightChild.keyPoint.y), random);

        path = new ArrayList<>(p);
        createConnectPoint(random);

        return;
    }
}
