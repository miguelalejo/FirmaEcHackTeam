
package ec.gob.firmadigital.servicio.cliente;

/**
 * @author Edison Lomas Almeida @ elomas@appshandler.com
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRest {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApplicationRest.class);
		app.run(args);
	}

}
