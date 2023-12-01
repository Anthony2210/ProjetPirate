import javax.swing.*;
import java.awt.*;

public class ControlesVitesseFrame extends JFrame {
    private VitesseSlider vitesseSlider;

    public ControlesVitesseFrame(VitesseSlider vitesseSlider) {
        this.vitesseSlider = vitesseSlider;
        setTitle("Contrôles de Vitesse des Bateaux");
        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Vitesse Marine:"));
        panel.add(vitesseSlider.getSliderMarine());
        panel.add(new JLabel("Vitesse Pirates:"));
        panel.add(vitesseSlider.getSliderPirates());
        panel.add(new JLabel("Vitesse Pêcheurs:"));
        panel.add(vitesseSlider.getSliderPecheurs());

        add(panel);
    }
}
