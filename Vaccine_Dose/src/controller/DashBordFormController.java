package controller;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    public ArrayList<String> date;
    public ArrayList<Integer> arrayList = new ArrayList<Integer>();
    public ArrayList<String>arrayList1 = new ArrayList<String>();
    public Label lblTodayVaccine;
    public Label lblTotalVaccined;
    public Label lblTotalDoseOneVaccination;
    public Label lblTotalDose2Vaccination;
    public ArrayList<Integer>arrayList2 = new ArrayList<Integer>();
    public ProgressIndicator pgiDoseTwo;
    public ProgressIndicator pgiDoseOne;
    public ProgressIndicator pgiTotalVaccination;
    public ImageView imgLocal1;
    public ImageView imgLocal2;
    public ImageView imgGlobal1;
    public ImageView imgGlobal2;


    public void initialize(){

        setValuesToLineChartt();

       //set value to label Today Vaccine

        Integer dose1 = getDose1DatefromDB().get(arrayList.size() - 1);
        Integer dosse2 = getDose2DatefromDB().get(arrayList2.size() - 1);
        int totalDoseToday=dose1+dosse2;
        lblTodayVaccine.setText("+"+totalDoseToday);


        totalDoseOneVaccinedCount();
        totalDose2vaccined();
        totalVaccined();
        setValueToProgressIndicator();


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

            ResultSet resultSet = statement.executeQuery("select DATE(regDateDose1)as date from vaccination where regDateDose1 >= date_sub(curdate(), interval 10 day) group by date;");

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

    public ArrayList<Integer> getDose1DatefromDB(){
        int c=0;

        Connection connection1 = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement preparedStatement1 = connection1.prepareStatement("select count(*) as count,date(regDateDose1) as date from vaccination where regDateDose1 >= date_sub(curdate(), interval 10 day) group by date;");
            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()){
                count = resultSet.getString(1);

                c= Integer.parseInt(count);

                arrayList.add(c);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayList;

    }


    public void setValuesToLineChartt(){

        XYChart.Series<String,Number> series = new XYChart.Series<>();

        int size = getDose1DatefromDB().size();
        for (int i = 0; i <size ; i++) {
            String date = sql().get(i);
            Integer intCount = getDose1DatefromDB().get(i);

            series.getData().add(new XYChart.Data<>(date,intCount));
       }

        series.setName("Dose One Completed");
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke: blue;");





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


    public int totalDoseOneVaccinedCount(){
        Connection connection = DBConnection.getInstance().getConnection();
        int intCount=0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from vaccination");



            while (resultSet.next()){
                String string = resultSet.getString(1);
                intCount = Integer.parseInt(string);
            }
            if (intCount<10){
                lblTotalDoseOneVaccination.setText("0"+intCount);
            }
            else {
                lblTotalDoseOneVaccination.setText(""+intCount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return intCount;
    }

    public int totalDose2vaccined(){
        Connection connection = DBConnection.getInstance().getConnection();
        int intCount=0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(regDateDose2) from vaccination");



            while (resultSet.next()){
                String string = resultSet.getString(1);
                intCount = Integer.parseInt(string);
            }
            if (intCount<10){
                lblTotalDose2Vaccination.setText("0"+intCount);
            }
            else {
                lblTotalDose2Vaccination.setText(""+intCount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return intCount;
    }

    public void totalVaccined(){
        int total = totalDoseOneVaccinedCount() + totalDose2vaccined();
        if(total<10){
            lblTotalVaccined.setText("0"+total);
        }
        else {
            lblTotalVaccined.setText(""+total);
        }
    }

    public ArrayList<Integer> getDose2DatefromDB(){
        int c=0;

        Connection connection1 = DBConnection.getInstance().getConnection();
        try {

            PreparedStatement preparedStatement1 = connection1.prepareStatement("select count(*) as count,date(regDateDose2) as date from vaccination where regDateDose2 >= date_sub(curdate(), interval 10 day) group by date;");
            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()){
                String count2 = resultSet.getString(1);

                c= Integer.parseInt(count2);

                arrayList2.add(c);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayList2;

    }


    public void setValueToProgressIndicator(){

        double i = ((double) totalDose2vaccined() * 100) / 200;
        pgiDoseTwo.setProgress(i/100);

        double v = ((double) totalDoseOneVaccinedCount() * 100) / 200;
        pgiDoseOne.setProgress(v/100);

        pgiTotalVaccination.setProgress((i+v)/100);

    }


    public void imgLocal1OnMouseClick(MouseEvent mouseEvent) {
        webAddressOpen("https://www.dailynews.lk/2021/05/13/local/249223/1145-covid-19-patients-recovered-sri-lanka");

    }

    public void imgLocal2OnMouseClicked(MouseEvent mouseEvent) {
        webAddressOpen("https://www.news.lk/news/political-current-affairs/item/30969-sri-lanka-confirms-22nd-death-from-covid-19");

    }

    public void imgGlobal1OnMousClicked(MouseEvent mouseEvent) {

        webAddressOpen("https://www.bbc.com/news/world-54337098");
    }

    public void imgGlobal2OnMouseClicked(MouseEvent mouseEvent) {
        webAddressOpen("https://www.aarc.org/nn20-covid-19-news-resources/");
    }

    public void webAddressOpen(String link){
        try {
            Desktop.getDesktop().browse(new URI(link));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
