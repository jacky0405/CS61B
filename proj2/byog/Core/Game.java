package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 38;
    private String seed = "";
    private TETile[][] world;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        initialGame();
        menu(false);
        detectNL();
//        ter.initialize(WIDTH, HEIGHT);
//        String input = "";
//        boolean isPlaying = false;
//
//        ter.renderFrame();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    private void initialGame() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    private void menu(boolean keySeed) {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, HEIGHT - 5, "Build Jacky's World !!");
        StdDraw.line(20, HEIGHT - 7, 60, HEIGHT - 7);
        StdDraw.text(midWidth, HEIGHT - 20, "New Game (N)");
        StdDraw.text(midWidth, HEIGHT - 22, "Load Game (L)");
        StdDraw.text(midWidth, HEIGHT - 24, "Quit (Q)");
        if (keySeed) {
            StdDraw.text(midWidth, HEIGHT - 28, "Enter A Seed:");
            StdDraw.text(midWidth, HEIGHT - 30, seed);
        }
        StdDraw.show();
    }

    private void detectNL() {
        int midWidth = WIDTH / 2;

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 'N' || key == 'n') {
                StdDraw.text(midWidth, HEIGHT - 28, "Enter A Seed:");
                StdDraw.show();
                while (true) {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    char seedNumber = StdDraw.nextKeyTyped();
                    if (seedNumber == 'S' || seedNumber == 's') {
                        createWorld(Integer.valueOf(seed));
                        playerMoving();
                        break;
                    }
                    seed += String.valueOf(seedNumber);
                    menu(true);
                }
            } else if (key == 'L' || key == 'l') {
                ter.initialize(WIDTH, HEIGHT);
                ter.renderFrame(this.world);
                playerMoving();
            }
        }
    }

    private void createWorld(int seed) {
        ter.initialize(WIDTH, HEIGHT);
        Random random = new Random(seed);

        this.world = new TETile[WIDTH][HEIGHT];
        for(int i=0; i < WIDTH; i++) {
            for (int j=0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        World.placeRooms(random);
        World.construct(this.world, World.floorPoints, Tileset.FLOOR);
        World.addWallPoints(this.world, random);
        World.construct(this.world,  World.wallPoints, Tileset.WALL);
        // draw a door
        this.world[ World.door.x][ World.door.y] = Tileset.LOCKED_DOOR;
        // draw a player
        this.world[ World.player.x][ World.player.y] = Tileset.PLAYER;

        System.out.println( World.floorPoints.size());
        System.out.println( World.wallPoints.size());

        ter.renderFrame(this.world);
    }

    private void playerMoving() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char move = StdDraw.nextKeyTyped();
            Position player = new Position(World.player.x,  World.player.y);
            if (move == 'W' || move == 'w') {
                player.y += 1;
                if (checkIfTouchWall(player)){
                    continue;
                }
                World.player.y += 1;
            } else if (move == 'S' || move == 's') {
                player.y -= 1;
                if (checkIfTouchWall(player)){
                    continue;
                }
                World.player.y -= 1;
            } else if (move == 'D' || move == 'd') {
                player.x += 1;
                if (checkIfTouchWall(player)){
                    continue;
                }
                World.player.x += 1;
            } else if (move == 'A' || move == 'a') {
                player.x -= 1;
                if (checkIfTouchWall(player)){
                    continue;
                }
                World.player.x -= 1;
            } else if (move == 'Q' || move == 'q') {
                menu(false);
                detectNL();
                break;
            }
            if (this.world[ World.player.x][ World.player.y] == Tileset.LOCKED_DOOR) {
                drawWin();
                break;
            }
            World.construct(this.world, World.floorPoints, Tileset.FLOOR);
            this.world[ World.player.x][ World.player.y] = Tileset.PLAYER;
            ter.renderFrame(world);
        }
    }

    private boolean checkIfTouchWall(Position p) {
        return this.world[p.x][p.y] == Tileset.WALL;
    }

    private void drawWin() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, "Congratulation!! You Win.");
        StdDraw.show();
    }

}
