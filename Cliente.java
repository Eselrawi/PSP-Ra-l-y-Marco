//Un cliente tiene que llegar intentar sentarse a es y si no puede se va


public class Cliente implements Runnable {
    private final int id;
    private final Barberia barberia;

    public Cliente(int id, Barberia barberia) {
        this.id = id;
        this.barberia = barberia;
    }

    public int getId() { return id; }

    @Override
    public void run() {
        barberia.llegaCliente(this);
    }
}

