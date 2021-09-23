/**
 * @author - Hw_Dulanjana
 * @date - 9/9/2021
 */

import com.sun.javafx.application.LauncherImpl;
import controller.StartScreenFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.misc.Launcher;

import javax.swing.*;
import java.io.IOException;

public class Appintializer extends Application {

    public static Stage primaryStage = null;
    public static Scene primaryScene = null;

    @Override
    public void init() throws Exception {
        StartScreenFormController init = new StartScreenFormController();
        init.checkFunction();
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Appintializer.class,AppintializerPreloader.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Appintializer.primaryStage = primaryStage;


    }
}
