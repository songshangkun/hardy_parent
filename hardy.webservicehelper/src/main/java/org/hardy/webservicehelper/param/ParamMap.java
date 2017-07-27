package org.hardy.webservicehelper.param;

import java.util.Map;
/**
 * webservice的map条件参数
 * @author ssk
 *
 */
public class ParamMap
{
  private String preFix = "ns2";
  private String methodName;
  private String namespace;
  private String paramName;
//  private static final String TYPE_SPACE = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:";
//  private static final String TYPE_SPACE_END = "\">";
  Map<String, ParamMapUnit> params;
  
  public String getPreFix()
  {
    return this.preFix;
  }
  
  public void setPreFix(String preFix)
  {
    this.preFix = preFix;
  }
  
  public String getMethodName()
  {
    return this.methodName;
  }
  
  public void setMethodName(String methodName)
  {
    this.methodName = methodName;
  }
  
  public String getNamespace()
  {
    return this.namespace;
  }
  
  public void setNamespace(String namespace)
  {
    this.namespace = namespace;
  }
  
  public String getParamName()
  {
    return this.paramName;
  }
  
  public void setParamName(String paramName)
  {
    this.paramName = paramName;
  }
  
  public Map<String, ParamMapUnit> getParams()
  {
    return this.params;
  }
  
  public void setParams(Map<String, ParamMapUnit> params)
  {
    this.params = params;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<").append(this.preFix).append(":").append(this.methodName).append(" xmlns:").append(this.preFix).append("=\"").append(this.namespace).append("\"").append(">");
    
    sb.append("<").append(this.paramName).append(">");
    for (String key : this.params.keySet()) {
      sb.append("<entry>").append("<key>").append(key).append("</key>").append("<value ").append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xsi:type=\"xs:").append(((ParamMapUnit)this.params.get(key)).getType()).append("\">").append(((ParamMapUnit)this.params.get(key)).getValue()).append("</value>").append("</entry>");
    }
    sb.append("</").append(this.paramName).append(">");
    sb.append("</").append(this.preFix).append(":").append(this.methodName).append(">");
    return sb.toString();
  }
}
