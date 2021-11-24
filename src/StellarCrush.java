
import java.awt.Font;
import java.awt.event.KeyEvent;

/* Acknowledgements/notes:
 - Some of this code based on code for Rubrica by Steve Kroon
 - Original inspiration idea for this project was IntelliVision's AstroSmash, hence the name
 */

/* Ideas for extensions/improvements:
 PRESENTATION:
 -theme your game
 -hall of fame/high score screen
 -modifiable field of view, rear-view mirror, enhance first-person display by showing extra information on screen
 -mouse control
 -autoscaling universe to keep all universe objects on screen (or making the edge of the universe repel objects)
 -better rendering in camera (better handling of objects on edges, and more accurate location rendering
 -improved gameplay graphics, including pictures/sprites/textures for game objects
 -add sounds for for various game events/music: Warning: adding both sounds and music will likely lead to major
 headaches and frustration, due to the way the StdAudio library works.  If you go down this route, you choose
 to walk the road alone...
 -full 3D graphics with 3D universe (no libraries)

 MECHANICS/GAMEPLAY CHANGES:
 -avoid certain other game objects rather than/in addition to riding into them
 -more interactions - missiles, auras, bombs, explosions, shields, etc.
 -more realistic physics for thrusters, inertia, friction, momentum, relativity?
 -multiple levels/lives
 -energy and hit points/health for game objects and players
 -multi-player mode (competitive/collaborative)
 -checking for impacts continuously during moves, rather than at end of each time step
 -Optimize your code to be able to deal with more objects (e.g. with a quad-tree) - document the improvement you get
 --QuadTree implementation with some of what you may want at : http://algs4.cs.princeton.edu/92search/QuadTree.java.html
 --https://github.com/phishman3579/java-algorithms-implementation/blob/master/src/com/jwetherell/algorithms/data_structures/QuadTree.java may also be useful - look at the Point Region Quadtree
 */
public class StellarCrush {
    // Main game class

    // CONSTANTS TUNED FOR GAMEPLAY EXPERIENCE
    static final int GAME_DELAY_TIME = 5000; // in-game time units between frame updates
    static final int TIME_PER_MS = 1000; // how long in-game time corresponds to a real-time millisecond
    static final double G = 6.67e-11; // gravitational constant
    static final double softE = 0.001; // softening factor to avoid division by zero calculating force for co-located objects
    static double scale = 5e10; // plotted universe size

    public static void main(String[] args) {

        //////////////////////////////////////////////////
        //title screen
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.clear(StdDraw.BLACK);
        boolean ready = false;
        Font currentFont = StdDraw.getFont();
        float size = 20;
        currentFont = currentFont.deriveFont(size);
        StdDraw.setFont(currentFont);

        int highscore = 0; 

        while (!ready) {

            StdDraw.text(0.5, 0.8, "Global Domination");
            StdDraw.text(0.5, 0.6, "Press spacebar to start");

            size = 20;
            currentFont = currentFont.deriveFont(size);
            StdDraw.setFont(currentFont);

            StdDraw.text(0.5, 0.5, "Use the arrow keys to move,");
            StdDraw.text(0.5, 0.4, "Quit (m)");
            StdDraw.text(0.5, 0.2, "Current Highscore: " + highscore);

            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                ready = true;
            }

        }

        ///////////////////////////////////////////////////
        //start game
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale();
        StdDraw.setXscale(-scale, scale);
        StdDraw.setYscale(-scale, scale);

        GameState newGame = new GameState();
        GameObjectLibrary myLibrary = new GameObjectLibrary();
        myLibrary.createUniverse(50);

        ////////////////////////////////////////////////////
        //start loop
        while (true) {

            newGame.update(GAME_DELAY_TIME / TIME_PER_MS, myLibrary.getObjects());

        }
        //System.out.println("game over");
    }
}
