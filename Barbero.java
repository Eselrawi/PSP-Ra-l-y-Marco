
public class Barbero implements Runnable {
    private final int id;
    private final Barberia barberia;
    private final int tiempoCorteMs;
    private volatile boolean libre = true; // indica si está libre (se lee desde otros hilos)
//volatile hace que los cambios se vean inmediatamente
    public Barbero(int id, Barberia barberia, int tiempoCorteMs) {
        this.id = id;
        this.barberia = barberia;
        this.tiempoCorteMs = tiempoCorteMs;
    }

    public int getId() {
        return id;
    }

    public boolean estaLibre() {
        return libre;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) { //el barbero esta trabajando hasta que se interrumpe el hilo
            Cliente cliente = barberia.siguienteCliente(); // devuelve cliente si hay o duerme el barbero si no hay clientes
            if (cliente == null) {
                // hilo interrumpido 
                break;
            }

            // barbero pasa a  ocupado y atiende
            libre = false;
            System.out.println("Barbero " + id + " atiende a Cliente " + cliente.getId());

            try {
                Thread.sleep(tiempoCorteMs); // simula tiempo de corte
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println("Cliente " + cliente.getId() + " ha terminado con Barbero " + id);
            // después del corte, la silla queda libre y el siguiente cliente podrá ser atendido
            libre = true;
        }
        System.out.println("Barbero " + id + " finaliza su turno.");
    }
}



