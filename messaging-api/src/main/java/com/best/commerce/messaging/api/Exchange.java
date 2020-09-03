package com.best.commerce.messaging.api;

public interface Exchange<P> extends Command<P> {
  String exchange();
  String routingKey();
}
