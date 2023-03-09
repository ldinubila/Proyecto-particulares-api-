package ar.edu.unlam.proyecto.controladores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlam.proyecto.entidades.Archivo;

@RestController
@CrossOrigin
@RequestMapping("v1/zoom")
public class ControladorZoom {
	@GetMapping("prueba")
    public ResponseEntity prueba() throws IOException {
		String url = "https://api.zoom.us/v2/users";
		String token="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6ImZoRDVxb0swUUhDZ3U2QTRWVF9ES1EiLCJleHAiOjE2MzYwNzIyMzgsImlhdCI6MTYzNjA2NjgzOX0.qG-GxTgR9Zkp0ajnQ5Zzu2fk1O1kM2FF7mW5m4EC81Y";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("Authorization", "Bearer " + token);

		int responseCode = con.getResponseCode();
	
		BufferedReader in = new BufferedReader(
		           new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		in.close();

        return ResponseEntity.ok(response);

    }
}
