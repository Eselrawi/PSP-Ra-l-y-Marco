
public class Main {
    // CONFIGURACIÓN 
    public static final int NUM_BARBEROS = 2;
    public static final int NUM_SILLAS = 1;
    public static final int TIEMPO_CORTE_MS = 3000;
    public static final int TIEMPO_LLEGADA_MS = 1000;
    public static final int TOTAL_CLIENTES = 10; // 0 es infinito
    
    public static void main(String[] args) throws InterruptedException {
        Barberia barberia = new Barberia(NUM_SILLAS);

        // Crear y arrancar barberos
        Thread[] hilosBarbero = new Thread[NUM_BARBEROS];
        for (int i = 0; i < NUM_BARBEROS; i++) {
            Barbero b = new Barbero(i + 1, barberia, TIEMPO_CORTE_MS);
            barberia.addBarbero(b);
            hilosBarbero[i] = new Thread(b, "Barbero-" + (i + 1));
            hilosBarbero[i].start();
        }

        // Generar clientes (infinito o finito según TOTAL_CLIENTES)
        int id = 1;
        if (TOTAL_CLIENTES <= 0) {
            
            while (true) {
                Cliente c = new Cliente(id++, barberia);
                new Thread(c, "Cliente-" + id).start();
                Thread.sleep(TIEMPO_LLEGADA_MS);
            }
        } else {
            
            for (int i = 0; i < TOTAL_CLIENTES; i++) {
                Cliente c = new Cliente(id++, barberia);
                new Thread(c, "Cliente-" + id).start();
                Thread.sleep(TIEMPO_LLEGADA_MS);
            }
            
            // para que los barberos terminen y salir.
            System.out.println("Todos los clientes generados. Esperando a que terminen los cortes...");
            
            long espera = (long) (TOTAL_CLIENTES * TIEMPO_CORTE_MS / Math.max(1, NUM_BARBEROS)) + 5000;
            Thread.sleep(espera);

            // Interrumpimos hilos barbero para terminar el programa 
            for (Thread t : hilosBarbero) {
                t.interrupt();
            }
            
        }
    }
}



