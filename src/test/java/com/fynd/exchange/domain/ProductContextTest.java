package com.fynd.exchange.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fynd.exchange.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductContextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductContext.class);
        ProductContext productContext1 = new ProductContext();
        productContext1.setId(1L);
        ProductContext productContext2 = new ProductContext();
        productContext2.setId(productContext1.getId());
        assertThat(productContext1).isEqualTo(productContext2);
        productContext2.setId(2L);
        assertThat(productContext1).isNotEqualTo(productContext2);
        productContext1.setId(null);
        assertThat(productContext1).isNotEqualTo(productContext2);
    }
}
