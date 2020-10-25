package ec.gob.firmadigital.servicio.cliente.controllador;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.firmadigital.servicio.cliente.modelo.SignRequest;
import ec.gob.firmadigital.servicio.cliente.modelo.SignResponse;
import ec.gob.firmadigital.servicio.cliente.modelo.SignedDocument;
import ec.gob.firmadigital.servicio.cliente.servicio.ServicioFirma;
import ec.gob.firmadigital.servicio.cliente.store.SignedSingleton;
import ec.gob.firmadigital.servicio.cliente.utils.Utils;
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

	@RequestMapping("/ping")
	@CrossOrigin(origins = "https://localhost:3000")
	public ResponseEntity<SignResponse> ping() {
		return new ResponseEntity<SignResponse>(new SignResponse(SignResponse.CODE_OK, "Server is ready"),
				HttpStatus.OK);
	}

	@PostMapping
	@ResponseBody
	@RequestMapping("/firmar/word")
	@CrossOrigin(origins = "https://localhost:3000")
	public ResponseEntity<SignResponse> recibir(@RequestBody SignRequest parametroFirma) {
		try {
			String firmado = servicioFirma.firmar(parametroFirma);
			return new ResponseEntity<SignResponse>(new SignResponse(firmado), HttpStatus.CREATED);
		} catch (RubricaException e) {
			return new ResponseEntity<SignResponse>(new SignResponse(SignResponse.CODE_ERROR, e.getMessage()),
					HttpStatus.OK);
		}
	}

	@RequestMapping("/firmar/word/{uuid}")
	@CrossOrigin(origins = "https://localhost:3000")
	public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid) {
		try {
			byte[] data = new byte[0];
			SignedDocument signedDocument = SignedSingleton.getInstance().get(uuid);
			if (signedDocument != null) {
				data = Files.readAllBytes(Paths.get(signedDocument.getPath()));
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				headers.add("Content-Disposition",
						String.format("attachment; filename=\"%s\"", Utils.buildName(signedDocument.getName())));
				headers.add("Content-Length", String.format("%d", data.length));
				return new ResponseEntity<>(data, headers, HttpStatus.OK);
			}
			return new ResponseEntity<byte[]>(data, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(new byte[0], HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
