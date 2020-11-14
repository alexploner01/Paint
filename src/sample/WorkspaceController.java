package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import sample.models.PaintModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static sample.Main.paintModel;

public class WorkspaceController extends Controllers implements Initializable {

    Main main;

    @FXML
    public MenuItem openMenuItem;
    public MenuItem saveAsMenuItem;
    public MenuItem closeMenuItem;
    public MenuButton fileMenu;
    public ImageView saveButton;
    public ImageView undoButton;
    public ImageView redoButton;
    public ImageView toolsButton;
    public ImageView shapesButton;
    public ImageView textAreaButton;
    public Canvas drawPaneCenter;

    ArrayList<ImageView> imageViews = new ArrayList<>();

    Point2D startPoint;
    Point2D endPoint;
    private AnimationTimer loop;

    ArrayList<ColorDot> colorDots = new ArrayList<>();
    static File datei = null;
    public static Image openFromStartViewImage;
    private boolean isUpToDate;

    public void saveFile(MouseEvent mouseEvent) {

        if (datei == null) {
            saveAs(null);
        } else {
            writeInFile(datei);
        }
        isUpToDate = true;
    }

    private void writeInFile(File datei) {
        try {
            WritableImage writableImage = new WritableImage((int) drawPaneCenter.getWidth(), (int) drawPaneCenter.getHeight());
            drawPaneCenter.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", datei);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extensionFilter1 = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().addAll(extensionFilter, extensionFilter1);

        File file = fileChooser.showOpenDialog(Main.mainStroyBoard);

        if (file != null) {
            Image image = null;
            try {
                image = new Image(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            closeCurrentWindow(null);

            drawPaneCenter.getGraphicsContext2D().drawImage(image, 0, 0);

        }

    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void saveAs(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(Main.mainStroyBoard);

        if (file != null) {

            if (datei == null) {
                datei = file;
            }
            writeInFile(file);
        }
        isUpToDate = true;
    }

    public void undo(MouseEvent mouseEvent) {

    }

    public void redo(MouseEvent mouseEvent) {

    }


    public void openToolView(MouseEvent mouseEvent) {
        setEffect(0);
        System.out.println("brushesLeft loaded");
        Main.updateLeftSide("brushesLeft.fxml");
    }

    public void openShapesView(MouseEvent mouseEvent) {
        setEffect(1);
        System.out.println("shapesLeftView loaded");
        Main.updateLeftSide("shapesLeftView.fxml");
    }

    public void openTextView(MouseEvent mouseEvent) {
        setEffect(2);
        System.out.println("textLeftView loaded");
        Main.updateLeftSide("textLeftView.fxml");
    }

    public void setMain(Main main) {
        this.main = main;
    }


    public void mousePressedOrRelasedOnDrawPaneCenter(MouseEvent dragEvent) {
        if (isShape) {
            drawShapes(dragEvent.getX(), dragEvent.getY());
        } else if (isText) {
            if (dragEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                drawText(dragEvent.getX(), dragEvent.getY(), paintModel.text);
            }
        }
        isUpToDate = false;
    }

    public void drawText(double x, double y, String text) {
        drawPaneCenter.getGraphicsContext2D().setGlobalAlpha(paintModel.opacity);
        drawPaneCenter.getGraphicsContext2D().setFill(paintModel.getPaintingColor());
        drawPaneCenter.getGraphicsContext2D().setFont(new Font(paintModel.fontSize));
        drawPaneCenter.getGraphicsContext2D().fillText(text, x, y);
    }

    public void drawShapes(double x, double y) {
        if (!paintModel.isMousePressedFirstTime) {
            startPoint = new Point2D(x, y);
            paintModel.isMousePressedFirstTime = true;
            System.out.println("first");
        } else {
            endPoint = new Point2D(x, y);
            if (paintModel.getSelectedTool() == 4) {
                drawOnCanvas(new Rectangle());
            } else if (paintModel.getSelectedTool() == 5) {
                drawOnCanvas(new Circle());
            } else if (paintModel.getSelectedTool() == 6) {
                drawOnCanvas(new javafx.scene.shape.Polygon(3.0, 2.0, 2.1));
            } else if (paintModel.getSelectedTool() == 7) {
                drawOnCanvas(new Line());
            } else if (paintModel.getSelectedTool() == 8) {
                drawOnCanvas(new Polygon(2, 2, 2, 2, 2));
            } else if (paintModel.getSelectedTool() == 9) {

            }
            paintModel.isMousePressedFirstTime = false;
        }
    }


    private void drawOnCanvas(Shape shape) {

        GraphicsContext graphicsContext = drawPaneCenter.getGraphicsContext2D();

        graphicsContext.setGlobalAlpha(paintModel.opacity);

        if (shape instanceof Rectangle) {
            graphicsContext.setFill(paintModel.getPaintingColor());

            try {
                if (startPoint.getX() < endPoint.getX() && startPoint.getY() < endPoint.getY()) {
                    graphicsContext.fillRect(startPoint.getX(), startPoint.getY(), (int) (endPoint.getX() - startPoint.getX()), (int) (endPoint.getY() - startPoint.getY()));
                } else if (startPoint.getX() > endPoint.getX() && startPoint.getY() > endPoint.getY()) {
                    graphicsContext.fillRect(endPoint.getX(), endPoint.getY(), (int) (startPoint.getX() - endPoint.getX()), (int) (startPoint.getY() - endPoint.getY()));
                } else if (startPoint.getX() > endPoint.getX() && endPoint.getY() > startPoint.getY()) {
                    graphicsContext.fillRect(endPoint.getX(), endPoint.getY() - (int) (endPoint.getY() - startPoint.getY()), (int) (startPoint.getX() - endPoint.getX()), (int) (endPoint.getY() - startPoint.getY()));
                } else if (startPoint.getX() < endPoint.getX() && endPoint.getY() < startPoint.getY()) {
                    graphicsContext.fillRect(startPoint.getX(), startPoint.getY() - (int) (startPoint.getY() - endPoint.getY()), (int) (endPoint.getX() - startPoint.getX()), (int) (startPoint.getY() - endPoint.getY()));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("hehe");
            }
        } else if (shape instanceof Line) {
            graphicsContext.setStroke(paintModel.getPaintingColor());
            graphicsContext.setLineWidth(paintModel.strokeWidth);

            graphicsContext.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

        } else if (shape instanceof Circle) {
            graphicsContext.setFill(paintModel.getPaintingColor());
            if (startPoint.getX() < endPoint.getX() && startPoint.getY() < endPoint.getY()) {
                graphicsContext.fillOval(startPoint.getX(), startPoint.getY(), (int) (endPoint.getX() - startPoint.getX()), (int) (endPoint.getY() - startPoint.getY()));
            } else if (startPoint.getX() > endPoint.getX() && startPoint.getY() > endPoint.getY()) {
                graphicsContext.fillOval(endPoint.getX(), endPoint.getY(), (int) (startPoint.getX() - endPoint.getX()), (int) (startPoint.getY() - endPoint.getY()));
            } else if (startPoint.getX() > endPoint.getX() && endPoint.getY() > startPoint.getY()) {
                graphicsContext.fillOval(endPoint.getX(), endPoint.getY() - (int) (endPoint.getY() - startPoint.getY()), (int) (startPoint.getX() - endPoint.getX()), (int) (endPoint.getY() - startPoint.getY()));
            } else if (startPoint.getX() < endPoint.getX() && endPoint.getY() < startPoint.getY()) {
                graphicsContext.fillOval(startPoint.getX(), startPoint.getY() - (int) (startPoint.getY() - endPoint.getY()), (int) (endPoint.getX() - startPoint.getX()), (int) (startPoint.getY() - endPoint.getY()));
            }
        } else if (shape instanceof Polygon) {
            graphicsContext.setFill(paintModel.getPaintingColor());
            if (((Polygon) shape).getPoints().size() == 3) {
                System.out.println("hohohooh");
                double[] xCoordiantes = {startPoint.getX(), endPoint.getX(), startPoint.getX() + (endPoint.getX() - startPoint.getX()) / 2};
                double[] yCoordiantes = {endPoint.getY(), endPoint.getY(), startPoint.getY()};
                graphicsContext.fillPolygon(xCoordiantes, yCoordiantes, 3);
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hoi: " + drawPaneCenter);

        paintModel = new PaintModel(drawPaneCenter);
        //paintModel.drawWithTool(null);

        GraphicsContext graphicsContext = drawPaneCenter.getGraphicsContext2D();
        graphicsContext.setFill(javafx.scene.paint.Color.WHITE);
        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, paintModel.SCENE_WIDTH, paintModel.SCENE_HEIGHT);

        startAnimation();

        drawPaneCenter.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            drawPaneCenter.setCursor(Cursor.CROSSHAIR);                                //TODO Image
        });
        drawPaneCenter.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            drawPaneCenter.setCursor(Cursor.DEFAULT);
        });

        drawPaneCenter.addEventFilter(MouseEvent.ANY, e -> {

            if (paintModel.getSelectedTool() < 4 && paintModel.getSelectedTool() != -1) {
                paintModel.mouseLocation = new Point2D(e.getX(), e.getY());

                paintModel.mousePressed = e.isPrimaryButtonDown();
                isUpToDate = false;
            }

        });

        if(openFromStartViewImage != null) {
            drawPaneCenter.getGraphicsContext2D().drawImage(openFromStartViewImage, 0, 0);
        }

        Main.mainStroyBoard.setOnCloseRequest(event -> {
            System.out.println(isUpToDate);
            closeCurrentWindow(event);
        });


        imageViews.add(toolsButton);
        imageViews.add(shapesButton);
        imageViews.add(textAreaButton);
    }

    private void closeCurrentWindow(WindowEvent event) {
        if (WorkspaceController.datei == null || !isUpToDate) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            ButtonType speichern = new ButtonType("Speichern");
            ButtonType nichtspeichern = new ButtonType("Nicht Speichern");
            ButtonType abbrechen = new ButtonType("Abbrechen");

            alert.getButtonTypes().addAll(speichern, nichtspeichern, abbrechen);
            alert.setContentText("Möchten sie ihre Änderungen speichern?");
            Image warning = new Image("Images/warning.png");
            ImageView imageView = new ImageView(warning);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            alert.setGraphic(imageView);
            Optional<ButtonType> result = alert.showAndWait();

            if(!result.isPresent()) {

            } else if(result.get() == speichern) {
                saveFile(null);
            } else if(result.get() == nichtspeichern) {

            } else if(result.get() == abbrechen) {
                if(event != null) {
                    event.consume();
                }
            }
        }
    }

    private void startAnimation() {

        GraphicsContext graphicsContext = drawPaneCenter.getGraphicsContext2D();

        loop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (paintModel.getSelectedTool() != -1) {
                    if (paintModel.mousePressed) {

                        paintModel.isMousePressedFirstTime = false;

                        Image brush = paintModel.createBrush(paintModel.strokeWidth, paintModel.paintingColor);
                        paintModel.brushWidthHalf = brush.getWidth() / 2.0;
                        paintModel.brushHeightHalf = brush.getHeight() / 2.0;


                        graphicsContext.drawImage(brush, paintModel.mouseLocation.getX() - paintModel.brushWidthHalf, paintModel.mouseLocation.getY() - paintModel.brushHeightHalf);


                        //Enable this and the drawn Lines will be much smoother
                        //Also disable the line above.
                        //bresenhamLine(paintModel.prevMouseLocation.getX() - paintModel.brushWidthHalf / 2, paintModel.prevMouseLocation.getY() - paintModel.brushHeightHalf / 2, paintModel.mouseLocation.getX() - paintModel.brushWidthHalf / 2, paintModel.mouseLocation.getY() - paintModel.brushHeightHalf / 2);

                        paintModel.pressure += paintModel.pressureDelay;
                        if (paintModel.pressure > 1) {
                            paintModel.pressure = 1;
                        } else {
                            paintModel.pressure = 0;
                        }
                    }

                    paintModel.prevMouseLocation = new Point2D(paintModel.mouseLocation.getX(), paintModel.mouseLocation.getY());
                }
            }
        };


        loop.start();

    }

    public void bresenhamLine(double x0, double y0, double x1, double y1) {
        GraphicsContext graphicsContext = drawPaneCenter.getGraphicsContext2D();
        double dx = Math.abs(x1 - x0), sx = x0 < x1 ? 1. : -1.;
        double dy = -Math.abs(y1 - y0), sy = y0 < y1 ? 1. : -1.;
        double err = dx + dy, e2; /* error value e_xy */
        while (true) {

            int variation = (int) (paintModel.pressure * (paintModel.brushVariations.length - 1));
            Image brushVariation = paintModel.brushVariations[variation];

            graphicsContext.setGlobalAlpha(paintModel.pressure);
            graphicsContext.drawImage(brushVariation, (x0 - paintModel.strokeWidth / 2.0), (y0 - paintModel.strokeWidth / 2.0));

            if (x0 == x1 && y0 == y1)
                break;
            e2 = 2. * err;
            if (e2 > dy) {
                err += dy;
                x0 += sx;
            } /* e_xy+e_x > 0 */
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            } /* e_xy+e_y < 0 */
        }
    }


    public void clearAll(ActionEvent actionEvent) {
        drawPaneCenter.getGraphicsContext2D().clearRect(0,0, drawPaneCenter.getWidth(), drawPaneCenter.getHeight());
        GraphicsContext graphicsContext = drawPaneCenter.getGraphicsContext2D();
        graphicsContext.setFill(javafx.scene.paint.Color.WHITE);
        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, paintModel.SCENE_WIDTH, paintModel.SCENE_HEIGHT);
    }

    private void setEffect(int index) {
        for (int i = 0; i < imageViews.size(); i++) {
            if(i == index) {
                imageViews.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255, 0, 0, 0.8), 10, 0, 0, 0);");
            } else {
                imageViews.get(i).setStyle("");
            }
        }
    }
}
