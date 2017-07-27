package hardy.securite.handler.webHandler;

import java.util.Arrays;

public class ScopeSpace {
	
    public static final String REQUEST_SCOPE = "request";
    
    public static final String SESSION_SCOPE = "session";
    
    public static final String PAGECONTEXT_SCOPE = "pageContext";
    
    public static final String APPLICATIO_SCOPE = "application";
    
    /**
     * attr属性名称
     */
    private String[] names;
    /**
     * scope域名称
     */
    private String scope;
    /**
     * scope:names
     */
    private String code;

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String getScope() {
		return scope;
	}
	
	 

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if(code.indexOf(":")==-1)throw new RuntimeException("code:"+code+" not correct!");
		String[] sc = code.split(":");
		if(sc.length<2)throw new RuntimeException("code:"+code+" not correct!");
		if(!REQUEST_SCOPE.equalsIgnoreCase(sc[0])&&
		   !SESSION_SCOPE.equalsIgnoreCase(sc[0])&&
		   !PAGECONTEXT_SCOPE.equalsIgnoreCase(sc[0])&&
		   !APPLICATIO_SCOPE.equalsIgnoreCase(sc[0]))
		throw new RuntimeException("scope:"+sc[0]+" not correct!"); 
		this.scope = sc[0];
		this.names = sc[1].split(",");
		this.code = code;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "ScopeSpace [names=" + Arrays.toString(names) + ", scope="
				+ scope + "]";
	}
    
    public static void main(String[] args) {
    	ScopeSpace ss = new ScopeSpace();
    	ss.setCode(ScopeSpace.REQUEST_SCOPE+":name");
    	System.out.println(ss);
	}
}
