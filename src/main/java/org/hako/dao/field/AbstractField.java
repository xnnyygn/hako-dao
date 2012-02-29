/**
 * Copyright 2012 XnnYygn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.hako.dao.field;

import org.hako.dao.Entity;

/**
 * Abstract field.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public abstract class AbstractField<T> implements Field<T> {

  protected final Entity owner;
  protected final FieldName name;
  protected final boolean pk;

  /**
   * Create.
   * 
   * @param pwner
   * @param name
   * @param pk
   */
  public AbstractField(Entity pwner, FieldName name, boolean pk) {
    super();
    this.owner = pwner;
    this.name = name;
    this.pk = pk;
  }

  public String getColumnName() {
    return name.getColumnName();
  }

  public String getPropertyName() {
    return name.getPrpertyName();
  }

  public boolean isPk() {
    return pk;
  }

  public Entity getOwner() {
    return owner;
  }

}
