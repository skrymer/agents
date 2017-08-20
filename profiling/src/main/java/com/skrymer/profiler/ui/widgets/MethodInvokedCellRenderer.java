package com.skrymer.profiler.ui.widgets;

import com.skrymer.profiler.events.MethodInvokedEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * Created by skrymer on 27/04/17.
 */
public class MethodInvokedCellRenderer implements CellRenderer {

  @Override
  public void render(DefaultMutableTreeNode node, ProfilingTreeCellRenderer cellRenderer) {
    MethodInvokedEvent methodInvokedEvent = (MethodInvokedEvent) node.getUserObject();
    cellRenderer.setText(methodInvokedEvent.getMethodName());
    cellRenderer.setDefaultColor();

    Enumeration enumeration = node.breadthFirstEnumeration();

    while(enumeration.hasMoreElements()){
      DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) enumeration.nextElement();

      if(currNode.getUserObject() instanceof MethodInvokedEvent.MethodCallDuration){
        MethodInvokedEvent.MethodCallDuration methodCallDuration = (MethodInvokedEvent.MethodCallDuration) currNode.getUserObject();
        if(methodCallDuration.getDuration() > METHOD_CALL_DURATION_THRESHOLD){
          cellRenderer.setWarningColor();
          break;
        }
      }
    }
  }
}
