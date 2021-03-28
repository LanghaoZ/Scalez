package cc.lzhong.scalez.service;

import cc.lzhong.scalez.dao.ProductDao;
import cc.lzhong.scalez.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProduct();
    }

}