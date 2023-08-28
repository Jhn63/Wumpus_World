import java.util.LinkedList;

public class Wumpus extends Agent{

    public Wumpus(Unit[][] env) {
        super(env);
    }

    public boolean kill() {
        LinkedList<Agent> list = location.getAgents();
        for (Agent a : list) {
            if ( a instanceof Explorer) {
                a.setDead();
                return true;
            }
        }
        return false;
    }

    @Override
    public void act() {
        while (isAlive()) {
            if (kill()) {
                System.out.println("Wumpus matou o Agente");
                break;
            }
        }
    }
}
