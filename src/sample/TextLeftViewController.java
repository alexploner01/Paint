package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static sample.Main.paintModel;

public class TextLeftViewController extends Controllers implements Initializable {

    public TextField opacityTextFiled;
    public Slider opacitySlider;
    public ColorPicker colorPicker;
    public TextArea textArea;
    public ImageView textTool;
    public HBox hbox;
    public Slider fontSlider;
    public TextField fontTextField;


    ArrayList<ImageView> imageViews = new ArrayList<>();

    public void changeOpacityFromSlider(MouseEvent mouseEvent) {          //TODO fá che ara vai con düc dui i modi
        opacityTextFiled.setText(String.valueOf((int)opacitySlider.getValue()));
        paintModel.updateOpacityFromSlider(opacitySlider.getValue());
    }

    public void changeOpacityFromTextField(ActionEvent actionEvent) {
        int opacity = paintModel.updateOpacityFromTextField(opacityTextFiled.getText());
        opacitySlider.setValue(opacity);
        opacityTextFiled.setText(String.valueOf(opacity));
    }

    public void colorPicked(ActionEvent actionEvent) {
        paintModel.setPaintingColor(colorPicker.getValue());
    }


    public void texAreaButtonPressed(MouseEvent mouseEvent) {
        paintModel.setIsToolSelected(10);
    }

    void setText() {
        isShape = false;
        isBrush = false;
        isText = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int opacity = (int)paintModel.getOpacity();
        if(paintModel.getOpacity() <= 1) {
            opacity = (int)(paintModel.getOpacity()*100);
        }
        opacitySlider.setValue(opacity);
        opacityTextFiled.setText(String.valueOf(opacity));
        colorPicker.setValue(paintModel.getPaintingColor());
        paintModel.text = null;
        setText();
        texAreaButtonPressed(null);

        imageViews.add(textTool);
        setEffect(0);

    }

    public void writeInTextAreaDetected(KeyEvent keyEvent) {
        paintModel.text = textArea.getText();
    }

    private void setEffect(int index) {
        for (int i = 0; i < imageViews.size(); i++) {
            if(i == index) {
                imageViews.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255, 0, 0, 1), 10, 0, 0, 0);");
            } else {
                imageViews.get(i).setStyle("");
            }
        }

    }

    public void changeFontFromSlider(MouseEvent mouseEvent) {
        paintModel.fontSize = (int) fontSlider.getValue();
        fontTextField.setText(String.valueOf((int)fontSlider.getValue()));
    }

    public void changeFontFromTextField(ActionEvent actionEvent) {
        Integer fontSize = paintModel.updateFontSizeFromTextField(fontTextField.getText());
        fontSlider.setValue(fontSize);
    }
}
