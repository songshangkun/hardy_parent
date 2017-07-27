package org.hardy.springutil.webservice.terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.hardy.springutil.webservice.ParamJson;
import org.hardy.webservicehelper.GetResponse;

import com.song.tool.soap.SOAPMessageTemplate;

/**
 * 一个WebService的Client端的扩展
 * 可直接使用
 * @author 09
 *
 */

public class WebServiceClient {
	 public final String  wsdl ;
	 /**
	  *  地址和端口
	  * @param interAddres
	  * @throws SOAPException
	  * @throws IOException
	  * @throws JAXBException
	  * @throws DocumentException
	  */
     public WebServiceClient(String interAddres,int port) {
    	 wsdl = "http://"+interAddres+":"+String.valueOf(port)+"/webServiceRemoting?wsdl";
     }
     
     /**
      * 获取传递soap的模板
      * @return
      */
     public SOAPMessageTemplate getInstance(){
    	 GetResponse gresponse = new GetResponse(wsdl,
 				"http://webservice.extention.jsonhelper.ssh.song.com/",
 				"webServiceRemoting","WebServiceRemotingServerPort");
    	 SOAPMessageTemplate smt = gresponse.getInstance();
 		  smt.setMethodName("getResult");
	      smt.setResponseType("DOM4J");
		return smt;
     }
     
     /**
      *  呼叫目标方法
      * @param bean
      * @param method
      * @param params
      * @return
      * @throws SOAPException
      * @throws IOException
      * @throws JAXBException
      * @throws DocumentException
      */
     public String callMethod(String bean,String method,Object[] params) throws SOAPException, IOException, JAXBException, DocumentException{
    	 SOAPMessageTemplate smt = getInstance();
    	 ParamJson pj = new ParamJson();   
		 pj.addBeanMethod(bean, method,params);  
         Map<String,Object> condition = new HashMap<>(); condition.put("params", pj.toString());
         smt.setCondition(condition); 
         Document doc = (Document)smt.getResponse();
		 Element ele = (Element)doc.selectNodes("/ns2:getResultResponse/return").get(0);
		 return ele.getText();
     }
     /**
      *  呼叫目标方法
      * @param token
      * @param bean
      * @param method
      * @param params
      * @return
      * @throws SOAPException
      * @throws IOException
      * @throws JAXBException
      * @throws DocumentException
      */
     public String callMethod(Object token,String bean,String method,Object[] params) throws SOAPException, IOException, JAXBException, DocumentException{
    	 SOAPMessageTemplate smt = getInstance();
    	 ParamJson pj = new ParamJson();   
		 pj.addBeanMethod(token,bean, method,params);  
         Map<String,Object> condition = new HashMap<>(); condition.put("params", pj.toString());
         smt.setCondition(condition); 
         Document doc = (Document)smt.getResponse();
		 Element ele = (Element)doc.selectNodes("/ns2:getResultResponse/return").get(0);
		 return ele.getText();
     }
 
     
	    
}
