package fbutil.firebase4j.error;



public class JacksonUtilityException extends Throwable {

	
	private static final long serialVersionUID = 1L;

	public JacksonUtilityException( String message ) {
		super( message );
	}
	
	public JacksonUtilityException( String message, Throwable cause ) {
		super( message, cause );
	}
	
}
