package org.hardy.util.asyncDemo;

public abstract class Fonction<T> {
	 
	  private static long  MAX = Long.MAX_VALUE;
     
      private static long MIN = Long.MIN_VALUE;
      
      protected long start;
      
      protected long end;
 
	    public abstract  T produice(long index);
	    
	    public Fonction<T> range(String range){
	    	  this.setRange(range);
	    	  return this;
	    }

		public long[] getRange() {
			return new long[]{start,end};
		}

		public void setRange(String range) {
			String[] ranges = range.split(":");
			if("-unfinir".equals(ranges[0]))this.start = MIN;
			if("-unfinir".equals(ranges[1]))this.end = MIN;
			if("unfinir".equals(ranges[0]))this.start = MAX;
			if("unfinir".equals(ranges[1])) this.end = MAX;
			if(ranges[0].matches("\\d+"))this.start = Long.parseLong(ranges[0]);
			if(ranges[1].matches("\\d+"))this.end = Long.parseLong(ranges[1]);
		}
		
		 

		
	    
	    
}
