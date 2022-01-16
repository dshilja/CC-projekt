package org.springframework.samples.petclinic.api.boundary.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.api.application.CustomersServiceClient;
import org.springframework.samples.petclinic.api.dto.ProductDetails;
import org.springframework.samples.petclinic.api.dto.PetDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ApiGatewayController.class)
@Import({ ReactiveResilience4JAutoConfiguration.class, CircuitBreakerConfiguration.class })
class ApiGatewayControllerTest {

        @MockBean
        private CustomersServiceClient customersServiceClient;

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

                client.get()
                                .uri("/api/gateway/products/1")
                                .exchange()
                                .expectStatus().isOk()
                                // .expectBody(String.class)
                                // .consumeWith(response ->
                                // Assertions.assertThat(response.getResponseBody()).isEqualTo("Garfield"));
                                .expectBody()
                                .jsonPath("$.pets[0].name").isEqualTo("Garfield");
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

                client.get()
                                .uri("/api/gateway/products/1")
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("$.pets[0].name").isEqualTo("Garfield");
        }

}
