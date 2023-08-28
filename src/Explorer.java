import java.util.LinkedList;

public class Explorer extends Agent{
    private boolean withTreasure;

    public Explorer(Unit[][] env) {
        super(env);
        this.withTreasure = false;
    }

    protected void look() {
        String s = location.getContent();
        if (s != null) {

            if (s.equals(Unit.TREASURE)) {
                grab();
            } else if (s.equals(Unit.PIT)) {
                setDead();
                System.out.println("O agente caiu no " + Unit.PIT);
            }

        }
    }

    public void perceive() {
        LinkedList<String> list = location.getPerceptions();
        for (String s : list) {
            System.out.println("O Agente percepeu um(a) " + s);
        }
        if (list.isEmpty()) {
            System.out.println("O Agente não percepeu nada " );
        }
    }

    public void grab() {
        if ( location.removeContent() ) {
            this.withTreasure = true;
            System.out.println("O Agente pegou o " + Unit.TREASURE);
        }
    }

    public boolean scape() {
        if (withTreasure && location.isExit()) {
            System.out.println("O Agente escapou com o " + Unit.TREASURE);
            return true;
        }
        return false;
    }

    @Override
    public void act() {
        while (isAlive() && !scape()) {
            perceive();
            look();
            move(movementGenerator());
            try {
                Thread.sleep(2000);
            } catch (Exception e) { }
        }
    }
}
