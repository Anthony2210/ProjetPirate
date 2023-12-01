import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Bateau {
    private int x, y;
    private int largeur, hauteur;
    private final Image image;

    public Bateau(int x, int y, int largeur, int hauteur, Image image) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public Image getImage() {
        return image;
    }

    public void dessiner(Graphics g) {
        g.drawImage(image, x, y, largeur, hauteur, null);
    }

    public void deplacer(int largeurFenetre, int hauteurFenetre, int dx, int dy) {
        x += dx;
        y += dy;

        if (x < 0) x = 0;
        if (x > largeurFenetre - largeur) x = largeurFenetre - largeur;
        if (y < 0) y = 0;
        if (y > hauteurFenetre - hauteur) y = hauteurFenetre - hauteur;
    }

    public void deplacerVers(Bateau destination, List<Bateau> obstacles, int vitesseMarine,int largeurFenetre, int hauteurFenetre, int tailleGrille, int largeurGrille, int hauteurGrille) {
        Queue<Noeud> filePrioritaire = new PriorityQueue<>();
        List<Noeud> exploredNodes = new ArrayList<>();

        Noeud initialNode = new Noeud(this.x / tailleGrille, this.y / tailleGrille);
        Noeud goalNode = new Noeud(destination.x / tailleGrille, destination.y / tailleGrille);

        filePrioritaire.add(initialNode);

        while (!filePrioritaire.isEmpty()) {
            Noeud current = filePrioritaire.poll();

            if (current.equals(goalNode)) {
                List<Noeud> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.getParent();
                }
                Collections.reverse(path);

                if (path.size() > 1) {
                    Noeud nextNode = path.get(1);
                    int dx = (nextNode.getX() - initialNode.getX()) * tailleGrille;
                    int dy = (nextNode.getY() - initialNode.getY()) * tailleGrille;

                    int distance = (int) Math.sqrt(dx * dx + dy * dy);
                    if (distance > vitesseMarine) {
                        dx = (dx * vitesseMarine) / distance;
                        dy = (dy * vitesseMarine) / distance;
                    }

                    boolean collision = false;
                    for (Bateau obstacle : obstacles) {
                        if (obstacle != this && obstacle.seToucheAvec(this.x + dx, this.y + dy)) {
                            collision = true;
                            break;
                        }
                    }

                    if (!collision) {
                        this.deplacer(largeurFenetre, hauteurFenetre, dx, dy);
                    }
                }

                break;
            }

            exploredNodes.add(current);

            for (Noeud neighbor : current.getNeighbors()) {
                if (neighbor.getX() >= 0 && neighbor.getX() < largeurGrille && neighbor.getY() >= 0 && neighbor.getY() < hauteurGrille) {
                    if (!exploredNodes.contains(neighbor) && !filePrioritaire.contains(neighbor)) {
                        neighbor.setParent(current);
                        filePrioritaire.add(neighbor);
                    }
                }
            }
        }
    }


    public boolean seToucheAvec(Bateau autreBateau) {
        return x + largeur >= autreBateau.x && x <= autreBateau.x + largeur
                && y + hauteur >= autreBateau.y && y <= autreBateau.y + hauteur;
    }

    public boolean seToucheAvec(int newX, int newY) {
        return newX + largeur >= x && newX <= x + largeur
                && newY + hauteur >= y && newY <= y + hauteur;
    }
    public void deplacerVersPoint(int destX, int destY, List<Bateau> obstacles, int vitesse, int largeurFenetre, int hauteurFenetre, int tailleGrille, int largeurGrille, int hauteurGrille) {
        Queue<Noeud> filePrioritaire = new PriorityQueue<>();
        List<Noeud> exploredNodes = new ArrayList<>();

        Noeud initialNode = new Noeud(this.x / tailleGrille, this.y / tailleGrille);
        Noeud goalNode = new Noeud(destX / tailleGrille, destY / tailleGrille);

        filePrioritaire.add(initialNode);

        while (!filePrioritaire.isEmpty()) {
            Noeud current = filePrioritaire.poll();

            if (current.equals(goalNode)) {
                List<Noeud> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.getParent();
                }
                Collections.reverse(path);

                if (path.size() > 1) {
                    Noeud nextNode = path.get(1);
                    int dx = (nextNode.getX() - initialNode.getX()) * tailleGrille;
                    int dy = (nextNode.getY() - initialNode.getY()) * tailleGrille;

                    int distance = (int) Math.sqrt(dx * dx + dy * dy);
                    if (distance > vitesse) {
                        dx = (dx * vitesse) / distance;
                        dy = (dy * vitesse) / distance;
                    }

                    boolean collision = false;
                    for (Bateau obstacle : obstacles) {
                        if (obstacle != this && obstacle.seToucheAvec(this.x + dx, this.y + dy)) {
                            collision = true;
                            break;
                        }
                    }

                    if (!collision) {
                        this.deplacer(largeurFenetre, hauteurFenetre, dx, dy);
                    }
                }

                break;
            }

            exploredNodes.add(current);

            for (Noeud neighbor : current.getNeighbors()) {
                if (neighbor.getX() >= 0 && neighbor.getX() < largeurGrille && neighbor.getY() >= 0 && neighbor.getY() < hauteurGrille) {
                    if (!exploredNodes.contains(neighbor) && !filePrioritaire.contains(neighbor)) {
                        neighbor.setParent(current);
                        filePrioritaire.add(neighbor);
                    }
                }
            }
        }
    }
    public void deplacerVersCible(Bateau bateau, int xCible, int yCible) {

        // Calculer la distance entre le bateau et la cible
        int deltaX = xCible - bateau.getX();
        int deltaY = yCible - bateau.getY();

        // Calculer la distance totale à parcourir
        double distanceTotale = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Calculer le déplacement en x et y
        double deplacementX = (deltaX / distanceTotale);
        double deplacementY = (deltaY / distanceTotale);

        // Mettre à jour la position du bateau
        int nouvelleX = bateau.getX() + (int) deplacementX;
        int nouvelleY = bateau.getY() + (int) deplacementY;
        bateau.setX(nouvelleX); // Supposons que chaque bateau ait une méthode setX() et setY() pour mettre à jour sa position
        bateau.setY(nouvelleY);
    }
}
