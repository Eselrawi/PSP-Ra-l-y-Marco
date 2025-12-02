public class Barbero {
    threadGrupo barberoGrupo = new threadGrupo("BarberoGrupo");

    thread barbero0Thread = new thread(barberoGrupo, new BarberoRunnable(), "Barbero0Thread");
    thread barbero1Thread = new thread(barberoGrupo, new BarberoRunnable(), "Barbero1Thread");
    
}
