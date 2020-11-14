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

public class ShapesLeftController extends Controllers implements Initializable {

    public TextField strokeWidthTextField;
    public Slider strokeWidthSlider;
    public TextField opacityTextFiled;
    public Slider opacitySlider;
    public ColorPicker colorPicker;
    public javafx.scene.image.ImageView rectTool;
    public javafx.scene.image.ImageView circleTool;
    public javafx.scene.image.ImageView triangleTool;
    public javafx.scene.image.ImageView lineTool;
    //public javafx.scene.image.ImageView pentagonTool;
    //public javafx.scene.image.ImageView hexagonTool;


    ArrayList<ImageView> imageViews = new ArrayList<>();

    public void changeStrokeWidthFromTextField(ActionEvent actionEvent) {
        int strokeWidth = paintModel.updateStrokeWidthFromTextField(strokeWidthTextField.getText());
        strokeWidthSlider.setValue(strokeWidth);
        strokeWidthTextField.setText(String.valueOf(strokeWidth));
    }

    public void changeStrokeWidthFromSlider(MouseEvent mouseEvent) {
        paintModel.updateStrokeWidthFromSlider(strokeWidthSlider.getValue());
        strokeWidthTextField.setText(String.valueOf((int)strokeWidthSlider.getValue()));
    }


    public void colorPicked(ActionEvent actionEvent) {
        paintModel.setPaintingColor(colorPicker.getValue());
    }

    public void changeOpacityFromSlider(MouseEvent mouseEvent) {
        opacityTextFiled.setText(String.valueOf((int)opacitySlider.getValue()));
        paintModel.updateOpacityFromSlider(opacitySlider.getValue());
    }


    public void changeOpacityFromTextField(ActionEvent actionEvent) {
        int opacity = paintModel.updateOpacityFromTextField(opacityTextFiled.getText());
        opacitySlider.setValue(opacity);
        opacityTextFiled.setText(String.valueOf(opacity));
    }

    public void rectSelected(MouseEvent mouseEvent) {
        setEffect(0);
        paintModel.setIsToolSelected(4);
    }

    public void circleSelected(MouseEvent mouseEvent) {
        setEffect(1);
        paintModel.setIsToolSelected(5);
    }

    public void triangleSelected(MouseEvent mouseEvent) {
        setEffect(2);
        paintModel.setIsToolSelected(6);
    }

    public void lineSelected(MouseEvent mouseEvent) {
        setEffect(3);
        paintModel.setIsToolSelected(7);
    }

    public void pentagonSelected(MouseEvent mouseEvent) {
        setEffect(4);
        paintModel.setIsToolSelected(8);
    }

    public void hexagonSelected(MouseEvent mouseEvent) {
        setEffect(5);
        paintModel.setIsToolSelected(9);
    }

    void setShape() {
        isShape = true;
        isBrush = false;
        isText = false;
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
        opacitySlider.setValue(opacity);
        opacityTextFiled.setText(String.valueOf(opacity));
        setShape();
        rectSelected(null);

        imageViews.add(rectTool);
        imageViews.add(circleTool);
        imageViews.add(triangleTool);
        imageViews.add(lineTool);
        //imageViews.add(pentagonTool);
        //imageViews.add(hexagonTool);
        setEffect(0);
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
}
