package com.programmingskillz.familytreexmlparser.data.repository;

import com.programmingskillz.familytreexmlparser.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Iterator;

/**
 * @author Durim Kryeziu
 */
@Repository
public class DocumentRepository {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public DocumentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveDoc(TreeNode entries) {
    insert(entries, null);
  }

  public void insert(TreeNode node, Integer parentId) {
    parentId = saveEntry(node.getData(), parentId);

    Iterator<TreeNode> iterator = node.getChildren().iterator();

    while (iterator.hasNext()) {
      insert(iterator.next(), parentId);
    }
  }

  public int saveEntry(String name, Integer parentId) {
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