package com.jewelry.config.dialect;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect extends MySQLDialect {

  public CustomMySQLDialect() {
    super();

    registerFunction("match", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "MATCH(?1) AGAINST (?2 IN BOOLEAN MODE)"));
    registerFunction("match2", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "MATCH(?1, ?2) AGAINST (?3 IN BOOLEAN MODE)"));
    registerFunction("match3", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "MATCH(?1, ?2, ?3) AGAINST (?4 IN BOOLEAN MODE)"));
  }
}
