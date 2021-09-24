package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/16/2021
 **/
public class DashBordFormController {
    public AnchorPane rootD;
    public LineChart<String,Number> lineChart;


    public void btnLoadLineChartOnAction(ActionEvent actionEvent) {

        Connection connection = DBConnection.getInstance().getConnection();
//        connection.prepareStatement("select ")

        lineChart.getData().clear();

        XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String, Number>("Dec",20));
        series.getData().add(new XYChart.Data<String, Number>("Jan",50));
        series.getData().add(new XYChart.Data<String, Number>("Feb",10));
        series.getData().add(new XYChart.Data<String, Number>("March",70));
        series.getData().add(new XYChart.Data<String, Number>("April",60));
        series.setName("First Dose Done");

        XYChart.Series<String,Number> series2 = new XYChart.Series<String, Number>();
        series2.getData().add(new XYChart.Data<String, Number>("Dec",10));
        series2.getData().add(new XYChart.Data<String, Number>("Jan",20));
        series2.getData().add(new XYChart.Data<String, Number>("Feb",5));
        series2.getData().add(new XYChart.Data<String, Number>("March",25));
        series2.getData().add(new XYChart.Data<String, Number>("April",35));
        series2.setName("Second Dose Done");

        lineChart.getData().addAll(series,series2);

    }
}
