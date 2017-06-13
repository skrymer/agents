package com.skrymer.profiler.ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Rename!
 */
public class ProfilingTreeCellRenderer extends DefaultTreeCellRenderer {
  private final Color defaultTextColor;
  private final Color warningTextColor;

  public ProfilingTreeCellRenderer(Color defaultTextColor, Color warningTextColor){
    this.defaultTextColor = defaultTextColor;
    this.warningTextColor = warningTextColor;
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

    if(value instanceof DefaultMutableTreeNode){
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      RenderFactory.getRenderer(node).ifPresent(cellRenderer ->  cellRenderer.render(node, this));
    }

    return this;
  }

  void setDefaultColor() {
    setForeground(defaultTextColor);
  }

  void setWarningColor() {
    setForeground(warningTextColor);
  }
}
