import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final int DIMENSION = 4;

    private Unit[][] environment;
    private Random random;

    public Main() {
        this.random = new Random();
        generateWorld();
    }

    public Unit[][] generateWorld() {
        int x, y;
        this.environment = new Unit[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                environment[i][j] = new Unit(i, j);
            }
        }

        environment[0][0].setExit();

        //setting pit spots and perceptions
        int nPit = (int) (Math.pow(DIMENSION, 2) * 0.2);
        for (int i = nPit; i > 0; i--) {

            x = random.nextInt(DIMENSION);
            y = random.nextInt(DIMENSION);
            if (!environment[x][y].isExit() && environment[x][y].setContent(Unit.PIT)) {

                ArrayList<Unit> list = getAdjacent(x, y);
                for (Unit u : list) {
                    u.setPerception(Unit.BREEZE);
                }

            } else {
                i++;
            }
        }

        //setting treasure
        while (true) {
            x = random.nextInt(DIMENSION);
            y = random.nextInt(DIMENSION);
            if (!environment[x][y].isExit() && environment[x][y].setContent(Unit.TREASURE)) {

                ArrayList<Unit> list = getAdjacent(x, y);
                for (Unit u : list) {
                    u.setPerception(Unit.SHINING);
                }
                break;

            }
        }
        return environment;
    }

    public void putAgent(Agent agent) {
        if (agent instanceof Explorer) {
            environment[0][0].setAgent(agent);
            agent.setLocation(environment[0][0]);
        }
        else if (agent instanceof Wumpus){

            int x, y;
            while (true) {
                x = random.nextInt(Main.DIMENSION);
                y = random.nextInt(Main.DIMENSION);
                if (!environment[x][y].isExit()) {

                    environment[x][y].setAgent(agent);
                    agent.setLocation(environment[x][y]);

                    ArrayList<Unit> list = getAdjacent(x, y);
                    for (Unit u : list) {
                        u.setPerception(Unit.STINK);
                    }
                    break;

                }
            }
        }
    }

    private ArrayList<Unit> getAdjacent(int x, int y) {
        ArrayList<Unit> list = new ArrayList<>();
        if (x-1 >= 0) {
            list.add(environment[x-1][y]);
        }
        if (y-1 >= 0) {
            list.add(environment[x][y-1]);
        }
        if (x+1 < DIMENSION) {
            list.add(environment[x+1][y]);
        }
        if (y+1 < DIMENSION) {
            list.add(environment[x][y+1]);
        }
        return list;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Agent a1 = new Explorer(main.environment);
        Agent a2 = new Wumpus(main.environment);

        main.putAgent(a1);
        main.putAgent(a2);

        ExecutorService e = Executors.newFixedThreadPool(2);
        e.execute(a1);
        e.execute(a2);
        e.shutdown();
    }
}
