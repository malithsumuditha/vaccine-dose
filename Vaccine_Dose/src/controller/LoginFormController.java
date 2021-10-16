package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
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
    String userName;
    Blob userImage;

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {

        signin(actionEvent);


    }
    public void signin(ActionEvent actionEvent){
        if (txtID.getText().isEmpty()){
            DoctorRegFOrmController.errorAlert("Please enter userID");
            txtID.requestFocus();
            txtID.setStyle("-fx-border-color:red");

        }else if (txtPassword.getText().isEmpty()){
            DoctorRegFOrmController.errorAlert("Please enter Password");
            txtPassword.requestFocus();
            txtID.setStyle("-fx-border-color:null");
            txtPassword.setStyle("-fx-border-color:red");
        }else{

            String text = txtID.getText();
            String substring = text.substring(0, 1);

            if (substring.equals("P")){

                loginUser(actionEvent,"phireg","PID","Password","PName"," PHI ","image");

            }else if (substring.equals("D")){

                loginUser(actionEvent,"doctor","id","password","name"," Doctor ","image");
            }
            else if (substring.equals("A")){
                loginUser(actionEvent,"adminreg","id","Password","AName"," Admin ","image");
            }else {
                loginError();
            }

        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        signin(actionEvent);
    }


    public void loginUser(ActionEvent actionEvent,String tableName,String uId,String uPassword,String name,String userType,String image){
        String userID = txtID.getText();
        String password = txtPassword.getText();


        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select "+uId+ ","+uPassword+","+name+","+image+ " from "+tableName+" where "+uId+"=? and "+uPassword+"=?");
            preparedStatement.setObject(1,userID);
            preparedStatement.setObject(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()){

                userName = userType+resultSet.getString(3);
                userImage = resultSet.getBlob(4);
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




    public void loading(ActionEvent actionEvent) throws IOException {
        String text = txtID.getText();
        String substring = text.substring(0, 1);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBordMainViewForm.fxml"));
        root = loader.load();

        DashBordMainViewFormController dashBordMainViewFormController =loader.getController();
        dashBordMainViewFormController.lblTest.setText(substring);
        dashBordMainViewFormController.lblUserName.setText("Hello"+userName);

//        if (userImage==null){
//            dashBordMainViewFormController.setUserImage(null);
//            return;
//        }
        try {
            InputStream binaryStream = userImage.getBinaryStream();

            dashBordMainViewFormController.setUserImage(binaryStream);


//            Image image = new Image(binaryStream);
//            imgImageView.setImage(image);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




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
