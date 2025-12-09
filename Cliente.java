
public class Cliente implements Runnable {
    private final int id;
    private final Barberia barberia;

    public Cliente(int id, Barberia barberia) {
        this.id = id;
        this.barberia = barberia;
    }

    public int getId() {
        return id;
    } // Cogemos su id con el constructor

    @Override
    public void run() {
        barberia.llegaCliente(this); // Llama a la barberia
        
    }
}


