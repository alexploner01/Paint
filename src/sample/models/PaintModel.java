package sample.models;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class PaintModel {


    /**
     * Drawing
     */
    public static double SCENE_WIDTH = 1280;
    public static double SCENE_HEIGHT = 720;

    public Canvas canvas;

    public Point2D mouseLocation = new Point2D(0, 0);
    public boolean mousePressed = false;
    public Point2D prevMouseLocation = new Point2D(0, 0);


    double brushMaxSize = 30;

    public double pressure = 1;
    public double pressureDelay = 0.04;
    public double pressureDirection = 1;

    public double strokeTimeMax = 1;
    public double strokeTime = 0;
    public double strokeTimeDelay = 0.07;

    public Image[] brushVariations = new Image[256];

    public Color paintingColor = Color.GREEN;

    public boolean isMousePressedFirstTime;

    public Integer fontSize = 12;


    public String text;

    /**
     * Choose Tool
     */
    private boolean[] isToolSelected = new boolean[11];

    public int strokeWidth = 10;
    //Image brush = createBrush(strokeWidth, Color.CHOCOLATE);
    public double brushWidthHalf = strokeWidth / 2.0;
    public double brushHeightHalf = strokeWidth / 2.0;


    public double opacity = 1;


    public PaintModel(Canvas canvas) {
        this.canvas = canvas;
        System.out.println(canvas);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public double getOpacity() {
        return opacity;
    }

    public Color getPaintingColor() {
        return paintingColor;
    }

    public void setIsToolSelected(int indexOfSelectedTool) {

        for (int i = 0; i < isToolSelected.length; i++) {
            if (i == indexOfSelectedTool) {
                isToolSelected[i] = true;
            } else {
                isToolSelected[i] = false;
            }
        }
    }

    public int getSelectedTool() {
        int i;
        for (i = 0; i < isToolSelected.length; i++) {
            if (isToolSelected[i]) {
                break;
            }
        }
        if (i == isToolSelected.length) {
            return -1;
        }
        return i;
    }

    public int updateStrokeWidthFromTextField(String strokeWidth) {

        try {
            Integer strokeWidthInteger = Integer.parseInt(strokeWidth);
            if (strokeWidthInteger > 0 && strokeWidthInteger <= 50) {
                this.strokeWidth = strokeWidthInteger;
                System.out.println("StrokeWidth changed");
            } else {
                return this.strokeWidth;
            }
        } catch (NumberFormatException e) {
            System.out.println("changeStrokeWidthFromTextField Fehler beim Parsen");
        }
        return this.strokeWidth;
    }

    public void updateStrokeWidthFromSlider(double strokeWidth) {
        this.strokeWidth = (int) strokeWidth;

        System.out.println("StrokeWidth changed");
    }

    public Integer updateFontSizeFromTextField(String fontsize) {
        try {
            Integer fontSize = Integer.parseInt(fontsize);
            if(fontSize > 0 && fontSize <= 100) {
              this.fontSize = fontSize;
            }
        } catch (NumberFormatException e) {
            System.out.println("changeFontSizeFromTextField Fehler beim Parsen");
        }
        return this.fontSize;
    }


    public void createBrushVariations() {

        for (int i = 0; i < brushVariations.length; i++) {

            double size = (strokeWidth - 1) / (double) brushVariations.length * (double) i + 1;
            brushVariations[i] = createBrush(size, paintingColor);
        }

    }


    public Image createBrush(double radius, Color color) {

        // create gradient image with given color

        RadialGradient gradient1 = null;

        Shape brush = null;
        if (getSelectedTool() == 0) {
            brush = new Circle(radius);
            gradient1 = new RadialGradient(0, 0, 0, 0, radius, false, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 1)), new Stop(1, color.deriveColor(1, 0.1, 1, 0)));
            brush.setFill(gradient1);

        } else if (getSelectedTool() == 1) {
            brush = new Circle(radius);
            gradient1 = new RadialGradient(0, 0, 0, 0, radius, false, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 0.3)), new Stop(1, color.deriveColor(1, 1, 1, 0)));
            brush.setFill(gradient1);
        } else if (getSelectedTool() == 2) {
            brush = new Rectangle(2 * radius, 2 * radius);
            LinearGradient rect = new LinearGradient(mouseLocation.getX() - radius, mouseLocation.getY() - radius, mouseLocation.getX() + radius, mouseLocation.getY() + radius, false, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 0.3)), new Stop(1, color.deriveColor(1, 1, 1, 0)));
            brush.setFill(rect);
        } else if (getSelectedTool() == 3) {
            brush = new Circle(radius);
            color = Color.WHITE;
            gradient1 = new RadialGradient(0, 0, 0, 0, radius, true, CycleMethod.NO_CYCLE, new Stop(0, color.deriveColor(1, 1, 1, 1)), new Stop(1, color.deriveColor(1, 1, 1, 0)));
            brush.setFill(gradient1);
        } else {
            System.out.println("Error: no Tool selected");
        }

        brush.setOpacity(opacity);


        // create image
        return createImage(brush);

    }

    public Image createImage(Node node) {

        WritableImage wi;

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) node.getBoundsInLocal().getWidth();
        int imageHeight = (int) node.getBoundsInLocal().getHeight();

        wi = new WritableImage(imageWidth, imageHeight);
        node.snapshot(parameters, wi);

        return wi;

    }

    public void setPaintingColor(Color paintingColor) {
        this.paintingColor = paintingColor;
    }

    public void updateOpacityFromSlider(double opacity) {
        this.opacity = opacity / 100;
    }

    public int updateOpacityFromTextField(String opacity) {
        try {
            Double opacityDouble = Double.parseDouble(opacity);
            this.opacity = opacityDouble / 100;
        } catch (NumberFormatException e) {
            System.out.println("updateOpacityFromTextField Fehler beim Parsen");
        }
        return (int) (this.opacity * 100);
    }
}
