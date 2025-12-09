/**
 * Barbero: hilo que atiende clientes.
 * - Pide siguiente cliente a la barbería (siguienteCliente())
 * - Si no hay clientes, se duerme dentro de siguienteCliente()
 * - Simula el corte con Thread.sleep(tiempoCorteMs)
 */
public class Barbero implements Runnable {
    private final int id;
    private final Barberia barberia;
    private final int tiempoCorteMs;
    private volatile boolean libre = true; // indica si está libre (se lee desde otros hilos)

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
        while (!Thread.currentThread().isInterrupted()) {
            Cliente cliente = barberia.siguienteCliente(); // duerme aquí si no hay clientes
            if (cliente == null) {
                // hilo interrumpido o barberia devolvió null
                break;
            }

            // Marcar ocupado y atender
            libre = false;
            System.out.println("Barbero " + id + " atiende a Cliente " + cliente.getId());

            try {
                Thread.sleep(tiempoCorteMs); // simula tiempo de corte
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println("Cliente " + cliente.getId() + " ha terminado con Barbero " + id);
            // después del corte, la silla queda libre y el siguiente cliente (si hay) podrá sentarse/ser atendido
            libre = true;
        }
        System.out.println("Barbero " + id + " finaliza su turno.");
    }
}



