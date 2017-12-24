package com.programmingskillz.familytreexmlparser.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

  private String data;
  private TreeNode parent;
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

  public List<TreeNode> getChildren() {
    return children;
  }

  public void addChild(String data) {
    TreeNode newNode = new TreeNode(data);
    newNode.setParent(this);
    this.children.add(newNode);
  }
}