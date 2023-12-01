import javax.swing.*;

public class VitesseSlider {
    private static int vitesseMarine = 1;
    private static int vitessePirates = 1;
    private static int vitessePecheurs = 1;

    private final JSlider sliderMarine;
    private final JSlider sliderPirates;
    private final JSlider sliderPecheurs;

    public VitesseSlider(BateauxCollision bateauxCollision) {
        sliderMarine = new JSlider(JSlider.HORIZONTAL, 1, 5, vitesseMarine);
        sliderPirates = new JSlider(JSlider.HORIZONTAL, 1, 5, vitessePirates);
        sliderPecheurs = new JSlider(JSlider.HORIZONTAL, 1, 5, vitessePecheurs);

        sliderMarine.addChangeListener(e -> {
            vitesseMarine = sliderMarine.getValue();
            bateauxCollision.repaint();
        });

        sliderPirates.addChangeListener(e -> {
            vitessePirates = sliderPirates.getValue();
            bateauxCollision.repaint();
        });
        sliderPecheurs.addChangeListener(e -> {
            vitessePecheurs = sliderPecheurs.getValue();
            bateauxCollision.repaint();
        });
    }

    public int getVitesseMarine() {
        return vitesseMarine;
    }

    public int getVitessePirates() {
        return vitessePirates;
    }

    public int getVitessePecheurs() {
        return vitessePecheurs;
    }

    public JSlider getSliderMarine() {
        return sliderMarine;
    }

    public JSlider getSliderPirates() {
        return sliderPirates;
    }

    public JSlider getSliderPecheurs() {
        return sliderPecheurs;
    }
}

