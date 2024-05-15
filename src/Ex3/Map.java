//my id:322530080
package Ex3;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 *
 * @author boaz.benmoshe
 */
public class Map implements Map2D {
    private int[][] _map;
    private boolean _cyclicFlag = true;

    /**
     * Constructs a w*h 2D raster map with an init value v.
     *
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v) {
        init(w, h, v);
    }

    /**
     * Constructs a square map (size*size).
     *
     * @param size
     */
    public Map(int size) {
        this(size, size, 0);
    }

    /**
     * Constructs a map from a given 2D array.
     *
     * @param data
     */
    public Map(int[][] data) {
        init(data);
    }

    /*Construct a 2D w*h matrix of double.
        method: will run 2 for loops, one that will run until get to
        width, and one will run until get to high.
        in each iteration the loops will fill the map with parameter v.
     */
    @Override
    public void init(int w, int h, int v) {
        this._map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this._map[i][j] = v;
            }
        }
    }

    /*Constructs a 2D raster map from a given 2D int array (deep copy).
    method: will firstly do the init as mentioned above and will create a deep
    copy of the array with the method of arraycopy.
     */
    @Override
    public void init(int[][] arr) {
        init(arr.length, arr[0].length, 0);
        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(arr[i], 0, this._map[i], 0, arr[i].length);
        }
    }

    /*Computes a deep copy of the underline 2D matrix.
      method: will copy the map to "ans" by manual arraycopy method.
     */
    @Override
    public int[][] getMap() {
        int[][] ans = new int[this.getWidth()][this.getHeight()];
        for (int i = 0; i < this.getWidth(); i++) {
            if (this.getHeight() >= 0) System.arraycopy(this._map[i], 0, ans[i], 0, this.getHeight());
        }
        return ans;
    }

    /*will return the width of this 2D map (first coordinate).
     method:the width is the length of the map so will get
     and return the length of the map.
     */
    @Override
    public int getWidth() {
        return this._map.length;
    }

    /*will return the height of this 2D map (second coordinate).
       method:the height is the length of each array in the map so will get
     and return the length of the map[0](length of each array).
     */
    @Override
    public int getHeight() {
        return this._map[0].length;
    }

    /*return the [x][y] (int) value of the map[x][y]
     method: the function input is x,y values so will return the
     map value in x,y coordinate.
     */
    @Override
    public int getPixel(int x, int y) {
        return this._map[x][y];
    }

    /*return the [p.x][p.y] (int) value of the map.
    method: the function input is a pixel (as an object) so will check the x
    and y values of this specific pixel and will return the x,y values as a
    pixel object.
     */
    @Override
    public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(), p.getY());
    }

    /*Set the [x][y] coordinate of the map to v.
    method:will find the x,y coordinate of the map and will set it to v as asked.
     */
    @Override
    public void setPixel(int x, int y, int v) {
        this._map[x][y] = v;
    }

    /*Set the [x][y] coordinate of the map to v.
    method: the input of this function is a pixel object, so will
    get the x and the y value of the specific object and will set it to v as asked.
    the difference from the function that is mentioned above is the input of the function.
     */
    @Override
    public void setPixel(Pixel2D p, int v) {
        this._map[p.getX()][p.getY()] = v;
    }

    @Override
    /**
     * Fills this map with the new color (new_v) starting from p.
     * Method:firstly will make a copy of the map with implementation of all distance function.
     * A nested loop will iterate threw the whole 2D array that is representing the map.
     * All the positive numbers in the 2D array are numbers that considered as connected component,
     * so the function will fill them with v. The answer that will be returned will be the int amount
     * of the indexes that was filled.
     */
    public int fill(Pixel2D xy, int new_v) {
        int ans = 0;
        int[][] newMap = this.allDistance(xy, -1).getMap();
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                if (newMap[i][j] >= 0) {
                    this.setPixel(i, j, new_v);
                    ans++;
                }
            }
        }
        return ans;
    }

    @Override
    /**
     * BFS like shortest the computation based on iterative raster implementation of BFS, see:
     * https://en.wikipedia.org/wiki/Breadth-first_search.
     * Compute the shortest valid path between p1 and p2.
     * method: if p1=p2: will return the pixel of p1.
     * else: will get the all distance map by "get map" method.
     * will fill the starting pixel and the ending pixel to the ans[0],ans[length]
     * by getting the x and y values of each and setting them as a pixel.
     * if the map is cyclic: will run a loop that will fill the ans array. the loop
     * will start from the cell that holds the ending value -1 value(back tracking), each iteration
     * will
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        Pixel2D[] ans = null;  // the result.
        if (p1.equals(p2)) {
            ans = new Pixel2D[]{p1};
            return ans;
        }
        int[][] disMap = this.allDistance(p1, obsColor).getMap();
        int x = p2.getX();
        int y = p2.getY();
        int sx = p1.getX();
        int sy = p1.getY();
        int w = this.getWidth();
        int h = this.getHeight();
        ans = new Pixel2D[disMap[x][y] + 1];
        ans[disMap[x][y]] = new Index2D(x, y);
        ans[0] = new Index2D(sx, sy);
        for (int i = disMap[x][y] - 1; i > 0; i--) {
            if (this._cyclicFlag) {
                if (x == 0 && disMap[w - 1][y] == disMap[x][y] - 1) {
                    x = w - 1;
                    ans[i] = new Index2D(x, y);
                }
                else if (y == 0 && disMap[x][h - 1] == disMap[x][y] - 1) {
                    y = h - 1;
                    ans[i] = new Index2D(x, y);
                }
                else if (x == w - 1 && disMap[0][y] == disMap[x][y] - 1) {
                    x = 0;
                    ans[i] = new Index2D(x, y);
                }
                else if (y == h - 1 && disMap[x][0] == disMap[x][y] - 1) {
                    y = 0;
                    ans[i] = new Index2D(x, y);
                }
               else if (x + 1 < disMap.length && disMap[x + 1][y] == disMap[x][y] - 1) {
                    x++;
                    ans[i] = new Index2D(x, y);
                }
               else if (y + 1 < disMap[0].length && disMap[x][y + 1] == disMap[x][y] - 1) {
                    y++;
                    ans[i] = new Index2D(x, y);
                }
               else if (y - 1 >= 0 && disMap[x][y - 1] == disMap[x][y] - 1) {
                    y--;
                    ans[i] = new Index2D(x, y);
                }
              else  if (x - 1 >= 0 && disMap[x - 1][y] == disMap[x][y] - 1) {
                    x--;
                    ans[i] = new Index2D(x, y);
                }

            } else {
                if (x + 1 < disMap.length && disMap[x + 1][y] == disMap[x][y] - 1) {
                    x++;
                    ans[i] = new Index2D(x, y);
                }
                else if (y + 1 < disMap[0].length && disMap[x][y + 1] == disMap[x][y] - 1) {
                    y++;
                    ans[i] = new Index2D(x, y);
                }
               else if (y - 1 >= 0 && disMap[x][y - 1] == disMap[x][y] - 1) {
                    y--;
                    ans[i] = new Index2D(x, y);
                }
               else if (x - 1 >= 0 && disMap[x - 1][y] == disMap[x][y] - 1) {
                    x--;
                    ans[i] = new Index2D(x, y);
                }
            }
        }
        return ans;
    }

    /*return true if p is with in this map.
    method: the function input is a pixel object. the function will check if the
    pixel is inside the boundaries of the map by checking that the x value of the
    pixel is rather higher that map length nor lesser than 0 and that y value of the pixel
    is rather higher that map arrays length nor lesser than 0.
     */
    @Override
    public boolean isInside(Pixel2D p) {
        return p.getX() <= this._map.length && p.getY() <= this._map[0].length && p.getX() >= 0 && p.getY() >= 0;
    }

    /*return true if this map should be addressed as a cyclic one.
    method: will return the boolean value of the cyclic condition.
     */
    @Override
    public boolean isCyclic() {
        return this._cyclicFlag;
    }
    /*Set the cyclic flag of this map
    method: the input of the function is the boolean which decides the cyclic
    value of the map, the function will set the cyclic flag to the cy value rather true
    or false.
     */

    @Override
    public void setCyclic(boolean cy) {
        this._cyclicFlag = cy;
    }

    /*an auxiliary function for the all distance function.
     method: will check if the index that is under the current index in the array
     is valid by checking that he is -2(the value of valid pixels(will set in the function),
     and is inside the boundaries of the map
     */
    public static boolean downIsValid(int x, int y, int[][] a) {
        return x - 1 >= 0 && a[x - 1][y] == -2;
    }
    /*an auxiliary function for the all distance function.
     method: will check if the index that is above the current index in the array
     is valid by checking that he is -2(the value of valid pixels(will set in the function),
     and is inside the boundaries of the map
     */

    public static boolean upIsValid(int x, int y, int[][] a) {
        return x + 1 < a.length && a[x + 1][y] == -2;
    }

    /*an auxiliary function for the all distance function.
         method: will check if the index that is right to the current index in the array
         is valid by checking that he is -2(the value of valid pixels(will set in the function),
         and is inside the boundaries of the map
         */
    public static boolean rightIsValid(int x, int y, int[][] a) {
        return y + 1 < a[0].length && a[x][y + 1] == -2;
    }

    /*an auxiliary function for the all distance function.
         method: will check if the index that is left to the current index in the array
         is valid by checking that he is -2(the value of valid pixels(will set in the function),
         and is inside the boundaries of the map
         */
    public static boolean LeftIsValid(int x, int y, int[][] a) {
        boolean w = y - 1 >= 0 && a[x][y - 1] == -2;

        return w;
    }

    /*an auxiliary function for the all distance function.
    method: this function will check all the valid detraction's of a
     non-cyclic map from the current index and will change them by adding one from the
     value of the current index value.
     */
    public static void changeNotCyclic(int i, int j, int[][] a) {
        if (downIsValid(i, j, a)) {
            a[i - 1][j] = a[i][j] + 1;
        }
        if (upIsValid(i, j, a)) {
            a[i + 1][j] = a[i][j] + 1;
        }
        if (rightIsValid(i, j, a)) {
            a[i][j + 1] = a[i][j] + 1;
        }
        if (LeftIsValid(i, j, a)) {
            a[i][j - 1] = a[i][j] + 1;
        }
    }
    /*Compute a new map (with the same dimension as this map) with the
     * shortest path distance (obstacle avoiding) from the start point.
     * method: firstly this function will make a copy of the map by getMap method.
     * Then will set the obstacles to -1 as were asked(by 2 nested for loops) .
     *  after set the valid indexes  to -2(by 2 nested for loops).
     * after the map is compliantly full by -1,-2 will set the starting point to be 0.
     *
     *  after will start 3 new nested for loops:
     * first one: will start from 0 and each time will increase by one(the value of the current pixel)
     * second and third: each iteration will run threw all the 2D array and find the index with value
     * of the first for loop.
     *
     * if the map is cyclic:
     * will check by "if" conditions is the indexes that are on the opposite side of the map
     *  are valid(as determined in the map2D interface) if so, will set them to be the current index+1.
     * if the current index is not on the boundaries of the map will implement the "changeNotCyclic"
     * function(to change the valid indexes that are not related to the cyclic way, regularly).
     *
     * if the map is not cyclic: will just implement the "changeNotCyclic" function threw the nested loop.
     */

    @Override
    public Map2D allDistance(Pixel2D start, int obsColor) {
        Map2D ans;
        int width = this.getWidth();
        int height = this.getHeight();
        int[][] newMap = this.getMap();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (newMap[i][j] == obsColor) {
                    newMap[i][j] = -1;
                } else {
                    newMap[i][j] = -2;
                }
            }
        }
        newMap[start.getX()][start.getY()] = 0;
        for (int b = 0; b < width * height; b++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (newMap[i][j] == b) {
                        if (this._cyclicFlag) {
                            if (i == 0 && newMap[width - 1][j] == -2) {
                                newMap[width - 1][j] = b + 1;
                            }
                            if (j == 0 && newMap[i][height - 1] == -2) {
                                newMap[i][height - 1] = b + 1;
                            }
                            if (i == width - 1 && newMap[0][j] == -2) {
                                newMap[0][j] = b + 1;
                            }
                            if (j == height - 1 && newMap[i][0] == -2) {
                                newMap[i][0] = b + 1;
                            }
                            changeNotCyclic(i, j, newMap);
                        } else {
                            changeNotCyclic(i, j, newMap);
                        }
                    }
                }
            }
        }
        ans = new Map(newMap);
        return ans;
    }
}
