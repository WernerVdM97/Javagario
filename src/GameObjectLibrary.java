
import java.util.Collection;
import java.util.HashSet;

public class GameObjectLibrary {
// Class for defining various game objects, and putting them together to create content
// for the game world.  Default assumption is objects face in the direction of their velocity, and are spherical.

    // UNIVERSE CONSTANTS - TUNED BY HAND FOR RANDOM GENERATION
    private static final double PLAYER_MASS = 5E25;
    //collections
    private Collection<GameObject> objects = new HashSet<GameObject>();

    public Collection<GameObject> getObjects() {
        return objects;
    }

    public void createUniverse(int j) {
        boolean SpaceOpen = true;

//        GameObject sun = new GameObject(1.5, 3e28, 6e9, StdDraw.YELLOW, new Vector(new double[]{0, 0}), new Vector(new double[]{0, 0}));
//        objects.add(sun);
//        sun.draw();

        PlayerObject Player1 = new PlayerObject(PLAYER_MASS, 1.5e9, StdDraw.WHITE, new Vector(new double[]{0, 0}), new Vector(new double[]{1.5e10, 1.5e10}));
        objects.add(Player1);
        Player1.draw();

        GameObject sun = new GameObject(1, 1e25, 2.5e9, StdDraw.YELLOW, new Vector(new double[]{0, 0}), new Vector(new double[]{-1.5e10, -1.5e10}));
        objects.add(sun);
        sun.draw();

//        for (int i = 0; i < j; i++) {
//
//            double xCo = Math.random() * 7.5e10 - 3.5e10;
//            double yCo = Math.random() * 7.5e10 - 3.5e10;
//            Vector position = new Vector(new double[]{xCo, yCo});
//
//            double size = Math.random();
//
//            Vector velocity = null;
//            //////////////////////////////////////////////////////////////////////////////////
//            //calculate the speed to orbit sun with
//            double speed = Math.sqrt((3e28 * 6.67e-11) / position.magnitude());
//            Vector unitV = position.direction();
//            velocity = unitV.times(speed);
//            velocity = new Vector(new double[]{-velocity.cartesian(1), velocity.cartesian(0)});
//            //velocity = new Vector(new double[]{0,0});
//            //////////////////////////////////////////////////////////////////////////////////
//
//            GameObject newObj = new GameObject(size, velocity, position);
//
//            for (GameObject CrntObj : objects) {
//
//
//                double radi = CrntObj.getRadius() + newObj.getRadius();
//                double distance = CrntObj.getPosition().distanceTo(newObj.getPosition());
//
//                if (distance < radi * 1.1) {
//                    SpaceOpen = false;
//                    i--;
//                }
//
//            }
//
//            if (newObj.getPosition().magnitude() < 2.5e10) {
//                SpaceOpen = false;
//                i--;
//            }
//
//            if (SpaceOpen) {
//                objects.add(newObj);
//                newObj.draw();
//            }
//            SpaceOpen = true;
//        }

    }
}
