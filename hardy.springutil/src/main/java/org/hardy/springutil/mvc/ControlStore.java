package org.hardy.springutil.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControlStore {
    
	 public String getName();
	
	 public String call(HttpServletRequest request,HttpServletResponse reponse);
	 
	 public String call();
 
}
