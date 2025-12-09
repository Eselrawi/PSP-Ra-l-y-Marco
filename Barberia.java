import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Barberia {
    private final Queue<Cliente> salaDeEspera = new LinkedList<>();
    private final int sillas;
    private final List<Barbero> barberos = new LinkedList<>();

    public Barberia(int sillas) {
        this.sillas = sillas;
    }

    public void addBarbero(Barbero b) {
        barberos.add(b);
    }

    public synchronized void llegaCliente(Cliente c) {

        // 1. ¿Hay barbero libre? Atiende sin esperar
        for (Barbero b : barberos) {
            if (b.estaLibre()) {
                System.out.println("Cliente " + c.getId() + " llega y ES ATENDIDO inmediatamente por Barbero " + b.getId());
                salaDeEspera.offer(c);
                notifyAll();
                return;
            }
        }

        // 2. Si no hay barberos libres → ¿hay sillas?
        if (salaDeEspera.size() < sillas) {
            salaDeEspera.offer(c);
            System.out.println("Cliente " + c.getId() + " se sienta en una silla.");
            notifyAll();
        } else {
            System.out.println(" Cliente " + c.getId() + " no tiene silla y se va.");
        }
    }

    public synchronized Cliente siguienteCliente() {
        while (salaDeEspera.isEmpty()) {
            try {
                wait(); // barbero duerme
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return salaDeEspera.poll();
    }
}


    

