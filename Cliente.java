public class Cliente implements Runnable {
    private final int id;
    private final SalaDeEspera sala;

    public Cliente(int id, SalaDeEspera sala) {
        this.id = id;
        this.sala = sala;
    }

    @Override
    public void run() {
        System.out.println("Cliente " + id + " llega");
        boolean sentado = sala.entrar(this);
        if (!sentado) {
            System.out.println("Cliente " + id + " se va (no tiene silla).");
            return; // termina hilo
        }
        System.out.println("Cliente " + id + " esperando");
        
    }

    public int getId() { return id; }
}
