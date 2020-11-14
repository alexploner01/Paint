package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.models.PaintModel;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {

    static BorderPane root;
    static Stage mainStroyBoard;
    //static WorkspaceController workspaceController = new WorkspaceController();
    static BrushesLeftController brushesLeftController = new BrushesLeftController();

    static PaintModel paintModel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStroyBoard = primaryStage;
        root = FXMLLoader.load(getClass().getResource("startView.fxml"));
        mainStroyBoard.setTitle("MyPaint");
        mainStroyBoard.setScene(new Scene(root, Toolkit.getDefaultToolkit().getScreenSize().width, (Toolkit.getDefaultToolkit().getScreenSize().height) - 70));


        mainStroyBoard.setMaximized(true);
        mainStroyBoard.show();
    }

    public static void createNewFile() {
        System.out.println("mousePressedOrRelasedOnDrawPaneCenter");
        try {
            root = FXMLLoader.load(Main.class.getResource("workspace2.fxml"));
            mainStroyBoard.setScene(new Scene(root, Toolkit.getDefaultToolkit().getScreenSize().width, (Toolkit.getDefaultToolkit().getScreenSize().height) -70));
            //Thread.sleep(1000);
            /*workspaceController.openToolView( null/* new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));*/
            System.out.println("new file created");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    public static void updateLeftSide(String file) {
        try {
            root.setLeft(FXMLLoader.load(Main.class.getResource(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
