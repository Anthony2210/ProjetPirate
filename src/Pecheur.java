import java.awt.*;
import java.util.List;

public class Pecheur extends Bateau {
    private List<PointPeche> pointsDePeche;
    private PointPeche pointCible;
    private long tempsDebutPeche;
    private int vitessePecheur;
    private int tempsRestantPourPeche = 5000; // Temps par défaut pour pêcher
    private BateauxCollision bateauxCollision;
    private PointPeche cibleActuelle;

    public Pecheur(int x, int y, int largeur, int hauteur, Image image, List<PointPeche> pointsDePeche, PointPeche pointCible, int vitessePecheur, BateauxCollision bateauxCollision) {
        super(x, y, largeur, hauteur, image);
        this.pointsDePeche = pointsDePeche;
        this.vitessePecheur = vitessePecheur;
        this.bateauxCollision = bateauxCollision;
        this.pointCible = pointCible;
        this.cibleActuelle = pointCible;
        chercherPointPeche();
    }

    public void chercherPointPeche() {
        double distanceMin = Double.MAX_VALUE;
        for (PointPeche point : pointsDePeche) {
            double distance = Math.sqrt(Math.pow(getX() - point.getX(), 2) + Math.pow(getY() - point.getY(), 2));
            if (distance < distanceMin) {
                distanceMin = distance;
                pointCible = point;
                cibleActuelle = pointCible;
            }
        }
    }

    public void pecher() {
        if (pointCible != null && estSurPointPeche()) {
            tempsRestantPourPeche -= BateauxCollision.DELAI;
            if (tempsRestantPourPeche <= 0) {
                pointsDePeche.remove(pointCible);
                pointCible = null;
                chercherPointPeche();
                tempsRestantPourPeche = 5000; // Réinitialiser le temps pour la prochaine pêche
            }
        } else {
            chercherPointPeche();
            deplacerVersPointPeche(BateauxCollision.getObstacles(this));
        }
    }

    public boolean estSurPointPeche() {
        if (pointCible == null) {
            return false;
        }
        return Math.abs(getX() - pointCible.getX()) <= 10 && Math.abs(getY() - pointCible.getY()) <= 10;
    }

    public void deplacerVersPointPeche(List<Bateau> obstacles) {
        if (this.pointCible != null) {
            int destX = pointCible.getX();
            int destY = pointCible.getY();
            super.deplacerVersPoint(destX, destY, obstacles, this.vitessePecheur, BateauxCollision.LARGEUR_FENETRE, BateauxCollision.HAUTEUR_FENETRE, BateauxCollision.TAILLE_GRILLE, BateauxCollision.LARGEUR_GRILLE, BateauxCollision.HAUTEUR_GRILLE);
        }
    }

    public PointPeche getCibleActuelle() {
        return cibleActuelle;
    }
    public PointPeche getPointCible() {
        return this.pointCible;
    }
}
