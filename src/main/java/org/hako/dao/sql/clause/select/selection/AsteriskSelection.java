package org.hako.dao.sql.clause.select.selection;

import java.util.List;

public class AsteriskSelection implements Selection {

  public String toPrepared() {
    return "*";
  }

  public List<Object> getParams() {
    return NO_PARAM;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    if (obj.getClass() != getClass()) return false;
    return true;
  }

}
