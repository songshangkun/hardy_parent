package org.hardy.util.io.conversion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ObjectConversion extends ConversionImpl{
   
	public byte[] objToByteArray(Object obj){
		   ByteArrayOutputStream bos = null;
		   byte[] byts = null;
		   try{
			   bos = new ByteArrayOutputStream();
			     super.objToStream(obj, bos);
			     byts = bos.toByteArray();
		        }finally{
		        	if(bos!=null){
		        		try {
							bos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}	
		        }
		   return  byts;
	   }
      
	@SuppressWarnings("unchecked")
	public <T>T byteArrayToObj(byte[] bs){
		   ByteArrayInputStream bis = null; 
		   Object obj = null;
		   bis = new ByteArrayInputStream(bs);
		   obj = super.streamToObject(bis); 
		   return  (T)obj;    
	   }
}
