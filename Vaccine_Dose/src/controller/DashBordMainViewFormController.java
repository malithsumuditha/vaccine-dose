package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/16/2021
 **/
public class DashBordMainViewFormController implements Initializable {
    public AnchorPane mainPanel;
    public AnchorPane changingPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Parent parent = null;
        try {
            parent = FXMLLoader.load(this.getClass().getResource("../view/DashBordForm.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);

    }

    public void btnDashBordOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/DashBordForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }
    public void btnRegisterOnAction() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/RegForm.fxml"));
        changingPanel.getChildren().clear();
        changingPanel.getChildren().add(parent);
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {

        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.setIconified(true);
    }
}
