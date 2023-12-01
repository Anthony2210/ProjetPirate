import java.awt.*;

public class PointPeche {
    private int x;
    private int y;
    private TypeRessource ressource;

    public PointPeche(int x, int y, TypeRessource ressource) {
        this.x = x;
        this.y = y;
        this.ressource = ressource;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TypeRessource getRessource() {
        return ressource;
    }

    /**
     * Dessine le point de pêche sur la carte.
     * Utilise différentes formes et couleurs pour représenter les types de ressources.
     *
     * @param g L'objet Graphics utilisé pour le dessin.
     */
    public void dessiner(Graphics g) {
        switch (ressource) {
            case ALGUE:
                g.setColor(Color.GREEN);
                g.fillOval(x, y, 10, 10); // Dessiner une petite forme ovale pour représenter l'algue.
                break;
            case POISSON:
                g.setColor(Color.BLUE);
                g.fillRect(x, y, 10, 10); // Dessiner un carré pour représenter le poisson.
                break;
            case TRESOR:
                g.setColor(Color.YELLOW);
                g.fillPolygon(new int[]{x, x + 10, x + 5}, new int[]{y, y, y + 10}, 3); // Dessiner un triangle pour représenter le trésor.
                break;
        }
    }
}
