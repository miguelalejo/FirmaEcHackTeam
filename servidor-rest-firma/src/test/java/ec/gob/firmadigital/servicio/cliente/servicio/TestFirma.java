/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ec.gob.firmadigital.servicio.cliente.servicio;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import ec.gob.firmadigital.servicio.cliente.controllador.FirmaRest;
import ec.gob.firmadigital.servicio.cliente.modelo.SignRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestFirma {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	FirmaRest firmaRest;

	@Autowired
	ServicioFirma servicioFirma;

	@Test
	public void deberiaSaludar() {
		ResponseEntity<String> respuesta = restTemplate
				.getForEntity(String.format("http://localhost:%s/servicio-firma/saludo", port), String.class);
		assertThat(respuesta.getBody()).isEqualTo("Hola");
	}

	@Ignore
	public void deberiaDevolveListaItems() throws Exception {
		SignRequest dtoParametroFirma = new SignRequest();
		byte[] archivoWord = readFileToByteArray(ManejadorDocumentoTest.obtenerArchivo("prueba.docx"));
		byte[] certificado = readFileToByteArray(ManejadorDocumentoTest.obtenerArchivo("BC.p12"));
		String clave = "Password#1";
		dtoParametroFirma.setKey(servicioFirma.encode(certificado));
		dtoParametroFirma.setDocument(servicioFirma.encode(archivoWord));
		dtoParametroFirma.setPin(clave);
		String firmado = restTemplate.postForObject(String.format("http://localhost:%s/productos", port),
				dtoParametroFirma, String.class);

		File tempFile = File.createTempFile("word", "." + "test1.docx");
		System.out.println("Temporal para comprobacion manual: " + tempFile.getAbsolutePath());

		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(servicioFirma.decode(firmado));
			fos.flush();
		}

	}
}
