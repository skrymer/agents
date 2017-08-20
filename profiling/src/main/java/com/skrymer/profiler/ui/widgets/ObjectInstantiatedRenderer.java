package com.skrymer.profiler.ui.widgets;

import com.skrymer.profiler.events.ObjectInstantiatedEvent;
import com.skrymer.profiler.events.MethodInvokedEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * Created by skrymer on 5/06/17.
 */
public class ObjectInstantiatedRenderer implements CellRenderer {

  /**
   * Renders a class node
   *
   * @param node
   * @param profilingTreeCellRenderer
   */
  @Override
  public void render(DefaultMutableTreeNode node, ProfilingTreeCellRenderer profilingTreeCellRenderer) {
    renderObjectInstantiatedEventNode(node, profilingTreeCellRenderer);
  }

  private void renderObjectInstantiatedEventNode(DefaultMutableTreeNode node, ProfilingTreeCellRenderer profilingTreeCellRenderer) {
    ObjectInstantiatedEvent objectInstantiatedEvent = (ObjectInstantiatedEvent) node.getUserObject();
    profilingTreeCellRenderer.setText(objectInstantiatedEvent.getClassName());
    profilingTreeCellRenderer.setDefaultColor();

    Enumeration enumeration = node.breadthFirstEnumeration();

    while(enumeration.hasMoreElements()){
      DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) enumeration.nextElement();

      if(currNode.getUserObject() instanceof MethodInvokedEvent){
        MethodInvokedEvent methodInvokedEvent = (MethodInvokedEvent) currNode.getUserObject();

        if(methodInvokedEvent.getMethodCallDuration().getDuration() > METHOD_CALL_DURATION_THRESHOLD){
          profilingTreeCellRenderer.setWarningColor();
          break;
        }
      }
    }
  }
}
