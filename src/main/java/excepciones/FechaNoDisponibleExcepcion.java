package excepciones;

@SuppressWarnings("serial")
public class FechaNoDisponibleExcepcion extends RuntimeException {

	public FechaNoDisponibleExcepcion() {
		super();
	}

	public FechaNoDisponibleExcepcion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FechaNoDisponibleExcepcion(String message, Throwable cause) {
		super(message, cause);
	}

	public FechaNoDisponibleExcepcion(String message) {
		super(message);
	}

	public FechaNoDisponibleExcepcion(Throwable cause) {
		super(cause);
	}	

}
