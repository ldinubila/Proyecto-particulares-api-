package excepciones;

	@SuppressWarnings("serial")
	public class ExcepcionMailRepetido  extends RuntimeException {

		public ExcepcionMailRepetido () {
			super();
		}


		public ExcepcionMailRepetido (String message) {
			super(message);
		}

		public ExcepcionMailRepetido (Throwable cause) {
			super(cause);
		}	
	}