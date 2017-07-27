package org.hardy.test.jsonhelper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	  private Object[] objs;
	  private int age;
	  private Date[] dars;
	  private List<Date> dates;
	  private Map<String, Object> maps;
	  private Set<String> set;
	  private List<Object> list;
	  private Integer[] ints;
	  private double[] dds;
	  
	  public Object[] getObjs()
	  {
	    return this.objs;
	  }
	  
	  public void setObjs(Object[] objs)
	  {
	    this.objs = objs;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public int getAge()
	  {
	    return this.age;
	  }
	  
	  public void setAge(int age)
	  {
	    this.age = age;
	  }
	  
	  public Date[] getDars()
	  {
	    return this.dars;
	  }
	  
	  public void setDars(Date[] dars)
	  {
	    this.dars = dars;
	  }
	  
	  public List<Date> getDates()
	  {
	    return this.dates;
	  }
	  
	  public void setDates(List<Date> dates)
	  {
	    this.dates = dates;
	  }
	  
	  public Map<String, Object> getMaps()
	  {
	    return this.maps;
	  }
	  
	  public void setMaps(Map<String, Object> maps)
	  {
	    this.maps = maps;
	  }
	  
	  public Set<String> getSet()
	  {
	    return this.set;
	  }
	  
	  public void setSet(Set<String> set)
	  {
	    this.set = set;
	  }
	  
	  public List<Object> getList()
	  {
	    return this.list;
	  }
	  
	  public void setList(List<Object> list)
	  {
	    this.list = list;
	  }
	  
	  public Integer[] getInts()
	  {
	    return this.ints;
	  }
	  
	  public void setInts(Integer[] ints)
	  {
	    this.ints = ints;
	  }
	  
	  public double[] getDds()
	  {
	    return this.dds;
	  }
	  
	  public void setDds(double[] dds)
	  {
	    this.dds = dds;
	  }
	  
	  public String toString()
	  {
	    return "Person [name=" + this.name + ", objs=" + Arrays.toString(this.objs) + ", age=" + this.age + ", dars=" + Arrays.toString(this.dars) + ", dates=" + this.dates + ", maps=" + this.maps + ", set=" + this.set + ", list=" + this.list + ", ints=" + Arrays.toString(this.ints) + ", dds=" + Arrays.toString(this.dds) + "]";
	  }
}
