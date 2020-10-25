package ec.gob.firmadigital.servicio.cliente.controllador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.firmadigital.servicio.cliente.modelo.DTOParametroFirma;
import ec.gob.firmadigital.servicio.cliente.servicio.ServicioFirma;
import io.rubrica.exceptions.RubricaException;

@RestController
@RequestMapping("/servicio-firma")
public class FirmaRest {

	@Autowired
	private ServicioFirma servicioFirma;

	@RequestMapping("/saludo")
	public ResponseEntity<String> greeting() {
		return new ResponseEntity<String>("Hola", HttpStatus.CREATED);
	}

	@RequestMapping("/firmar/word")
	public @PostMapping @ResponseBody ResponseEntity<String> recibir(@RequestBody DTOParametroFirma parametroFirma) {
		try {
			String firmado = servicioFirma.firmar(parametroFirma);
			return new ResponseEntity<String>(firmado, HttpStatus.CREATED);
		} catch (RubricaException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
