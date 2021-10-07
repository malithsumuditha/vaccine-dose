package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import tm.ViewAllDoctorsTM;
import tm.ViewAllPersonsTM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Optional;
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
    public TableView<ViewAllDoctorsTM> tblViewDoctors;
    public JFXTextField txtSearchDocID;

    public void initialize(){
        PersonRegFormController.autoGenerateID(lblDoctorID,"doctor","D");
        photoUpload();
        loadDatatoTable();
        selectTableItem();
        btnUpdateDeletesetDisable(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtDName.requestFocus();
            }
        });

        //add Listner to filterField

        txtSearchDocID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(oldValue,newValue);
            }
        });

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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws FileNotFoundException {

        String id = lblDoctorID.getText();
        String name = txtDName.getText();
        String contact = txtDContact.getText();
        String nic = txtDNic.getText();
        String gender = null;

        if (rdbDMale.isSelected()){
            gender = "Male";
        }else if (rdbDFemale.isSelected()){
            gender = "Female";
        }

        FileInputStream fin = new FileInputStream(photoUpload());

        int length = (int)file.length();



        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update doctor set name=?,contact=?,nic=?,gender=?,image=? where id=?");
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,contact);
            preparedStatement.setObject(3,nic);
            preparedStatement.setObject(4,gender);
            preparedStatement.setBinaryStream(5,fin,length);
            preparedStatement.setObject(6,id);

            int i = preparedStatement.executeUpdate();

            setReset();

            if (i==0){
                errorAlert("Something Error");
            }
            else {
                confirmAlert("Successfully Updated");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String id = lblDoctorID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to Delete...? ",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from doctor where id=?");
                preparedStatement.setObject(1,id);
                int i = preparedStatement.executeUpdate();
                loadDatatoTable();
                setReset();

                if (i==0){
                    errorAlert("Something Error");
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }

    public void btnDResetOnAction(ActionEvent actionEvent) {
        setReset();
    }

    public static void confirmAlert(String displayAlert){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,displayAlert);
        alert.showAndWait();
    }

    public static void errorAlert(String displayAlert){
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
        lblImagePath.setText("Image Path");
        tblViewDoctors.getSelectionModel().clearSelection();
        btnDAdd.setDisable(false);
        btnUpdateDeletesetDisable(true);
        txtAccPassword.setDisable(false);
        txtCPassword.setDisable(false);
    }

    public void setBorderColor(JFXTextField name,String color){
        name.setStyle("-fx-border-color:"+color);
    }

    public static boolean numberCheck(String num){

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

    public void loadDatatoTable(){

        ObservableList<ViewAllDoctorsTM> items = tblViewDoctors.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id,name,contact,nic,gender,image from doctor");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String contact = resultSet.getString(3);
                String nic = resultSet.getString(4);
                String gender = resultSet.getString(5);
                Blob img = resultSet.getBlob(6);

                ViewAllDoctorsTM viewAllDoctorsTM = new ViewAllDoctorsTM();
                viewAllDoctorsTM.setId(id);
                viewAllDoctorsTM.setName(name);
                viewAllDoctorsTM.setContact(contact);
                viewAllDoctorsTM.setNic(nic);
                viewAllDoctorsTM.setGender(gender);
                viewAllDoctorsTM.setBlob(img);

                items.add(viewAllDoctorsTM);
            }

            tblViewDoctors.refresh();

            tblViewDoctors.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblViewDoctors.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblViewDoctors.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
            tblViewDoctors.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblViewDoctors.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("gender"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void selectTableItem(){
        tblViewDoctors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllDoctorsTM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllDoctorsTM> observable, ViewAllDoctorsTM oldValue, ViewAllDoctorsTM newValue) {
                btnDAdd.setDisable(true);
                btnUpdateDeletesetDisable(false);
                txtAccPassword.setDisable(true);
                txtCPassword.setDisable(true);

                ViewAllDoctorsTM selectedItem = tblViewDoctors.getSelectionModel().getSelectedItem();

                if(selectedItem==null){
                    return;
                }

                String id = selectedItem.getId();
                String name = selectedItem.getName();
                String contact = selectedItem.getContact();
                String nic = selectedItem.getNic();
                String gender = selectedItem.getGender();
                Blob blob = selectedItem.getBlob();

                lblDoctorID.setText(id);
                txtDName.setText(name);
                txtDContact.setText(contact);
                txtDNic.setText(nic);
                lblImagePath.setText(null);

                if (gender.equals("Male")){
                    rdbDMale.setSelected(true);
                    rdbDFemale.setSelected(false);
                }else {
                    rdbDFemale.setSelected(true);
                    rdbDMale.setSelected(false);
                }

                if (blob==null){
                    imgImageView.setImage(null);
                    return;
                }

                try {
                    InputStream binaryStream = blob.getBinaryStream();
                    Image image = new Image(binaryStream);
                    imgImageView.setImage(image);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }


    ObservableList<ViewAllDoctorsTM> masterData = FXCollections.observableArrayList();
    public void search(String oldValue, String newValue){

        ObservableList<ViewAllDoctorsTM> filteredList = FXCollections.observableArrayList();

        if(txtSearchDocID == null || (newValue.length() < oldValue.length()) || newValue == null) {
            tblViewDoctors.setItems(masterData);
            loadDatatoTable();
        }
        else {
            newValue = newValue.toUpperCase();
            for(ViewAllDoctorsTM doctorTM : tblViewDoctors.getItems()) {
                String filterId = doctorTM.getId();
                String filterName = doctorTM.getName();
                if(filterId.toUpperCase().contains(newValue) || filterName.toUpperCase().contains(newValue)) {

                    filteredList.add(doctorTM);
                }
            }
            tblViewDoctors.setItems(filteredList);
        }

    }

    public void btnUpdateDeletesetDisable(Boolean isDisable){
        btnDelete.setDisable(isDisable);
        btnUpdate.setDisable(isDisable);
    }
}
