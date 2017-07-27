package org.hardy.statics.constants;

public enum PathMode {
    WEBROOT,CLASSPATH,FILE;
   
    private PathMode(){}
    
	public static PathMode getPathMode(int i){
		 switch (i) {
		case 0:
			return WEBROOT;
		case 1:
			return CLASSPATH;
		case 2:
			return FILE;
		default:
			return CLASSPATH;
		}
	}
    
}
