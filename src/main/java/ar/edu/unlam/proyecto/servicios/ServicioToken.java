package ar.edu.unlam.proyecto.servicios;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.proyecto.entidades.Token;
import ar.edu.unlam.proyecto.repositorios.RepositorioDeToken;

@Service
public class ServicioToken {
	private RepositorioDeToken repositorioDeToken;

	@Autowired
	public ServicioToken(RepositorioDeToken repositorioDeToken) {
		this.repositorioDeToken = repositorioDeToken;
	}

	@Transactional
	public void saveUserEmail(String email, Long id) {
		Token token = this.crearToken(email, id);
		repositorioDeToken.save(token);
	}

	private Token crearToken(String email, Long id) {
		Token t = new Token();
		t.setEmailId(email);
		t.setTokenID(id);
		return t;
	}

	@Transactional
	public void updateToken(String email, String authenticationToken, String secretKey) {
		Token t = this.modificarToken(email, authenticationToken, secretKey);
		this.repositorioDeToken.save(t);
	}

	private Token modificarToken(String email, String authenticationToken, String secretKey) {
		Token t = (Token) this.repositorioDeToken.findAllByEmailId(email);
		t.setEmailId(email);
		t.setAuthenticationToken(authenticationToken);
		t.setSecretKey(secretKey);
		return t;
	}

	public Long getTokenDetail(String email) {

		List<Token> tokenDetails = repositorioDeToken.findAllByEmailId(email);

		if (tokenDetails.size() > 0) {
			return tokenDetails.get(0).getTokenID();
		} else {
			return 0L;
		}

	}

	public Long tokenAuthentication(String token, int emailId) {
		List<Token> tokenDetails = repositorioDeToken.findByUserAndToken(token, emailId);

		if (tokenDetails.size() > 0) {
			return tokenDetails.get(0).getTokenID();
		} else {
			return 0L;
		}

	}

}
