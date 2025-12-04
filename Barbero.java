public class Barbero implements Runnable {
    private final int id;
    private final SalaDeEspera sala;
    private final int tiempoCorteMs;

    public Barbero(int id, SalaDeEspera sala, int tiempoCorteMs) {
        this.id = id;
        this.sala = sala;
        this.tiempoCorteMs = tiempoCorteMs;
    }

    @Override
    public void run() {
        while (true) {
            Cliente c = sala.siguienteCliente(); // duerme si no hay clientes
            System.out.println("Barbero " + id + " atiende a Cliente " + c.getId());
            try { Thread.sleep(tiempoCorteMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
    }
}

