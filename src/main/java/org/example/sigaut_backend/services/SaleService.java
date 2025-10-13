package org.example.sigaut_backend.services;

import org.example.sigaut_backend.config.ApiResponse;
import org.example.sigaut_backend.models.Product;
import org.example.sigaut_backend.models.Sale;
import org.example.sigaut_backend.repository.ProductRepository;
import org.example.sigaut_backend.repository.SaleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class SaleService {
    private static final String BARCODE_EXISTS_MESSAGE = "El codigo de barras ya está registrado";
    private static final String SALE_NOT_FOUND_MESSAGE = "Venta no encontrada";

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    // 🔵 Obtener todas las ventas
    public ResponseEntity<ApiResponse> getAllSales() {
        return new ResponseEntity<>(new ApiResponse(saleRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    // 🔵 Obtener venta por ID
    public ResponseEntity<ApiResponse> getSaleById(Long id) {
        return saleRepository.findById(id)
                .map(sale -> new ResponseEntity<>(new ApiResponse(sale, HttpStatus.OK), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ApiResponse(SALE_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST));
    }

    // 🟢 Crear venta y actualizar stock
    public ResponseEntity<ApiResponse> createSale(Sale sale) {
        // Validar lista de productos
        if (sale.getProductsList() == null || sale.getProductsList().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("Debe incluir al menos un producto", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        // Validar stock disponible
        for (Product p : sale.getProductsList()) {
            Optional<Product> productOpt = productRepository.findById(p.getId());
            if (productOpt.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("Producto con ID " + p.getId() + " no existe", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }

            Product product = productOpt.get();
            if (product.getStock() < p.getAccountSale()) {
                return new ResponseEntity<>(new ApiResponse("Stock insuficiente para el producto " + product.getName(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
        }

        // Actualizar stock
        for (Product p : sale.getProductsList()) {
            Product product = productRepository.findById(p.getId()).get();
            product.setStock(product.getStock() - p.getAccountSale());
            productRepository.save(product);
        }

        // Establecer fecha actual si no se envía
        if (sale.getDateSale() == null) {
            sale.setDateSale(new Date());
        }

        Sale savedSale = saleRepository.save(sale);
        return new ResponseEntity<>(new ApiResponse(savedSale, HttpStatus.OK), HttpStatus.OK);
    }

    // 🔴 Eliminar venta (opcional)
    public ResponseEntity<ApiResponse> deleteSale(Long id) {
        Optional<Sale> saleOpt = saleRepository.findById(id);
        if (saleOpt.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(SALE_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        saleRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Venta eliminada correctamente", HttpStatus.OK), HttpStatus.OK);
    }
}
