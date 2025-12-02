import java.util.LinkedList;

public class SalaDeEspera {

    private int numSillas;
    private LinkedList<Cliente> cola;

    public SalaDeEspera(int numSillas) {
        this.numSillas = numSillas;
        this.cola = new LinkedList<>();
    }

    // intenta sentarse y si sale tru se une a la cola fifo y si la cola esta llena devuelve false
    public synchronized boolean entrar(Cliente c) {

        if (cola.size() == numSillas) {
            return false; 
        }

        cola.addLast(c); 
        notifyAll();     // avisamos a los barberos por que puede que esten dormidos
        return true;
    }

    // El barbero duerme hasta que haya un cliente cuando lo hay saca el primero de la lista
    public synchronized Cliente siguienteCliente() {

        while (cola.isEmpty()) {
            try {
                wait(); // barbero duerme
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return cola.removeFirst(); 
    }
}


    

