import java.util.LinkedList;

public class Unit {
    public static final String TREASURE = "tesouro";
    public static final String PIT = "poco";

    public static final String SHINING = "brilho";
    public static final String BREEZE = "brisa";
    public static final String STINK = "fedor";

    public final int X, Y;

    private LinkedList<String> perceptions;
    private LinkedList<Agent> agents;
    private String content;
    private boolean exit;

    public Unit(int x, int y) {
        this.X = x;
        this.Y = y;

        this.perceptions = new LinkedList<>();
        this.agents = new LinkedList<>();
        this.content = null;
        this.exit = false;
    }

    public void setExit() {
        this.exit = true;
    }

    public void setAgent(Agent agent) {
        agents.add(agent);
    }

    public void setPerception(String perception) {
        perceptions.add(perception);
    }

    public boolean setContent(String content) {
        if (this.content == null) {
            this.content = content;
            return true;
        }
        return false;
    }

    public LinkedList<Agent> getAgents() {
        return agents;
    }

    public LinkedList<String> getPerceptions() {
        return perceptions;
    }

    public String getContent() {
        return content;
    }

    public boolean removeContent() {
        if (content.equals(TREASURE)) {
            this.content = null;
            return true;
        }
        return false;
    }

    public boolean removeAgent(Agent agent) {
        return agents.remove(agent);
    }

    public boolean isExit() {
        return exit;
    }
}
