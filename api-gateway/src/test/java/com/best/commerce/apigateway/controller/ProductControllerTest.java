package com.best.commerce.apigateway.controller;

import com.best.commerce.apigateway.ApiGatewayApplication;
import com.best.commerce.apigateway.service.ProductServiceImpl;
import com.best.commerce.apigateway.util.Fixtures;
import com.best.commerce.product.api.dto.ProductDto;
import com.best.commerce.product.api.messaging.command.CreateProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static com.best.commerce.product.api.type.DeliveryOptionType.DIRECT;
import static com.best.commerce.product.api.type.PaymentOptionType.INSTALLMENTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(
    classes = {ApiGatewayApplication.class, ProductServiceImpl.class, ObjectMapper.class})
@ActiveProfiles("test")
class ProductControllerTest {
  private static final String PRODUCT_URI = "/v1/product";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @MockBean private RabbitTemplate template;

  @Value("${cors.allowed-origins}")
  private String origin;

  @Test
  void whenCreateProductThenMessageIsSent() throws Exception {
    // given
    ProductDto product = Fixtures.buildProductDto(Set.of(INSTALLMENTS), Set.of(DIRECT));
    CreateProduct command = new CreateProduct(product);
    Mockito.doNothing().when(template).convertAndSend(command.queue(), command);

    // when
    mockMvc
        .perform(
            post(PRODUCT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ORIGIN, origin)
                .content(mapper.writeValueAsString(product)))
        .andExpect(status().isOk());

    // then
    Mockito.verify(template).convertAndSend(command.queue(), command);
  }

  @Test
  void whenRequiredFieldIsNullThenValidationError() throws Exception {
    // given
    ProductDto product = Fixtures.buildProductDto(Set.of(INSTALLMENTS), Set.of(DIRECT));
    product.setMerchantId(null);

    // when
    Exception resolvedException =
        mockMvc
            .perform(
                post(PRODUCT_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ORIGIN, origin)
                    .content(mapper.writeValueAsString(product)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResolvedException();

    // then
    Assertions.assertTrue(resolvedException instanceof MethodArgumentNotValidException);
    MethodArgumentNotValidException validationException =
        (MethodArgumentNotValidException) resolvedException;
    BindingResult bindingResult = validationException.getBindingResult();
    Assertions.assertNotNull(bindingResult);
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    Assertions.assertNotNull(fieldErrors);
    Assertions.assertEquals(1, fieldErrors.size());
    FieldError error = fieldErrors.get(0);
    Assertions.assertEquals("productDto", error.getObjectName());
    Assertions.assertEquals("merchantId", error.getField());
    Assertions.assertEquals("must not be null", error.getDefaultMessage());
  }
}

/*



*/
