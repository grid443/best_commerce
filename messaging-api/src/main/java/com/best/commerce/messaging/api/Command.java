package com.best.commerce.messaging.api;

public interface Command<P> {
  P payload();

  String queue();
}
