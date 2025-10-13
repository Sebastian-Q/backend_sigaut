package org.example.sigaut_backend.controller.sale;

import org.example.sigaut_backend.config.ApiResponse;
import org.example.sigaut_backend.models.Sale;
import org.example.sigaut_backend.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSale(@PathVariable Long id) {
        return saleService.deleteSale(id);
    }
}
