package sample;

import javafx.scene.image.Image;

import java.io.Serializable;

public class ColorDot implements Serializable {

    transient Image brush;
    double x;
    double y;

    public ColorDot(Image brush, double x, double y) {
        this.brush = brush;
        this.x = x;
        this.y = y;
    }
}
