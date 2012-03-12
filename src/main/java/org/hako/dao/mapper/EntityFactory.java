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
package org.hako.dao.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hako.None;
import org.hako.Option;
import org.hako.Some;

/**
 * Factory of entity.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityFactory<T> {

  private static final Log logger = LogFactory.getLog(EntityFactory.class);
  private final Class<T> entityClass;
  private final List<Setter> setters;

  /**
   * Default constructor.
   */
  public EntityFactory(Class<T> entityClass) {
    super();
    this.entityClass = entityClass;
    this.setters = filterSetter(entityClass);
  }

  /**
   * Filter setters.
   * 
   * @param clazz
   * @return setters
   */
  private List<Setter> filterSetter(Class<?> clazz) {
    List<Setter> setters = new ArrayList<Setter>();
    // filter method setters
    for (Method m : clazz.getMethods()) {
      String name = m.getName();
      if (name.startsWith("set") && name.length() > 3
          && m.getParameterTypes().length == 1) {
        setters.add(new MethodSetter(m));
      }
    }
    // filter field setters
    for (Field f : clazz.getFields()) {
      setters.add(new FieldSetter(f));
    }
    return setters;
  }

  /**
   * Create instance with properties.
   * 
   * @param props properties
   * @return instance
   * @throws InstantiationException if failed to create instance
   * @throws IllegalAccessException if failed to create instance
   * @see Class#newInstance()
   */
  public T create(Map<String, Object> props) throws InstantiationException,
      IllegalAccessException {
    T instance = entityClass.newInstance();
    Map<String, Object> lowerCasedKeyProps = changeKeyToLowerCase(props);
    for (Setter s : setters) {
      String lowerCasedPropertyName = s.getPropertyName().toLowerCase();
      if (lowerCasedKeyProps.containsKey(lowerCasedPropertyName)) {
        s.set(instance, lowerCasedKeyProps.get(lowerCasedPropertyName));
      }
    }
    return instance;
  }

  /**
   * Create from some properties or none.
   * 
   * @param propsOpt properties option
   * @return some instance or none
   */
  public Option<T> create(Option<Map<String, Object>> propsOpt) {
    if (propsOpt.hasValue()) {
      try {
        return new Some<T>(create(propsOpt.get()));
      } catch (Exception e) {
        logger.warn("failed to create instance of [" + entityClass + "]", e);
      }
    }
    return new None<T>();
  }

  /**
   * Create form properties list.
   * 
   * @param propsList properties list
   * @return instance list
   */
  public List<T> create(List<Map<String, Object>> propsList) {
    List<T> instances = new ArrayList<T>();
    for (Map<String, Object> props : propsList) {
      try {
        instances.add(create(props));
      } catch (Exception e) {
        logger.warn("failed to create instance of [" + entityClass + "]", e);
      }
    }
    return instances;
  }

  /**
   * Change key to lower case.
   * 
   * @param srcMap source map
   * @return map with lower cased key
   */
  private Map<String, Object> changeKeyToLowerCase(Map<String, Object> srcMap) {
    Map<String, Object> destMap = new HashMap<String, Object>();
    for (Map.Entry<String, Object> e : srcMap.entrySet()) {
      destMap.put(e.getKey().toLowerCase(), e.getValue());
    }
    return destMap;
  }

}
