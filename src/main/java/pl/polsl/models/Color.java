package pl.polsl.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Color {

    private final int r;
    private final int g;
    private final int b;

    public static final Color background = new Color(255, 255, 255);
    public static final Color border = new Color(0, 0, 0);
    public static final Color end = new Color(255, 242, 0);
    public static final Color player = new Color(237, 28, 36);
    public static final Color subEnd = new Color(255, 127, 39);
    public static final Set<Color> specialColors = new HashSet<>(Arrays.asList(background, border, end, player, subEnd));
    public static final Set<Color> nonMovableColors = new HashSet<>(Arrays.asList(border, end, subEnd));

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getSum(){
        return r + g + b;
    }

    public java.awt.Color toColor(){
        return new java.awt.Color(r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return r == color.r &&
                g == color.g &&
                b == color.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "(" + r + "," + g + "," + b + ")";
    }

}
