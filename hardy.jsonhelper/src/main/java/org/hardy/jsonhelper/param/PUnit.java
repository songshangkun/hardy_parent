package org.hardy.jsonhelper.param;

/**
 * 以json方式传递参数的最小单元
 * @author ssk
 *
 */
public class PUnit {
	 /**
	  * 参数属性名称
	  */
	  private String name;
	  /**
	   * 参数的值
	   */
	  private Object value;
	  /**
	   * 参数目标类型
	   */
	  private Class<?> targetType;
	  /**
	   * 参数值的类型
	   */
	  private Class<?> valueType;
	  
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
	  
	  public Class<?> getTargetType()
	  {
	    return this.targetType;
	  }
	  
	  public void setTargetType(Class<?> targetType)
	  {
	    this.targetType = targetType;
	  }
	  
	  public Class<?> getValueType()
	  {
	    return this.valueType;
	  }
	  
	  public void setValueType(Class<?> valueType)
	  {
	    this.valueType = valueType;
	  }
	  
	  public String toString()
	  {
	    return "PUnit [name=" + this.name + ", value=" + this.value + ", targetType=" + this.targetType + ", valueType=" + this.valueType + "]";
	  }
}
