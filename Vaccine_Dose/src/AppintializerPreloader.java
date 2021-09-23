import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import javafx.stage.StageStyle;
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/24/2021
 **/
public class AppintializerPreloader extends Preloader {

    private Stage proloaderStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.proloaderStage = primaryStage;
        Parent parent = FXMLLoader.load(this.getClass().getResource("view/LoadingForm.fxml"));
        Scene scene=new Scene(parent);
        primaryStage.setScene(scene);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();


=======
        primaryStage.centerOnScreen();
        primaryStage.show();
>>>>>>> Stashed changes
=======
        primaryStage.centerOnScreen();
        primaryStage.show();
>>>>>>> Stashed changes
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
       if (info.getType() == StateChangeNotification.Type.BEFORE_START){
           proloaderStage.hide();
       }
    }
}
