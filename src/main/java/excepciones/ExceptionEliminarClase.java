package excepciones;

@SuppressWarnings("serial")
public class ExceptionEliminarClase extends RuntimeException {

	public ExceptionEliminarClase() {
		super();
	}

	public ExceptionEliminarClase(String message) {
		super(message);
	}

	public ExceptionEliminarClase(Throwable cause) {
		super(cause);
	}

}
