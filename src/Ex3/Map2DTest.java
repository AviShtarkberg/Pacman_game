//my id:322530080
package Ex3;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class Map2DTest {
    //sets a new regular 2D array for the tests
    public static final int[][] map1 = {{-2, -2, -1, -2}, {-2, -2, -2, -2}, {-1, -2, -1, -2}};

    //sets a new "maze" kind 2D array for the tests
    public static final int[][] maze = {{-1, -1, -1, -1, -1, -1}, {-1, -2, -2, -2, -2, -1},
            {-1, -2, -1, -1, -2, -1}, {-1, -2, -1, -2, -2, -1}, {-1, -2, -1, -1, -1, -1}, {-1, -2, -1, -1, -1, -1},
            {-1, -2, -2, -2, -2, -2}, {-1, -1, -1, -1, -1, -1}};

    /*this test tests that the map that we get from the getMap method is the one that we expect.
    Moreover, will check that chancing the copy of the test doesn't affect the original map.
     */
    @Test
    public void getMapTest() {
        Map map2 = new Map(map1);
        int[][] newMap = map2.getMap();
        for (int i = 0; i < newMap.length; i++) {
            for (int j = 0; j < newMap[0].length; j++) {
                assertEquals(newMap[i][j], map1[i][j]);
            }
        }
        newMap[2][3] = 3;
        boolean a = newMap[2][3] != map1[2][3];
        assertTrue(a);
    }

    /*this test will check if the width that we get from a specific map is correct
    (using map1 as an array to test).
     */
    @Test
    public void getWidthTest() {
        Map2D map = new Map(map1);
        int w = map.getWidth();
        assertEquals(w, 3);
    }

    /*this test will check if the height that we get from a specific map is correct
    (using map1 as an array to test).
     */
    @Test
    public void getHeightTest() {
        Map2D map = new Map(map1);
        int h = map.getHeight();
        assertEquals(h, 4);
    }

    /*this test will test if the specific pixel with x,y values of the map that is represented
    as 2D array of map1 have the same value as in the array.
     */
    @Test
    public void getPixelTestType1() {
        Map2D map = new Map(map1);
        int pixel = map.getPixel(0, 0);
        assertEquals(pixel, -2);
    }

    /*this test will test if the specific pixel of the map that is represented
   as 2D array of map1 have the same value as in the array.
    */
    @Test
    public void getPixelTestType2() {
        Pixel2D a = new Index2D(2, 0);
        Map2D map = new Map(map1);
        int pixel = map.getPixel(a);
        assertEquals(pixel, -1);
    }

    /*this test will check if the setPixel with x,y values function with 2 nested for loops
    will set all the pixels of the array to a specific value.
     */
    @Test
    public void setPixelTestType1() {
        Map2D map = new Map(map1);
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                map.setPixel(i, j, 20);
            }
        }
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                assertEquals(map.getMap()[i][j], 20);
            }
        }

    }

    /*this test will check if the setPixel with specific pixel function
    will set the right pixel from the array to a specific value.
     */
    @Test
    public void setPixelTestType2() {
        Pixel2D a = new Index2D(2, 0);
        Map2D map = new Map(map1);
        map.setPixel(a, -100);
        assertEquals(map.getMap()[2][0], -100);
    }

    /*this test will test the fill function in a none cyclic maze map. the test will
    check if all the connected component are filled with new_v value.
     will check the amount of the indexes that was changed after the filling function.
     */
    @Test
    public void fillNotCyclicTest() {
        Map2D map2 = new Map(maze);
        Pixel2D zero = new Index2D(3, 3);
        int a = map2.fill(zero, 100);
        int[][] expected = {{-1, -1, -1, -1, -1, -1}, {-1, 100, 100, 100, 100, -1}, {-1, 100, -1, -1, 100, -1},
                {-1, 100, -1, 100, 100, -1}, {-1, 100, -1, -1, -1, -1}, {-1, 100, -1, -1, -1, -1}, {-1, 100, 100, 100, 100, 100},
                {-1, -1, -1, -1, -1, -1}};
        for (int i = 0; i < map2.getWidth(); i++) {
            for (int j = 0; j < map2.getHeight(); j++) {
                assertEquals(map2.getMap()[i][j], expected[i][j]);
            }
        }
        assertEquals(a, 16);
    }

    /*this test will test the fill function in a cyclic regular map. the test will
    check if all the connected component are filled with new_v value.
     will check the amount of the indexes that was changed after the filling function.
     */
    @Test
    public void fillCyclicTest() {
        Pixel2D zero = new Index2D(2, 1);
        Map2D map2 = new Map(map1);
        map2.setCyclic(true);
        int a = map2.fill(zero, 100);
        int[][] expected = {{100, 100, -1, 100}, {100, 100, 100, 100}, {-1, 100, -1, 100}};
        for (int i = 0; i < map2.getWidth(); i++) {
            for (int j = 0; j < map2.getHeight(); j++) {
                assertEquals(expected[i][j], map2.getMap()[i][j]);
            }
        }
        assertEquals(a, 9);
    }
    /*this test will test the shortest path without cyclic mode.
       the test will run threw map1 and will check that the shortest path function is getting the right
       pixels array as asked.
        */
    @Test
    public void shortestPathNotCyclic() {
        Map2D map2 = new Map(map1);
        map2.setCyclic(false);
        Pixel2D zero = new Index2D(2, 1);
        Pixel2D step1 = new Index2D(1, 1);
        Pixel2D step2 = new Index2D(1, 2);
        Pixel2D step3 = new Index2D(1, 3);
        Pixel2D aim = new Index2D(2, 3);
        Pixel2D[] exp = {zero, step1, step2, step3, aim};
        assertArrayEquals(map2.shortestPath(zero, aim, -1), exp);
    }

    /*this test will test the shortest path with cyclic mode.
    the test will run threw map1 and will check that the shortest path function( with
    consideration to the cyclic settings as given at the interface) is getting the right
    pixels array as asked.
     */
    @Test
    public void shortestPathCyclic() {
        Map2D map2 = new Map(map1);
        map2.setCyclic(true);
        Pixel2D zero = new Index2D(2, 1);
        Pixel2D step1 = new Index2D(0, 1);
        Pixel2D step2 = new Index2D(0, 0);
        Pixel2D aim = new Index2D(0, 3);
        Pixel2D[] exp = {zero, step1, step2, aim};
        assertArrayEquals(map2.shortestPath(zero, aim, -1), exp);
    }

    /*Test the isInside function with 2 different pixels, one that is inside the map
    and one that is outside the map. expect to get true and false respectively.
     */
    @Test
    public void isInsideTest() {
        Map2D map2 = new Map(maze);
        Pixel2D p1 = new Index2D(2, 1);
        Pixel2D p2 = new Index2D(100, 10);
        assertTrue(map2.isInside(p1));
        assertFalse(map2.isInside(p2));
    }

    /*test (set,is) cyclic functions by determine map1 to be cyclic and maze map to be
    not cyclic(checks the setCyclic function) and after checks the isCyclic function by checking
    if the "cyclicFlag" was changed.
     */
    @Test
    public void CyclicTest() {
        Map2D map2 = new Map(map1);
        map2.setCyclic(false);
        assertFalse(map2.isCyclic());
        Map2D map3 = new Map(maze);
        map2.setCyclic(true);
        assertTrue(map3.isCyclic());
    }


    /*this test will test all the "is valid" functions.the test will check the boolean
    value that is returned from each function by checking if the next pixel is inside the map,
    and with the value of -2(allowed values to step on).
     */
    @Test
    public void IsValidTest() {
        int[][] map2 = {{-2, -2, -1, -2}, {-2, -2, -2, -2}, {-1, -2, -1, -2}};
        assertTrue(Map.downIsValid(1, 0, map2));
        assertFalse(Map.downIsValid(0, 0, map2));
        assertFalse(Map.downIsValid(0, 1, map2));
        assertTrue(Map.upIsValid(1, 3, map2));
        assertFalse(Map.upIsValid(2, 1, map2));
        assertFalse(Map.upIsValid(1, 2, map2));
        assertTrue(Map.rightIsValid(1, 2, map2));
        assertFalse(Map.rightIsValid(1, 3, map2));
        assertFalse(Map.rightIsValid(2, 1, map2));
        assertTrue(Map.LeftIsValid(1, 1, map2));
        assertFalse(Map.LeftIsValid(1, 0, map2));
        assertFalse(Map.LeftIsValid(2, 1, map2));
    }

    /* this test will test the changeNotCyclic function, will check that all the valid indexes
    have been changed and the one that not suppose to change stays as they are.
     */
    @Test
    public void testChange() {
        map1[2][1] = 0;
        map1[1][1] = 1;
        Map.changeNotCyclic(1, 1, map1);
        assertEquals(map1[1][0], 2);
        assertEquals(map1[0][1], 2);
        assertEquals(map1[1][2], 2);
        assertEquals(map1[2][1], 0);
    }

    /*this test tests the allDistance function without cyclic map,
    the aim is to make a kind of "maze" as 2D array and check that the allDistance
    function will fill the steps correctly.
     */
    @Test
    public void allDistanceTestNotCyclic() {
        int[][] expected = {{-1, -1, -1, -1, -1, -1}, {-1, 6, 5, 4, 3, -1}, {-1, 7, -1, -1, 2, -1},
                {-1, 8, -1, 0, 1, -1}, {-1, 9, -1, -1, -1, -1}, {-1, 10, -1, -1, -1, -1}, {-1, 11, 12, 13, 14, 15},
                {-1, -1, -1, -1, -1, -1}};
        Pixel2D zero = new Index2D(3, 3);
        Map2D map2 = new Map(maze);
        Map2D dis = map2.allDistance(zero, -1);
        for (int i = 0; i < dis.getWidth(); i++) {
            for (int j = 0; j < dis.getHeight(); j++) {
                assertEquals(expected[i][j], dis.getMap()[i][j]);
            }
        }
    }

    /*this test will test the allDistance function with a cyclic map.
    the values of the expected map should be as cyclic was defined in the interface.
     */
    @Test
    public void allDistanceTestCyclic() {
        Pixel2D zero = new Index2D(2, 1);
        Map2D map2 = new Map(map1);
        map2.setCyclic(true);
        Map2D dis = map2.allDistance(zero, -1);
        int[][] expected = {{2, 1, -1, 3}, {2, 1, 2, 3}, {-1, 0, -1, 4}};
        for (int i = 0; i < map2.getWidth(); i++) {
            for (int j = 0; j < map2.getHeight(); j++) {
                assertEquals(expected[i][j], dis.getMap()[i][j]);
            }
        }
    }

}
