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
<<<<<<< HEAD
import org.springframework.samples.onlinestore.api.dto.ProductDetails;
=======
import org.springframework.samples.onlinestore.api.application.VisitsServiceClient;
import org.springframework.samples.onlinestore.api.dto.ProductDetails;
import org.springframework.samples.onlinestore.api.dto.PetDetails;
import org.springframework.samples.onlinestore.api.dto.VisitDetails;
import org.springframework.samples.onlinestore.api.dto.Visits;
>>>>>>> 47e081b3da849c871bbba02c16e2cb54028812fc
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

    @Autowired
    private WebTestClient client;

    /**
     * Test Resilience4j fallback method
     */
    @Test
    void getProductDetails_withServiceError() {
        ProductDetails product = new ProductDetails();
        Mockito
            .when(customersServiceClient.getProduct(1))
            .thenReturn(Mono.just(product));

        client.get()
            .uri("/api/gateway/products/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
    }

}
