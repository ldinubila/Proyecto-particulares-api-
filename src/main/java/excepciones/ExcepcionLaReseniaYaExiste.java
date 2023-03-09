package excepciones;


@SuppressWarnings("serial")
public class ExcepcionLaReseniaYaExiste  extends RuntimeException {

	public ExcepcionLaReseniaYaExiste () {
		super();
	}


	public ExcepcionLaReseniaYaExiste (String message) {
		super(message);
	}

	public ExcepcionLaReseniaYaExiste (Throwable cause) {
		super(cause);
	}	
}
