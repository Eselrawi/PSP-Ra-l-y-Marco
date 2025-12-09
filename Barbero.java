public class Barbero implements Runnable {
    private final int id;
    private final Barberia barberia;
    private final int tiempoCorteMs;
    private boolean libre = true;

    public Barbero(int id, Barberia barberia, int tiempoCorteMs) {
        this.id = id;
        this.barberia = barberia;
        this.tiempoCorteMs = tiempoCorteMs;
    }

    public int getId() { return id; }

    public boolean estaLibre() {
        return libre;
    }

    @Override
    public void run() {
        while (true) {
            Cliente cliente = barberia.siguienteCliente(); // espera si está vacía

            libre = false;
            System.out.println(" Barbero " + id + " corta a Cliente " + cliente.getId());

            try {
                Thread.sleep(tiempoCorteMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println(" Cliente " + cliente.getId() + " ha terminado con Barbero " + id);
            libre = true;
        }
    }
}


