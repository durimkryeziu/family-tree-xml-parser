package com.programmingskillz.service;

import com.programmingskillz.exceptions.MoreThanOneRootException;
import com.programmingskillz.repository.DocumentRepository;
import com.programmingskillz.exceptions.RootNotFoundException;
import com.programmingskillz.model.Entries;
import com.programmingskillz.model.Entry;
import com.programmingskillz.util.EntryUtils;
import com.programmingskillz.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Durim Kryeziu
 * @since Mar 08, 2017.
 */
@Service
public class DocumentService {

    private DocumentRepository repository;

    private int count = 0;

    @Autowired
    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public void insertDoc(Entries entries) throws MoreThanOneRootException, RootNotFoundException {

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

        count = 0;

        createTree(rootNode, childrenEntries);

        System.out.println(count);
        repository.saveDoc(rootNode);
    }

    public void createTree(TreeNode root, List<Entry> children) {
        for (Entry child : children) {
            count++;
            if (root.getData().equals(child.getParentName())) {
                root.addChild(child.getValue());
                List<TreeNode> rootChildren = root.getChildren();
                createTree(rootChildren.get(rootChildren.size() - 1), children);
            }
        }
    }
}