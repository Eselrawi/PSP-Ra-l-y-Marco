public class Main {
    public static void main(String[] args) {

        Barberia barberia = new Barberia(2); // 2 sillas de espera

        // Crear barberos
        Barbero b1 = new Barbero(1, barberia, 2000);
        Barbero b2 = new Barbero(2, barberia, 2000);

        barberia.addBarbero(b1);
        barberia.addBarbero(b2);

        // Hilos barberos
        new Thread(b1).start();
        new Thread(b2).start();

        // Crear clientes cada 1 segundo
        for (int i = 1; i <= 10; i++) {
            Cliente c = new Cliente(i, barberia);
            new Thread(c).start();

            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }
}

