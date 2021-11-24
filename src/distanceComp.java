
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Werner
 */
public class distanceComp implements Comparator<GameObject> {
    
    private Vector origin;

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }
    
    
    @Override
    public int compare(GameObject o1, GameObject o2) {

        //System.out.println("lol");
        int compare = 0;
        double distance = origin.distanceTo(o1.getPosition());
        double otherDistance = origin.distanceTo(o2.getPosition());

        //double distance = o1.getPosition().magnitude();
        //double otherDistance = o2.getPosition().magnitude();
        
        if (distance > otherDistance) {
            compare = -1;
        } else if (distance < otherDistance) {
            compare = 1;
        }

        return compare;
    }
}
