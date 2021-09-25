package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/16/2021
 **/
public class DashBordFormController {
    public AnchorPane rootD;
    public LineChart<String,Number> lineChart;
    public String count;
    public String dateOfVaccined;
    public ArrayList<String> date;
    public ArrayList<Integer> arrayList = new ArrayList<Integer>();
    public ArrayList<String>arrayList1 = new ArrayList<String>();


    public void initialize(){

       setValuesToLineChartt();

    }


    public void btnLoadLineChartOnAction(ActionEvent actionEvent) {
    }

//    public void loadLineChart(){
//
////        System.out.println(lineChartSQLDate());
//
////        String first_dose = loadLineChartSQLDose("First Dose");
////        int intCout = Integer.parseInt(first_dose);
//
//        lineChart.getData().clear();
//
//        XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
//        series.getData().add(new XYChart.Data<String, Number>("Dec",20));
//        series.getData().add(new XYChart.Data<String, Number>("Jan",50));
//        series.getData().add(new XYChart.Data<String, Number>("Feb",10));
//        series.getData().add(new XYChart.Data<String, Number>("March",70));
//        series.getData().add(new XYChart.Data<String, Number>("April",60));
//        series.setName("First Dose Done");
//
//
////        String second_dose = loadLineChartSQLDose("Second Dose");
////        intCout = Integer.parseInt(second_dose);
//
//        XYChart.Series<String,Number> series2 = new XYChart.Series<String, Number>();
//        series2.getData().add(new XYChart.Data<String, Number>("Dec",10));
//        series2.getData().add(new XYChart.Data<String, Number>("Jan",20));
//        series2.getData().add(new XYChart.Data<String, Number>("Feb",5));
//        series2.getData().add(new XYChart.Data<String, Number>("March",25));
//        series2.getData().add(new XYChart.Data<String, Number>("April",35));
//        series2.setName("Second Dose Done");
//
//        lineChart.getData().addAll(series,series2);
//    }


    public ArrayList<String> sql(){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select DATE(regDateDose1) from vaccination");

            String date=null;

            while (resultSet.next()){

                String string = resultSet.getString(1);
                if (string.equals(date)){

//                    System.out.println("null");
                }
                else {

                    date= resultSet.getString(1);

                    arrayList1.add(date);
                }
                date= resultSet.getString(1);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayList1;
    }

    public ArrayList<Integer> getDatefromDB(){


        Connection connection1 = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement preparedStatement1 = connection1.prepareStatement("select count(*) as count,date(regDateDose1) as date from vaccination where regDateDose1 >= date_sub(curdate(), interval 7 day) group by date;");
            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()){
                count = resultSet.getString(1);

                int i = Integer.parseInt(count);

                arrayList.add(i);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;
    }


    public void setValuesToLineChartt(){

        XYChart.Series<String,Number> series = new XYChart.Series<>();

        int size = sql().size();
        for (int i = 0; i <size ; i++) {
            Integer intCount = getDatefromDB().get(i);
            String date = sql().get(i);

            series.getData().add(new XYChart.Data<>(date,intCount));
       }

        series.setName("Dose One Completed");
        lineChart.getData().add(series);



      //XYChart.Series<String,Number> series2 = new XYChart.Series<>();



//        for (int i = 0; i <size ; i++) {
//
//            Integer intCount = getDatefromDB().get(i);
//            String date = sql().get(i);
//
//            series2.getData().add(new XYChart.Data<>(date,intCount));
//        }
//
//        series2.setName("Dose Two Completed");
//
//
//        lineChart.getData().add(series2);


    }


}
