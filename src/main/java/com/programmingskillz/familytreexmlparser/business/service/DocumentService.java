package com.programmingskillz.familytreexmlparser.business.service;

import com.programmingskillz.familytreexmlparser.business.domain.Entries;
import com.programmingskillz.familytreexmlparser.business.domain.Entry;
import com.programmingskillz.familytreexmlparser.business.exception.MoreThanOneRootException;
import com.programmingskillz.familytreexmlparser.business.exception.RootNotFoundException;
import com.programmingskillz.familytreexmlparser.data.repository.DocumentRepository;
import com.programmingskillz.familytreexmlparser.util.EntryUtils;
import com.programmingskillz.familytreexmlparser.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Durim Kryeziu
 */
@Service
public class DocumentService {

  private DocumentRepository repository;

  @Autowired
  public DocumentService(DocumentRepository repository) {
    this.repository = repository;
  }

  public void insertDoc(Entries entries) {

    if (entries == null) {
      throw new IllegalArgumentException("Entries cannot be null");
    }

    Entry root = EntryUtils.getRootEntry(entries);

    if (root == null) {
      throw new RootNotFoundException();
    }

    if (!EntryUtils.isOneRoot(entries)) {
      throw new MoreThanOneRootException();
    }

    List<Entry> childrenEntries = EntryUtils.getChildrenEntries(entries);

    TreeNode rootNode = new TreeNode(root.getValue());

    createTree(rootNode, childrenEntries);

    repository.saveDoc(rootNode);
  }

  public void createTree(TreeNode root, List<Entry> children) {
    for (Entry child : children) {
      if (root.getData().equals(child.getParentName())) {
        root.addChild(child.getValue());
        List<TreeNode> rootChildren = root.getChildren();
        createTree(rootChildren.get(rootChildren.size() - 1), children);
      }
    }
  }
}