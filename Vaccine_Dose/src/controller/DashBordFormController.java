package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/16/2021
 **/
public class DashBordFormController {
    public AnchorPane rootD;
    public LineChart<String,Number> lineChart;
    public String count;
    public String dateOfVaccined;

    public void initialize(){
       loadLineChart();
    }


    public String loadLineChartSQLDose(String dose){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from vaccination where dose =?");
//            preparedStatement.setObject(1,"dose");
            preparedStatement.setObject(1,dose);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                count= resultSet.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return count;

    }

    public void btnLoadLineChartOnAction(ActionEvent actionEvent) {
    }

    public void loadLineChart(){
        System.out.println(lineChartSQLDate());

        String first_dose = loadLineChartSQLDose("First Dose");
        int intCout = Integer.parseInt(first_dose);

        lineChart.getData().clear();

        XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String, Number>("Dec",intCout));
        series.getData().add(new XYChart.Data<String, Number>("Jan",50));
        series.getData().add(new XYChart.Data<String, Number>("Feb",10));
        series.getData().add(new XYChart.Data<String, Number>("March",70));
        series.getData().add(new XYChart.Data<String, Number>("April",60));
        series.setName("First Dose Done");


        String second_dose = loadLineChartSQLDose("Second Dose");
        intCout = Integer.parseInt(second_dose);

        XYChart.Series<String,Number> series2 = new XYChart.Series<String, Number>();
        series2.getData().add(new XYChart.Data<String, Number>("Dec",intCout));
        series2.getData().add(new XYChart.Data<String, Number>("Jan",20));
        series2.getData().add(new XYChart.Data<String, Number>("Feb",5));
        series2.getData().add(new XYChart.Data<String, Number>("March",25));
        series2.getData().add(new XYChart.Data<String, Number>("April",35));
        series2.setName("Second Dose Done");

        lineChart.getData().addAll(series,series2);
    }

    public String lineChartSQLDate(){
        Connection connection = DBConnection.getInstance().getConnection();
        String s ="vaccineName";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select regDateDose1 from vaccination");
            ResultSet resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()){
                dateOfVaccined = resultSet1.getString(1);
                dateOfVaccined  = dateOfVaccined.substring(8, 10);

                if (dateOfVaccined.equals("19")){
                    dateOfVaccined="January";
                }else if (dateOfVaccined.equals("20")){
                    dateOfVaccined="February";
                }
                System.out.println(dateOfVaccined);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dateOfVaccined;
    }

}
