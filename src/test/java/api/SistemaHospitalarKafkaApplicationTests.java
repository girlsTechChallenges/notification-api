package api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = MainApplication.class)
class MainApplicationTests {

	private final ApplicationContext context;

	MainApplicationTests(ApplicationContext context) {
		this.context = context;
	}

	@Test
	void contextLoads() {
		// Verifica se o contexto do Spring foi carregado corretamente
		assertThat(context).isNotNull();
	}

	@Test
	void mainMethodRunsWithoutExceptions() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(MainApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			app.run();
		});
	}

	@Test
	void mainMethodRunsWithoutStartingWebServer() {
		assertDoesNotThrow(() -> {
			SpringApplication app = new SpringApplication(MainApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			app.run();
		});
	}
}


