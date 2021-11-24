
import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;

public class GameObject {
    // Default implementation of a game object

    private double size;
    private double mass;
    private double radius;
    private Color colour;
    private Vector velocity;
    private Vector position;
    private boolean split;
    private int score = 0;
    private int lives = 5;

    public double getMass() {
        return mass;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return this.score;
    }

    public int getLives() {
        return this.lives;
    }

    public double getRadius() {
        return radius;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getPosition() {
        return position;
    }

    public double getSize() {
        return size;
    }

    public Color getColour() {
        return colour;
    }

    public GameObject(double size, Vector velocity, Vector position) {
        this.size = size;
        this.colour = StdDraw.BOOK_RED;
        this.velocity = velocity;
        this.position = position;
        this.radius = calculateRadi();
        this.mass = calculateMass();
        this.split = false;
    }

    public GameObject(double size, double mass, double radius, Color colour, Vector velocity, Vector position) {
        this.size = size;
        this.mass = mass;
        this.radius = radius;
        this.colour = colour;
        this.velocity = velocity;
        this.position = position;
        this.split = false;

    }

    public GameObject(double mass, double radius, Color colour, Vector velocity, Vector position) {
        this.mass = mass;
        this.radius = radius;
        this.colour = colour;
        this.velocity = velocity;
        this.position = position;
        this.split = false;

    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public GameObject() {
    }

    /*public GameObject(double mass, double radius, Color colour, Vector velocity, Vector position) {
     this.mass = mass;
     this.radius = radius;
     this.colour = colour;
     this.velocity = velocity;
     this.position = position;
     }*/
    private double calculateRadi() {

        double radi = size * (5e10) / 25 + (5e10) / 80;
        return radi;
    }

    public void draw() {

        StdDraw.setPenColor(colour);
        StdDraw.filledCircle(position.cartesian(0), position.cartesian(1), radius);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(position.cartesian(0), position.cartesian(1), radius);

    }

    private double calculateMass() {
        double massa = size * 9.9e25 + 1e24;
        return massa;

    }

    public void move(Vector resVect) {

        Vector acceleration = resVect.times(1 / mass);
        velocity = velocity.plus(acceleration.times(5000));
        position = position.plus(velocity.times(5000));

    }

    public Vector forceFrom(GameObject other) {

        Vector curVect;

        Vector resV = this.position.minus(other.position);
        double distance = resV.magnitude();
        double Force = (StellarCrush.G * this.mass * other.mass) / (distance * distance);
        curVect = resV.direction().times(Force);

        return curVect;
    }

    public void bounce(GameObject other) {
        //double Vel1 = 0, Vel2 = 0;
        //double yVel1 = 0, yVel2 = 0;
//        double xVel,yVel;
//
//        Vector distance = this.position.minus(other.position);
//        Vector unitv = distance.direction();
//
//        xVel = (this.mass * this.velocity.cartesian(0) + other.mass * other.velocity.cartesian(0)) / other.mass;
//        yVel = (this.mass * this.velocity.cartesian(1) + other.mass * other.velocity.cartesian(1)) / other.mass;
//
//        this.velocity = new Vector(new double[]{xVel, yVel});
//        xVel1 = (this.mass * this.velocity.cartesian(0) + other.mass * other.velocity.cartesian(0)) / (this.mass + other.mass * 0.001 * (this.velocity.cartesian(0) - other.velocity.cartesian(0)));
//        yVel1 = (this.mass * this.velocity.cartesian(1) + other.mass * other.velocity.cartesian(1)) / (this.mass + other.mass * 0.001 * (this.velocity.cartesian(1) - other.velocity.cartesian(1)));
//
//        xVel2 = (other.mass * other.velocity.cartesian(0) + this.mass * this.velocity.cartesian(0)) / (other.mass + this.mass * 0.001 * (other.velocity.cartesian(0) - this.velocity.cartesian(0)));
//        yVel2 = (other.mass * other.velocity.cartesian(1) + this.mass * this.velocity.cartesian(1)) / (other.mass + this.mass * 0.001 * (other.velocity.cartesian(1) - this.velocity.cartesian(1)));
        //this.velocity = new Vector(new double[]{xVel1, yVel1});
        //other.velocity = new Vector(new double[] {xVel2, yVel2});

        double newVel;
        double normV = 0;
        double newVel2;
        double normV2 = 0;

        Vector unitV1 = this.getPosition().minus(other.getPosition()).direction();
        Vector unitV2 = other.getPosition().minus(this.getPosition()).direction();

        double oldVel1 = this.velocity.dot(unitV1);
        double oldVel2 = other.velocity.dot(unitV2);

        newVel = (this.mass * oldVel1 + other.mass * oldVel2 + other.mass * StellarCrush.softE * oldVel1 - oldVel2 * StellarCrush.softE * oldVel2) / (this.mass + other.mass);
        newVel2 = (other.mass * oldVel2 + this.mass * oldVel1 + this.mass * StellarCrush.softE * oldVel2 - oldVel1 * StellarCrush.softE * oldVel1) / (other.mass + this.mass);

        this.setVelocity(this.velocity.direction().times(newVel));
        other.setVelocity(other.velocity.direction().times(newVel2));

    }

    public boolean interact(GameObject ThtObj) {

        boolean remove = false;
        Vector unitV = this.position.minus(ThtObj.position);
        double distance = unitV.magnitude();

        double radi = this.radius + ThtObj.radius;

        if (distance <= radi) {                         // check to see if the two bodies collide

            if (this.radius > ThtObj.radius * 1.1) {

                if (this instanceof PlayerObject) {
                    absorb(ThtObj);
                    remove = true;

                }

            } else {

                if (this instanceof PlayerObject) {

                    if (this.velocity.magnitude() > 50000 && ThtObj.radius > 2e9) {
                        bounce(ThtObj);
                        ThtObj.setSplit(true);
                        remove = true;
                    }
                }

                bounce(ThtObj);

            }

            if (distance < (radi * 0.95)) {

                Vector deltaV = ThtObj.position.minus(this.position);
                ThtObj.setPosition(this.position.plus(deltaV.times(1.1)));

            }
        }

        return remove;
    }

    public Collection<GameObject> split() {

        Collection<GameObject> newObjects = new HashSet<>();

        Vector speed = new Vector(new double[]{0, 0});
        Vector pos1;
        Vector pos2;

        pos1 = this.getPosition().plus(this.position.direction().times(this.radius / 2));
        pos2 = this.getPosition().minus(this.position.direction().times(this.radius / 2));

        GameObject half1 = new GameObject(this.size / 2, this.mass / 2, this.radius / 2.1, this.colour, speed, pos1);
        GameObject half2 = new GameObject(this.size / 2, this.mass / 2, this.radius / 2.1, this.colour, speed, pos2);

        newObjects.add(half1);
        newObjects.add(half2);


        return newObjects;

    }

    private void absorb(GameObject ThtObj) {

        score++;
        this.setMass(this.getMass() + ThtObj.getMass());
        this.setRadius(this.getRadius() + ThtObj.getRadius() * 0.1);


    }
}
