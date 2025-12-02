// Cliente.java
public class Cliente {
    public static void main(String[] args) {
        
    
    for (int i = 0; i < 5; i++) {
        thread clienteThread = new thread(new ClienteRunnable(i), "ClienteThread-" + i);
    }
    
}
}