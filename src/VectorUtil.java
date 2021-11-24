
public class VectorUtil {
    // Class containing additional utility functions for working with vectors.

    public static final Vector TWO_D_ZERO = new Vector(new double[]{0, 0});

    static Vector rotate(Vector v, double ang) {

        double xCo = v.cartesian(0);
        double yCo = v.cartesian(1);

        double xF = 0, yF = 0;

        xF = (xCo * Math.cos(-ang)) - (yCo * Math.sin(-ang));     //x′=xcosθ−ysinθ
        yF = (yCo * Math.cos(-ang)) + (xCo * Math.sin(-ang));    //y′=ycosθ+xsinθ

        return new Vector(new double[]{xF, yF});
        // Rotate v by ang radians clockwise - two dimensions only.
    }

    static double direction(Vector v) {

        double ang = Math.atan2(v.cartesian(1), v.cartesian(0));
        
        if (ang < 0) {
            ang += Math.PI * 2;
        }

        return ang;
        // Returns direction of v, but sets angle to Math.PI/2 when v is the zero vector
        // Used to avoid exception in Vector.java
    }
}
