package util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.util.Map;

public class GeneradorGraficas {
    
    public static ChartPanel crearGraficaBarrasInventario(Map<String, Integer> datos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), "Stock", entry.getKey());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Inventario Actual",
            "Productos",
            "Cantidad en Stock",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
        
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(new Color(240, 240, 255));
        
        return new ChartPanel(chart);
    }
    
    public static ChartPanel crearGraficaBajoStock(Map<String, Integer> datos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), "Stock Bajo", entry.getKey());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Productos con Bajo Stock",
            "Productos",
            "Cantidad",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );
        
        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(new Color(255, 240, 240));
        
        return new ChartPanel(chart);
    }
}