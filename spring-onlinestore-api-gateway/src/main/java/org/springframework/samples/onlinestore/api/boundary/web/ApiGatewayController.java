/*
 * Copyright 2002-2017 the original author or authors.
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
package org.springframework.samples.onlinestore.api.boundary.web;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.samples.onlinestore.api.application.CustomersServiceClient;
<<<<<<< HEAD
import org.springframework.samples.onlinestore.api.dto.ProductDetails;
=======
import org.springframework.samples.onlinestore.api.application.VisitsServiceClient;
import org.springframework.samples.onlinestore.api.dto.ProductDetails;
import org.springframework.samples.onlinestore.api.dto.Visits;
>>>>>>> 47e081b3da849c871bbba02c16e2cb54028812fc
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Maciej Szarlinski
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gateway")
public class ApiGatewayController {

    private final CustomersServiceClient customersServiceClient;

    private final ReactiveCircuitBreakerFactory cbFactory;

    @GetMapping(value = "products/{productId}")
    public Mono<ProductDetails> getProductDetails(final @PathVariable int productId) {
        return customersServiceClient.getProduct(productId); /* TODO */
/*
        return customersServiceClient.getProduct(productId)
            .flatMap(product ->
                visitsServiceClient.getVisitsForPets(product.getPetIds())
                    .transform(it -> {
                        ReactiveCircuitBreaker cb = cbFactory.create("getProductDetails");
                        return cb.run(it, throwable -> emptyVisitsForPets());
                    })
            );*/
    }
}
