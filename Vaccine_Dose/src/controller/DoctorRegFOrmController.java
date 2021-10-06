package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author - Hw_Dulanjana
 * @date - 9/20/2021
 */
public class DoctorRegFOrmController {
    public JFXTextField txtDName;
    public JFXRadioButton rdbDMale;
    public JFXRadioButton rdbDFemale;
    public JFXButton btnDAdd;
    public JFXTextField txtDContact;
    public JFXTextField txtDNic;
    public Label lblDoctorID;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnReset;
    public JFXPasswordField txtAccPassword;
    public JFXPasswordField txtCPassword;
    public JFXButton btnFileChooser;
    public File file;
    public ImageView imgImageView;
    public Label lblImagePath;

    public void initialize(){
        PersonRegFormController.autoGenerateID(lblDoctorID,"doctor","D");
        photoUpload();
    }

    public void rdbDMaleOnAction(ActionEvent actionEvent) {
        rdbDFemale.setStyle("-fx-border-color:null");
        rdbDMale.setStyle("-fx-border-color:null");
        rdbDFemale.setSelected(false);
    }

    public void rdbDFemaleOnAction(ActionEvent actionEvent) {
        rdbDFemale.setStyle("-fx-border-color:null");
        rdbDMale.setStyle("-fx-border-color:null");
        rdbDMale.setSelected(false);
    }

    public void btnDAddOnAction(ActionEvent actionEvent) throws FileNotFoundException {

        Connection connection = DBConnection.getInstance().getConnection();
        String id = lblDoctorID.getText();
        String name = txtDName.getText();
        String contact = txtDContact.getText();
        String nic = txtDNic.getText();
        String password = txtAccPassword.getText();
        String cPassword = txtCPassword.getText();
        String gender = null;

        if (rdbDMale.isSelected()){
            gender = "Male";
        }else if (rdbDFemale.isSelected()){
            gender = "Female";
        }

        if(txtDName.getText().isEmpty()){
            errorAlert("Please Enter Name");
            setBorderColor(txtDName,"red");

        }else if (txtDContact.getText().isEmpty()){
            errorAlert("Please Enter Contact");
            setBorderColor(txtDName,"null");
            setBorderColor(txtDContact,"red");

        }else if(numberCheck(contact)){

            if (txtDNic.getText().isEmpty()){
                errorAlert("Please Enter NIC");
                setBorderColor(txtDNic,"red");
                setBorderColor(txtDContact,"null");
                setBorderColor(txtDName,"null");

            }else if (rdbDMale.isSelected() || rdbDFemale.isSelected()) {

                if (txtAccPassword.getText().isEmpty()){

                    errorAlert("Please Enter Password");
                    txtAccPassword.setStyle("-fx-border-color:red");
                    setBorderColor(txtDContact,"null");
                    setBorderColor(txtDName,"null");
                    setBorderColor(txtDNic,"null");

                }else if (txtCPassword.getText().isEmpty()){

                    errorAlert("Please Enter Confirm Password");
                    txtCPassword.setStyle("-fx-border-color:red");
                    txtAccPassword.setStyle("-fx-border-color:null");
                    setBorderColor(txtDContact,"null");
                    setBorderColor(txtDName,"null");
                    setBorderColor(txtDNic,"null");

                }

                else if (password.equals(cPassword)){

                    txtCPassword.setStyle("-fx-border-color:null");
                    txtAccPassword.setStyle("-fx-border-color:null");

                    if (photoUpload()==null){
                        btnFileChooser.setStyle("-fx-border-color:red");
                        errorAlert("Please choose image File");
                    }
                    else {

                        btnFileChooser.setStyle("-fx-border-color:null");

                        FileInputStream fin = new FileInputStream(photoUpload());

                        int length = (int)file.length();

                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement("insert into doctor values(?,?,?,?,?,?,?)");
                            preparedStatement.setObject(1,id);
                            preparedStatement.setObject(2,name);
                            preparedStatement.setObject(3,contact);
                            preparedStatement.setObject(4,nic);
                            preparedStatement.setObject(5,gender);
                            preparedStatement.setObject(6,password);
                            preparedStatement.setBinaryStream(7,fin,length);

                            int i = preparedStatement.executeUpdate();

                            if (i!=0){
                                confirmAlert("Successfully Added");
                                setReset();
                            }else {
                                errorAlert("Something Error");
                            }

                        } catch (SQLException throwables) {
                            errorAlert("Something Error");
                            throwables.printStackTrace();
                        }

                    }

                }




                else {
                    errorAlert("Please enter same Password for confirm Password");
                    txtCPassword.setStyle("-fx-border-color:red");
                    txtAccPassword.setStyle("-fx-border-color:red");
                }
            } else {
                errorAlert("Please select gender");
                rdbDFemale.setStyle("-fx-border-color:red");
                rdbDMale.setStyle("-fx-border-color:red");
                setBorderColor(txtDNic,"null");

            }


        }else {

            errorAlert("Please enter valid number");

        }




    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnDResetOnAction(ActionEvent actionEvent) {
        setReset();
    }

    public void confirmAlert(String displayAlert){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,displayAlert);
        alert.showAndWait();
    }

    public void errorAlert(String displayAlert){
        Alert alert = new Alert(Alert.AlertType.ERROR,displayAlert);
        alert.showAndWait();
    }

    public void setReset(){
        PersonRegFormController.autoGenerateID(lblDoctorID,"doctor","D");
        txtDName.clear();
        txtDContact.clear();
        txtDNic.clear();
        rdbDMale.setSelected(false);
        rdbDFemale.setSelected(false);
        txtAccPassword.clear();
        txtCPassword.clear();
        txtDName.requestFocus();
    }

    public void setBorderColor(JFXTextField name,String color){
        name.setStyle("-fx-border-color:"+color);
    }

    public boolean numberCheck(String num){

        boolean b = num.charAt(0) == '0' && num.length() == 10 && num.matches("[0-9]+");
        return b;
    }

    public void setTextBordersNull(){
        txtDName.setStyle("-fx-border-color:null");
        txtDContact.setStyle("-fx-border-color:null");
        txtDNic.setStyle("-fx-border-color:null");
    }

    public File photoUpload(){
        btnFileChooser.setOnAction((ActionEvent t) ->{
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fc.getExtensionFilters().addAll(ext1,ext2);
            file = fc.showOpenDialog(btnFileChooser.getScene().getWindow());

            BufferedImage bf;

            try {
                bf = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bf,null);
                imgImageView.setImage(image);
                lblImagePath.setText(String.valueOf(file));

                if (file!=null){
                    btnFileChooser.setStyle("-fx-border-color:null");
                    btnFileChooser.setStyle("-fx-background-color:#3742fa");
                }

            } catch (IOException ex) {
                Logger.getLogger(VaccinationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } );

        return file;
    }

}
