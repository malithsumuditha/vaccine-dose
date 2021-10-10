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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import tm.ViewAllDoctorsTM;
import tm.ViewAllPHITM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author - Hw_Dulanjana
 * @date - 9/19/2021
 */
public class PHIRegFormController {
    public JFXTextField txtPHIName;
    public JFXRadioButton rdbPHIMale;
    public JFXRadioButton rdbPHIFemale;
    public JFXTextField txtPHIAddress;
    public JFXTextField txtPHIContact;
    public JFXTextField txtPHINIC;
    public Label lblPHIID;
    public JFXButton btnPHIAdd;
    public JFXButton btnPHIUpdate;
    public JFXButton btnPHIDelete;
    public JFXButton btnPHIReset;
    public JFXTextField txtPHICity;
    public JFXPasswordField txtAccPasssword;
    public JFXPasswordField txtConfirmPassword;
    public Button btnChooseImage;
    public FileInputStream img;
    public File file;
    public ImageView imgImageView;
    public TableView<ViewAllPHITM> tblViewAllPHI;
    public Button btnChoose;
    public Label lblImagePath;
    public JFXTextField txtSearchPHI;

    public void initialize(){
        autogenarate();
        txtPHIName.requestFocus();
        uploadImage();
        loadDatatoTable();
        selectTableData();
        btnUpdateDeletesetDisable(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtPHIName.requestFocus();
            }
        });

        //add Listner to filterField

        txtSearchPHI.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(oldValue,newValue);
            }
        });

    }

    public void rdbPHIMaleOnAction(ActionEvent actionEvent) {
        rdbPHIFemale.setSelected(false);
    }

    public void rdbPHIFemaleOnAction(ActionEvent actionEvent) {
        rdbPHIMale.setSelected(false);
    }

    public void btnPHIAddOnAction(ActionEvent actionEvent) throws FileNotFoundException {
        datainsert();
    }

    public void btnPHIUpdateOnAction(ActionEvent actionEvent) throws FileNotFoundException {

        String id = lblPHIID.getText();
        String name = txtPHIName.getText();
        String contact = txtPHIContact.getText();
        String nic = txtPHINIC.getText();
        String address = txtPHIAddress.getText();
        String city = txtPHICity.getText();

        String gender = null;

        if (rdbPHIMale.isSelected()){
            gender = "Male";
        }else if (rdbPHIFemale.isSelected()){
            gender = "Female";
        }

        FileInputStream fin = new FileInputStream(uploadImage());

        int length = (int)file.length();



        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update phireg set PName=?,PAddress=?,PContact=?,PNIC=?,PGender=?,WCity=?,image=? where PID=?");
            preparedStatement.setObject(1,name);
            preparedStatement.setObject(2,address);
            preparedStatement.setObject(3,contact);
            preparedStatement.setObject(4,nic);
            preparedStatement.setObject(5,gender);
            preparedStatement.setObject(6,city);
            preparedStatement.setBinaryStream(7,fin,length);
            preparedStatement.setObject(8,id);

            int i = preparedStatement.executeUpdate();

            txtClear();

            if (i==0){
                DoctorRegFOrmController.errorAlert("Something Error");
            }
            else {
                DoctorRegFOrmController.confirmAlert("Successfully Updated");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void btnPHIDeleteOnAction(ActionEvent actionEvent) {

        String id = lblPHIID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to Delete...? ", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)){
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from phireg where PID=?");
                preparedStatement.setObject(1,id);
                int i = preparedStatement.executeUpdate();

                loadDatatoTable();
                txtClear();

                if (i==0){
                    DoctorRegFOrmController.errorAlert("Something Error");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void btnPHIResetOnAction(ActionEvent actionEvent) {

        txtClear();
    }
    public void datainsert() throws FileNotFoundException {
        String Password = txtAccPasssword.getText();
        String CPassword = txtConfirmPassword.getText();

        if(txtPHIName.getText().isEmpty()){
            ErrorMassage("PHI Name");
            ErrorBorderCl(txtPHIName);
            txtPHIName.clear();
            txtPHIName.requestFocus();
        }else if(txtPHIAddress.getText().isEmpty()){
            ErrorMassage("PHI Address");
            ErrorBorderCl(txtPHIAddress);
            txtPHIAddress.clear();
            txtPHIAddress.requestFocus();
        }else if(txtPHIContact.getText().isEmpty()){
            ErrorMassage("Contact Number");
            ErrorBorderCl(txtPHIContact);
            txtPHIContact.clear();
            txtPHIContact.requestFocus();
        }else if(txtPHINIC.getText().isEmpty()){
            ErrorBorderCl(txtPHINIC);
            ErrorMassage("NIC Number");
            txtPHINIC.clear();
            txtPHINIC.requestFocus();
        }else if (txtAccPasssword.getText().isEmpty()){
            ErrorMassage("Password");
            ErrorBorderClPWD(txtAccPasssword);
            txtAccPasssword.clear();
            txtAccPasssword.requestFocus();
        }else if(txtPHICity.getText().isEmpty()){
            ErrorMassage("working City");
            ErrorBorderCl(txtPHICity);
            txtPHICity.clear();
            txtPHICity.requestFocus();

        }else if(CPassword.equals(Password)){

            txtAccPasssword.setStyle("-fx-border-color:null");
            txtConfirmPassword.setStyle("-fx-border-color:null");

            if (DoctorRegFOrmController.numberCheck(txtPHIContact.getText())){

                NullBorderCl();
                String PName = txtPHIName.getText();
                String PAddress = txtPHIAddress.getText();
                String PContact = txtPHIContact.getText();
                String PNic = txtPHINIC.getText();
                String PCity = txtPHICity.getText();
                String gender = null;
                String PID = lblPHIID.getText();

                if (rdbPHIFemale.isSelected()) {
                    gender = "Female";
                } else if (rdbPHIMale.isSelected()) {
                    gender = "Male";
                } else {
                    ErrorMassage("Gender Field");
                }

                Connection connection = DBConnection.getInstance().getConnection();


                    btnChoose.setStyle("-fx-border-color:null");


                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("insert into phireg Values(?,?,?,?,?,?,?,?,?)");
                        preparedStatement.setObject(1, PID);
                        preparedStatement.setObject(2, PName);
                        preparedStatement.setObject(3, PAddress);
                        preparedStatement.setObject(4, PContact);
                        preparedStatement.setObject(5, PNic);
                        preparedStatement.setObject(6, gender);
                        preparedStatement.setObject(7, PCity);
                        preparedStatement.setObject(8, Password);

                        if (uploadImage() == null){

                            File img = new File("Vaccine_Dose/src/image/219986.png");
                            FileInputStream fin = new FileInputStream(img);
                            preparedStatement.setBinaryStream(9, (InputStream)fin,(int)img.length());
                        }else {

                            FileInputStream fin = new FileInputStream(uploadImage());

                            int length = (int)file.length();
                            preparedStatement.setBinaryStream(9,fin,length);
                        }



                        int i = preparedStatement.executeUpdate();


                        if (i != 0) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successful Added !! ");
                            alert.showAndWait();
                            txtClear();
                            txtPHIName.requestFocus();
                            autogenarate();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error ! , Please TRy Again.. ");
                            alert.showAndWait();
                        }
                    } catch (SQLException throwables) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Something Error ! , Please TRy Again.. ");
                        alert.showAndWait();
                        throwables.printStackTrace();
                    }





            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please enter Valid number....");
                alert.showAndWait();
                txtPHIContact.setStyle("-fx-border-color:red");
            }



        }

           else {
            ErrorBorderClPWD(txtConfirmPassword);
            ErrorBorderClPWD(txtAccPasssword);
            txtAccPasssword.clear();
            txtConfirmPassword.clear();
            txtAccPasssword.requestFocus();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong Password Combination...");
            alert.showAndWait();
        }
    }
    public void autogenarate(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select PID from PHIReg order by PID desc limit 1");
            boolean IfExif = resultSet.next();

            if (IfExif){
                String oldV = resultSet.getString(1);
                oldV = oldV.substring(2, oldV.length());

                int NewID = Integer.parseInt(oldV);
                NewID= NewID+1;

                if(NewID < 10){
                    lblPHIID.setText("PH00"+NewID);
                }else if(NewID<100){
                    lblPHIID.setText("PH0"+NewID);
                }else{
                    lblPHIID.setText("PH"+NewID);
                }
            }
            else{
                lblPHIID.setText("PH001");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void txtClear(){

        autogenarate();

        tblViewAllPHI.getSelectionModel().clearSelection();

        txtPHIName.clear();
        txtPHICity.clear();
        txtPHIAddress.clear();
        txtAccPasssword.clear();
        txtConfirmPassword.clear();
        txtPHINIC.clear();
        txtPHIContact.clear();

        rdbPHIFemale.setSelected(false);
        rdbPHIMale.setSelected(false);
        imgImageView.setImage(null);
        lblImagePath.setText("Image Path");

        loadDatatoTable();
        btnPHIAdd.setDisable(false);
        btnUpdateDeletesetDisable(true);
        txtPHIName.requestFocus();

        txtAccPasssword.setDisable(false);
        txtConfirmPassword.setDisable(false);

        File file = new File("Vaccine_Dose/src/image/219986.png");
        Image image = new Image(file.toURI().toString());
        imgImageView.setImage(image);

    }
    public void ErrorMassage(String errorField){
        Alert alert = new Alert(Alert.AlertType.ERROR," "+errorField+" Field is cannot be emty, Please Fill this field..! ");
        alert.showAndWait();
    }
    public void ErrorBorderCl(JFXTextField name){
        name.setStyle("-fx-border-color:red");
    }
    public void ErrorBorderClPWD(JFXPasswordField name){
        name.setStyle("-fx-border-color:red");
    }
    public void NullBorderCl(){


        txtPHIName.setStyle("-fx-border-color:null");
        txtPHIAddress.setStyle("-fx-border-color:null");
        txtPHIContact.setStyle("-fx-border-color:null");
        txtAccPasssword.setStyle("-fx-border-color:null");
        txtConfirmPassword.setStyle("-fx-border-color:null");
        txtPHINIC.setStyle("-fx-border-color:null");
        txtPHICity.setStyle("-fx-border-color:null");
    }


    public File uploadImage(){

        btnChoose.setOnAction((ActionEvent t) ->{
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fc.getExtensionFilters().addAll(ext1,ext2);
            file = fc.showOpenDialog(btnChoose.getScene().getWindow());

            BufferedImage bf;

            try {
                bf = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bf,null);
                imgImageView.setImage(image);
                lblImagePath.setText(String.valueOf(file));

                if (file!=null){
                    btnChoose.setStyle("-fx-border-color:null");
                }

            } catch (IOException ex) {
                Logger.getLogger(VaccinationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } );

        return file;

    }

    public void loadDatatoTable(){
        ObservableList<ViewAllPHITM> items = tblViewAllPHI.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select PID,PName,PAddress,PContact,PGender,PNIC,WCity,image from phireg");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String nic = resultSet.getString(6);
                String city = resultSet.getString(7);
                Blob img = resultSet.getBlob(8);

                ViewAllPHITM viewAllPHITM = new ViewAllPHITM();
                viewAllPHITM.setId(id);
                viewAllPHITM.setName(name);
                viewAllPHITM.setAddress(address);
                viewAllPHITM.setContact(contact);
                viewAllPHITM.setNic(nic);
                viewAllPHITM.setGender(gender);
                viewAllPHITM.setCity(city);
                viewAllPHITM.setBlob(img);

                items.add(viewAllPHITM);
            }

            tblViewAllPHI.refresh();

            tblViewAllPHI.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblViewAllPHI.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblViewAllPHI.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
            tblViewAllPHI.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
            tblViewAllPHI.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("gender"));
            tblViewAllPHI.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblViewAllPHI.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("city"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectTableData(){
        tblViewAllPHI.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ViewAllPHITM>() {
            @Override
            public void changed(ObservableValue<? extends ViewAllPHITM> observable, ViewAllPHITM oldValue, ViewAllPHITM newValue) {

                btnUpdateDeletesetDisable(false);
                btnPHIAdd.setDisable(true);
                txtAccPasssword.setDisable(true);
                txtConfirmPassword.setDisable(true);

                ViewAllPHITM selectedItem = tblViewAllPHI.getSelectionModel().getSelectedItem();

                if(selectedItem==null){
                    return;
                }

                String id = selectedItem.getId();
                String name = selectedItem.getName();
                String contact = selectedItem.getContact();
                String address = selectedItem.getAddress();
                String city = selectedItem.getCity();
                String nic = selectedItem.getNic();
                String gender = selectedItem.getGender();
                Blob blob = selectedItem.getBlob();

                lblPHIID.setText(id);
                txtPHIName.setText(name);
                txtPHIContact.setText(contact);
                txtPHINIC.setText(nic);
                txtPHIAddress.setText(address);
                txtPHICity.setText(city);
                lblImagePath.setText(null);

                if (gender.equals("Male")){
                    rdbPHIMale.setSelected(true);
                    rdbPHIFemale.setSelected(false);
                }else {
                    rdbPHIFemale.setSelected(true);
                    rdbPHIMale.setSelected(false);
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

    ObservableList<ViewAllPHITM> masterData = FXCollections.observableArrayList();
    public void search(String oldValue, String newValue){

        ObservableList<ViewAllPHITM> filteredList = FXCollections.observableArrayList();

        if(txtSearchPHI == null || (newValue.length() < oldValue.length()) || newValue == null) {
            tblViewAllPHI.setItems(masterData);
            loadDatatoTable();
        }
        else {
            newValue = newValue.toUpperCase();
            for(ViewAllPHITM phiTM : tblViewAllPHI.getItems()) {
                String filterId = phiTM.getId();
                String filterName = phiTM.getName();
                if(filterId.toUpperCase().contains(newValue) || filterName.toUpperCase().contains(newValue)) {

                    filteredList.add(phiTM);
                }
            }
            tblViewAllPHI.setItems(filteredList);
        }

    }

    public void btnUpdateDeletesetDisable(Boolean isDisable){
        btnPHIDelete.setDisable(isDisable);
        btnPHIUpdate.setDisable(isDisable);
    }
}
