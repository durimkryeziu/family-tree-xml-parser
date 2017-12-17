package com.programmingskillz.familytreexmlparser.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

  private String data;
  private TreeNode parent = null;
  private List<TreeNode> children;

  public TreeNode(String data) {
    this.data = data;
    this.children = new ArrayList<>();
  }

  public String getData() {
    return data;
  }

  private void setParent(TreeNode parent) {
    this.parent = parent;
  }

  public TreeNode getParent() {
    return parent;
  }

  public void addChildren(List<TreeNode> children) {
    for (TreeNode child : children) {
      child.setParent(this);
    }
    this.children.addAll(children);
  }

  public List<TreeNode> getChildren() {
    return children;
  }

  public void addChild(TreeNode child) {
    child.setParent(this);
    this.children.add(child);
  }

  public void addChild(String data) {
    TreeNode newNode = new TreeNode(data);
    newNode.setParent(this);
    this.children.add(newNode);
  }
}