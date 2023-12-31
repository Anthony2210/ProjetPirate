import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * La classe <code>BateauxCollision</code> représente un jeu où différents types de bateaux
 * (pirates, marines et pêcheurs) se déplacent sur un plan d'eau et interagissent entre eux.
 * Cette classe gère le mouvement des bateaux, les collisions, les explosions et les interfaces utilisateur
 * comme les curseurs pour ajuster la vitesse des bateaux.
 */
public class BateauxCollision extends JPanel implements ActionListener {
    // Constantes
    public static final int LARGEUR_FENETRE = 1000;
    public static final int HAUTEUR_FENETRE = 600;
    public final int LARGEUR_BATEAU = 50;
    public final int HAUTEUR_BATEAU = 50;
    public static final int DELAI = 10;
    private static final long EXPLOSION_DUREE = 500;
    public static final int TAILLE_GRILLE = 20; // Taille d'une cellule de la grille
    public static final int LARGEUR_GRILLE = LARGEUR_FENETRE / TAILLE_GRILLE;
    public static final int HAUTEUR_GRILLE = HAUTEUR_FENETRE / TAILLE_GRILLE;
    private final Image explosionImage;
    private Image bateauPimage;
    private Image bateauMimage;
    private Image bateauPeche;
    private boolean explosionActive = false;
    private int explosionX = 0;
    private int explosionY = 0;
    private long explosionDebut = 0;
    private final VitesseSlider vitesseSlider;
    private static final int nombreDePointsPeche = 10;
    private static final int NOMBRE_BATEAUX_PIRATE = 7;
    private static final int NOMBRE_BATEAUX_MARINE = 7;
    private static final int NOMBRE_BATEAUX_PECHEUR = 5;
    private int compteur = 0;


    // Variables d'instance
    private final List<PointPeche> pointsPeche;
    private static final List<Bateau> bateauxPirate = new ArrayList<>();
    private static final List<Bateau> bateauxMarine = new ArrayList<>();
    private static final List<Bateau> bateauxPecheurs = new ArrayList<>();


    /**
     * Constructeur pour créer une instance de BateauxCollision.
     * Initialise les listes de bateaux, les images, les sliders de vitesse et configure l'interface utilisateur.
     */
    public BateauxCollision() {
        javax.swing.Timer timer = new javax.swing.Timer(DELAI, this);
        timer.start();

        this.vitesseSlider = new VitesseSlider(this); // Initialisation de vitesseSlider
        ControlesVitesseFrame vitesseFrame = new ControlesVitesseFrame(this.vitesseSlider);
        vitesseFrame.setVisible(true);


        ImageIcon bateauPIcon = new ImageIcon(getClass().getResource("img/bateauPimage.png"));
        bateauPimage = bateauPIcon.getImage();

        ImageIcon bateauMIcon = new ImageIcon(getClass().getResource("img/bateauMimage.png"));
        bateauMimage = bateauMIcon.getImage();

        ImageIcon bateauPecheIcon = new ImageIcon(getClass().getResource("img/bateauPeche.png"));
        bateauPeche = bateauPecheIcon.getImage();


        ImageIcon explosionIcon = new ImageIcon(getClass().getResource("img/explosion.png"));
        explosionImage = explosionIcon.getImage();

        Random random = new Random();

        pointsPeche = new ArrayList<>();
        // Ajouter des points de pêche avec des ressources aléatoires
        for (int i = 0; i < nombreDePointsPeche; i++) {
            int x = random.nextInt(LARGEUR_FENETRE);
            int y = random.nextInt(HAUTEUR_FENETRE);
            TypeRessource ressource = TypeRessource.values()[random.nextInt(TypeRessource.values().length)];
            pointsPeche.add(new PointPeche(x, y, ressource));
        }


        for (int i = 0; i < NOMBRE_BATEAUX_PECHEUR; i++) {
            int x = random.nextInt(LARGEUR_FENETRE / 4); // Seulement sur le côté gauche
            int y = random.nextInt(HAUTEUR_FENETRE - HAUTEUR_BATEAU);
            PointPeche pointCibleInitial = pointsPeche.get(random.nextInt(pointsPeche.size()));
            bateauxPecheurs.add(new Pecheur(x, y, LARGEUR_BATEAU, HAUTEUR_BATEAU, bateauPeche, pointsPeche, pointCibleInitial, 2, this));
        }

        Color couleurFond = new Color(30, 144, 255);
        setBackground(couleurFond);
        setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));

        JPanel panelSliders = new JPanel(new GridLayout(2, 2));
        panelSliders.add(new JLabel("Vitesse Marine:"));
        panelSliders.add(vitesseSlider.getSliderMarine());
        panelSliders.add(new JLabel("Vitesse Pirates:"));
        panelSliders.add(vitesseSlider.getSliderPirates());
        panelSliders.add(new JLabel("Vitesse Pecheurs:"));
        panelSliders.add(vitesseSlider.getSliderPecheurs());

        add(panelSliders, BorderLayout.SOUTH);
    }
    /**
     * Méthode appelée pour peindre les composants du jeu.
     * Elle dessine les bateaux, les explosions et gère les mises à jour de l'affichage.
     *
     * @param g L'objet Graphics utilisé pour dessiner les composants.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (PointPeche point : pointsPeche) {
            point.dessiner(g);
        }

        for (Bateau bateau : bateauxPirate) {
            bateau.dessiner(g);
        }

        for (Bateau bateau : bateauxMarine) {
            bateau.dessiner(g);
        }

        for (Bateau bateau : bateauxPecheurs) {
            bateau.dessiner(g);
        }

        if (explosionActive) {
            long tempsActuel = System.currentTimeMillis();
            long tempsDepuisExplosion = tempsActuel - explosionDebut;

            if (tempsDepuisExplosion < EXPLOSION_DUREE) {
                g.drawImage(explosionImage, explosionX, explosionY, LARGEUR_BATEAU, HAUTEUR_BATEAU, null);
            } else {
                explosionActive = false;
            }
        }
    }

    /**
     * Méthode implémentant l'actionListener pour gérer les événements d'action, tels que les mouvements des bateaux.
     * Elle gère la logique de déplacement des bateaux, les collisions et les explosions.
     *
     * @param e L'événement d'action.
     */
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        compteur++; // Incrémentation du compteur
        List<Bateau> tousLesBateaux = new ArrayList<>();
        tousLesBateaux.addAll(bateauxPirate);
        tousLesBateaux.addAll(bateauxMarine);
        tousLesBateaux.addAll(bateauxPecheurs);




        if (compteur == 50) { // Apparition des pirates
            for (int i = 0; i < NOMBRE_BATEAUX_PIRATE; i++) {
                int x = LARGEUR_FENETRE; // Juste à droite de la fenêtre
                int y = random.nextInt(HAUTEUR_FENETRE - HAUTEUR_BATEAU);
                Bateau pirate = new Bateau(x, y, LARGEUR_BATEAU, HAUTEUR_BATEAU, bateauPimage);
                bateauxPirate.add(pirate);
                pirate.deplacerVersPoint(LARGEUR_FENETRE - LARGEUR_BATEAU, y, new ArrayList<>(), 1, LARGEUR_FENETRE, HAUTEUR_FENETRE, TAILLE_GRILLE, LARGEUR_GRILLE, HAUTEUR_GRILLE);
            }
        } else if (compteur == 60) { // Apparition des marines
            for (int i = 0; i < NOMBRE_BATEAUX_MARINE; i++) {
                int x = LARGEUR_BATEAU; // Juste à gauche de la fenêtre
                int y = random.nextInt(HAUTEUR_FENETRE - HAUTEUR_BATEAU);
                Bateau marine = new Bateau(x, y, LARGEUR_BATEAU, HAUTEUR_BATEAU, bateauMimage);
                bateauxMarine.add(marine);
                marine.deplacerVersPoint(0, y, new ArrayList<>(), 1, LARGEUR_FENETRE, HAUTEUR_FENETRE, TAILLE_GRILLE, LARGEUR_GRILLE, HAUTEUR_GRILLE);
            }
        }

        startBateauTasks();

        gérerCollisions();

        repaint();

    }
    private void gérerCollisions() {
        Random random = new Random();

        for (int i = 0; i < bateauxPirate.size(); i++) {
            Bateau pirate = bateauxPirate.get(i);
            vérifierCollisionEtCoulerBateau(pirate, bateauxMarine, random);
            vérifierCollisionEtCoulerBateau(pirate, bateauxPecheurs, random);
        }

        for (int i = 0; i < bateauxMarine.size(); i++) {
            Bateau marine = bateauxMarine.get(i);
            vérifierCollisionEtCoulerBateau(marine, bateauxPirate, random);
            vérifierCollisionEtCoulerBateau(marine, bateauxPecheurs, random);
        }
    }

    private void vérifierCollisionEtCoulerBateau(Bateau bateau, List<Bateau> autresBateaux, Random random) {
        for (int j = 0; j < autresBateaux.size(); j++) {
            Bateau autreBateau = autresBateaux.get(j);
            if (bateau.seToucheAvec(autreBateau)) {
                // Déterminer le bateau qui coule
                Bateau bateauCoulé = autreBateau instanceof Pecheur || random.nextBoolean() ? autreBateau : bateau;

                // Supprimer le bateau coulé de la liste
                if (bateauCoulé == autreBateau) {
                    autresBateaux.remove(j);
                } else {
                    autresBateaux.remove(bateau);
                    // Sortie anticipée pour éviter des erreurs d'indexation
                    break;
                }

                // Afficher l'explosion à l'emplacement du bateau coulé
                afficherExplosion(bateauCoulé.getX(), bateauCoulé.getY());
            }
        }
    }

    private void afficherExplosion(int x, int y) {
        explosionActive = true;
        explosionX = x;
        explosionY = y;
        explosionDebut = System.currentTimeMillis();
    }

    /**
     * Trouve le bateau le plus proche du bateau donné dans une liste spécifiée de bateaux.
     *
     * @param bateau Le bateau pour lequel trouver le plus proche.
     * @param listeBateaux La liste des bateaux à considérer.
     * @return Le bateau le plus proche ou <code>null</code> si aucun n'est trouvé.
     */
    private Bateau trouverBateauPlusProche(Bateau bateau, List<Bateau> listeBateaux) {
        Bateau plusProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Bateau autreBateau : listeBateaux) {
            if (autreBateau != bateau) {
                double distanceHeuristique = Math.sqrt(Math.pow(bateau.getX() - autreBateau.getY(), 2) + Math.pow(bateau.getX() - autreBateau.getY(), 2));

                if (distanceHeuristique < distanceMin) {
                    distanceMin = distanceHeuristique;
                    plusProche = autreBateau;
                }
            }
        }

        return plusProche;
    }
    public void startBateauTasks() {
        // Pour chaque pêcheur
        for (Bateau bateau : bateauxPecheurs) {
            Pecheur pecheur = (Pecheur) bateau;
            PointPeche pointPecheProche = pecheur.getPointCible();
            new BateauTask(bateau, this, pointPecheProche).execute();
        }

        // Pour chaque pirate
        for (Bateau bateau : bateauxPirate) {
            Bateau cible = trouverBateauPlusProche(bateau, bateauxPecheurs);
            new BateauTask(bateau, this, cible).execute();
        }

        // Pour chaque marine
        for (Bateau bateau : bateauxMarine) {
            Bateau cible = trouverBateauPlusProche(bateau, bateauxPirate);
            new BateauTask(bateau, this, cible).execute();
        }
    }

    // Méthode pour obtenir tous les obstacles (bateaux)
    public static List<Bateau> getObstacles(Bateau bateauExclu) {
        List<Bateau> obstacles = new ArrayList<>();

        // Ajoutez tous les bateaux, à l'exception de 'bateauExclu'
        for (Bateau bateau : bateauxPirate) {
            if (bateau != bateauExclu) {
                obstacles.add(bateau);
            }
        }

        for (Bateau bateau : bateauxMarine) {
            if (bateau != bateauExclu) {
                obstacles.add(bateau);
            }
        }

        for (Bateau bateau : bateauxPecheurs) {
            if (bateau != bateauExclu) {
                obstacles.add(bateau);
            }
        }

        return obstacles;
    }


    public static int getLargeurGrille() {
        return LARGEUR_GRILLE;
    }

    public static int getHauteurGrille() {
        return HAUTEUR_GRILLE;
    }


    public Image getExplosionImage() {
        return explosionImage;
    }

    public boolean isExplosionActive() {
        return explosionActive;
    }

    public void setExplosionActive(boolean explosionActive) {
        this.explosionActive = explosionActive;
    }

    public int getExplosionX() {
        return explosionX;
    }

    public void setExplosionX(int explosionX) {
        this.explosionX = explosionX;
    }

    public int getExplosionY() {
        return explosionY;
    }

    public void setExplosionY(int explosionY) {
        this.explosionY = explosionY;
    }

    public long getExplosionDebut() {
        return explosionDebut;
    }

    public void setExplosionDebut(long explosionDebut) {
        this.explosionDebut = explosionDebut;
    }

    public VitesseSlider getVitesseSlider() {
        return vitesseSlider;
    }

    public List<PointPeche> getPointsPeche() {
        return pointsPeche;
    }

    public List<Bateau> getBateauxPirate() {
        return bateauxPirate;
    }

    public List<Bateau> getBateauxMarine() {
        return bateauxMarine;
    }

    public List<Bateau> getBateauxPecheurs() {
        return bateauxPecheurs;
    }



}




