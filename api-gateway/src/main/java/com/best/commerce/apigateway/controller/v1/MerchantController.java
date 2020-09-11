package com.best.commerce.apigateway.controller.v1;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${cors.allowed-origins}")
@RestController
@RequestMapping(path = "/v1/merchant")
@Api("Merchant operations")
public class MerchantController {}
