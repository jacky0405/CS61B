package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

public class World {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 38;
    private static final int maxRooms = 30;
    private static final int minRooms = 20;
    public static Position door;
    public static Position player;
    public static List<Room> rooms = new ArrayList<>();
    public static HashSet<Position> floorPoints = new HashSet<>();
    public static HashSet<Position> wallPoints = new HashSet<>();


    public static void placeRooms(Random random) {
        BPSpace root = new BPSpace(new Position(0,0), WIDTH, HEIGHT);

        ArrayDeque<BPSpace> spaceQueue = new ArrayDeque<>();
        List<BPSpace> spaceList = new ArrayList<>();
        spaceQueue.offer(root);
        spaceList.add(root);

        int num = RandomUtils.uniform(random, minRooms, maxRooms);

        while (num > 0 && !spaceQueue.isEmpty()) {
            BPSpace space = spaceQueue.poll();
            if (space.doPartition(random)) {
                spaceList.add(space.leftChild);
                spaceList.add(space.rightChild);
                spaceQueue.offer(space.leftChild);
                spaceQueue.offer(space.rightChild);
            }
            num--;
        }

        //create room and connect path
        root.createRoom(random);
        root.createPathBetweenChildren(random);
        for (BPSpace s : spaceList) {
            if (s.path != null) {
                floorPoints.addAll(s.path);
            }
            if (s.room != null) {
                rooms.add(s.room);
                floorPoints.addAll(s.room.roomPositions());
            }
        }
        Position[] playerCollection = floorPoints.toArray(new Position[0]);
        player = playerCollection[RandomUtils.uniform(random, floorPoints.size())];
    }

    public static void addWallPoints(TETile[][] world, Random random) {
        int[] direction;
        List<int[]> dirList = new ArrayList<>();
        for (int i=-1; i < 2; i++) {
            for(int j=-1; j < 2; j++) {
                direction = new int[2];
                direction[0] = i;
                direction[1] = j;
                dirList.add(direction);
            }
        }
        for (Position fp : floorPoints) {
            for (int[] dir : dirList) {
                Position wallPoint = new Position(fp.x + dir[0], fp.y + dir[1]);
                if (wallPoint.x < 0 || wallPoint.y < 0 || wallPoint.x > WIDTH || wallPoint.y > HEIGHT) continue;
                if (world[wallPoint.x][wallPoint.y] == Tileset.FLOOR) continue;

                wallPoints.add(new Position(wallPoint.x,wallPoint.y));
            }
        }
        Position[] doorCollection = wallPoints.toArray(new Position[0]);
        door = doorCollection[RandomUtils.uniform(random, wallPoints.size())];
    }

    public static void construct(TETile[][] world, HashSet<Position> points, TETile tile) {
        for (Position p : points) {
            world[p.x][p.y] = tile;
        }
    }

}
