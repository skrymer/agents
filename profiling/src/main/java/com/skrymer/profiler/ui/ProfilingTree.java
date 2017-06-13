package com.skrymer.profiler.ui;

import com.skrymer.profiler.events.Event;
import com.skrymer.profiler.events.MethodInvokedEvent;
import com.skrymer.profiler.events.ObjectInstantiatedEvent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.*;
import java.util.Enumeration;
import java.util.Optional;

/**
 * Tree structure
 *
 * - profiled classes
 *    |
 *    -- com.skrymer.someClass
 *        |
 *        -- Count: 10
 *        -- methodName
 *            |
 *            -- Invocation count: 42
 *            -- Avg duration: 42ms
 */
public class ProfilingTree extends JPanel {
  public static final int COUNT_LEAF_INDEX = 0;
  public static final String ROOT_NODE_TEXT = "Profiled classes";

  private DefaultMutableTreeNode rootNode;
  private DefaultTreeModel treeModel;

  public ProfilingTree(){
    buildProfilingTree();
  }

  private void buildProfilingTree() {
    rootNode = new DefaultMutableTreeNode(ROOT_NODE_TEXT);
    JTree tree = new JTree(rootNode);
    treeModel = new DefaultTreeModel(rootNode);
    tree.setModel(treeModel);
    tree.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
    tree.setCellRenderer(new ProfilingTreeCellRenderer(Color.BLACK, Color.RED));
    this.setLayout(new BorderLayout());
    this.add(tree, BorderLayout.CENTER);
  }

  public void addEvent(Event event) {
    if (event instanceof ObjectInstantiatedEvent) {
      handleObjectInstantiatedEvent((ObjectInstantiatedEvent) event);
    }

    if (event instanceof MethodInvokedEvent) {
      handleMethodInvokedEvent((MethodInvokedEvent) event);
    }

    treeModel.nodeChanged(rootNode);
  }

  private void handleObjectInstantiatedEvent(ObjectInstantiatedEvent objectInstantiatedEvent) {
    if(classNodeExists(objectInstantiatedEvent.getClassName())){
      updateClassNode(objectInstantiatedEvent);
    }else {
      rootNode.add(createClassNode(objectInstantiatedEvent));
    }
  }

  private void updateClassNode(ObjectInstantiatedEvent objectInstantiatedEvent) {
    findClassNode(objectInstantiatedEvent.getClassName()).ifPresent(node -> {
      DefaultMutableTreeNode countLeaf = (DefaultMutableTreeNode) node.getChildAt(COUNT_LEAF_INDEX);
      countLeaf.setUserObject("Count: " + objectInstantiatedEvent.getNewCount());
      treeModel.nodeChanged(countLeaf);
    });
  }

  private MutableTreeNode createClassNode(ObjectInstantiatedEvent objectInstantiatedEvent) {
    DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(objectInstantiatedEvent);
    DefaultMutableTreeNode countLeaf = new DefaultMutableTreeNode("Count: " + objectInstantiatedEvent.getNewCount());
    classNode.add(countLeaf);
    return classNode;
  }

  private Optional<DefaultMutableTreeNode> findClassNode(String className) {
    Enumeration enumeration = rootNode.breadthFirstEnumeration();

    while(enumeration.hasMoreElements()){
      DefaultMutableTreeNode currNode = (DefaultMutableTreeNode) enumeration.nextElement();

      if(currNode.getUserObject() instanceof ObjectInstantiatedEvent){
        ObjectInstantiatedEvent objectInstantiatedEvent = (ObjectInstantiatedEvent) currNode.getUserObject();

        if(objectInstantiatedEvent.getClassName().equals(className)) {
          return Optional.of(currNode);
        }
      }
    }

    return Optional.empty();
  }

  private boolean classNodeExists(String className) {
    return findClassNode(className).isPresent();
  }

  private void handleMethodInvokedEvent(MethodInvokedEvent methodInvokedEvent) {
    findClassNode(methodInvokedEvent.getClassName()).ifPresent(classNode -> {
      if(methodNodeExists(classNode, methodInvokedEvent.getMethodName())){
        updateMethodNode(classNode, methodInvokedEvent);
      }
      else{
        classNode.add(createMethodNode(methodInvokedEvent));
      }
      treeModel.nodeChanged(classNode);
    });
  }

  private MutableTreeNode createMethodNode(MethodInvokedEvent methodInvokedEvent) {
    DefaultMutableTreeNode methodNameNode = new DefaultMutableTreeNode(methodInvokedEvent);
    DefaultMutableTreeNode invocationCount = new DefaultMutableTreeNode("Invocation count: " + methodInvokedEvent.getInvocationCount());
    DefaultMutableTreeNode durationLeaf = new DefaultMutableTreeNode(methodInvokedEvent.getMethodCallDuration());
    methodNameNode.add(invocationCount);
    methodNameNode.add(durationLeaf);

    return methodNameNode;
  }

  private void updateMethodNode(DefaultMutableTreeNode classNode, MethodInvokedEvent methodInvokedEvent) {
    findMethodNode(classNode, methodInvokedEvent.getMethodName()).ifPresent(methodNode -> {
      methodNode.getFirstLeaf().setUserObject("Invocation count: " + methodInvokedEvent.getInvocationCount());
      methodNode.getLastLeaf().setUserObject(methodInvokedEvent.getMethodCallDuration());
      treeModel.nodeStructureChanged(methodNode);
    });
  }

  private Optional<DefaultMutableTreeNode> findMethodNode(DefaultMutableTreeNode classNode, String methodName) {
    Enumeration enumeration = classNode.breadthFirstEnumeration();

    while(enumeration.hasMoreElements()){
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();

      if(node.getUserObject() instanceof MethodInvokedEvent) {
        MethodInvokedEvent methodInvokedEvent = (MethodInvokedEvent) node.getUserObject();
        if(methodInvokedEvent.getMethodName().equals(methodName)) {
          return Optional.of(node);
        }
      }
    }

    return Optional.empty();
  }

  private boolean methodNodeExists(DefaultMutableTreeNode classNode, String methodName) {
    return findMethodNode(classNode, methodName).isPresent();
  }

}
