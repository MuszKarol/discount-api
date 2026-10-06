package pl.musz.karol.discountapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.musz.karol.discountapi.controller.DiscountController;
import pl.musz.karol.discountapi.controller.ImageController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DiscountApiApplicationTests {

	@Autowired
	private DiscountController discountController;

	@Autowired
	private ImageController imageController;

	@Test
	void contextLoads() {
		assertThat(discountController).isNotNull();
		assertThat(imageController).isNotNull();
	}
}
