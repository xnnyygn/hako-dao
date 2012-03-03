package org.hako.dao.sql.expression.value;

public class ValueFactory {

  public static LongValue create(long l) {
    return new LongValue(l);
  }

  public static StringValue create(String s) {
    return new StringValue(s);
  }

  public static ObjectValue create(Object obj) {
    return new ObjectValue(obj);
  }
}
