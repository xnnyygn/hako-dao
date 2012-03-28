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
package org.hako.dao;

import java.util.Map;

import org.hako.dao.mapping.EntityMeta;

/**
 * A wrapper class for class and entity meta association.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityMappings {

  private final Map<Class<?>, EntityMeta> entityMetaMap;

  /**
   * Create with entity meta map.
   * 
   * @param entityMetaMap
   */
  public EntityMappings(Map<Class<?>, EntityMeta> entityMetaMap) {
    super();
    this.entityMetaMap = entityMetaMap;
  }

  /**
   * Find entity meta by class. If not found, throw
   * {@link MappingNotFoundException}.
   * 
   * @param clazz
   * @return entity meta of class
   * @throws MappingNotFoundException if entity meta not found
   * @see #entityMetaMap
   */
  public EntityMeta findByClass(Class<?> clazz)
      throws MappingNotFoundException {
    if (!entityMetaMap.containsKey(clazz)) {
      throw new MappingNotFoundException("mapping for class ["
          + clazz.getName() + "] not found");
    }
    return entityMetaMap.get(clazz);
  }
  
}
