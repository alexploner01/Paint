package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;

public class StartViewConroller {

    public Main main = null;

    public void createNewFile(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.createNewFile();
        }
    }

    public void searchFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(Main.mainStroyBoard);

        if (file != null) {
            Image image = null;
            try {
                image = new Image(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            WorkspaceController.openFromStartViewImage = image;
            WorkspaceController.datei = file;
            Main.createNewFile();
        }
    }

}
