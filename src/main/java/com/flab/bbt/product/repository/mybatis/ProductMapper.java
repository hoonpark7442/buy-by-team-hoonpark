package com.flab.bbt.product.repository.mybatis;

import com.flab.bbt.product.domain.PriceTable;
import com.flab.bbt.product.domain.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

@Mapper
public interface ProductMapper {

    int save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findListWithPagination(Pageable pageable);

    Optional<Product> findBySkuCode(String skuCode);

    int savePriceTable(PriceTable priceTable);

    Optional<PriceTable> findPriceTableByProductId(@Param("productId") Long productId,
        @Param("currentTime") LocalDateTime currentTime);
}
