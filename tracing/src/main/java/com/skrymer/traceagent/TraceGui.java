package com.skrymer.traceagent;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by skrymer on 4/06/17.
 */
public class TraceGui extends JFrame implements Observer {
  private DefaultListModel<Tracer.ObjectTrace> listModel;
  private final JTextArea traceInfo;
  final JList<Tracer.ObjectTrace> tracedClassesList;

  public static void showGui(){
    new TraceGui();
  }

  private TraceGui(){
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setTitle("Trace GUI");
    traceInfo = new JTextArea();
    traceInfo.setForeground(Color.GREEN);
    traceInfo.setBackground(Color.BLACK);
    traceInfo.setMargin(new Insets(5,5,5,5));
    listModel = new DefaultListModel();
    tracedClassesList = new JList<>(listModel);

    buildContent();
    setSize(1000, 600);
    setVisible(true);
    Tracer.getTracer().addObserver(this);
  }

  private void buildContent() {
    tracedClassesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    tracedClassesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    tracedClassesList.addListSelectionListener(e -> {
      traceInfo.setText("");
      Tracer.ObjectTrace objectTrace = tracedClassesList.getSelectedValue();
      objectTrace.getInvocationTraces().stream().forEach(callTrace -> {
        traceInfo.append(callTrace.toString());
        traceInfo.append("\n");
      });
    });

    JScrollPane listScroller = new JScrollPane(tracedClassesList);
    listScroller.setPreferredSize(new Dimension(350, 560));

    JPanel tracedClassesPanel = new JPanel();
    tracedClassesPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Traced classes")
    );
    tracedClassesPanel.add(listScroller);
    add(tracedClassesPanel, BorderLayout.WEST);

    JPanel infoPanel = new JPanel();
    infoPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK), "TRACE")
    );
    infoPanel.setLayout(new BorderLayout());

    JScrollPane traceInfoScroll = new JScrollPane(traceInfo);
    infoPanel.add(traceInfoScroll);

    add(infoPanel, BorderLayout.CENTER);
  }

  @Override
  public void update(Observable o, Object arg) {
    synchronized (this) {
      if (listModel.indexOf(arg) != -1) {
        listModel.setElementAt((Tracer.ObjectTrace) arg, listModel.indexOf(arg));
      }
      else {
        listModel.addElement((Tracer.ObjectTrace) arg);
      }
    }

    traceInfo.setText("");
    Tracer.ObjectTrace objectTrace = tracedClassesList.getSelectedValue();
    objectTrace.getInvocationTraces().stream().forEach(callTrace -> {
      traceInfo.append(callTrace.toString());
      traceInfo.append("\n");
    });
    traceInfo.setCaretPosition(traceInfo.getText().length());
  }
}
