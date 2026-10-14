import java.util.concurrent.atomic.AtomicInteger;

// Fase 2 - Classe che gestisce la risorsa condivisa
public class CentroSmistamento {
    private int pacchiDisponibili;
    private final int pacchiIniziali;
    
    // Fase 4 - Utilizzo di variabili atomiche per le statistiche ad alta frequenza
    private final AtomicInteger tentativiTotali = new AtomicInteger(0);
    private final AtomicInteger assegnazioniRiuscite = new AtomicInteger(0);
    private final AtomicInteger assegnazioniFallite = new AtomicInteger(0);

    public CentroSmistamento(int pacchiIniziali) {
        this.pacchiIniziali = pacchiIniziali;
        this.pacchiDisponibili = pacchiIniziali;
    }

    // Fase 3 & 4 - Metodo sincronizzato per garantire l'esclusione mutua sul check-then-act
    public synchronized boolean prelevaPacco() {
        // Ogni interazione conta come tentativo, ma incrementiamo dopo per non bloccare
        if (pacchiDisponibili > 0) {
            pacchiDisponibili--;
            assegnazioniRiuscite.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    public void incrementaTentativo() {
        tentativiTotali.incrementAndGet();
    }

    public void incrementaFallito() {
        assegnazioniFallite.incrementAndGet();
    }

    public void stampaReport() {
        System.out.println("\n========== REPORT FINALE SIMULAZIONE ==========");
        System.out.println("Pacchi Iniziali:          " + pacchiIniziali);
        System.out.println("Pacchi Assegnati:         " + assegnazioniRiuscite.get());
        System.out.println("Pacchi Rimanenti:         " + pacchiDisponibili);
        System.out.println("Tentativi Effettuati:     " + tentativiTotali.get());
        System.out.println("Assegnazioni Riuscite:    " + assegnazioniRiuscite.get());
        System.out.println("Assegnazioni Fallite:     " + assegnazioniFallite.get());
        System.out.println("===============================================");
        
        // Verifica consistenza
        boolean coerenzaSomma = (assegnazioniRiuscite.get() + assegnazioniFallite.get()) == tentativiTotali.get();
        System.out.println("Verifica (Riuscite + Fallite = Tentativi): " + (coerenzaSomma ? "OK" : "ERRORE"));
    }
    
    public synchronized int getPacchiDisponibili() {
        return pacchiDisponibili;
    }
}
