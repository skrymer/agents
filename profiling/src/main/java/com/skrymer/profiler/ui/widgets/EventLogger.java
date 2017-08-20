package com.skrymer.profiler.ui.widgets;

import com.skrymer.profiler.events.*;
import com.skrymer.profiler.events.Event;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrymer on 13/06/17.
 */
public class EventLogger extends JPanel {
  private JTextArea eventLogTextArea;

  public EventLogger(){
    buildEventLog();
  }

  private void buildEventLog() {
    eventLogTextArea = new JTextArea();
    eventLogTextArea.setMargin(new Insets(5,10,0,5));
    eventLogTextArea.setBackground(Color.BLACK);
    eventLogTextArea.setForeground(Color.GREEN);

    JScrollPane scrollPane = new JScrollPane(eventLogTextArea);

    this.setLayout(new BorderLayout());
    this.add(scrollPane, BorderLayout.CENTER);
  }

  public void log(Event event){
    eventLogTextArea.append(event.getDescription());
    eventLogTextArea.setCaretPosition(eventLogTextArea.getText().length());
  }
}
