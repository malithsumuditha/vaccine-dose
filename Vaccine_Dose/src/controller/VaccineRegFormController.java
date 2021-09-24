package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tm.ViewAllPersonsListTM;
import tm.ViewRegVaccineTM;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author - Hw_Dulanjana
 * @date - 9/18/2021
 */
public class VaccineRegFormController {
    public JFXTextField txtVaccineName;
    public JFXTextField txtMCountry;
    public JFXTextField txtCompany;
    public Label lblVaccineCode;
    public JFXListView<ViewRegVaccineTM> lstVaccineView;
    public Button btnChoose;
    public ImageView imgImageView;
    public Label lblImagePath;
    File file;

    public void initialize(){
        txtVaccineName.requestFocus();
        autoGenarateCode();
        Loadlist();
        photoUpload();
        selectListItem();



    }
    public void btnVaccineAddOnAction(ActionEvent actionEvent) throws FileNotFoundException {
        if(txtVaccineName.getText().isEmpty()){
            ErrorBorderCl(txtVaccineName);
            ErrorMassage("Vaccine name");
            txtVaccineName.clear();
            txtVaccineName.requestFocus();
        }else if(txtMCountry.getText().isEmpty()){
            ErrorBorderCl(txtMCountry);
            ErrorMassage("Manufacture Country");
            txtMCountry.clear();
            txtMCountry.requestFocus();
        }else if(txtCompany.getText().isEmpty()) {
            ErrorBorderCl(txtCompany);
            ErrorMassage("Manufacture Company");
            txtCompany.clear();
            txtCompany.requestFocus();
        }else if (photoUpload()==null){
            btnChoose.setStyle("-fx-border-color:red");
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please Choose Image File");
            alert.showAndWait();
        }else {

            btnChoose.setStyle("-fx-border-color:null");
            NullBorderCl();
            String VName = txtVaccineName.getText();
            String MCountry = txtMCountry.getText();
            String VCompany = txtCompany.getText();
            String VCode = lblVaccineCode.getText();

            FileInputStream fin = new FileInputStream(photoUpload());

            int len = (int)file.length();

            Connection connection = DBConnection.getInstance().getConnection();
            try {

                PreparedStatement preparedStatement = connection.prepareStatement("insert into vaccinereg values(?,?,?,?,?)");
                preparedStatement.setObject(1, VCode);
                preparedStatement.setObject(2, VName);
                preparedStatement.setObject(3, MCountry);
                preparedStatement.setObject(4, VCompany);
                preparedStatement.setBinaryStream(5,fin,len);


                int i = preparedStatement.executeUpdate();
                if (i != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successful Added !! ");
                    alert.showAndWait();
                    textAllClear();
                    txtVaccineName.requestFocus();
                    autoGenarateCode();
                    Loadlist();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Somthing Error !! Please try again.");
                    alert.showAndWait();
                }

            } catch (SQLException throwables) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error !! Please try again. ");
                alert.showAndWait();
                throwables.printStackTrace();
            }


        }
    }
    public void autoGenarateCode(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select vcode from vaccinereg order by vcode desc limit 1");
            boolean ifNext = resultSet.next();

            if(ifNext){
                String oldValue = resultSet.getString(1);
                oldValue =oldValue.substring(1,oldValue.length());
                int newID = Integer.parseInt(oldValue);
                newID=newID+1;
                if(newID<10){
                    lblVaccineCode.setText("V00"+newID);
                }else if(newID<100){
                    lblVaccineCode.setText("V0"+newID);
                }else{
                    lblVaccineCode.setText("V");
                }
            }else{
                lblVaccineCode.setText("V001");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void textAllClear () {
        txtVaccineName.clear();
        txtCompany.clear();
        txtMCountry.clear();
        lblImagePath.setText("Image Path");
        imgImageView.setImage(null);

    }
    public void Loadlist(){
        ObservableList<ViewRegVaccineTM> items = lstVaccineView.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vaccinereg");

            while(resultSet.next()){
                String vcode = resultSet.getString(1);
                String vname = resultSet.getString(2);
                String mcountry = resultSet.getString(3);
                String mcompany = resultSet.getString(4);
                ViewRegVaccineTM viewRegVaccineTM = new ViewRegVaccineTM(vcode,vname,mcountry,mcompany);
                items.add(viewRegVaccineTM);
            }
            lstVaccineView.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void ErrorMassage(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR," "+errorField+" Field is cannot be emty, Please Fill this field..! ");
        alert.showAndWait();
    }
    public void ErrorBorderCl(JFXTextField name){
        name.setStyle("-fx-border-color:red");
    }
    public void NullBorderCl(){
        txtVaccineName.setStyle("-fx-border-color:null");
        txtCompany.setStyle("-fx-border-color:null");
        txtMCountry.setStyle("-fx-border-color:null");
    }




public File photoUpload(){

    btnChoose.setOnAction((ActionEvent t) -> {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        file = fc.showOpenDialog(btnChoose.getScene().getWindow());

        BufferedImage bf;

        try {
            bf = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bf, null);
            imgImageView.setImage(image);
            lblImagePath.setText(String.valueOf(file));

            if (file!=null){
                btnChoose.setStyle("-fx-border-color:null");
            }


//          ===================== SET TO ADD BUTTON INSERT ==================

//            FileInputStream fin = new FileInputStream(file);
//            int len = (int)file.length();
//
//            Connection connection = DBConnection.getInstance().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("insert into vaccinereg (image) values(?)");
//            preparedStatement.setBinaryStream(1,fin,len);
//            int status = preparedStatement.executeUpdate();
//            if(status>0)
//            {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Information Dialog");
//                alert.setHeaderText("Information dialog");
//                alert.setContentText("Photo saved successfully");
//                alert.showAndWait();
//
//            }
//            else
//            {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error Dialog.");
//                alert.setHeaderText("Error Information");
//                alert.showAndWait();
//            }
        } catch (IOException ex) {
            Logger.getLogger(VaccinationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    });

return file;

}

public void selectListItem(){
        lstVaccineView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewRegVaccineTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewRegVaccineTM> observable, ViewRegVaccineTM oldValue, ViewRegVaccineTM newValue) {

                ViewRegVaccineTM selectedItem = lstVaccineView.getSelectionModel().getSelectedItem();
                if (selectedItem==null){
                    return;
                }
                lblVaccineCode.setText(selectedItem.getVcode());
                txtVaccineName.setText(selectedItem.getVname());
                txtMCountry.setText(selectedItem.getMcompany());
                txtCompany.setText(selectedItem.getMcountry());




            }
        });

}

}
