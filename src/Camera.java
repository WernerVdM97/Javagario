
import java.util.Collection;
import java.util.TreeSet;

public class Camera {
    // Virtual camera - uses a plane one unit away from the focal point
    // For ease of use, this simply locates where the centre of the object is, and renders it if that is in the field of view.
    // Further, the correct rendering is approximated by a circle centred on the projected centre point.

    private final IViewPort holder; // Object from whose perspective the first-person view is drawn
    private final Draw dr; // Canvas on which to draw
    private double FOV; // field of view of camera

    public Draw getDraw() {
        return dr;
    }

    Camera(IViewPort holder, double FOV) {
        this.holder = holder;  // Constructs a camera with field of view FOV, held by holder, and rendered on canvas dr.
        this.FOV = FOV;
        dr = new Draw();
        dr.setCanvasSize(800, 800);
        dr.setLocationOnScreen(850, 1);
        dr.setXscale(-FOV / 2, FOV / 2);
        dr.setYscale(-FOV / 2, FOV / 2);
    }

    public void render(Collection<GameObject> universe) {
        // Renders the collection from the camera perspective
        distanceComp sorter = new distanceComp();
        sorter.setOrigin(holder.getLocation());

        TreeSet<GameObject> seenObjects = new TreeSet<GameObject>(sorter);
        Collection<GameObject> objects = universe;

        Vector xAxes = VectorUtil.rotate(holder.getFacingVector(), FOV / 2);
        double xAngle = VectorUtil.direction(xAxes);                            //angle of feild of view from the xAxes

        Vector tempPos;
        double tempX;
        double tempY;


        for (GameObject curObj : objects) {              //filter seen objects

            if (curObj != holder) {

                tempX = curObj.getPosition().cartesian(0) - holder.getLocation().cartesian(0);
                tempY = curObj.getPosition().cartesian(1) - holder.getLocation().cartesian(1);

                tempPos = new Vector(new double[]{tempX, tempY});

                tempPos = VectorUtil.rotate(tempPos, xAngle);

                if (tempPos.cartesian(0) > 0 && tempPos.cartesian(1) > 0) {

                    seenObjects.add(curObj);

                }
            }
        }

        dr.clear(StdDraw.GRAY);


        for (GameObject curObj : seenObjects) {     //draw
            if (curObj != holder) {

                tempX = curObj.getPosition().cartesian(0) - holder.getLocation().cartesian(0);
                tempY = curObj.getPosition().cartesian(1) - holder.getLocation().cartesian(1);
                tempPos = new Vector(new double[]{tempX, tempY});
                tempPos = VectorUtil.rotate(tempPos, (xAngle + FOV / 2));

                double distance = holder.getLocation().distanceTo(curObj.getPosition());

                double xCo = VectorUtil.direction(tempPos);
                xCo = Math.toDegrees(xCo);

                if (xCo > 45) {

                    xCo = 360 - xCo;
                    xCo = -1 * xCo;
                }

                double radi;
                distance = distance / 1e10;
                radi = (curObj.getSize() * FOV / 10) / distance * 2;

                xCo = Math.toRadians(xCo);

                dr.setPenColor(curObj.getColour());
                dr.filledCircle(-xCo, 0, radi);

                if (curObj.getRadius() * 1.2 < holder.highlightLevel()) {
                    dr.setPenColor(StdDraw.ORANGE);
                } else {
                    dr.setPenColor(StdDraw.BLACK);
                }
                dr.circle(-xCo, 0, radi);
            }
        }

        dr.show(5);

    }
}
