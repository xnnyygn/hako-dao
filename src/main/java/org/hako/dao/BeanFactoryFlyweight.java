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

import java.util.HashMap;
import java.util.Map;

import org.hako.dao.mapper.EntityFactory;

/**
 * Bean factory flyweight. This class will cache factory of class.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class BeanFactoryFlyweight {

  private static Map<Class<?>, EntityFactory<?>> factoryCache =
      new HashMap<Class<?>, EntityFactory<?>>();

  /**
   * Get bean factory by class. If not found, bean factory will be created and
   * put into {@link #factoryCache}.
   * 
   * @param clazz
   * @return bean factory
   */
  // TODO maybe cause synchronize problem
  @SuppressWarnings("unchecked")
  public static <T> EntityFactory<T> getBeanFactory(Class<T> clazz) {
    if (!factoryCache.containsKey(clazz)) {
      factoryCache.put(clazz, new EntityFactory<T>(clazz));
    }
    return (EntityFactory<T>) factoryCache.get(clazz);
  }

}
