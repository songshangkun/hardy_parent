package org.hardy.statics.time;


public enum DatePatten {
    ZH("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","yyyy-MM","MM-dd"),
    FR("dd/MM/yyyy HH:mm:ss","dd/MM/yyyy","MM/yyyy","dd/MM"),
    EN("MM-dd-yyyy HH:mm:ss","MM-dd-yyyy","MM-yyyy","MM-dd");
    private DatePatten(){}
    private DatePatten(String all,String simple,String ym,String md){
    	      this.all = all;
    	      this.simple = simple;
    	      this.ym = ym;
    	      this.md = md;
    }
      private String all;
      private String simple;
      private String ym;
      private String md;
      
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getSimple() {
		return simple;
	}
	public void setSimple(String simple) {
		this.simple = simple;
	}
	public String getYm() {
		return ym;
	}
	public void setYm(String ym) {
		this.ym = ym;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	
}
