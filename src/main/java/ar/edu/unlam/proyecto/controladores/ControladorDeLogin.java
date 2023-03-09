package ar.edu.unlam.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Token.GenerateToken;
import ar.edu.unlam.proyecto.dtos.LoginDto;
import ar.edu.unlam.proyecto.dtos.UsuarioDto;
import ar.edu.unlam.proyecto.entidades.Usuario;
import ar.edu.unlam.proyecto.servicios.ServicioDeUsuario;
import ar.edu.unlam.proyecto.servicios.ServicioToken;

@RestController
@CrossOrigin
@RequestMapping("v1/login")
public class ControladorDeLogin {

    private ServicioDeUsuario servicioDeUsuario;
    private ServicioToken servicioToken;

    @Autowired
    public ControladorDeLogin(ServicioDeUsuario servicioDeUsuario,ServicioToken servicioToken) {
        this.servicioDeUsuario = servicioDeUsuario;
        this.servicioToken = servicioToken;
    }
    
    GenerateToken generateToken = new GenerateToken();  
    
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody UsuarioDto usuario) {
		
		LoginDto login = new LoginDto();

		// Authenticate User.
		Usuario user = servicioDeUsuario.LoginUsuario(usuario.getEmail(), usuario.getContrasenia());
		/*
		 * If User is authenticated then Do Authorization Task.
		 */
		if (user != null) {
//			/*
//			 * Generate token.
//			 */
//			String tokenData[] = generateToken.createJWT(user.getEmail(), "JavaTpoint", "JWT Token",
//					user.getRol().name(), 43200000);
//
//			// get Token.
//			login.setToken(tokenData[0]);
//			login.setRol(user.getRol());
//			login.setUserId(user.getId());
//
//			// Create the Header Object
//
//			// Add token to the Header.
//
//			// Check if token is already exist.
//			long isUserEmailExists = servicioToken.getTokenDetail(usuario.getEmail());
//
//			/*
//			 * If token exist then update Token else create and insert the token.
//			 */
//			if (isUserEmailExists > 0) {
//				servicioToken.updateToken(user.getEmail(), login.getToken(), tokenData[1]);
//			} else {
//				servicioToken.saveUserEmail(user.getEmail(), user.getId());
//				servicioToken.updateToken(user.getEmail(), login.getToken(), tokenData[1]);
//			}
//	        return ResponseEntity.ok(login);
	        return ResponseEntity.ok(user);

		}

		// if not authenticated return status what we get.
		else {
			return ResponseEntity.badRequest().body("Usuario contraseña incorrecta");
		}

	}
}