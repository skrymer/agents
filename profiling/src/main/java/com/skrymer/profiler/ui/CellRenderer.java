package com.skrymer.profiler.ui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by skrymer on 27/04/17.
 */
public interface CellRenderer {
  //  TODO convert this to a property
  int METHOD_CALL_DURATION_THRESHOLD = 50;

  /**
   * Renders the given profilingTreeCellRenderer based on the userobject associated with the given node
   * @param node
   * @param profilingTreeCellRenderer
   */
  void render(DefaultMutableTreeNode node, ProfilingTreeCellRenderer profilingTreeCellRenderer);
}
