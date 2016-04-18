package ships.nathan;

import asteroidsfw.Vector2d;
import asteroidsfw.ai.AsteroidPerception;
import asteroidsfw.ai.Perceptions;
import asteroidsfw.ai.ShipControl;
import asteroidsfw.ai.ShipMind;

import java.util.EnumMap;
import java.util.HashSet;

public class Shipperino implements ShipMind
{
    private static final double THRESHOLD_TIME_BEFORE_DECELERATE = 0.5d;
    private static final double THRESHOLD_POSITION = 2.0d;

    private interface State
    {
        void update(Shipperino ship, Perceptions perceptions, double delta);
    }

    private enum ShipState implements State
    {
        MOVING_TOWARDS
                {
                    @Override
                    public void update(Shipperino ship, Perceptions perceptions, double delta)
                    {
                        ship.control.thrustForward(true);
                        ship.moveTo();
                    }
                },
        STOPPING
                {
                    @Override
                    public void update(Shipperino ship, Perceptions perceptions, double delta)
                    {
                        ship.stop();
                    }
                },
        EVADING
                {
                    @Override
                    public void update(Shipperino ship, Perceptions perceptions, double delta)
                    {
                        ship.evade();
                    }
                },
        STATE_DEFAULT
                {
                    @Override
                    public void update(Shipperino ship, Perceptions perceptions, double delta)
                    {
                        ship.defaultBehavior(perceptions);
                    }
                },
        DEAD
                {
                    @Override
                    public void update(Shipperino ship, Perceptions perceptions, double delta)
                    {
                        if (!ship.amDead())
                        {
                            ship.changeState(ShipState.MOVING_TOWARDS);
                        }
                    }
                };
    }

    private void setInitOrientation()
    {
        orientationEnteringEvading = orientation;
    }

    private Vector2d orientationEnteringEvading = null;

    private static final double FRAME_DURATION = 1.0d / 30.0d;

    private static final double ACCEL_VAL = 10.0d / 3.0d;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private static final double MAX_ANGLE_ROTATION = 4.0d;
    private static final double MAX_THRUST = 100d;

    private static final int RSH_CONST = 7;

    private static final int HORIZONTAL_QUADS = (SCREEN_WIDTH >> RSH_CONST) + 1;
    private static final int VERTICAL_QUADS = (SCREEN_HEIGHT >> RSH_CONST) + 1;

    // Bullets are shot out at ship speed * bullet speed modifier
    private static final double BULLET_SPEED_MODIFIER = 150.0d;
    private static final double THRESHOLD_TIME_INTERSECTION = 0.75d;

    private final int[][] quadCounts = new int[VERTICAL_QUADS][HORIZONTAL_QUADS];
    private final int[][] zeroArray = new int[VERTICAL_QUADS][HORIZONTAL_QUADS];

    private final Quad[][] quadMap = new Quad[VERTICAL_QUADS][HORIZONTAL_QUADS];

    private static final Vector2d INIT_MOVE_TO = new Vector2d(600d, 300d);
    private static final Vector2d DEFAULT_AIM = new Vector2d(0d, 0d);

    private ShipControl control;
    private ShipState state = ShipState.MOVING_TOWARDS;

    private Vector2d position;
    private Vector2d velocity;
    private Vector2d orientation;

    private Vector2d target = INIT_MOVE_TO;

    private Quad myQuad;
    private ImminentCollision imminentCollision = new ImminentCollision();

    public Shipperino()
    {
        for (int row = 0; row < quadMap.length; row++)
        {
            for (int col = 0; col < quadMap[0].length; col++)
            {
                quadMap[row][col] = new Quad(row, col);
            }
        }

        for (Quad[] row : quadMap)
        {
            for (Quad quad : row)
            {
                int colWest = getQuadCol(quad.col, -1);
                int colEast = getQuadCol(quad.col, 1);

                int rowNorth = getQuadRow(quad.row, -1);
                int rowSouth = getQuadRow(quad.row, 1);

                quad.addNeighbor(Direction.NORTH, quadMap[rowNorth][quad.col]);
                quad.addNeighbor(Direction.NORTH_EAST, quadMap[rowNorth][colEast]);
                quad.addNeighbor(Direction.EAST, quadMap[quad.row][colEast]);
                quad.addNeighbor(Direction.SOUTH_EAST, quadMap[rowSouth][colEast]);
                quad.addNeighbor(Direction.SOUTH, quadMap[rowSouth][quad.col]);
                quad.addNeighbor(Direction.SOUTH_WEST, quadMap[rowSouth][colWest]);
                quad.addNeighbor(Direction.WEST, quadMap[quad.row][colWest]);
                quad.addNeighbor(Direction.NORTH_WEST, quadMap[rowNorth][colWest]);
            }
        }
    }

    @Override
    public void init(ShipControl control)
    {
        this.control = control;
        control.thrustForward(true);
        control.shooting(true);
    }

    private void defaultBehavior(Perceptions perceptions)
    {
        AsteroidPerception[] asteroids = perceptions.asteroids();

        AsteroidPerception asteroid = imminentCollision.asteroid;

        if (asteroid != null)
        {
            orientTowards(asteroid.pos());
        }
        else
        {
            orientTowards(DEFAULT_AIM);
        }
    }

    private void evade()
    {
        if (imminentCollision.willCollide)
        {
            double dot = orientationEnteringEvading.dot(orientation);

            System.err.println("dot: " + dot);
            if (dot < 0.9d)
            {
                control.thrustBackward(true);
            }
            else
            {
                control.rotateLeft(true);
            }
        }
        else
        {
            changeState(ShipState.STOPPING);
        }
    }

    @Override
    public void think(Perceptions perceptions, double delta)
    {
        boolean amDead = amDead();

        if (!amDead)
        {
            position = control.pos();
            velocity = control.v();
            orientation = control.direction();
            imminentCollision.reset();
            myQuad = getQuad(control.pos());
            resetQuads();

            checkForCollisions(perceptions.asteroids());

            if (imminentCollision.willCollide && !state.equals(ShipState.EVADING))
            {
                setInitOrientation();
                changeState(ShipState.EVADING);
            }

            state.update(this, perceptions, delta);
        }
        else
        {
            if (!state.equals(ShipState.DEAD))
            {
                changeState(ShipState.DEAD);
            }
        }
    }

    private boolean amDead()
    {
        return control.respawnIn() > 0d;
    }

    private static double getTimeThreshold(int radius)
    {
        switch (radius)
        {
            case 5:
                return 0.25d;
            case 10:
                return 0.5d;
            case 20:
                return 0.75d;
            default:
                return 1.0d;
        }
    }

    private void orientTowards(Vector2d destination)
    {
        Vector2d dist = destination.$minus(position);

        double cross = orientation.cross(dist);

        if (cross < 0)
        {
            // rotate left
            control.rotateLeft(true);
            control.rotateRight(false);
        }
        else if (cross > 0)
        {
            // rotate right
            control.rotateRight(true);
            control.rotateLeft(false);
        }
    }

    private void moveTo()
    {
        moveTo(target);
    }

    private void moveTo(Vector2d destination)
    {
        double deltaX = Math.abs(destination.x() - position.x());
        double deltaY = Math.abs(destination.y() - position.y());

        if (deltaX < THRESHOLD_POSITION && deltaY < THRESHOLD_POSITION)
        {
            changeState(ShipState.STOPPING);
            control.thrustBackward(false);
        }
        else
        {
            double timeX = getTimeTilIntersection(destination.x(), position.x(), 0d, velocity.x());
            double timeY = getTimeTilIntersection(destination.y(), position.y(), 0d, velocity.y());

            if (timeX < 0 || timeY < 0)
            {
                orientTowards(destination);
            }
            else
            {
                if (timeX < THRESHOLD_TIME_BEFORE_DECELERATE && timeY < THRESHOLD_TIME_BEFORE_DECELERATE)
                {
                    changeState(ShipState.STOPPING);
                }
            }
        }
    }

    private void stop()
    {
        boolean checkX = Math.abs(velocity.x()) > 1.0d;
        boolean checkY = Math.abs(velocity.y()) > 1.0d;

        if (checkX && checkY)
        {
            boolean thrustBackwards = velocity.x() > 0d && velocity.y() > 0d;

            control.thrustBackward(thrustBackwards);
            control.thrustForward(!thrustBackwards);
        }
        else if (checkX)
        {
            boolean thrustBackwards = velocity.x() > 0d;

            control.thrustBackward(thrustBackwards);
            control.thrustForward(!thrustBackwards);
        }
        else if (checkY)
        {
            boolean thrustBackwards = velocity.y() > 0d;

            control.thrustBackward(thrustBackwards);
            control.thrustForward(!thrustBackwards);
        }
        else
        {
            changeState(ShipState.STATE_DEFAULT);
            control.thrustBackward(false);
            control.thrustForward(false);
        }
    }

    private void changeState(ShipState newState)
    {
        System.err.println("changing state from " + state.name() + " to " + newState.name());

        control.thrustBackward(false);
        control.thrustForward(false);
        control.rotateLeft(false);
        control.rotateRight(false);

        state = newState;
    }

    private class ImminentCollision
    {
        private AsteroidPerception asteroid = null;
        private double distance = Double.MAX_VALUE;
        private double tx = Double.MAX_VALUE;
        private double ty = Double.MAX_VALUE;
        private double dt = Double.MAX_VALUE;
        private double threshold = Double.MAX_VALUE;
        private boolean willCollide = false;

        private void update(AsteroidPerception asteroid, double tx, double ty, double dt, double threshold, boolean willCollide)
        {
            this.asteroid = asteroid;
            this.tx = tx;
            this.ty = ty;
            this.dt = dt;
            this.threshold = threshold;
            this.willCollide = willCollide;

            if (willCollide)
            {
                System.err.println("ImminentCollision::update - willCollide: " + willCollide);
            }
        }

        private void reset()
        {
            asteroid = null;
            distance = Double.MAX_VALUE;
            tx = Double.MAX_VALUE;
            ty = Double.MAX_VALUE;
            dt = Double.MAX_VALUE;
            threshold = Double.MAX_VALUE;
            willCollide = false;
        }

        @Override
        public String toString()
        {
            return asteroid.pos() + ", t: " + tx + "," + ty + "; dt: " + dt + ", threshold: " + threshold + ", willCollide: " + willCollide;
        }

        @Override
        public boolean equals(Object other)
        {
            if (!(other instanceof ImminentCollision))
            {
                return false;
            }

            ImminentCollision col = (ImminentCollision) other;

            return this == col || asteroid.equals(col.asteroid);
        }
    }

    // Check for collisions each frame or do the method where I try to run from the closest asteroids each frame (assume we're all particles with the same charge)
    // If checking for collisions, how to account for
    private void checkForCollisions(AsteroidPerception[] asteroids)
    {
        boolean willIntersect = false;

        for (AsteroidPerception asteroid : asteroids)
        {
            Quad asteroidQuad = getQuad(asteroid.pos());
            quadCounts[asteroidQuad.row][asteroidQuad.col]++;

            if (myQuad.isNeighbor(asteroidQuad))
            {
                double dist = getSquaredDistToAsteroid(asteroid);

                if (dist < SQUARED_DIST_THRESHOLD)
                {
                    Vector2d posAst = asteroid.pos();
                    Vector2d velAst = asteroid.v();

                    double tXIntersect = getTimeTilIntersection(posAst.x(), position.x(), velAst.x(), velocity.x());
                    double tYIntersect = getTimeTilIntersection(posAst.y(), position.y(), velAst.y(), velocity.y());
                    double threshold = getTimeThreshold(asteroid.radius());
                    double dt = Math.abs(tXIntersect - tYIntersect);

                    willIntersect = tXIntersect > 0
                            && tYIntersect > 0
                            && dt < threshold;

                    if (willIntersect || asteroid.v().normalize().dot(velocity.normalize()) < -0.98d)
                    {
                        if (!imminentCollision.willCollide || dt < imminentCollision.dt)
                        {
                            imminentCollision.update(asteroid, tXIntersect, tYIntersect, dt, threshold, willIntersect);
                        }
                    }
                }

                if (!imminentCollision.willCollide && (imminentCollision.asteroid == null || imminentCollision.distance > dist))
                {
                    imminentCollision.asteroid = asteroid;
                }
            }
        }

        if (!willIntersect)
        {
            imminentCollision.willCollide = false;
        }
    }

    private double getTimeTilIntersection(double posAst, double posShip, double velAst, double velShip)
    {
        if (posAst == posShip)
        {
            return 0d;
        }

        double tIntersection = Double.MAX_VALUE;

        if (velAst != velShip)
        {
            tIntersection = (posAst - posShip) / (velShip - velAst);
        }

        return tIntersection;
    }

    private static final double SQUARED_DIST_THRESHOLD = 15000d;

    private boolean isAsteroidCloseToShip(AsteroidPerception asteroid)
    {
        double dist = getSquaredDistToAsteroid(asteroid);
        return dist < SQUARED_DIST_THRESHOLD;
    }

    private int getQuadRow(int row, int delta)
    {
        return getQuadIndex(row, delta, VERTICAL_QUADS);
    }

    private int getQuadCol(int col, int delta)
    {
        return getQuadIndex(col, delta, HORIZONTAL_QUADS);
    }

    private int getQuadIndex(int index, int delta, int max)
    {
        int i = index + delta;

        if (i < 0)
        {
            i = max - 1;
        }

        if (i == max)
        {
            i = 0;
        }

        return i;
    }

    private double getSquaredDistToAsteroid(AsteroidPerception asteroid)
    {
        Vector2d pos = asteroid.pos();
        double dx = pos.x() - control.pos().x();
        double dy = pos.y() - control.pos().y();

        return (dx * dx) + (dy * dy);
    }

    private void updateQuads(AsteroidPerception[] asteroids)
    {
        resetQuads();

        for (AsteroidPerception asteroid : asteroids)
        {
            Quad quad = getQuad(asteroid.pos());
            quadCounts[quad.row][quad.col]++;
        }
    }

    private void resetQuads()
    {
        System.arraycopy(zeroArray, 0, quadCounts, 0, quadCounts.length);
    }

    private Quad getQuad(Vector2d position)
    {
        int row = (int) position.y() >> RSH_CONST;
        int col = (int) position.x() >> RSH_CONST;

        if (row < 0)
        {
            row = 0;
        }
        if (col < 0)
        {
            col = 0;
        }

        return quadMap[row][col];
    }

    private class Quad
    {
        private final int row;
        private final int col;

        private final EnumMap<Direction, Quad> neighborMap = new EnumMap<Direction, Quad>(Direction.class);
        private final HashSet<Quad> neighbors = new HashSet<Quad>();

        Quad(int row, int col)
        {
            this.row = row;
            this.col = col;
        }

        void addNeighbor(Direction dir, Quad neighbor)
        {
            neighborMap.put(dir, neighbor);
            neighbors.add(neighbor);
        }

        boolean isNeighbor(Quad quad)
        {
            return neighbors.contains(quad);
        }

        Quad getNeighbor(Direction dir)
        {
            return neighborMap.get(dir);
        }

        public String toString()
        {
            return "[" + row + "," + col + "]";
        }
    }

    private enum Direction
    {
        NORTH_WEST(-1, -1),
        NORTH(-1, 0),
        NORTH_EAST(-1, 1),
        EAST(0, 1),
        SOUTH_EAST(1, 1),
        SOUTH(1, 0),
        SOUTH_WEST(1, -1),
        WEST(0, -1);

        private final int rowDelta;
        private final int colDelta;

        Direction(int rowDelta, int colDelta)
        {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }
    }

    static public final float PI = 3.1415927f;

    static public float atan2(float y, float x)
    {
        if (x == 0f)
        {
            if (y > 0f)
            {
                return PI / 2;
            }
            if (y == 0f)
            {
                return 0f;
            }
            return -PI / 2;
        }
        final float atan, z = y / x;
        if (Math.abs(z) < 1f)
        {
            atan = z / (1f + 0.28f * z * z);
            if (x < 0f)
            {
                return atan + (y < 0f ? -PI : PI);
            }
            return atan;
        }
        atan = PI / 2 - z / (z * z + 0.28f);
        return y < 0f ? atan - PI : atan;
    }

    private boolean isAsteroidInNearbyQuad(AsteroidPerception asteroid)
    {
        Quad asteroidQuad = getQuad(asteroid.pos());
        return myQuad.isNeighbor(asteroidQuad);
    }
}
