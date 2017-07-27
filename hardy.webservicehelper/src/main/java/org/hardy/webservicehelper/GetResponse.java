package org.hardy.webservicehelper;

import com.song.tool.soap.SOAPMessageTemplate;

public class GetResponse{
  private String wsdlUrl;
  private String nameSpace;
  private String nameSpaceName;
  private String portName;
  
  public GetResponse() {}
  
  public GetResponse(String wsdlUrl, String nameSpace, String nameSpaceName, String portName)
  {
    this.wsdlUrl = wsdlUrl;
    this.nameSpace = nameSpace;
    this.nameSpaceName = nameSpaceName;
    this.portName = portName;
  }
  
  public SOAPMessageTemplate getInstance()
  {
    SOAPMessageTemplate soap = new SOAPMessageTemplate();
    soap.setWsdlUrl(this.wsdlUrl);
    soap.setNameSpace(this.nameSpace);
    soap.setNameSpaceName(this.nameSpaceName);
    soap.setPortName(this.portName);
    soap.setPrefix("ns2");
    return soap;
  }
  
  public String getWsdlUrl()
  {
    return this.wsdlUrl;
  }
  
  public void setWsdlUrl(String wsdlUrl)
  {
    this.wsdlUrl = wsdlUrl;
  }
  
  public String getNameSpace()
  {
    return this.nameSpace;
  }
  
  public void setNameSpace(String nameSpace)
  {
    this.nameSpace = nameSpace;
  }
  
  public String getNameSpaceName()
  {
    return this.nameSpaceName;
  }
  
  public void setNameSpaceName(String nameSpaceName)
  {
    this.nameSpaceName = nameSpaceName;
  }
  
  public String getPortName()
  {
    return this.portName;
  }
  
  public void setPortName(String portName)
  {
    this.portName = portName;
  }
}
