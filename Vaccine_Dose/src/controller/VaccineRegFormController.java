package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author - Hw_Dulanjana
 * @date - 9/18/2021
 */
public class VaccineRegFormController {
    public JFXTextField txtVaccineName;
    public JFXTextField txtMCountry;
    public JFXTextField txtCompany;
    public Label lblVaccineCode;

    public void btnVaccineAddOnAction(ActionEvent actionEvent) {
        String VaccineName = txtVaccineName.getText();
        String MCountry = txtMCountry.getText();
        String Company = txtCompany.getText();
        String VCode = lblVaccineCode.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into VaccineReg values(?,?,?,?)");
            preparedStatement.setObject(1,VCode);
            preparedStatement.setObject(2,VaccineName);
            preparedStatement.setObject(3,MCountry);
            preparedStatement.setObject(4,Company);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
