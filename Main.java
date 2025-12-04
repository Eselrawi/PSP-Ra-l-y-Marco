public class Main {
     public static final int NUM_SILLAS = 5;
    public static final int NUM_BARBEROS = 2;
    public static final int TIEMPO_CORTE_MS = 2000;
    public static final int TIEMPO_LLEGADA_MS = 3000;

    public static void main(String[] args) {
        SalaDeEspera sala = new SalaDeEspera(NUM_SILLAS);

        int id = 1; //esto genera clientes constantemente y suma 1 al id
        while (true) {
            new Thread(new Cliente(id++, sala)).start();
            try { Thread.sleep(TIEMPO_LLEGADA_MS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
    
    }
}
