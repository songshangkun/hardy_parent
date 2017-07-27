package org.hardy.webservicehelper.param;

/**
 * webservice单位条件的拼装字符
 * @author 09
 *
 */
public class ParamUnit
{
  private String name = "'arg0";
  private Object value;
  
  public ParamUnit() {}
  
  public ParamUnit(String name, Object value)
  {
    this.name = name;
    this.value = value;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<").append(getName()).append(">").append(this.value.toString()).append("</").append(getName()).append(">");
    return sb.toString();
  }
}