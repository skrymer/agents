package com.skrymer.profiler.ui.widgets;

import com.skrymer.profiler.events.ObjectInstantiatedEvent;
import com.skrymer.profiler.events.MethodInvokedEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Optional;

public class RenderFactory {

  /**
   *
   * @param node
   * @return renderer based on the event type associated with the given node
   */
  public static Optional<CellRenderer> getRenderer(DefaultMutableTreeNode node){
     if(node.getUserObject() instanceof MethodInvokedEvent){
       return Optional.of(new MethodInvokedCellRenderer());
     }

     if(node.getUserObject() instanceof MethodInvokedEvent.MethodCallDuration){
       return Optional.of(new MethodCallDurationRenderer());
     }

     if(node.getUserObject() instanceof ObjectInstantiatedEvent){
       return Optional.of(new ObjectInstantiatedRenderer());
     }

     return Optional.empty();
   }
}
