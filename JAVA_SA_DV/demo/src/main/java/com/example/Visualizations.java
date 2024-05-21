package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Visualizations {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Loan Data Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2)); // Adjust grid layout depending on number of plots
        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Set background color

        // Load dataset
        String filePath = "C:\\Users\\mayur\\OneDrive\\Desktop\\Trimester(3)\\Java(3)\\Java_CAC\\demo\\src\\main\\resources\\loan_data.csv";

        // Create each panel
        frame.add(createBoxPlotPanel(filePath));
        frame.add(createHistogramPanel(filePath));
        frame.add(createScatterPlotPanel(filePath, "FICO Score vs Interest Rate", "FICO Score", "Interest Rate"));
        frame.add(createLineChartPanel(filePath)); // Add line chart panel
        // frame.add(createBarChartPanel(filePath)); // Add bar chart panel
        frame.pack();
        frame.setVisible(true);
    }

    private static ChartPanel createBoxPlotPanel(String filePath) {
        DefaultBoxAndWhiskerCategoryDataset dataset = loadBoxPlotDataset(filePath);
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "Box Plot of FICO Scores", "Category", "FICO Score",
                dataset, true);
        chart.setBackgroundPaint(new Color(240, 240, 240)); // Set plot background color
        chart.getCategoryPlot().setBackgroundPaint(Color.white); // Set plot area background color

        return new ChartPanel(chart);
    }

    private static DefaultBoxAndWhiskerCategoryDataset loadBoxPlotDataset(String filePath) {
        DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            List<Double> ficoValues = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                ficoValues.add(Double.parseDouble(values[5])); // Assuming FICO score is at index 5
            }
            dataset.add(ficoValues, "FICO Score", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private static ChartPanel createHistogramPanel(String filePath) {
        HistogramDataset dataset = new HistogramDataset();
        double[] interestRates = loadDataForHistogram(filePath, 1);

        dataset.addSeries("Interest Rates", interestRates, 20);

        JFreeChart chart = ChartFactory.createHistogram(
                "Distribution of Interest Rates", "Interest Rate", "Frequency",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(new Color(240, 240, 240)); // Set plot background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white); // Set plot area background color

        // Customize domain axis
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);

        // Customize range axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));

        return new ChartPanel(chart);
    }

    private static double[] loadDataForHistogram(String filePath, int columnIndex) {
        List<Double> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                data.add(Double.parseDouble(values[columnIndex]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private static ChartPanel createScatterPlotPanel(String filePath, String title, String xLabel, String yLabel) {
        XYSeries series = new XYSeries("FICO vs Interest Rate");
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                series.add(Double.parseDouble(values[5]), Double.parseDouble(values[1])); // Assuming FICO score is at index 5 and interest rate at index 1
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot(
                title, xLabel, yLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(new Color(240, 240, 240)); // Set plot background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white); // Set plot area background color

        // Customize domain axis
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);

        // Customize range axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));

        return new ChartPanel(chart);
    }

    private static ChartPanel createLineChartPanel(String filePath) {
        XYSeries series = new XYSeries("Interest Rate Over Time");
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // Skip header
            int raceId = 0; // Assuming each line represents a subsequent race/event
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                series.add(raceId++, Double.parseDouble(values[1])); // Assuming interest rate is at index 1
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Interest Rate Over Time", "Event ID", "Interest Rate",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(new Color(240, 240, 240)); // Set plot background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white); // Set plot area background color

        // Customize domain axis
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);

        // Customize range axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));
        rangeAxis.setLabelFont(new Font("Arial", Font.BOLD, 14));

        return new ChartPanel(chart);
    }

}