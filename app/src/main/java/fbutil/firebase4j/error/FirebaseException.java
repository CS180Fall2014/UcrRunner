package fbutil.firebase4j.error;



public class FirebaseException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public FirebaseException( String message ) {
		super( message );
	}
	
	public FirebaseException( String message, Throwable cause ) {
		super( message, cause );
	}
	
}
