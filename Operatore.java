// Fase 2 - Classe che modella il Thread Operatore
public class Operatore extends Thread {
    private final int idOperatore;
    private final CentroSmistamento centro;
    private int pacchiAssegnatiDaMe = 0;

    public Operatore(int id, CentroSmistamento centro) {
        this.idOperatore = id;
        this.centro = centro;
    }

    @Override
    public void run() {
        // L'operatore continua a provare finché ci sono pacchi nel centro
        while (centro.getPacchiDisponibili() > 0) {
            centro.incrementaTentativo(); // Tracciamo il tentativo svolto
            
            // Introduciamo un piccolo ritardo casuale per simulare il lavoro reale
            // e forzare l'avvicendamento dei thread (Thread.sleep)
            try {
                Thread.sleep((int)(Math.random() * 10));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (centro.prelevaPacco()) {
                pacchiAssegnatiDaMe++;
                System.out.println("Operatore [" + idOperatore + "] ha assegnato con successo un pacco.");
            } else {
                centro.incrementaFallito();
                System.out.println("Operatore [" + idOperatore + "] tentativo fallito: pacchi esauriti.");
            }
        }
    }
}