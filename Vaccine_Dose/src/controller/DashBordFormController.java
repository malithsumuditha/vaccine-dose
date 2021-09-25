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
       sql();
    }


//    public String loadLineChartSQLDose(String dose){
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from vaccination where dose =?");
////            preparedStatement.setObject(1,"dose");
//            preparedStatement.setObject(1,dose);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()){
//                count= resultSet.getString(1);
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return count;
//
//    }

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

//    public ArrayList<String> lineChartSQLDate(){
//        Connection connection = DBConnection.getInstance().getConnection();
//        String s ="vaccineName";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("select regDateDose1 from vaccination");
//            ResultSet resultSet1 = preparedStatement.executeQuery();
//
//            while (resultSet1.next()){
//                dateOfVaccined = resultSet1.getString(1);
//                dateOfVaccined  = dateOfVaccined.substring(8, 10);
//
//                date.add(dateOfVaccined);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return date;
//    }

    public void sql(){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select DATE(regDateDose1) OnlyDatePartFromTimestamp from vaccination");



            XYChart.Series<String,Number> series = new XYChart.Series<>();
            int i = 0;
            int size = getDatefromDB().size();
            String date=null;

            while (resultSet.next()){

//                Integer integer = getDatefromDB().get(i);
//                System.out.println(integer);
//                String date = resultSet.getString(1);
//                //date  = date.substring(5, 10);
//                series.getData().add(new XYChart.Data<>(date,integer));
//
//                i++;

                String string = resultSet.getString(1);
                if (string.equals(date)){

                }
                else {

                    date= resultSet.getString(1);

                    arrayList1.add(date);
                }
                date= resultSet.getString(1);


//                System.out.println(arrayList1);

            }

            System.out.println(arrayList1);


            lineChart.getData().add(series);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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



}
