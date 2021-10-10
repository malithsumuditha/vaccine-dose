package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import tm.ViewAllAdminTM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/28/2021
 **/
public class AdminRegisterFormController {
    public JFXTextField txtName;
    public JFXRadioButton rdbMale;
    public JFXRadioButton rdbFemale;
    public JFXTextField txtContact;
    public JFXTextField txtNIC;
    public Label lblAdminID;
    public JFXTextField txtEmail;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnReset;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtConfirmPassword;
    public TableView<ViewAllAdminTM> tblAdmin;
    public Button btnChooseImage;
    public Label lblImagePath;
    public ImageView imgImageView;
    File file;

    public void initialize(){
       autogenarate();
       txtName.requestFocus();
       loaddatatable();
       photoUpload();
    }
    public void InsertData() throws FileNotFoundException {
        String AName = txtName.getText();
        String AContact = txtContact.getText();
        String ANIC = txtNIC.getText();
        String Gender = null;
        String AEmail = txtEmail.getText();
        String AUserName = txtUserName.getText();
        String Password = txtPassword.getText();
        String CPassword = txtConfirmPassword.getText();
        String id = lblAdminID.getText();

        if(rdbMale.isSelected()){
            Gender = "Male";
        }else if(rdbFemale.isSelected()){
            Gender = "Female";
        }else{
            ErrorMassage("Gender ");
        }
        if(txtName.getText().isEmpty()){
            ErrorBorderCl(txtName);
            ErrorMassage("Ädmin Name");
            txtName.clear();
            txtName.requestFocus();
        }else if(txtContact.getText().isEmpty()){
            ErrorBorderCl(txtContact);
            ErrorMassage("Admin Contact Number");
            txtName.clear();
            txtName.requestFocus();
        }else if(txtNIC.getText().isEmpty()){
            ErrorBorderCl(txtNIC);
            ErrorMassage("Admin NIC number");
            txtNIC.clear();
            txtNIC.requestFocus();
        }else if(txtEmail.getText().isEmpty()) {
            ErrorBorderCl(txtEmail);
            ErrorMassage("Admin Email address");
            txtEmail.clear();
            txtEmail.requestFocus();
        }else if(txtUserName.getText().isEmpty()){
            ErrorBorderCl(txtUserName);
            ErrorMassage("User Name");
            txtUserName.clear();
            txtUserName.requestFocus();
        }else if(txtPassword.getText().isEmpty()){
            ErrorBorderClPWD(txtPassword);
            ErrorMassage("Password");
            txtPassword.clear();
            txtPassword.requestFocus();
        }else if(txtConfirmPassword.getText().equals(txtPassword.getText())){
            NullBorderCl();

            if (photoUpload()==null){
//                btnChooseImage.setStyle("-fx-background-color: #00b894");
                btnChooseImage.setStyle("-fx-border-color:red");
                Alert alert = new Alert(Alert.AlertType.ERROR,"Please Choose Image File");
                alert.showAndWait();
            }else {
                btnChooseImage.setStyle("-fx-border-color:null");
                Connection connection = DBConnection.getInstance().getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into AdminReg values(?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setObject(1,id);
                    preparedStatement.setObject(2,AName);
                    preparedStatement.setObject(3,AContact);
                    preparedStatement.setObject(4,ANIC);
                    preparedStatement.setObject(5,Gender);
                    preparedStatement.setObject(6,AEmail);
                    preparedStatement.setObject(7,AUserName);
                    preparedStatement.setObject(8,Password);

                    if (photoUpload() == null){

                        File img = new File("Vaccine_Dose/src/image/219986.png");
                        FileInputStream fin = new FileInputStream(img);
                        preparedStatement.setBinaryStream(9, (InputStream)fin,(int)img.length());
                    }else {

                        FileInputStream fin = new FileInputStream(photoUpload());

                        int length = (int)file.length();
                        preparedStatement.setBinaryStream(9,fin,length);
                    }

                    int i = preparedStatement.executeUpdate();
                    if(i!=0){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Successfull Added !!");
                        alert.showAndWait();
                        loaddatatable();

                    }else{
                        ErrorAlert();
                    }

                } catch (SQLException throwables) {
                    ErrorAlert();
                    throwables.printStackTrace();
                }
                allvalueClear();
                autogenarate();
                txtName.requestFocus();
            }


        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR,"Passsword and Confirm Password are not same !!");
            alert.showAndWait();
            ErrorBorderClPWD(txtPassword);
            ErrorBorderClPWD(txtConfirmPassword);
            txtPassword.clear();
            txtConfirmPassword.clear();
            txtPassword.requestFocus();
        }

    }
    public void autogenarate(){
        PersonRegFormController.autoGenerateID(lblAdminID,"AdminReg","A");
    }

    public void rdbMaleOnAction(ActionEvent actionEvent) {
        rdbFemale.setSelected(false);
    }

    public void rdbFemaleOnAction(ActionEvent actionEvent) {
        rdbMale.setSelected(false);
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws FileNotFoundException {
        InsertData();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        allvalueClear();
        txtName.requestFocus();
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
        txtName.setStyle("-fx-border-color:null");
        txtNIC.setStyle("-fx-border-color:null");
        txtContact.setStyle("-fx-border-color:null");
        txtPassword.setStyle("-fx-border-color:null");
        txtConfirmPassword.setStyle("-fx-border-color:null");
        txtEmail.setStyle("-fx-border-color:null");
        txtUserName.setStyle("-fx-border-color:null");
    }
    public void allvalueClear(){
        txtName.clear();
        txtContact.clear();
        txtNIC.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtEmail.clear();
        lblImagePath.setText("Image Path");

        File file = new File("Vaccine_Dose/src/image/219986.png");
        Image image = new Image(file.toURI().toString());
        imgImageView.setImage(image);

        btnChooseImage.setStyle("-fx-border-color:null");

    }
    public void loaddatatable(){
        ObservableList<ViewAllAdminTM> items = tblAdmin.getItems();
        items.clear();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *  from AdminReg");
            while(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String contact = resultSet.getString(3);
                String nic = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String email = resultSet.getString(6);

                ViewAllAdminTM viewAllAdminTM = new ViewAllAdminTM();

                viewAllAdminTM.setId(id);
                viewAllAdminTM.setName(name);
                viewAllAdminTM.setContact(contact);
                viewAllAdminTM.setNic(nic);
                viewAllAdminTM.setGender(gender);
                viewAllAdminTM.setEmail(email);

                items.add(viewAllAdminTM);
            }
            tblAdmin.refresh();

            tblAdmin.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblAdmin.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblAdmin.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
            tblAdmin.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblAdmin.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("gender"));
            tblAdmin.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("email"));

        } catch (SQLException throwables) {
            ErrorAlert();
            throwables.printStackTrace();

        }
    }
    public void ErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR,"Something Error !! Please try Again .");
        alert.showAndWait();
    }

    public File photoUpload(){
        btnChooseImage.setOnAction((ActionEvent t) ->{
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fc.getExtensionFilters().addAll(ext1,ext2);
            file = fc.showOpenDialog(btnChooseImage.getScene().getWindow());

            BufferedImage bf;

            try {
                bf = ImageIO.read(file);
                WritableImage image = SwingFXUtils.toFXImage(bf,null);
                imgImageView.setImage(image);
                lblImagePath.setText(String.valueOf(file));

                if (file!=null){
                    btnChooseImage.setStyle("-fx-border-color:null");
                }

            } catch (IOException ex) {
                Logger.getLogger(VaccinationFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } );

        return file;
    }
}
