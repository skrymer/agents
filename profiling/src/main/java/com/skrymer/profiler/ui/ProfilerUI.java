package com.skrymer.profiler.ui;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.skrymer.profiler.Profiler;
import com.skrymer.profiler.events.Event;
import com.skrymer.profiler.events.ObjectInstantiatedEvent;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Profiler UI
 */
public class ProfilerUI implements Observer {
  private EventLogger eventLogger;
  private ProfilingTree profilingTree;
  private ClassCountPieChart classCountPieChart;

  public static void show() {
    new ProfilerUI();
  }

  private ProfilerUI(){
    Profiler.instance().addObserver(this);
    configureUI();
    JFrame frame = buildFrame();
    frame.setContentPane(buildPanel());
    frame.setVisible(true);
  }

  private void configureUI() {
    UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
    Options.setDefaultIconSize(new Dimension(18, 18));

    try {
      UIManager.setLookAndFeel(Options.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.err.println("Can't set look & feel:" + e);
    }
  }

  private JFrame buildFrame() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setTitle("PROFILER");
    return frame;
  }

  private JSplitPane buildPanel() {
    Dimension minimumSize = new Dimension(100, 50);

    profilingTree = new ProfilingTree();
    JScrollPane scrollingProfilingTree = new JScrollPane(profilingTree);
    scrollingProfilingTree.setMinimumSize(minimumSize);

    classCountPieChart = new ClassCountPieChart();

    JSplitPane profilingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollingProfilingTree, classCountPieChart);
    profilingPanel.setDividerLocation(500);

    eventLogger = new EventLogger();
    JScrollPane scrollConsole = new JScrollPane(eventLogger);
    scrollConsole.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    scrollConsole.setMinimumSize(minimumSize);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, profilingPanel, scrollConsole);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(500);

    return splitPane;
  }

  @Override
  public void update(Observable o, Object event) {
    synchronized (this) {
      profilingTree.addEvent((Event) event);
      eventLogger.log((Event) event);

      if(event instanceof ObjectInstantiatedEvent) {
        classCountPieChart.objectInstantiated(((ObjectInstantiatedEvent) event).getClassName(), ((ObjectInstantiatedEvent) event).getNewCount());
      }
    }
  }
}
