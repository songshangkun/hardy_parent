package hardy.securite.exception;

public class RefuseRequestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	
	private String type;
	
	
	 
	public RefuseRequestException(String type,String message) {
		super(message);
		this.type = type;
	}

	 public void setType(String type) {
		this.type = type;
	}

	public String getType(){
		 return this.type;
	 }
    
	
}
