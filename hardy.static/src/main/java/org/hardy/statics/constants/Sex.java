package org.hardy.statics.constants;

public enum Sex {
	
	
    MEN("男","men","masculin"),WOMEN("女","women","femme"),UNKOWN("未知","unkown","inconnu");
    
    private Sex(){}
    
    private Sex(String zh,String en,String fr){
    	   this.name_zh = zh;
    	   this.name_en= en;
    	   this.name_fr = fr;
    }
    private String name_zh;
    private String name_en;
    private String name_fr;
	public String getName_zh() {
		return name_zh;
	}

	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_fr() {
		return name_fr;
	}

	public void setName_fr(String name_fr) {
		this.name_fr = name_fr;
	}
    
    
}
