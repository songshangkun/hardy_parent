package org.hardy.cachehelper.ehcache;

 

import java.lang.reflect.Method;

import org.hardy.statics.cache.annotation.Ehcache;
import org.hardy.statics.ope.OpeEnum;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
 * 操作ehcache的引擎
 * @author 09
 *
 */
public class Ehcachengine
{
  /**
   * Ehcache管理	
   */
  private CacheManager manager;
  /**
   * 配置文件路径
   */
  private String configPath;
  /**
   * 获取缓存唯一键值的接口
   */
  private EhcacheUniqueKey key;
  
  public CacheManager getManager()
  {
    return this.manager;
  }
  
  public void setManager(CacheManager manager)
  {
    this.manager = manager;
  }
  
  public String getConfigPath()
  {
    return this.configPath;
  }
  
  public Ehcachengine() {}
  
  public Ehcachengine(String configPath)
  {
    this.configPath = configPath;
  }
  
  public void setConfigPath(String configPath)
  {
    this.configPath = configPath;
    this.manager = EhcacheUtil.config(configPath);
  }
  
  /**
   * 对方法标注ehcache的处理
   * @param method  方法
   * @param value   结果集的值
   * @param args  方法参数
   * @return
   */
  @SuppressWarnings("unchecked")
public <T> T ope(Method method, Object value, Object... args)
  {
    if (method.isAnnotationPresent(Ehcache.class))
    {
      String principal = args.length == 1 ? key.KEY(new Object[] { method.getName(), args[0] }) : args.length == 0 ? method.getName() : key.KEY(new Object[] { method.getName(), args });
      Ehcache ec = (Ehcache)method.getAnnotation(Ehcache.class);
      if ((ec.config() != null) && (!"".equals(ec.config().trim()))) {
        this.manager = EhcacheUtil.config(ec.config());
      }
      Cache cache = this.manager.getCache(ec.cacheName());
      Element ele = null;
      switch (ec.operator())
      {
      case FIND: 
        try
        {
          ele = cache.get(principal);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          return null;
        }
        if (ele != null) {
          return (T)ele.getObjectValue();
        }
        return null;
      case ADD: 
        ele = new Element(principal, value);
        cache.put(ele);
        return (T) cache.get(principal);
      case DELETE: 
        ele = cache.get(principal);
        boolean b = cache.remove(principal);
        if (b) {
          return (T) new Boolean(true);
        }
        return (T) new Boolean(false);
      case UPDATE: 
        ele = new Element(principal, value);
        cache.put(ele);
        return (T) cache.get(principal);
      case REMOUVEAll: 
        cache.removeAll();
        return null;
      case FINDAll: 
        return (T) cache.getAll(cache.getKeys());
      }
      return null;
    }
    return null;
  }
  
  /**
   * 直接根据ehcache配置文件处理
   * @param cacheName
   * @param cacheKey
   * @param cacheOpe
   * @param value
   * @param args
   * @return
   */
  @SuppressWarnings("unchecked")
public <T> T ope(String cacheName, String cacheKey, OpeEnum cacheOpe, Object value, Object... args)
  {
    String principal = args.length == 1 ? key.KEY(new Object[] { cacheKey, args[0] }) : args.length == 0 ? cacheKey : key.KEY(new Object[] { cacheKey, args });
    Cache cache = this.manager.getCache(cacheName);
    Element ele = null;
    switch (cacheOpe)
    {
    case FIND: 
      try
      {
        ele = cache.get(principal);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        return null;
      }
      if (ele != null) {
        return (T)ele.getObjectValue();
      }
      return null;
    case ADD: 
      ele = new Element(principal, value);
      cache.put(ele);
      return (T) cache.get(principal);
    case DELETE: 
      ele = cache.get(principal);
      boolean b = cache.remove(principal);
      if (b) {
        return (T) new Boolean(true);
      }
      return (T) new Boolean(false);
    case UPDATE: 
      ele = new Element(principal, value);
      cache.put(ele);
      return (T) cache.get(principal);
    case REMOUVEAll: 
      cache.removeAll();
      return null;
    case FINDAll: 
      return (T) cache.getAll(cache.getKeys());
    }
    return null;
  }
}

