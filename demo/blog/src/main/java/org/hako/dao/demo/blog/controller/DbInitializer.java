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
package org.hako.dao.demo.blog.controller;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.hako.dao.db.vendor.DbVendor;
import org.springframework.beans.factory.InitializingBean;

/**
 * Database initializer.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class DbInitializer implements InitializingBean {

  private DbVendor dbVendor;
  private String initSqlPath;

  /**
   * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  public void afterPropertiesSet() throws Exception {
    String initSqls = IOUtils.toString(new FileInputStream(initSqlPath));
    dbVendor.connect().createStatement().execute(initSqls);
  }

  /**
   * Setter method for property <tt>dbVendor</tt>.
   * 
   * @param dbVendor value to be assigned to property dbVendor
   */
  public void setDbVendor(DbVendor dbVendor) {
    this.dbVendor = dbVendor;
  }



  /**
   * Setter method for property <tt>initSqlPath</tt>.
   * 
   * @param initSqlPath value to be assigned to property initSqlPath
   */
  public void setInitSqlPath(String initSqlPath) {
    this.initSqlPath = initSqlPath;
  }

}
