package org.hardy.springutil.mvc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlProcessor {
 private ControlProcessor(){};
 private static ControlProcessor cp = new ControlProcessor();
 public static ControlProcessor getInstance(){
	 return cp;
 }
   
 private Map<String,ControlStore> core = new HashMap<String, ControlStore>();
	 
  public void add(String name,ControlStore cs){
	  core.put(name, cs);
  }
  
  public void add(ControlStore cs){
	  core.put(cs.getName(), cs);
  }
  
  public void publishEvent(HttpServletRequest request,HttpServletResponse response){
	  Collection<ControlStore> ccs = core.values();
	  for(ControlStore cs:ccs){
		  cs.call(request,response);
	  }
  }
  
  public void publishEvent(String[] names,HttpServletRequest request,HttpServletResponse response){
	   for(String name:names){
		   core.get(name).call(request,response);
	   }
  }
  /**
  @Deprecated
  public void publishEvent(){
	  HttpServletRequest request = AbstractController.getCurrentRequest();
	  HttpServletResponse response = AbstractController.getCurrentResponse();
	  Collection<ControlStore> ccs = core.values();
	  for(ControlStore cs:ccs){
		  cs.call(request,response);
	  }
  }
  @Deprecated
  public void publishEvent(String[] names){
	  HttpServletRequest request = AbstractController.getCurrentRequest();
	  HttpServletResponse response = AbstractController.getCurrentResponse();
	   for(String name:names){
		   core.get(name).call(request,response);
	   }
  }**/
	 
}
