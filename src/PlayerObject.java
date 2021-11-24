
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Collection;

public class PlayerObject extends GameObject implements IViewPort {

    private static final Color DEFAULT_COLOR = StdDraw.WHITE;
    private static final Color DEFAULT_FACING_COLOR = StdDraw.BLACK;
    private static final double DEFAULT_FOV = Math.PI / 2; // field of view of player's viewport
    private static final double FOV_INCREMENT = Math.PI / 48; // rotation speed
    private Camera cam;
    private Vector facing = new Vector(new double[]{0, 1});

    public PlayerObject(double mass, double radius, Color colour, Vector velocity, Vector position) {
        super(mass, radius, colour, velocity, position);
        cam = new Camera(this, this.DEFAULT_FOV);
    }

    @Override
    public void draw() {

        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.text(-StellarCrush.scale + 6e9, StellarCrush.scale - 3e9, "Score: " + this.getScore());
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(-StellarCrush.scale + 6e9, StellarCrush.scale - 6e9, "Lives: " + this.getLives());


        StdDraw.setPenColor(DEFAULT_COLOR);
        StdDraw.filledCircle(this.getPosition().cartesian(0), this.getPosition().cartesian(1), this.getRadius());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(this.getPosition().cartesian(0), this.getPosition().cartesian(1), this.getRadius());

        Vector facingLine = this.getPosition().plus(facing.times(this.getRadius()));
        Vector xAx = this.getPosition().plus((VectorUtil.rotate(facing, (DEFAULT_FOV / 2))).times(10e10));
        Vector yAx = this.getPosition().plus((VectorUtil.rotate(facing, -(DEFAULT_FOV / 2))).times(10e10));

        StdDraw.line(this.getPosition().cartesian(0), this.getPosition().cartesian(1), facingLine.cartesian(0), facingLine.cartesian(1));
    }

    public void drawFPview(Collection<GameObject> objects) {
        cam.render(objects);
    }

    void processCommand(int delay) {
        // Process keys applying to the player    
        // Retrieve 
        if (cam != null) {
            // No commands if no draw canvas to retrieve them from!
            Draw dr = cam.getDraw();
            if (dr != null) {
                // Example code
                if (dr.isKeyPressed(KeyEvent.VK_UP)) {

                    Vector move = facing.direction().times(3.5e2);
                    this.setVelocity(this.getVelocity().plus(move));      //move in facing direction
                }
                if (dr.isKeyPressed(KeyEvent.VK_DOWN)) {

                    Vector move = facing.direction().times(3.5e2);
                    this.setVelocity(this.getVelocity().minus(move));
                }
                if (dr.isKeyPressed(KeyEvent.VK_LEFT)) {

                    facing = VectorUtil.rotate(facing, -FOV_INCREMENT); //rotate left

                }
                if (dr.isKeyPressed(KeyEvent.VK_RIGHT)) {

                    facing = VectorUtil.rotate(facing, FOV_INCREMENT);  //rotate right

                }
                if (dr.isKeyPressed(KeyEvent.VK_M)) {
                    System.exit(0);
                }
            }
        }
    }

    public Vector getFacingVector() {
        return facing;
    }

    public double highlightLevel() {
        return this.getRadius();
    }

    public Vector getLocation() {

        Vector camPos = new Vector(new double[]{0, 0});

        camPos = this.getPosition().plus(this.getFacingVector().times(this.getRadius()));

        return camPos;
    }
}
