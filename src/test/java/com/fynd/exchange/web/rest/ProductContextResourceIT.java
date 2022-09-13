package com.fynd.exchange.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fynd.exchange.IntegrationTest;
import com.fynd.exchange.domain.ProductContext;
import com.fynd.exchange.repository.ProductContextRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductContextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductContextResourceIT {

    private static final String DEFAULT_L_1 = "AAAAAAAAAA";
    private static final String UPDATED_L_1 = "BBBBBBBBBB";

    private static final String DEFAULT_L_2 = "AAAAAAAAAA";
    private static final String UPDATED_L_2 = "BBBBBBBBBB";

    private static final String DEFAULT_L_3 = "AAAAAAAAAA";
    private static final String UPDATED_L_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/product-contexts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductContextRepository productContextRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductContextMockMvc;

    private ProductContext productContext;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductContext createEntity(EntityManager em) {
        ProductContext productContext = new ProductContext()
            .l1(DEFAULT_L_1)
            .l2(DEFAULT_L_2)
            .l3(DEFAULT_L_3)
            .contextId(DEFAULT_CONTEXT_ID)
            .isActive(DEFAULT_IS_ACTIVE);
        return productContext;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductContext createUpdatedEntity(EntityManager em) {
        ProductContext productContext = new ProductContext()
            .l1(UPDATED_L_1)
            .l2(UPDATED_L_2)
            .l3(UPDATED_L_3)
            .contextId(UPDATED_CONTEXT_ID)
            .isActive(UPDATED_IS_ACTIVE);
        return productContext;
    }

    @BeforeEach
    public void initTest() {
        productContext = createEntity(em);
    }

    @Test
    @Transactional
    void createProductContext() throws Exception {
        int databaseSizeBeforeCreate = productContextRepository.findAll().size();
        // Create the ProductContext
        restProductContextMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isCreated());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeCreate + 1);
        ProductContext testProductContext = productContextList.get(productContextList.size() - 1);
        assertThat(testProductContext.getl1()).isEqualTo(DEFAULT_L_1);
        assertThat(testProductContext.getl2()).isEqualTo(DEFAULT_L_2);
        assertThat(testProductContext.getl3()).isEqualTo(DEFAULT_L_3);
        assertThat(testProductContext.getContextId()).isEqualTo(DEFAULT_CONTEXT_ID);
        assertThat(testProductContext.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createProductContextWithExistingId() throws Exception {
        // Create the ProductContext with an existing ID
        productContext.setId(1L);

        int databaseSizeBeforeCreate = productContextRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductContextMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductContexts() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        // Get all the productContextList
        restProductContextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productContext.getId().intValue())))
            .andExpect(jsonPath("$.[*].l1").value(hasItem(DEFAULT_L_1)))
            .andExpect(jsonPath("$.[*].l2").value(hasItem(DEFAULT_L_2)))
            .andExpect(jsonPath("$.[*].l3").value(hasItem(DEFAULT_L_3)))
            .andExpect(jsonPath("$.[*].contextId").value(hasItem(DEFAULT_CONTEXT_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getProductContext() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        // Get the productContext
        restProductContextMockMvc
            .perform(get(ENTITY_API_URL_ID, productContext.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productContext.getId().intValue()))
            .andExpect(jsonPath("$.l1").value(DEFAULT_L_1))
            .andExpect(jsonPath("$.l2").value(DEFAULT_L_2))
            .andExpect(jsonPath("$.l3").value(DEFAULT_L_3))
            .andExpect(jsonPath("$.contextId").value(DEFAULT_CONTEXT_ID))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductContext() throws Exception {
        // Get the productContext
        restProductContextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductContext() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();

        // Update the productContext
        ProductContext updatedProductContext = productContextRepository.findById(productContext.getId()).get();
        // Disconnect from session so that the updates on updatedProductContext are not directly saved in db
        em.detach(updatedProductContext);
        updatedProductContext.l1(UPDATED_L_1).l2(UPDATED_L_2).l3(UPDATED_L_3).contextId(UPDATED_CONTEXT_ID).isActive(UPDATED_IS_ACTIVE);

        restProductContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductContext.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductContext))
            )
            .andExpect(status().isOk());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
        ProductContext testProductContext = productContextList.get(productContextList.size() - 1);
        assertThat(testProductContext.getl1()).isEqualTo(UPDATED_L_1);
        assertThat(testProductContext.getl2()).isEqualTo(UPDATED_L_2);
        assertThat(testProductContext.getl3()).isEqualTo(UPDATED_L_3);
        assertThat(testProductContext.getContextId()).isEqualTo(UPDATED_CONTEXT_ID);
        assertThat(testProductContext.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productContext.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productContext)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductContextWithPatch() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();

        // Update the productContext using partial update
        ProductContext partialUpdatedProductContext = new ProductContext();
        partialUpdatedProductContext.setId(productContext.getId());

        partialUpdatedProductContext
            .l1(UPDATED_L_1)
            .l2(UPDATED_L_2)
            .l3(UPDATED_L_3)
            .contextId(UPDATED_CONTEXT_ID)
            .isActive(UPDATED_IS_ACTIVE);

        restProductContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductContext.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductContext))
            )
            .andExpect(status().isOk());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
        ProductContext testProductContext = productContextList.get(productContextList.size() - 1);
        assertThat(testProductContext.getl1()).isEqualTo(UPDATED_L_1);
        assertThat(testProductContext.getl2()).isEqualTo(UPDATED_L_2);
        assertThat(testProductContext.getl3()).isEqualTo(UPDATED_L_3);
        assertThat(testProductContext.getContextId()).isEqualTo(UPDATED_CONTEXT_ID);
        assertThat(testProductContext.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateProductContextWithPatch() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();

        // Update the productContext using partial update
        ProductContext partialUpdatedProductContext = new ProductContext();
        partialUpdatedProductContext.setId(productContext.getId());

        partialUpdatedProductContext
            .l1(UPDATED_L_1)
            .l2(UPDATED_L_2)
            .l3(UPDATED_L_3)
            .contextId(UPDATED_CONTEXT_ID)
            .isActive(UPDATED_IS_ACTIVE);

        restProductContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductContext.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductContext))
            )
            .andExpect(status().isOk());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
        ProductContext testProductContext = productContextList.get(productContextList.size() - 1);
        assertThat(testProductContext.getl1()).isEqualTo(UPDATED_L_1);
        assertThat(testProductContext.getl2()).isEqualTo(UPDATED_L_2);
        assertThat(testProductContext.getl3()).isEqualTo(UPDATED_L_3);
        assertThat(testProductContext.getContextId()).isEqualTo(UPDATED_CONTEXT_ID);
        assertThat(testProductContext.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productContext.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductContext() throws Exception {
        int databaseSizeBeforeUpdate = productContextRepository.findAll().size();
        productContext.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductContextMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productContext))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductContext in the database
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductContext() throws Exception {
        // Initialize the database
        productContextRepository.saveAndFlush(productContext);

        int databaseSizeBeforeDelete = productContextRepository.findAll().size();

        // Delete the productContext
        restProductContextMockMvc
            .perform(delete(ENTITY_API_URL_ID, productContext.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductContext> productContextList = productContextRepository.findAll();
        assertThat(productContextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
