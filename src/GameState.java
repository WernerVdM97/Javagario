
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameState {

    // Class representing the game state and implementing main game loop update step.
    private Collection<GameObject> objects = null;
    private Collection<GameObject> removeThese = new HashSet<>();
    private Collection<GameObject> addThese = new HashSet<>();

    public void update(int delay, Collection<GameObject> universe) {

        objects = universe;

        StdDraw.clear(StdDraw.GRAY);

        calculateForces();

        for (GameObject CrntObj : objects) {

            for (GameObject ThtObj : objects) {

                if (CrntObj != ThtObj) {

                    if (CrntObj.interact(ThtObj)) {
                        removeThese.add(ThtObj);
                    }
                    if (ThtObj.isSplit()) {
                        addThese.addAll(ThtObj.split());
                    }

                }
            }
        }
        objects.addAll(addThese);

        objects.removeAll(removeThese);

        for (GameObject CrntObj : objects) {


            if (CrntObj instanceof PlayerObject) {
                PlayerObject temp = (PlayerObject) CrntObj;
                temp.processCommand(delay);
                temp.drawFPview(objects);
            }
            CrntObj.draw();

        }


        StdDraw.show(delay);
    }

    //private Map<GameObject, Vector> calculateForces() {
    private void calculateForces() {

        Map<GameObject, Vector> Planets = new HashMap<GameObject, Vector>();



        for (GameObject CrntObj : objects) {
            Vector resVect = new Vector(new double[]{0, 0});

            for (GameObject thatObj : objects) {

                if (CrntObj != thatObj) {
                    resVect = resVect.plus(thatObj.forceFrom(CrntObj));             // Calculate the force on each object for the next update step.

                }
            }

            Planets.put(CrntObj, resVect);

        }

        for (Map.Entry<GameObject, Vector> curObj : Planets.entrySet()) {

            curObj.getKey().move(curObj.getValue());
        }

    }
}
