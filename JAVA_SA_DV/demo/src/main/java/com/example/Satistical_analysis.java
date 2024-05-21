package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;

public class Satistical_analysis {
    public static void main(String[] args) {
        String csvFile = "C:\\Users\\mayur\\OneDrive\\Desktop\\Trimester(3)\\Java(3)\\Java_CAC\\demo\\src\\main\\resources\\loan_data.csv";
        String outputFile = "statistical_analysis.txt";

        List<List<Double>> dataset = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        int numberOfColumns = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] columns = headerLine.split(",");
                numberOfColumns = columns.length;
                for (String column : columns) {
                    columnNames.add(column);
                    dataset.add(new ArrayList<>());
                }
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < numberOfColumns; i++) {
                    double value = Double.parseDouble(values[i]);
                    dataset.get(i).add(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("MEAN OF THE COLUMNS\n\n");
            System.out.println("MEAN OF THE COLUMNS\n");
            for (int i = 0; i < numberOfColumns; i++) {
                if (!columnNames.get(i).equals("time")) {
                    double mean = calculateMean(dataset.get(i));
                    String output = "Mean of \"" + columnNames.get(i) + "\": " + mean + "\n";
                    writer.write(output);
                    System.out.println(output);
                }
            }
            double overallMean = calculateOverallMean(dataset);
            writer.write("\nOverall mean of all columns: " + overallMean + "\n\n");
            System.out.println("\nOverall mean of all columns: " + overallMean + "\n");

            writer.write("MODE OF ALL THE COLUMNS\n\n");
            System.out.println("MODE OF ALL THE COLUMNS\n");
            for (int i = 0; i < numberOfColumns; i++) {
                if (!columnNames.get(i).equals("time")) {
                    double mode = findMode(dataset.get(i));
                    String output = "Mode of \"" + columnNames.get(i) + "\": " + mode + "\n";
                    writer.write(output);
                    System.out.println(output);
                }
            }
            double overallMode = calculateOverallMode(dataset);
            writer.write("\nOverall mode of all columns: " + overallMode + "\n\n");
            System.out.println("\nOverall mode of all columns: " + overallMode + "\n");

            writer.write("MEDIAN OF COLUMNS\n\n");
            System.out.println("MEDIAN OF COLUMNS\n");
            for (int i = 0; i < numberOfColumns; i++) {
                if (!columnNames.get(i).equals("time")) {
                    double median = findMedian(dataset.get(i));
                    String output = "Median of \"" + columnNames.get(i) + "\": " + median + "\n";
                    writer.write(output);
                    System.out.println(output);
                }
            }
            double overallMedian = calculateOverallMedian(dataset);
            writer.write("\nOverall median of all columns: " + overallMedian + "\n\n");
            System.out.println("\nOverall median of all columns: " + overallMedian + "\n");

            writer.write("STANDARD DEVIATION OF COLUMNS\n\n");
            System.out.println("STANDARD DEVIATION OF COLUMNS\n");
            for (int i = 0; i < numberOfColumns; i++) {
                if (!columnNames.get(i).equals("time")) {
                    double stdDev = calculateStandardDeviation(dataset.get(i));
                    String output = "Standard deviation of \"" + columnNames.get(i) + "\": " + stdDev + "\n";
                    writer.write(output);
                    System.out.println(output);
                }
            }
            double overallStdDev = calculateOverallStandardDeviation(dataset);
            writer.write("\nOverall standard deviation of all columns: " + overallStdDev + "\n\n");
            System.out.println("\nOverall standard deviation of all columns: " + overallStdDev + "\n");

            writer.write("CORRELATION MATRIX\n\n");
            System.out.println("CORRELATION MATRIX\n");
            double[][] correlationMatrix = calculateCorrelationMatrix(dataset);
            writer.write(generateCorrelationMatrixString(correlationMatrix, columnNames));

            generateCorrelationMatrixGraph(correlationMatrix, columnNames);

            System.out.println("Statistical analysis saved to: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateMean(List<Double> list) {
        double sum = 0.0;
        for (double value : list) {
            sum += value;
        }
        return sum / list.size();
    }

    private static double calculateOverallMean(List<List<Double>> dataset) {
        double sum = 0.0;
        int totalCount = 0;
        for (List<Double> column : dataset) {
            for (double value : column) {
                sum += value;
                totalCount++;
            }
        }
        return sum / totalCount;
    }

    private static double findMode(List<Double> list) {
        
        return 0.0; 
    }

    private static double calculateOverallMode(List<List<Double>> dataset) {
     
        return 0.0; 
    }

    private static double findMedian(List<Double> list) {
        Collections.sort(list);
        int size = list.size();
        if (size % 2 == 0) {
            return (list.get(size / 2 - 1) + list.get(size / 2)) / 2.0;
        } else {
            return list.get(size / 2);
        }
    }

    private static double calculateOverallMedian(List<List<Double>> dataset) {
        List<Double> allValues = new ArrayList<>();
        for (List<Double> column : dataset) {
            allValues.addAll(column);
        }
        Collections.sort(allValues);
        int size = allValues.size();
        if (size % 2 == 0) {
            return (allValues.get(size / 2 - 1) + allValues.get(size / 2)) / 2.0;
        } else {
            return allValues.get(size / 2);
        }
    }

    private static double calculateStandardDeviation(List<Double> list) {
        double mean = calculateMean(list);
        double sumSquaredDiffs = 0.0;

        for (double value : list) {
            double diff = value - mean;
            sumSquaredDiffs += diff * diff;
        }

        return Math.sqrt(sumSquaredDiffs / list.size());
    }

    private static double calculateOverallStandardDeviation(List<List<Double>> dataset) {
        List<Double> allValues = new ArrayList<>();
        for (List<Double> column : dataset) {
            allValues.addAll(column);
        }
        return calculateStandardDeviation(allValues);
    }

    private static double[][] calculateCorrelationMatrix(List<List<Double>> dataset) {
        int numColumns = dataset.size();
        double[][] correlationMatrix = new double[numColumns][numColumns];

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numColumns; j++) {
                correlationMatrix[i][j] = calculateCorrelation(dataset.get(i), dataset.get(j));
            }
        }

        return correlationMatrix;
    }

    private static double calculateCorrelation(List<Double> column1, List<Double> column2) {
        double mean1 = calculateMean(column1);
        double mean2 = calculateMean(column2);

        double sumXY = 0.0;
        double sumX2 = 0.0;
        double sumY2 = 0.0;

        int n = column1.size();
        for (int i = 0; i < n; i++) {
            double x = column1.get(i);
            double y = column2.get(i);

            sumXY += (x - mean1) * (y - mean2);
            sumX2 += Math.pow(x - mean1, 2);
            sumY2 += Math.pow(y - mean2, 2);
        }

        double correlation = sumXY / Math.sqrt(sumX2 * sumY2);
        return correlation;
    }

    private static String generateCorrelationMatrixString(double[][] correlationMatrix, List<String> columnNames) {
        StringBuilder sb = new StringBuilder();

        // Append column names as legend
        sb.append("\t");
        for (String columnName : columnNames) {
            sb.append(columnName).append("\t");
        }
        sb.append("\n");

        // Append horizontal line
        for (int i = 0; i < correlationMatrix.length * 10; i++) {
            sb.append("-");
        }
        sb.append("\n");

        // Append correlation matrix
        for (int i = 0; i < correlationMatrix.length; i++) {
            sb.append(columnNames.get(i)).append("\t");
            for (int j = 0; j < correlationMatrix[i].length; j++) {
                sb.append(String.format("%.3f\t", correlationMatrix[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private static void generateCorrelationMatrixGraph(double[][] correlationMatrix, List<String> columnNames) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < correlationMatrix.length; i++) {
            XYSeries series = new XYSeries(columnNames.get(i));
            for (int j = 0; j < correlationMatrix[i].length; j++) {
                series.add(j, correlationMatrix[i][j]);
            }
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Correlation Matrix",
                "Variables",
                "Correlation",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setVerticalTickLabels(true);

        JFrame frame = new JFrame("Correlation Matrix");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}

