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
        if (cible instanceof PointPeche) {
            PointPeche pointPeche = (PointPeche) cible;
            // Déplacement direct vers le point de pêche
            bateau.deplacerVersPoint(pointPeche.getX(), pointPeche.getY(), BateauxCollision.getObstacles(bateau), 1, BateauxCollision.LARGEUR_FENETRE, BateauxCollision.HAUTEUR_FENETRE, BateauxCollision.TAILLE_GRILLE, BateauxCollision.LARGEUR_GRILLE, BateauxCollision.HAUTEUR_GRILLE);
        } else if (cible instanceof Bateau) {
            Bateau bateauCible = (Bateau) cible;
            // Déplacement direct vers le bateau cible
            bateau.deplacerVers(bateauCible, BateauxCollision.getObstacles(bateau), 1, BateauxCollision.LARGEUR_FENETRE, BateauxCollision.HAUTEUR_FENETRE, BateauxCollision.TAILLE_GRILLE, BateauxCollision.LARGEUR_GRILLE, BateauxCollision.HAUTEUR_GRILLE);
        } else {
            // Aucune cible spécifique, se déplacer vers le centre de la carte
            int centreX = BateauxCollision.LARGEUR_FENETRE / 2;
            int centreY = BateauxCollision.HAUTEUR_FENETRE / 2;
            bateau.deplacerVersPoint(centreX, centreY, BateauxCollision.getObstacles(bateau), 1, BateauxCollision.LARGEUR_FENETRE, BateauxCollision.HAUTEUR_FENETRE, BateauxCollision.TAILLE_GRILLE, BateauxCollision.LARGEUR_GRILLE, BateauxCollision.HAUTEUR_GRILLE);
        }
        return null;
    }


    @Override
    protected void done() {
        // Assurez-vous que cette mise à jour se fait sur l'EDT
        SwingUtilities.invokeLater(() -> {
            bateauxCollision.repaint();
        });
    }
}
