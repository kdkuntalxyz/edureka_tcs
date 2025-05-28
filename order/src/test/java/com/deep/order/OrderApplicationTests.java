package com.deep.order;

import com.deep.order.repo.Repo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OrderApplicationTests {

	@Mock
	Repo repo;
	@Test
	void contextLoads() {
	}

}
