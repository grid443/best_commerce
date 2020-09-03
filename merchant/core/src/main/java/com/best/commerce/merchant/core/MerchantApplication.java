package com.best.commerce.merchant.core;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MerchantApplication {

  public static void main(String[] args) {
    SpringApplication.run(MerchantApplication.class, args);
  }
}
