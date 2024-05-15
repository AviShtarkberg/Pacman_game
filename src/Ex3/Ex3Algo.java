//my id: 322530080
package Ex3;

import java.awt.*;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 * <p>
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo {
    private int _count;

    public Ex3Algo() {
        _count = 0;
    }

    @Override
    /**
     *  Add a short description for the algorithm as a String.
     */
    public String getInfo() {
        return "my algorithm will run threw 2 maps each iteration" +
                "the first map is the all distance map that was defined in the first part of the " +
                "ex3. each iteration my algorithm will find the shortest path to the closest pink point" +
                "in the map by calculating with all distance map. if there is a ghost aside of the pack man" +
                " the pack man will go to the negative direction of the ghost." +
                "if the pack man ate an green point, the pack man will run to catch the ghost because their are" +
                "eatable, the limit for running after the ghosts will be with respect to ghost remainTimeAsEatable function."
                ;
    }

    @Override
    /**
     * This ia the main method - that you should design, implement and test.
     */
    public int move(PacmanGame game) {
        if (_count == 0 || _count == 300) {
            int code = 0;
            int[][] board = game.getGame(0);
            printBoard(board);
            int blue = Game.getIntColor(Color.BLUE, code);
            int pink = Game.getIntColor(Color.PINK, code);
            int black = Game.getIntColor(Color.BLACK, code);
            int green = Game.getIntColor(Color.GREEN, code);
            System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
            String pos = game.getPos(code);
            System.out.println("Pacman coordinate: " + pos);
            GhostCL[] ghosts = game.getGhosts(code);
            printGhosts(ghosts);
            int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
        }
        _count++;
        int code = 0;
        int[][] board = game.getGame(code);
        Map map = new Map(board);
        map.setCyclic(game.isCyclic());
        GhostCL[] ghosts = game.getGhosts(code);
        String[] posArray = game.getPos(code).split(",");
        int xStart = Integer.parseInt(posArray[0]);
        int yStart = Integer.parseInt(posArray[1]);
        Pixel2D current = new Index2D(xStart, yStart);
        int obs = Game.getIntColor(Color.BLUE, code);
        int[][] disBoard = map.allDistance(current, obs).getMap();
        int xEnd = minPink(map, disBoard)[0];
        int yEnd = minPink(map, disBoard)[1];
        Pixel2D aim = new Index2D(xEnd, yEnd);
        Pixel2D[] way = map.shortestPath(current, aim, obs);
        int wayX = way[1].getX();
        int wayY = way[1].getY();
        int currentX = current.getX();
        int currentY = current.getY();
        int dir = 0;
        if (map.isCyclic()) {
            if (ghostIsValid(ghosts, 0, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 0, map, current, obs);
            }
            else if (ghostIsValid(ghosts, 1, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 1, map, current, obs);
            } else if (ghostIsValid(ghosts, 2, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 2, map, current, obs);
            } else if (ghostIsValid(ghosts, 3, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 3, map, current, obs);
            } else if (ghostIsValid(ghosts, 4, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 4, map, current, obs);
            } else if (ghostIsValid(ghosts, 5, map, current, obs)) {
                dir = ghostIsTarget(ghosts, 5, map, current, obs);
            } else  {
                dir = directionCyclic(currentX, currentY, wayX, wayY, map);
            }
        } else {
            dir = directionNotCyclic(currentX, currentY, wayX, wayY);
            for (int i = 0; i < 6; i++) {
                if (ghostIsValid(ghosts, i, map, current, obs)) {
                    dir = ghostIsTarget(ghosts, i, map, current, obs);
                }
            }
            if (ghostIsNear(ghosts, map, current, obs)) {
                dir = dirIfGhostIsNear(ghosts, map, current, obs);
            }
        }
        return dir;
    }

    private static void printBoard(int[][] b) {
        for (int y = 0; y < b[0].length; y++) {
            for (int[] ints : b) {
                int v = ints[y];
                System.out.print(v + "\t");
            }
            System.out.println();
        }
    }

    /*this function will find the minimum value in the all distance map that is pink in the game map.
    method: the function input will be the game map and the all distance map, the function will
    run threw a nested 2 for loops and will start with int temp that is map height multiplied by map width
    (the maximum value that can be at index at the all distance array) and each iteration will check
    that the temp current value is less than the last one. the function will return 1D array that contain the
    index(x,y) of the minimum value of the all distance map that is pink.
     */
    private static int[] minPink(Map map, int[][] disBoard) {
        int[] ans = new int[2];
        int xEnd = 0;
        int yEnd = 0;
        int pink = Game.getIntColor(Color.PINK, 0);
        int temp = map.getHeight() * map.getWidth();
        for (int j = 0; j < disBoard.length; j++) {
            for (int k = 0; k < disBoard[0].length; k++) {
                if (disBoard[j][k] < temp && map.getPixel(j, k) == pink) {
                    temp = disBoard[j][k];
                    xEnd = j;
                    yEnd = k;
                }
            }
        }
        ans[0] = xEnd;
        ans[1] = yEnd;
        return ans;
    }

    /*this function will return the direction that the pacman should go with respect to cyclic map.
    method: each time will check the current index and the target pixel (that will be defined by the shortest path
    function[1]), and will check if the pacman should go to left,right,up or down direction by checking
    if the value of x or y is + or - 1 if the pacman is in the edges of the map will determine with respect to
     cyclic mode settings.
     */
    private static int directionCyclic(int currentX, int currentY, int wayX, int wayY, Map map) {
        int w = map.getWidth();
        int h = map.getHeight();
        int dir = 0;
        if (wayX == w - 1) {
            dir = Game.LEFT;
        } else if (wayY == h - 1) {
            dir = Game.DOWN;
        } else if (wayX == 0) {
            dir = Game.RIGHT;
        } else if (wayY == 0) {
            dir = Game.UP;
        }
        if (wayX == currentX + 1) {
            dir = Game.RIGHT;
        } else if (wayX == currentX - 1) {
            dir = Game.LEFT;
        } else if (wayY == currentY - 1) {
            dir = Game.DOWN;
        } else if (wayY == currentY + 1) {
            dir = Game.UP;
        }
        return dir;
    }

    /*this function will return the direction that the pacman should go with respect to non-cyclic map.
    method: each time will check the current index and the target pixel (that will be defined by the shortest path
    function[1]), and will check if the pacman should go to left,right,up or down direction by checking
    if the value of x or y is + or - 1;

     */
    private static int directionNotCyclic(int currentX, int currentY, int wayX, int wayY) {
        int dir = 0;
        if (wayX == currentX + 1) {
            dir = Game.RIGHT;
        } else if (wayX == currentX - 1) {
            dir = Game.LEFT;
        } else if (wayY == currentY - 1) {
            dir = Game.DOWN;
        } else if (wayY == currentY + 1) {
            dir = Game.UP;
        }
        return dir;
    }

    /*this function will get the ghost pixel.
    method: the function will get int i and ghosts array.
    the function will use the getPos function to get the string that represent the
    ghost position in, then the function will split the string and get the x and y as int by parsing
    then will construct a pixel by index2D constructor.

     */
    private static Pixel2D ghostPixel(GhostCL[] ghosts, int i) {
        String ghostPos = ghosts[i].getPos(0);
        String[] ghostPosIndex = ghostPos.split(",");
        int ghostX = Integer.parseInt(ghostPosIndex[0]);
        int ghostY = Integer.parseInt(ghostPosIndex[1]);
        return new Index2D(ghostX, ghostY);
    }

    /*this function will return a boolean that represent if the ghost is small(valid to eat).
    method: the function will check that the remain time to eat ghost[i] is less than 1 sec
    and the shortest path between the current location and the ghost location is less than 5 steps.
     */
    private static boolean ghostIsValid(GhostCL[] ghosts, int i, Map map, Pixel2D current, int obs) {
        return (ghosts[i].remainTimeAsEatable(0) > 1 && map.shortestPath(ghostPixel(ghosts, i), current, obs).length < 5);
    }

    /*This function will set the ghost to be the target of the move if the ghost is eatable.
    method:the input of the function is the ghosts array, int i, the map, the current pixel of
    pacman and the obs color. the function will get the current x and y values of pacman with
    getX, getY method and if the ghost is eatable(checked with ghostIsValid method), will set the ghost[i]
    t be the target that we want the pacman to move to. the pixel for the first move each time will
    be calculated with shortestPath[1] method and getting the x and the y values of the first pixel
    that we want the pacman to move to. the dir will be determined with directionCyclic/directionNotCyclic
    with respect to the cyclic mode of the map.

     */
    private static int ghostIsTarget(GhostCL[] ghosts, int i, Map map, Pixel2D current, int obs) {
        int dir = 0;
        int currentX = current.getX();
        int currentY = current.getY();
        if (ghostIsValid(ghosts, i, map, current, obs)&&map.shortestPath(ghostPixel(ghosts, i), current, obs).length>1) {
            int x = map.shortestPath(ghostPixel(ghosts, i), current, obs)[1].getX();
            int y = map.shortestPath(ghostPixel(ghosts, i), current, obs)[1].getY();
            if (map.isCyclic()) {
                dir = directionCyclic(currentX, currentY, x, y, map);
            } else {
                dir = directionNotCyclic(currentX, currentY, x, y);
            }
        }
        return dir;
    }

    /*this function will check if there is a ghost near to pacman.
    method: the function will return true if there is a ghost that is

    */
    private static boolean ghostIsNear(GhostCL[] ghost, Map map, Pixel2D current, int obs) {
        for (int i = 1; i < 6; i++) {
            if (!ghostIsValid(ghost, i, map, current, obs) && map.shortestPath(ghostPixel(ghost, i), current, obs).length < 4) {
                return true;
            }
        }
        return false;
    }

    /*this function will return the dir that the pacman should go if there is a ghost near to him in not cyclic map.
    method: will check for each ghost if the ghost is near by for loop that will iterate threw the ghosts
    array and will check if the index of the array is bigger or smaller of the y and the x values of the
    current pacman location with using the ghost pixel method and the get x and get y methods. also will check
    if the ghost is near by meaning of shortest path by using ghostIsNear function and will check that the
    ghost is not small.

     */
    private static int dirIfGhostIsNear(GhostCL[] ghost, Map map, Pixel2D current, int obs) {
        int dir = 0;
        for (int i = 1; i < 6; i++) {
            if (ghostIsNear(ghost, map, current, obs)) {
                if (ghostPixel(ghost, i).getX() < current.getX() + 3) {
                    dir = Game.LEFT;
                } else if (ghostPixel(ghost, i).getX() > current.getX() - 3) {
                    dir = Game.RIGHT;
                } else if (ghostPixel(ghost, i).getY() > current.getY() - 3) {
                    dir = Game.UP;
                } else if (ghostPixel(ghost, i).getY() < current.getY() + 3) {
                    dir = Game.DOWN;
                }
            }
        }
        return dir;
    }

    private static void printGhosts(GhostCL[] gs) {
        for (int i = 0; i < gs.length; i++) {
            GhostCL g = gs[i];
            System.out.println(i + ") status: " + g.getStatus() + ",  type: " + g.getType() + ",  pos: " + g.getPos(0) + ",  time: " + g.remainTimeAsEatable(0));
        }
    }

}