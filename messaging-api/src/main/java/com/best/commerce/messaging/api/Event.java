package com.best.commerce.messaging.api;

public interface Event<P> {
  P payload();

  String topic();
}
