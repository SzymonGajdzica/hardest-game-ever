package pl.polsl.models;

import java.util.Objects;

public class DVector {

    private double x;
    private double y;

    public DVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DVector subtract(DVector DVector){
        return new DVector(x - DVector.getX(), y - DVector.getY());
    }

    public IVector toIVector(){
        return new IVector((int) x, (int) y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double x, double y){
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DVector dVector = (DVector) o;
        return Math.abs(Math.abs(dVector.x) - Math.abs(x)) < 1.0 &&
                Math.abs(Math.abs(dVector.y) - Math.abs(y)) < 1.0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void copy(DVector DVector) {
        x = DVector.getX();
        y = DVector.getY();
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
