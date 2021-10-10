package controller;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * @author - Hw_Dulanjana
 * @date - 9/9/2021
 */
public class LoginFormController {
    public PasswordField txtPassword;
    public TextField txtID;
    public AnchorPane root;
    public Button btnSignIn;


    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {

        String text = txtID.getText();
        String substring = text.substring(0, 1);

        if (substring.equals("P")){

            loginUser(actionEvent,"phireg","PID","Password");

        }else if (substring.equals("D")){

            loginUser(actionEvent,"doctor","id","password");
        }
        else {
            loginUser(actionEvent,"adminreg","id","Password");
        }


    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }


    public void loginUser(ActionEvent actionEvent,String tableName,String uId,String uPassword){
        String userID = txtID.getText();
        String password = txtPassword.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select "+uId+ ","+uPassword+ " from "+tableName+" where "+uId+"=? and "+uPassword+"=?");
            preparedStatement.setObject(1,userID);
            preparedStatement.setObject(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()){

                borderNull();
                loading(actionEvent);
            }
            else {
                loginError();
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


    }


    public void loginAdmin(){

    }

    public void loading(ActionEvent actionEvent) throws IOException {
        String text = txtID.getText();
        String substring = text.substring(0, 1);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBordMainViewForm.fxml"));
        root = loader.load();

        DashBordMainViewFormController dashBordMainViewFormController =loader.getController();
        dashBordMainViewFormController.lblTest.setText(substring);



        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//        Stage primaryStage= new Stage();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Vaccination");

    }

    public void loginError(){
        DoctorRegFOrmController.errorAlert("Wrong UserName or Password");
        txtPassword.clear();
        txtID.clear();
        txtID.setStyle("-fx-border-color:red");
        txtPassword.setStyle("-fx-border-color:red");
        txtID.requestFocus();
    }

    public void borderNull(){
        txtID.setStyle("-fx-border-color:null");
        txtPassword.setStyle("-fx-border-color:null");
    }
}
