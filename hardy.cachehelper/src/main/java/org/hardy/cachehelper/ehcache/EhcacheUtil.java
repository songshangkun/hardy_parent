package org.hardy.cachehelper.ehcache;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.sf.ehcache.CacheManager;

public class EhcacheUtil
{
  private static EhcacheUtil eu = new EhcacheUtil();
  
  public static CacheManager config(String configPath)
  {
    System.out.println("ehcachengin config=" + configPath);
    URL url = null;
    if (configPath.startsWith("classPath:"))
    {
      url = eu.getClass().getResource(configPath.substring(10, configPath.length()));
      System.out.println("URL_ehcache=" + url);
      return CacheManager.create(url);
    }
    if (configPath.startsWith("classPathRoot:"))
    {
      url = Object.class.getResource(configPath.substring(14, configPath.length()));
      System.out.println("URL_ehcache=" + url);
      return CacheManager.create(url);
    }
    if (configPath.startsWith("file:"))
    {
      InputStream fis = null;
      try
      {
        String path = configPath.substring(5, configPath.length());
        fis = new FileInputStream(new File(path));
        System.out.println("file_ehcache=" + path);
        return CacheManager.create(fis);
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      finally
      {
        try
        {
          fis.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}

