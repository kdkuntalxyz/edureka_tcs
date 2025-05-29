package com.deep.order.api;

import com.deep.order.config.MockKafkaConfig;
import com.deep.order.domain.Order;
import com.deep.order.repo.Repo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@Import(MockKafkaConfig.class)
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Repo repo;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProps(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void cleanUp() {
        repo.deleteAll();
    }

    @Test
    void testPlaceOrder() throws Exception {
        Order order = Order.builder()
                .customerId(1)
                .productId("prod456")
                .quantity(2)
                .amount(299.99)
                .build();

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.customerId").value(1));
    }

    @Test
    void testGetAllOrders() throws Exception {
        Order order = Order.builder()
                .customerId(2)
                .productId("prod123")
                .quantity(1)
                .amount(100.00)
                .status("CREATED")
                .build();
        repo.save(order);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testDeleteOrder() throws Exception {
        Order order = repo.save(Order.builder()
                .customerId(3)
                .productId("prod001")
                .quantity(1)
                .amount(199.99)
                .status("CREATED")
                .build());

        mockMvc.perform(delete("/orders/" + order.getId()))
                .andExpect(status().isOk());
    }
}
