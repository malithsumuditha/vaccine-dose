package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.ViewAllPersonsTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/18/2021
 **/
public class ViewAllPersonFormController {
    public TableView<ViewAllPersonsTM> tblViewPersons;

    public void initialize(){
        loadList();
    }

    public void loadList(){
        ObservableList<ViewAllPersonsTM> items = tblViewPersons.getItems();
        items.clear();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from person");

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String gender = resultSet.getString(6);
                String registerTime = resultSet.getString(7);

                ViewAllPersonsTM viewAllPersonsTM = new ViewAllPersonsTM(id,name,address,contact,nic,gender,registerTime);
                items.add(viewAllPersonsTM);
            }
            tblViewPersons.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            tblViewPersons.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            tblViewPersons.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
            tblViewPersons.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
            tblViewPersons.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("nic"));
            tblViewPersons.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));
            tblViewPersons.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("registerTime"));

            tblViewPersons.refresh();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}
