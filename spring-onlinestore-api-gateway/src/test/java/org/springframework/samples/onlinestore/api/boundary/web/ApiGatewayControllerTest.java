package org.springframework.samples.onlinestore.api.boundary.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.samples.onlinestore.api.application.CustomersServiceClient;
import org.springframework.samples.onlinestore.api.application.VisitsServiceClient;
import org.springframework.samples.onlinestore.api.dto.ProductDetails;
import org.springframework.samples.onlinestore.api.dto.PetDetails;
import org.springframework.samples.onlinestore.api.dto.VisitDetails;
import org.springframework.samples.onlinestore.api.dto.Visits;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@Import({ReactiveResilience4JAutoConfiguration.class, CircuitBreakerConfiguration.class})
class ApiGatewayControllerTest {

    @MockBean
    private CustomersServiceClient customersServiceClient;

    @MockBean
    private VisitsServiceClient visitsServiceClient;

    @Autowired
    private WebTestClient client;


    @Test
    void getProductDetails_withAvailableVisitsService() {
        ProductDetails product = new ProductDetails();
        PetDetails cat = new PetDetails();
        cat.setId(20);
        cat.setName("Garfield");
        product.getPets().add(cat);
        Mockito
            .when(customersServiceClient.getProduct(1))
            .thenReturn(Mono.just(product));

        Visits visits = new Visits();
        VisitDetails visit = new VisitDetails();
        visit.setId(300);
        visit.setDescription("First visit");
        visit.setPetId(cat.getId());
        visits.getItems().add(visit);
        Mockito
            .when(visitsServiceClient.getVisitsForPets(Collections.singletonList(cat.getId())))
            .thenReturn(Mono.just(visits));

        client.get()
            .uri("/api/gateway/products/1")
            .exchange()
            .expectStatus().isOk()
            //.expectBody(String.class)
            //.consumeWith(response ->
            //    Assertions.assertThat(response.getResponseBody()).isEqualTo("Garfield"));
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].visits[0].description").isEqualTo("First visit");
    }

    /**
     * Test Resilience4j fallback method
     */
    @Test
    void getProductDetails_withServiceError() {
        ProductDetails product = new ProductDetails();
        PetDetails cat = new PetDetails();
        cat.setId(20);
        cat.setName("Garfield");
        product.getPets().add(cat);
        Mockito
            .when(customersServiceClient.getProduct(1))
            .thenReturn(Mono.just(product));

        Mockito
            .when(visitsServiceClient.getVisitsForPets(Collections.singletonList(cat.getId())))
            .thenReturn(Mono.error(new ConnectException("Simulate error")));

        client.get()
            .uri("/api/gateway/products/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.pets[0].name").isEqualTo("Garfield")
            .jsonPath("$.pets[0].visits").isEmpty();
    }

}
