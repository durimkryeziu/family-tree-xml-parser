package com.programmingskillz.familytreexmlparser.infrastructure;

import com.programmingskillz.familytreexmlparser.domain.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class DocumentRepository {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public DocumentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveDoc(TreeNode entries) {
    saveEntries(entries, null);
  }

  private void saveEntries(TreeNode node, Integer parentId) {
    parentId = saveEntry(node.getData(), parentId);

    for (TreeNode treeNode : node.getChildren()) {
      saveEntries(treeNode, parentId);
    }
  }

  private int saveEntry(String name, Integer parentId) {
    String query = "INSERT INTO ENTRY (NAME, PARENT_ID) VALUES (?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, name);
      ps.setObject(2, parentId);
      return ps;
    }, keyHolder);

    return keyHolder.getKey().intValue();
  }
}