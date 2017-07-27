package org.hardy.util.io.conversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileConversion extends ConversionImpl{
	
	public  byte[] getBytesFromFile(File file) {  
        byte[] ret = null;  
        try {  
            if (file == null) {   
                return null;  
            }  
            FileInputStream in = new FileInputStream(file);  
            ret = super.streamToByteArray(in,1024);
        } catch (IOException e) {  
            // log.error("helper:get bytes from file process error!");  
            e.printStackTrace();  
        }  
        return ret;  
    }  
}
