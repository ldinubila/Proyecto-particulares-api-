package excepciones;
@SuppressWarnings("serial")
public class ExcepcionNoEsAlumno  extends RuntimeException {

	public ExcepcionNoEsAlumno () {
		super();
	}


	public ExcepcionNoEsAlumno (String message) {
		super(message);
	}

	public ExcepcionNoEsAlumno (Throwable cause) {
		super(cause);
	}	
}
