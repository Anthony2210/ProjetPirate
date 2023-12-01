import javax.swing.*;

public class BateauTask extends SwingWorker<Void, Void> {
    private final Bateau bateau;
    private final BateauxCollision bateauxCollision;
    private final Object cible; // Peut être un PointPeche ou un Bateau

    public BateauTask(Bateau bateau, BateauxCollision bateauxCollision, Object cible) {
        this.bateau = bateau;
        this.bateauxCollision = bateauxCollision;
        this.cible = cible;
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Logique de déplacement du bateau en fonction du type de cible
        if (cible instanceof PointPeche) {
            // Logique pour les pêcheurs se dirigeant vers un point de pêche
        } else if (cible instanceof Bateau) {
            // Logique pour les pirates ou les marines se dirigeant vers un autre bateau
        }
        return null;
    }

    @Override
    protected void done() {
        bateauxCollision.repaint();
    }
}
