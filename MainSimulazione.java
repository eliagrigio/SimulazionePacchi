// Classe Principale per gestire l'avvio della simulazione
public class MainSimulazione {
    public static void main(String[] args) {
        int NUM_PACCHI = 120;
        int NUM_OPERATORI = 50;

        CentroSmistamento centro = new CentroSmistamento(NUM_PACCHI);
        Operatore[] operatori = new Operatore[NUM_OPERATORI];

        System.out.println("Avvio simulazione con " + NUM_PACCHI + " pacchi e " + NUM_OPERATORI + " operatori...");
    
        // Creazione e avvio dei thread
        for (int i = 0; i < NUM_OPERATORI; i++) { 
            operatori[i] = new Operatore(i + 1, centro);
            operatori[i].start();
        }

        // Attesa del completamento di tutti i thread (Join)
        for (int i = 0; i < NUM_OPERATORI; i++) {
            try {
                operatori[i].join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrotto.");
            }
        }
        

        // Stampa dei risultati finali
        centro.stampaReport();
    }
}