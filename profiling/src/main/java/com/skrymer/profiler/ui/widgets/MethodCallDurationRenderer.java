package com.skrymer.profiler.ui.widgets;

import com.skrymer.profiler.events.MethodInvokedEvent;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by skrymer on 27/04/17.
 */
public class MethodCallDurationRenderer implements CellRenderer {

  @Override
  public void render(DefaultMutableTreeNode node, ProfilingTreeCellRenderer cellRenderer) {
    MethodInvokedEvent.MethodCallDuration methodCallDuration = (MethodInvokedEvent.MethodCallDuration) node.getUserObject();
    cellRenderer.setDefaultColor();

    if(methodCallDuration.getDuration() > METHOD_CALL_DURATION_THRESHOLD){
      cellRenderer.setWarningColor();
    }
  }
}
