import java.lang.Runnable;
import java.util.Collections;
import java.util.LinkedList;

public class Agent implements Runnable {
    public static final String MOVE_UP = "cima";
    public static final String MOVE_DOWN = "baixo";
    public static final String MOVE_LEFT = "esquerda";
    public static final String MOVE_RIGHT = "direita";

    protected Unit[][] environment;
    protected Unit location;
    protected boolean alive;

    public Agent(Unit[][] env) {
        this.alive = true;
        this.environment = env;
    }

    protected boolean move(String direction) {
        boolean done = false;

        switch (direction) {
            case MOVE_UP -> {
                if (location.X - 1 >= 0) {
                    environment[location.X][location.Y].removeAgent(this);
                    environment[location.X-1][location.Y].setAgent(this);
                    setLocation(environment[location.X-1][location.Y]);
                    done = true;
                }
                break;
            }
            case MOVE_DOWN -> {
                if (location.X + 1 < Main.DIMENSION) {
                    environment[location.X][location.Y].removeAgent(this);
                    environment[location.X+1][location.Y].setAgent(this);
                    setLocation(environment[location.X+1][location.Y]);
                    done = true;
                }
                break;
            }
            case MOVE_LEFT -> {
                if (location.Y - 1 >= 0) {
                    environment[location.X][location.Y].removeAgent(this);
                    environment[location.X][location.Y-1].setAgent(this);
                    setLocation(environment[location.X][location.Y-1]);
                    done = true;
                }
                break;
            }
            case MOVE_RIGHT -> {
                if (location.Y + 1 < Main.DIMENSION) {
                    environment[location.X][location.Y].removeAgent(this);
                    environment[location.X][location.Y+1].setAgent(this);
                    setLocation(environment[location.X][location.Y+1]);
                    done = true;
                }
                break;
            }
        }
        String s = (done) ? "O agente moveu-se para " : "O agente nao pode mover-se para ";
        System.out.println(s + direction);

        return done;
    }

    /* Random movement approach */
    protected String movementGenerator() {
        LinkedList<String> list = new LinkedList<>();

        list.add(MOVE_RIGHT);
        list.add(MOVE_LEFT);
        list.add(MOVE_DOWN);
        list.add(MOVE_UP);

        Collections.shuffle(list);
        return list.getFirst();
    }

    protected boolean isAlive() {
        return alive;
    }

    protected void look() {

    }

    public void setLocation(Unit location) {
        this.location = location;
    }

    public void setDead() {
        alive = false;
    }

    public void act() {

    }

    @Override
    public void run() {
        synchronized (this) {
            act();
        }
    }
}
