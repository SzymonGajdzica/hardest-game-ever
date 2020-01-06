package pl.polsl.models;

import java.util.Objects;

public class IVector {

    private final int x;
    private final int y;

    public IVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DVector toDVector(){
        return new DVector(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IVector moved(int x, int y){
        return new IVector(this.x + x, this.y + y);
    }

    public IVector copied(){
        return new IVector(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IVector iVector = (IVector) o;
        return x == iVector.x &&
                y == iVector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
