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
package org.hako.dao.mapper.annotation;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A setter use setter method to set property.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class MethodSetter implements Setter {

  private static final Log logger = LogFactory.getLog(MethodSetter.class);
  private final Method setter;
  private final String propertyName;

  /**
   * Create.
   * 
   * @param setter
   */
  public MethodSetter(Method setter) {
    super();
    this.setter = setter;
    this.propertyName = StringUtils.uncapitalize(setter.getName().substring(3));
  }

  public <T> T set(T instance, Object value) {
    try {
      setter.invoke(instance, value);
    } catch (Exception e) {
      logger.warn("failed to set value", e);
    }
    return instance;
  }

  public String getPropertyName() {
    return propertyName;
  }

}
