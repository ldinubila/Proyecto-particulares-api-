package excepciones;


@SuppressWarnings("serial")
public class ClaseNoExisteExcepcion extends RuntimeException {

	public ClaseNoExisteExcepcion() {
		super();
	}

	public ClaseNoExisteExcepcion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClaseNoExisteExcepcion(String message, Throwable cause) {
		super(message, cause);
	}

	public ClaseNoExisteExcepcion(String message) {
		super(message);
	}

	public ClaseNoExisteExcepcion(Throwable cause) {
		super(cause);
	}	

}
