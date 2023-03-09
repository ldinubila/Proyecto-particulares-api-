package excepciones;


@SuppressWarnings("serial")
public class ExcepcionExisteCompra  extends RuntimeException {

	public ExcepcionExisteCompra () {
		super();
	}


	public ExcepcionExisteCompra (String message) {
		super(message);
	}

	public ExcepcionExisteCompra (Throwable cause) {
		super(cause);
	}	
}