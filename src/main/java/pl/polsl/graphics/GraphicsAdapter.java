package pl.polsl.graphics;

import pl.polsl.models.Color;
import pl.polsl.models.IVector;

import java.awt.*;

public class GraphicsAdapter {

    private final Graphics graphics;

    public GraphicsAdapter(Graphics graphics){
        this.graphics = graphics;
    }

    public void drawRect(IVector position, IVector size, Color color) {
        graphics.setColor(color.toColor());
        graphics.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

}
