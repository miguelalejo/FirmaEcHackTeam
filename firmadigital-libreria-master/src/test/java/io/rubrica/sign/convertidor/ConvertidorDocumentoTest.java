package io.rubrica.sign.convertidor;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

public class ConvertidorDocumentoTest {
	
	@Ignore
	public void dberiaConverirDoc()
			throws FileNotFoundException, IOException, InterruptedException, ExecutionException {

		InputStream in = new ByteArrayInputStream(readFileToByteArray(obtenerArchivo("firmado.docx")));
		Path tempDirWithPrefix = Files.createTempDirectory("tempoffice");
		IConverter converter = LocalConverter.builder()
				.baseFolder(new File(tempDirWithPrefix.toString()))
				.workerPool(20, 25, 2, TimeUnit.SECONDS).processTimeout(5, TimeUnit.SECONDS).build();
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		Future<Boolean> conversion = converter.convert(in).as(DocumentType.DOC).to(bo).as(DocumentType.DOC).schedule();
		conversion.get();
		File tempFile2 = File.createTempFile("word", "." + "test2firmado.doc");
        System.out.println("Temporal2 para comprobacion manual: " + tempFile2.getAbsolutePath());
        
		try (OutputStream outputStream = new FileOutputStream(tempFile2)) {
			bo.writeTo(outputStream);
		}
		in.close();
		bo.close();
	}

	public static File obtenerArchivo(String nombreArchivo) throws FileNotFoundException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(nombreArchivo);
		if (url == null) {
			throw new FileNotFoundException("No existe.");
		}
		return new File(url.getFile());
	}
}
