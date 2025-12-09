import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Clase que representa la barbería.
 * - Mantiene la cola de espera (FIFO) con capacidad limitada (sillas).
 * - Registra los barberos disponibles.
 * - Coordina llegada de clientes y petición de siguiente cliente por parte de los barberos.
 *
 * Métodos sincronizados para evitar condiciones de carrera entre hilos.
 */
public class Barberia {
    private final Queue<Cliente> salaDeEspera = new LinkedList<>();
    private final int capacidadSillas;
    private final List<Barbero> barberos = new LinkedList<>();

    public Barberia(int capacidadSillas) {
        this.capacidadSillas = capacidadSillas;
    }

    public synchronized void addBarbero(Barbero b) {
        barberos.add(b);
    }

    //Llamada del cliente
    public synchronized void llegaCliente(Cliente c) {
        System.out.println("Cliente " + c.getId() + " llega");

        // Si hay algún barbero libre en ese momento el cliente es  tiene que ser atendido.
        // Solo imprimimos "atendido inmediatamente" si hay barbero libre ahora mismo.
        boolean hayBarberoLibre = false;
        for (Barbero b : barberos) {
            if (b.estaLibre()) {
                hayBarberoLibre = true;
                break;
            }
        }

        if (hayBarberoLibre) {
            // Aun así, el cliente entra en la cola: el barbero que esté libre lo cogerá inmediatamente.
            salaDeEspera.offer(c);
            System.out.println("Cliente " + c.getId() + " se sienta y será atendido inmediatamente si hay barbero libre");
            notifyAll(); // despierta a barberos dormidos
            return;
        }

        // Si no hay barbero libre: comprobar sillas de espera
        if (salaDeEspera.size() < capacidadSillas) {
            salaDeEspera.offer(c);
            System.out.println("Cliente " + c.getId() + " se sienta en la sala de espera.");
            notifyAll(); // avisar a barberos (si alguno duerme)
        } else {
            System.out.println("Cliente " + c.getId() + " se va (no hay sillas).");
        }
    }

    /**
     * Llamado por un barbero que quiere atender al siguiente cliente.
     * Si no hay clientes, el barbero espera (se duerme).
     * Devuelve el siguiente cliente (FIFO).
     */
    public synchronized Cliente siguienteCliente() {
        while (salaDeEspera.isEmpty()) {
            try {
                // barbero duerme hasta que llegue un cliente
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return salaDeEspera.poll(); // FIFO: removeFirst()
    }

    /**
     * Para depuración / pruebas: devuelve número actual de clientes esperando.
     */
    public synchronized int clientesEsperando() {
        return salaDeEspera.size();
    }

    /**
     * Para depuración / pruebas: devuelve capacidad de sillas.
     */
    public int getCapacidadSillas() {
        return capacidadSillas;
    }
}


