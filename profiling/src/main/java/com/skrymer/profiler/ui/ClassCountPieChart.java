package com.skrymer.profiler.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrymer on 13/06/17.
 */
public class ClassCountPieChart extends JPanel {
  final DefaultPieDataset dataset;

  public ClassCountPieChart(){
    dataset = new DefaultPieDataset();

    final JFreeChart chart = createChart(dataset);
    final ChartPanel chartPanel = new ChartPanel(chart);

    this.setLayout(new BorderLayout());
    this.add(chartPanel, BorderLayout.CENTER);
  }

  public void objectInstantiated(String className, int count){
    dataset.setValue(className, count);
  }

  private JFreeChart createChart(final PieDataset dataset) {

    final JFreeChart chart = ChartFactory.createPieChart3D(
        "Classes by count",
        dataset,
        false,
        true,
        false
    );

    final PiePlot3D plot = (PiePlot3D) chart.getPlot();
    plot.setStartAngle(290);
    plot.setDirection(Rotation.CLOCKWISE);
    plot.setForegroundAlpha(0.5f);
    plot.setNoDataMessage("No data to display");
    return chart;
  }
}
