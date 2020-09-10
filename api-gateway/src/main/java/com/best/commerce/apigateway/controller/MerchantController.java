package com.best.commerce.apigateway.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${cors.allowed-origins}")
@RestController
@RequestMapping(path = "/v1/merchant")
public class MerchantController {}
