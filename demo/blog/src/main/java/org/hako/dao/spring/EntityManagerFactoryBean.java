package org.hako.dao.spring;

import java.util.List;

import org.hako.dao.EntityManager;
import org.hako.dao.db.client.DbClient;
import org.hako.dao.mapper.DataDictionaryStrategy;
import org.hako.dao.mapper.DefaultDataDictionaryStrategy;
import org.hako.dao.mapper.EntityMapper;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory bean of {@link EntityManager}.
 * 
 * @author xnnyygn
 * @version %I%, %G%
 * @since 1.1.0
 */
public class EntityManagerFactoryBean implements FactoryBean {

  private List<Class<?>> classes;
  private DbClient dbClient;
  private DataDictionaryStrategy dataDictionaryStrategy =
      new DefaultDataDictionaryStrategy();

  /**
   * Use {@link EntityMapper} to map classes and create {@link EntityManager}.
   * 
   * @see EntityMapper#map(List)
   * @see EntityManager#EntityManager(DbClient, java.util.Map)
   * @see org.springframework.beans.factory.FactoryBean#getObject()
   */
  public Object getObject() throws Exception {
    EntityMapper entityMapper = new EntityMapper();
    entityMapper.updateDataDictionaryStrategy(dataDictionaryStrategy);
    return new EntityManager(dbClient, entityMapper.map(classes));
  }

  public Class<?> getObjectType() {
    return EntityManager.class;
  }

  public boolean isSingleton() {
    return true;
  }

  /**
   * Setter method for property <tt>dataDictionaryStrategy</tt>.
   * 
   * @param dataDictionaryStrategy value to be assigned to property
   *        dataDictionaryStrategy
   */
  public void setDataDictionaryStrategy(
      DataDictionaryStrategy dataDictionaryStrategy) {
    this.dataDictionaryStrategy = dataDictionaryStrategy;
  }

  /**
   * Setter method for property <tt>dbClient</tt>.
   * 
   * @param dbClient value to be assigned to property dbClient
   */
  public void setDbClient(DbClient dbClient) {
    this.dbClient = dbClient;
  }

  /**
   * Setter method for property <tt>classes</tt>.
   * 
   * @param classes value to be assigned to property classes
   */
  public void setClasses(List<Class<?>> classes) {
    this.classes = classes;
  }

}
