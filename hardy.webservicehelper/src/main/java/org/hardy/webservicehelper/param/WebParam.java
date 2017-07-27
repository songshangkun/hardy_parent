package org.hardy.webservicehelper.param;

import java.util.ArrayList;
import java.util.List;
/**
 * 定义一个webservice访问调取的条件xml文件
 * @author ssk
 *
 */
public class WebParam{
  public WebParam(String preFix, String methodName, String namespace)
  {
    this.preFix = preFix;
    this.methodName = methodName;
    this.namespace = namespace;
  }
  
  private String preFix = "ns2";
  private String methodName;
  private String namespace;
  private List<ParamUnit> params = new ArrayList<>();
  
  public WebParam() {}
  
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
  
  public String getPreFix()
  {
    return this.preFix;
  }
  
  public void setPreFix(String preFix)
  {
    this.preFix = preFix;
  }
  
  public List<ParamUnit> getParams()
  {
    return this.params;
  }
  
  public void setParams(List<ParamUnit> params)
  {
    this.params = params;
  }
  
  public WebParam addParam(ParamUnit param)
  {
    this.params.add(param);
    return this;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<").append(this.preFix).append(":").append(this.methodName).append(" xmlns:ns2=\"").append(this.namespace).append("\">");
    for (ParamUnit pu : this.params) {
      sb.append("<").append(pu.getName()).append(">").append(pu.getValue().toString()).append("</").append(pu.getName()).append(">");
    }
    sb.append("</").append(this.preFix).append(":").append(this.methodName).append(">");
    return sb.toString();
  }
}