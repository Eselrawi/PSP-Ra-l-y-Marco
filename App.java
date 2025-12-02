public class App {
    ThreadGroup tg = new ThreadGroup("Clientes");
    run() {
        Thread t1 = new Thread(tg, new Cliente("Cliente 1"));
        Thread t2 = new Thread(tg, new Cliente("Cliente 2"));
        t1.start();
        t2.start();
    }
}
