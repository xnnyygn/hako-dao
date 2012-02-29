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
package org.hako.dao.mapper.implict;

import java.lang.reflect.Field;


/**
 * Implicit entity mapper.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.0.0
 */
public class ImplicitEntityMapper {

  public EntityMeta setup(Class<?> entityCls) {
    ImplicitEntityBuilder builder = new ImplicitEntityBuilder();
    builder.setTableName(entityCls.getSimpleName());
    builder.addField("id");
    for (Field f : entityCls.getFields()) {
      builder.addField(f.getName(), f.getName());
    }
    postSetup(entityCls, builder);
    return builder.build();
  }

  private void postSetup(Class<?> entityCls, ImplicitEntityBuilder builder) {
    try {
      entityCls.getMethod("postSetup", ImplicitEntityBuilder.class).invoke(
          null, builder);
    } catch (Exception e) {
      // ignore exception
    }
  }

}
