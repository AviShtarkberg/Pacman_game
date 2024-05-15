//id:322530080
package Ex3;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    //this test will test the distance2D function with 1 distance that we expect to be 0, one that we
    //want to check what will return the right distance and will check with 2 pixels that its not
    //return wrong  distance.
    @Test
    public void distance2DTest() {
        Pixel2D a = new Index2D(5, 3);
        Pixel2D b = new Index2D(5, 3);
        Pixel2D x = new Index2D(2, 0);
        Pixel2D d = new Index2D(5, 4);
        double c = a.distance2D(b);
        double e = d.distance2D(x);
        double f = b.distance2D(a);
        assertEquals(c, 0);
        assertEquals(e, 5);
        assertNotEquals(f, 2);
    }
    //tests the toString of a pixel2D function, will check that after
    //using the toString method we get the string that we expect.
    @Test
    public void toStringTest() {
        Pixel2D a = new Index2D(5, 3);
        String c = a.toString();
        assertEquals("5,3",c);
        assertNotEquals("3,3",c);
    }
    //will test the equals function. will check that for 2 pixels that are the same we get true
    //and for a pixel and a non-same type class with same values we get false as expected.
    @Test
    public void equalsTest(){
        Pixel2D a = new Index2D(5, 3);
        Pixel2D b = new Index2D(5, 3);
        int [] x = {5,3};
        assertTrue(a.equals(b));
        assertFalse(x.equals(a));
    }
    //this function will check if the getX and the getY functions are working well.
    //will check that the x and y values that we get from a specific pixels are the one
    //that we expect.
    @Test
    public void getXGetYTest(){
        Pixel2D a = new Index2D(5, 3);
        int x =a.getX();
        int y = a.getY();
        assertEquals(5,x);
        assertEquals(3,y);
    }
}
