package br.com.rodolfo.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.repository.CozinhaRepository;
import br.com.rodolfo.algafood.util.DatabaseCleaner;
import br.com.rodolfo.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    private static final Integer COZINHA_ID_INEXISTENTE = Integer.MAX_VALUE;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaIndiana;
    private Cozinha cozinhaTailandesa;
    private Integer quantidadeCozinhaCadastrada;
    private String jsonCorretoCozinhaChinesa;
    private String jsonIncorretoCozinhaSemNome;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/cozinhas";
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepararDadosTeste();
    }

    @Test
    void deveRetornarHttpStatus200_QuandoConsultarCozinhas() {
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(quantidadeCozinhaCadastrada));
    }

    @Test
    void deveRetornarHttpStatus201_QuandoCadastrarCozinha() {
        RestAssured.given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarRespostaEHttpStatusCorretos_QuandoConsultarCozinhaExistente() {
        RestAssured.given()
            .pathParam("cozinhaId", cozinhaIndiana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", Matchers.equalTo(cozinhaIndiana.getNome()));
    }

    @Test
    void deveRetornarHttpStatus404_QuandoConsultarCozinhaInexistente() {
        RestAssured.given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarHttpStatus400_QuandoCadastrarCozinhaSemNome() {
        RestAssured.given()
            .body(jsonIncorretoCozinhaSemNome)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void prepararDadosTeste() {
        cozinhaIndiana = new Cozinha();
        cozinhaIndiana.setNome("Indiana");
        cozinhaIndiana = cozinhaRepository.save(cozinhaIndiana);

        cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaTailandesa = cozinhaRepository.save(cozinhaTailandesa);

        quantidadeCozinhaCadastrada = (int) cozinhaRepository.count();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");
        jsonIncorretoCozinhaSemNome = ResourceUtils.getContentFromResource("/json/incorreto/cozinha-sem-nome.json");
    }
}
