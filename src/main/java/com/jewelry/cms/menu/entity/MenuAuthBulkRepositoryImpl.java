package com.jewelry.cms.menu.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuAuthBulkRepositoryImpl {

  private final JdbcTemplate jdbcTemplate;

  public int saveAll(int batchCount, final List<MenuAuth> insertList){
    jdbcTemplate.batchUpdate(
        "INSERT INTO TB_AUTH_MENU ( " +
            "USER_ID, MENU_ID, ACCESS_AUTH, WRITE_AUTH, VIEW_AUTH, MODIFY_AUTH, REMOVE_AUTH, INPT_ID, INPT_DT " +
            ") VALUES ( " +
            "?, ?, ?, ?, ?, ?, ?, ?, ? " +
            ")"
        , new BatchPreparedStatementSetter(){
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, insertList.get(i).getUserId());
            ps.setString(2, insertList.get(i).getMenuId());
            ps.setString(3, insertList.get(i).getAccessAuth());
            ps.setString(4, insertList.get(i).getWriteAuth());
            ps.setString(5, insertList.get(i).getViewAuth());
            ps.setString(6, insertList.get(i).getModifyAuth());
            ps.setString(7, insertList.get(i).getRemoveAuth());
            ps.setString(8, insertList.get(i).getInptId());
            ps.setTimestamp(9, Timestamp.valueOf(insertList.get(i).getInptDt()));
          }

          @Override
          public int getBatchSize() {
            return batchCount;
          }
        }
    );
    insertList.clear();
    return batchCount;
  }
}
