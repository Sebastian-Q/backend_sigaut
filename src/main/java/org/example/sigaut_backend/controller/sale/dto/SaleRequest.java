package org.example.sigaut_backend.controller.sale.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sigaut_backend.models.Product;

import java.util.List;

@Getter
@Setter
public class SaleRequest {
    private Double total;
    private Double amountSale;
    private String employee;
    private String payMethod;
    private List<Product> productsList;
    private Long idUser;

    public SaleRequest(Double total, Double amountSale, String employee, String payMethod, List<Product> productsList, Long idUser) {
        this.total = total;
        this.amountSale = amountSale;
        this.employee = employee;
        this.payMethod = payMethod;
        this.productsList = productsList;
        this.idUser = idUser;
    }
}
