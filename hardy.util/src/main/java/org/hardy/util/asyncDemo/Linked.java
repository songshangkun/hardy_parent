package org.hardy.util.asyncDemo;

import java.util.List;

public interface Linked<T> {
   
	   T next();
	   
	   T previous();
	   
	   List<T>  iterator();
}
