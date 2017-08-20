package com.skrymer.profiler.ui;

import com.skrymer.profiler.Profiler;
import com.skrymer.profiler.events.Event;
import com.skrymer.profiler.events.ObjectInstantiatedEvent;
import com.skrymer.profiler.ui.widgets.ClassCountPieChart;
import com.skrymer.profiler.ui.widgets.EventLogger;
import com.skrymer.profiler.ui.widgets.ProfilingTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Profiler UI
 */
public class ProfilerUI extends JFrame implements ActionListener, Observer {
  public static final String CLASS_COUNT_DIAGRAM_COMMAND = "classCountDiagram";
  private JDesktopPane desktop;
  private EventLogger eventLogger;
  private JInternalFrame eventLoggerFrame;
  private ProfilingTree profilingTree;
  private ClassCountPieChart classCountPieChart;
  private JInternalFrame classCountPieChartFrame;

  public static void showUI() {
    javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
  }

  private static void createAndShowGUI() {
    JFrame.setDefaultLookAndFeelDecorated(true);

    ProfilerUI frame = new ProfilerUI();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private ProfilerUI() {
    super("InternalFrameDemo");
    Profiler.instance().addObserver(this);

    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset,
        screenSize.width - inset * 2,
        screenSize.height - inset * 2);

    desktop = new JDesktopPane();
    setContentPane(desktop);
    setJMenuBar(createMenuBar());
    buildInternalFrames();
  }

  protected JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    //Set up the lone menu.
    JMenu menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_D);
    menuBar.add(menu);

    //Set up the first menu item.
    JMenuItem menuItem = new JMenuItem("Class count");
    menuItem.setMnemonic(KeyEvent.VK_C);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
    menuItem.setActionCommand(CLASS_COUNT_DIAGRAM_COMMAND);
    menuItem.addActionListener(this);
    menu.add(menuItem);

    //Set up the second menu item.
    menuItem = new JMenuItem("Quit");
    menuItem.setMnemonic(KeyEvent.VK_Q);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
    menuItem.setActionCommand("quit");
    menuItem.addActionListener(this);
    menu.add(menuItem);

    return menuBar;
  }

  //React to menu selections.
  public void actionPerformed(ActionEvent e) {
    if (CLASS_COUNT_DIAGRAM_COMMAND.equals(e.getActionCommand())) {
      classCountPieChartFrame.setVisible(true);
      classCountPieChartFrame.setLocation(10, 10);
    } else {
      quit();
    }
  }

  private void buildInternalFrames() {
    buildClassCountInternalFrame();
    buildEventLogInternalFrame();
  }

  private void buildClassCountInternalFrame() {
    classCountPieChart = new ClassCountPieChart();
    classCountPieChartFrame = new JInternalFrame("class count",
        true,
        true,
        true,
        true);
    classCountPieChartFrame.setContentPane(classCountPieChart);
    classCountPieChartFrame.setSize(300, 300);
    desktop.add(classCountPieChartFrame);
  }

  private void buildEventLogInternalFrame() {
    eventLogger = new EventLogger();
    eventLoggerFrame = new JInternalFrame("Event log",
        true,
        true,
        true,
        true);
    eventLoggerFrame.setContentPane(eventLogger);
    eventLoggerFrame.setVisible(true);
    eventLoggerFrame.setLocation(10, 10);
    eventLoggerFrame.setSize(600, 300);
    desktop.add(eventLoggerFrame);
  }

  //Quit the application.
  protected void quit() {
    System.exit(0);
  }


  @Override
  public void update(Observable o, Object event) {
//    profilingTree.addEvent((Event) event);
    eventLogger.log((Event) event);

    if (event instanceof ObjectInstantiatedEvent) {
      classCountPieChart.objectInstantiated(((ObjectInstantiatedEvent) event).getClassName(), ((ObjectInstantiatedEvent) event).getNewCount());
    }
  }
}
