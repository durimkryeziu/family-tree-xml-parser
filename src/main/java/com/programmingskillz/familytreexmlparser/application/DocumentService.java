package com.programmingskillz.familytreexmlparser.application;

import com.programmingskillz.familytreexmlparser.application.validator.EntriesValidator;
import com.programmingskillz.familytreexmlparser.domain.Entries;
import com.programmingskillz.familytreexmlparser.domain.Entry;
import com.programmingskillz.familytreexmlparser.infrastructure.DocumentRepository;
import com.programmingskillz.familytreexmlparser.domain.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

  private final EntriesValidator validator;
  private final DocumentRepository repository;

  @Autowired
  public DocumentService(EntriesValidator validator, DocumentRepository repository) {
    this.validator = validator;
    this.repository = repository;
  }

  public void insertDoc(Entries entries) {
    validator.validate(entries);
    TreeNode rootNode = new TreeNode(entries.getRoot().getValue());
    createTree(rootNode, entries.getChildren());
    repository.saveDoc(rootNode);
  }

  private void createTree(TreeNode root, List<Entry> children) {
    for (Entry child : children) {
      if (root.getData().equals(child.getParentName())) {
        root.addChild(child.getValue());
        List<TreeNode> rootChildren = root.getChildren();
        createTree(rootChildren.get(rootChildren.size() - 1), children);
      }
    }
  }
}