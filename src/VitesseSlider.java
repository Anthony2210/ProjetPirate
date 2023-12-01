import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VitesseSlider {
    private static int vitesseMarine = 1;
    private static int vitessePirates = 1;
    private static int vitessePecheurs= 1;

    private final JSlider sliderMarine;
    private final JSlider sliderPirates;
    private JSlider sliderPecheurs;

    public VitesseSlider(BateauxCollision bateauxCollision) {
        sliderMarine = new JSlider(JSlider.HORIZONTAL, 1, 5, vitesseMarine);
        sliderPirates = new JSlider(JSlider.HORIZONTAL, 1, 5, vitessePirates);
        sliderPecheurs = new JSlider(JSlider.HORIZONTAL, 1, 5, vitessePecheurs);

        sliderMarine.setMajorTickSpacing(1);
        sliderMarine.setPaintTicks(true);
        sliderMarine.setPaintLabels(true);

        sliderPirates.setMajorTickSpacing(1);
        sliderPirates.setPaintTicks(true);
        sliderPirates.setPaintLabels(true);

        sliderPecheurs.setMajorTickSpacing(1);
        sliderPecheurs.setPaintTicks(true);
        sliderPecheurs.setPaintLabels(true);

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

