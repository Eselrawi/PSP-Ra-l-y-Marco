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

        
        //comprueba si hay barbero libre
        boolean hayBarberoLibre = false;
        for (Barbero b : barberos) { 
            if (b.estaLibre()) {//el cliente sera atendido inmediatemente
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

        // no hay barbero libre: comprobar sillas de espera
        if (salaDeEspera.size() < capacidadSillas) {
            salaDeEspera.offer(c);
            System.out.println("Cliente " + c.getId() + " se sienta en la sala de espera.");
            notifyAll(); // avisar a barberos (si alguno duerme)
        } else {
            System.out.println("Cliente " + c.getId() + " se va (no hay sillas).");//si no hay silla se va
        }
    }

    
    
     
    public synchronized Cliente siguienteCliente() {
        while (salaDeEspera.isEmpty()) {
            try {
        
                wait(); // barbero duerme aqui hasta que llega un cliente
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return salaDeEspera.poll(); // llega un cliente y se atiende en orden fifo
    }

   
    public synchronized int clientesEsperando() { //clientes esperando
        return salaDeEspera.size();
    }

    public int getCapacidadSillas() { //sillas
        return capacidadSillas;
    }
}


