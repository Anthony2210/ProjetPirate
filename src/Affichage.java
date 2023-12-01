import javax.swing.*;

public class Affichage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bateaux Collision");
            BateauxCollision bateauxCollision = new BateauxCollision();
            frame.add(bateauxCollision);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
