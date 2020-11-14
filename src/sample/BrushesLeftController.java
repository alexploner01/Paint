package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.Main.paintModel;

public class BrushesLeftController extends Controllers implements Initializable {

    public TextField strokeWidthTextField;
    public Slider strokeWidthSlider;
    public ColorPicker colorPicker;
    public TextField opacityTextFiled;
    public Slider opacitySlider;
    public ImageView pencilTool;
    public ImageView smallBrushTool;
    public ImageView largeBrushTool;
    public ImageView rubberTool;

    ArrayList<ImageView> imageViews = new ArrayList<>();

    public void pencilSelected(MouseEvent mouseEvent) {
        System.out.println("Pencil selected");
        setBrush();
        paintModel.setIsToolSelected(0);
        paintModel.createBrushVariations();
        setEffect(0);
    }

    public void smallBrushSelected(MouseEvent mouseEvent) {
        System.out.println("small brush selected");
        setBrush();
        paintModel.setIsToolSelected(1);
        paintModel.createBrushVariations();
        setEffect(1);
    }

    public void largeBrushSelected(MouseEvent mouseEvent) {
        System.out.println("large brush selected");
        setBrush();
        paintModel.setIsToolSelected(2);
        paintModel.createBrushVariations();
        setEffect(2);
    }

    public void RubberSelected(MouseEvent mouseEvent) {
        System.out.println("paint bucket selected");
        setBrush();
        paintModel.setIsToolSelected(3);
        paintModel.createBrushVariations();
        setEffect(3);
    }

    private void setEffect(int index) {
        for (int i = 0; i < imageViews.size(); i++) {
            if(i == index) {
                imageViews.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255, 0, 0, 1), 10, 0.2, 0, 0);");
            } else {
                imageViews.get(i).setStyle("");
            }
        }
    }


    public void changeStrokeWidthFromTextField(ActionEvent actionEvent) {
        int strokeWidth = paintModel.updateStrokeWidthFromTextField(strokeWidthTextField.getText());
        strokeWidthSlider.setValue(strokeWidth);
        strokeWidthTextField.setText(String.valueOf(strokeWidth));
        paintModel.createBrushVariations();
    }

    public void changeStrokeWidthFromSlider(MouseEvent mouseEvent) {
        paintModel.updateStrokeWidthFromSlider(strokeWidthSlider.getValue());
        strokeWidthTextField.setText(String.valueOf((int)strokeWidthSlider.getValue()));
        paintModel.createBrushVariations();
    }


    public void colorPicked(ActionEvent actionEvent) {
        paintModel.setPaintingColor(colorPicker.getValue());
        paintModel.createBrushVariations();
    }

    public void changeOpacityFromSlider(MouseEvent mouseEvent) {
        opacityTextFiled.setText(String.valueOf((int)opacitySlider.getValue()));
        paintModel.updateOpacityFromSlider(opacitySlider.getValue());
        paintModel.createBrushVariations();
    }


    public void changeOpacityFromTextField(ActionEvent actionEvent) {
        int opacity = paintModel.updateOpacityFromTextField(opacityTextFiled.getText());
        opacitySlider.setValue(opacity);
        opacityTextFiled.setText(String.valueOf(opacity));
        paintModel.createBrushVariations();
    }

    void setBrush() {
        isText = false;
        isShape = false;
        isBrush = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorPicker.setValue(paintModel.getPaintingColor());
        strokeWidthSlider.setValue(paintModel.getStrokeWidth());
        strokeWidthTextField.setText(String.valueOf(paintModel.getStrokeWidth()));
        int opacity = (int)paintModel.getOpacity();
        if(paintModel.getOpacity() <= 1) {
            opacity = (int)(paintModel.getOpacity()*100);
        }
        opacityTextFiled.setText(String.valueOf(opacity));
        opacitySlider.setValue(opacity);
        pencilSelected(null);
        setBrush();

        imageViews.add(pencilTool);
        imageViews.add(smallBrushTool);
        imageViews.add(largeBrushTool);
        imageViews.add(rubberTool);
        setEffect(0);
    }
}
