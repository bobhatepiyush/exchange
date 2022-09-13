package com.fynd.exchange.repository;

import com.fynd.exchange.domain.ProductContext;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductContext entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductContextRepository extends JpaRepository<ProductContext, Long> {}
