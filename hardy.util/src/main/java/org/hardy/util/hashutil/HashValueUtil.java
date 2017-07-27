package org.hardy.util.hashutil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hardy.reflex.SpringReflext;
import org.springframework.beans.NotReadablePropertyException;
import com.song.tool.md5.MD5Util;

/**
 * 自定义对象Hash值,
 * 对对象的所有属性逐一获取hash值并拼装
 * @author 09
 *
 */
public class HashValueUtil{
/**
 * 对集合对象取他的自定义hash值
 * @param sb
 * @param objs
 * @return
 */
  @SuppressWarnings("rawtypes")
public static String hash(StringBuilder sb, Object... objs)
  {
    for (Object obj : objs)
    {
      sb.append("-");
      if (obj != null)
      {
        if ((obj.getClass() == Integer.TYPE) || (obj.getClass() == Long.TYPE) || (obj.getClass() == Short.TYPE) || (obj.getClass() == Byte.TYPE) || (obj.getClass() == Float.TYPE) || (obj.getClass() == Double.TYPE) || (obj.getClass() == Character.TYPE) || (obj.getClass() == Boolean.TYPE) || ((obj instanceof Number)) || ((obj instanceof String)))
        {
          sb.append(obj.toString().hashCode());
        }
        else if ((obj instanceof Date))
        {
          sb.append(((Date)obj).getTime());
        }
        else if ((obj instanceof int[]))
        {
          sb.append(Arrays.toString((int[])obj));
        }
        else if ((obj instanceof short[]))
        {
          sb.append(Arrays.toString((short[])obj));
        }
        else if ((obj instanceof byte[]))
        {
          sb.append(Arrays.toString((byte[])obj));
        }
        else if ((obj instanceof char[]))
        {
          sb.append(Arrays.toString((char[])obj));
        }
        else if ((obj instanceof float[]))
        {
          sb.append(Arrays.toString((float[])obj));
        }
        else if ((obj instanceof double[]))
        {
          sb.append(Arrays.toString((double[])obj));
        }
        else if ((obj instanceof boolean[]))
        {
          sb.append(Arrays.toString((boolean[])obj));
        }
        else if ((obj instanceof long[]))
        {
          sb.append(Arrays.toString((long[])obj));
        }
        else if ((obj instanceof Object[]))
        {
          sb.append(Arrays.toString((Object[])obj));
        }
        else
        {
          Map map;
          if ((obj instanceof Map))
          {
            map = (Map)obj;
            for (Object key : map.keySet())
            {
              hash(sb, new Object[] { key });
              hash(sb, new Object[] { map.get(key) });
            }
          }
          else if ((obj instanceof Set))
          {
            Set set = (Set)obj;
            for (Object entry : set) {
              hash(sb, new Object[] { entry });
            }
          }
          else if ((obj instanceof List))
          {
            List list = (List)obj;
            for (Object entry : list) {
              hash(sb, new Object[] { entry });
            }
          }
          else
          {
            Field[] fs = obj.getClass().getDeclaredFields();
            for (Field f : fs)
            {
              Object result = null;
              try
              {
                result = SpringReflext.get(obj, f.getName());
              }
              catch (NotReadablePropertyException e)
              {
                sb.append(f.getName()).append("=");
              }
              if (result != null) {
                hash(sb, new Object[] { result });
              } else {
                sb.append("null");
              }
            }
          }
        }
      }
      else {
        sb.append("null");
      }
    }
    return sb.toString();
  }
  
  public static String md5Hash(Object... objs)
  {
    return MD5Util.getMD5String(hash(new StringBuilder(), objs));
  }
  
  public static int md5HashInt(Object... objs)
  {
    return MD5Util.getMD5String(hash(new StringBuilder(), objs)).hashCode();
  }
  
  
}

