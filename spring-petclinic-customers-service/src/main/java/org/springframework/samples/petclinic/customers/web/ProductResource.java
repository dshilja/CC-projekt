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
package org.springframework.samples.petclinic.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.Product;
import org.springframework.samples.petclinic.customers.model.ProductRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Maciej Szarlinski
 */
@RequestMapping("/products")
@RestController
@Timed("petclinic.product")
@RequiredArgsConstructor
@Slf4j
class ProductResource {

    private final ProductRepository productRepository;

    /**
     * Create Product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    /**
     * Read single Product
     */
    @GetMapping(value = "/{productId}")
    public Optional<Product> findProduct(@PathVariable("productId") int productId) {
        return productRepository.findById(productId);
    }

    /**
     * Read List of Products
     */
    @GetMapping
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Update Product
     */
    @PutMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("productId") int productId, @Valid @RequestBody Product productRequest) {
        final Optional<Product> product = productRepository.findById(productId);

        final Product productModel = product
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we
        // should consider using MapStruct.
        productModel.setProductName(productRequest.getProductName());
        productModel.setPrice(productRequest.getPrice());
        productModel.setCategory(productRequest.getCategory());
        productModel.setAddress(productRequest.getAddress());
        productModel.setTelephone(productRequest.getTelephone());
        log.info("Saving product {}", productModel);
        productRepository.save(productModel);
    }
}
