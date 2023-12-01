import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Noeud implements Comparable<Noeud> {
    private final int x;
    private final int y;
    private Noeud parent;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Noeud getParent() {
        return parent;
    }

    public void setParent(Noeud parent) {
        this.parent = parent;
    }

    public Noeud(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Noeud> getNeighbors() {
        List<Noeud> neighbors = new ArrayList<>();
        neighbors.add(new Noeud(x + 1, y));
        neighbors.add(new Noeud(x - 1, y));
        neighbors.add(new Noeud(x, y + 1));
        neighbors.add(new Noeud(x, y - 1));
        return neighbors;
    }

    @Override
    public int compareTo(Noeud autre) {
        return Integer.compare(this.x + this.y, autre.x + autre.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Noeud noeud = (Noeud) obj;
        return x == noeud.x && y == noeud.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
