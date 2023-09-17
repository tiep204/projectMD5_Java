package ra.model.dto.response;

import ra.model.domain.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryResponse {
    private Long id;
    private String name;
    private Date createAt = new Date();
    private boolean status;
    private List<Product> productList = new ArrayList<>();
}