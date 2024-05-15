//my id:322530080
package Ex3;

public class Index2D implements Pixel2D{
    private int _x, _y;
    public Index2D() {this(0,0);}
    public Index2D(int x, int y) {_x=x;_y=y;}
    public Index2D(Pixel2D t) {this(t.getX(), t.getY());}
    @Override
    public int getX() {
        return _x;
    }
    @Override
    public int getY() {
        return _y;
    }
    /* This method computes the 2D (Euclidean) distance between this pixel and p2 pixel, i.e.,
     (Math.sqrt(dx*dx+dy*dy))
    method: will get the x and y values of the pixel that is given and the pixel that we
    want to calculate distance with, will subtract their values(singed as delta x(dx) and delta y(dy)),
    and will calculate the distance with a sqr of dx and dy.(based on pythagorean theorem calculation)
     */
    public double distance2D(Pixel2D t) {
        double ans = 0;
        double dx = this.getX() - t.getX();
        double dy = this.getY() - t.getY();
        ans =Math.sqrt(dx*dx+dy*dy);
        return ans;
    }
    @Override
    public String toString() {
        return getX()+","+getY();
    }
    /*this function will check if 2 pixels are equals.
    method: will check that the given object is a pixel by "instanceof" method,
    if so and the pixel is not null will return true iff the distance of the given pixel
    with the pixel that we want to check is 0.
     */
    @Override
    public boolean equals(Object t) {
        boolean ans = false;
    if(t!=null && t instanceof Pixel2D){
        Pixel2D p = (Pixel2D) t;
        ans = this.distance2D(p)==0;
    }
        return ans;
    }
}
