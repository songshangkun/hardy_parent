package org.hardy.webservicehelper.param;
/**
 * webservice的map条件参数
 * @author ssk
 *
 */
public class ParamMapUnit
{
  private Object value;
  private String type;
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
}