import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Barberia { //atributos
    private final Queue<Cliente> salaDeEspera = new LinkedList<>(); //cola de clientes FIFO
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

        
        
        boolean hayBarberoLibre = false;
        for (Barbero b : barberos) { 
            if (b.estaLibre()) {// Si hay algún barbero libre en ese momento el cliente tiene que ser atendido.
                hayBarberoLibre = true;
                break;
            }
        }

        if (hayBarberoLibre) {
            salaDeEspera.offer(c); //Se añade el cliente a la cola
            System.out.println("Cliente " + c.getId() + " se será atendido ahora mismo ");
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
                // barbero duerme hasta que llega un cliente
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


