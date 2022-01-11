
package org.springframework.samples.onlinestore.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.onlinestore.customers.model.Product;
import org.springframework.samples.onlinestore.customers.model.ProductRepository;
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
@Timed("onlinestore.product")
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

        final Product productModel = product.orElseThrow(() -> new ResourceNotFoundException("Product "+productId+" not found"));
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        productModel.setProductName(productRequest.getProductName());
        productModel.setProductType(productRequest.getProductType());
        productModel.setProductPrice(productRequest.getProductPrice());
        productModel.setProductDescription(productRequest.getProductDescription());
        productModel.setProductImg_url(productRequest.getProductImg_url());
        log.info("Saving product {}", productModel);
        productRepository.save(productModel);
    }
}
