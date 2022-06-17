package br.com.rodolfo.algafood;

import java.math.BigDecimal;

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
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;
import br.com.rodolfo.algafood.domain.service.CadastroCozinhaService;
import br.com.rodolfo.algafood.util.DatabaseCleaner;
import br.com.rodolfo.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    private static final Integer RESTAURANTE_ID_INEXISTENTE = Integer.MAX_VALUE;
    private static final BigDecimal FRETE_COBRADO = new BigDecimal("15");

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Restaurante restaurante1;
    private Restaurante restaurante2;
    private Integer quantidadeRestauranteCadastrado;
    private String jsonCorretoRestauranteArgentino;
    private String jsonIncorretoRestauranteArgentinoSemTaxaFrete;
    private String jsonIncorretoRestauranteArgentinoSemCozinha;
    private String jsonIncorretoRestauranteArgentinoComCozinhaInxistente;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/restaurantes";
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepararDadosTeste();
    }

    @Test
    void deveRetornarHttpStatus200_QuandoConsultarRestaurantes() {
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterQuantidadeCorretaDeRestaurantes_QuandoConsultarRestaurantes() {
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(quantidadeRestauranteCadastrado));
    }

    @Test
    void deveRetornarHttpStatus201_QuandoCadastrarRestaurante() {
        RestAssured.given()
            .body(jsonCorretoRestauranteArgentino)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarHttpStatusERespostaCorretos_QuandoConsultarRestauranteExistente() {
        RestAssured.given()
            .pathParam("restauranteId", restaurante1.getId())
            .accept(ContentType.JSON)
        .when()
            .get("{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", Matchers.equalTo(restaurante1.getNome()));
    }

    @Test
    void deveRetornarHttpStatus404_QuandoConsultarRestauranteInexistente() {
        RestAssured.given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarHttpStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        RestAssured.given()
            .body(jsonIncorretoRestauranteArgentinoSemTaxaFrete)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarHttpStatus400_QuandoCadastrarRestauranteSemCozinha() {
        RestAssured.given()
            .body(jsonIncorretoRestauranteArgentinoSemCozinha)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarHttpStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        RestAssured.given()
            .body(jsonIncorretoRestauranteArgentinoComCozinhaInxistente)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private void prepararDadosTeste() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Chinesa");

        cozinha = cadastroCozinhaService.salvar(cozinha);

        restaurante1 = new Restaurante();
        restaurante1.setNome("Restaurante Brasil");
        restaurante1.setTaxaFrete(FRETE_COBRADO);
        restaurante1.setCozinha(cozinha);

        restaurante2 = new Restaurante();
        restaurante2.setNome("Restaurante Americano");
        restaurante2.setTaxaFrete(FRETE_COBRADO);
        restaurante2.setCozinha(cozinha);

        restauranteRepository.save(restaurante1);
        restauranteRepository.save(restaurante2);

        quantidadeRestauranteCadastrado = (int) restauranteRepository.count();

        jsonCorretoRestauranteArgentino = ResourceUtils.getContentFromResource("/json/correto/restaurante-argentino.json");
        jsonIncorretoRestauranteArgentinoSemTaxaFrete = ResourceUtils.getContentFromResource(
            "/json/incorreto/restaurante-argentino-sem-taxa-frete.json");
        jsonIncorretoRestauranteArgentinoSemCozinha = ResourceUtils.getContentFromResource(
            "/json/incorreto/restaurante-argentino-sem-cozinha.json");
        jsonIncorretoRestauranteArgentinoComCozinhaInxistente = ResourceUtils.getContentFromResource(
            "/json/incorreto/restaurante-argentino-com-cozinha-inexistente.json");
    }
}
