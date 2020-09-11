package com.best.commerce.messaging.api.v1;

/**
 * Request-reply message. It must be in the API of the module that processes it
 *
 * @param <P> payload type
 */
public interface Exchange<P> extends Command<P> {
  /** Exchange point name */
  String exchange();

  /** Binding key between exchange and queue */
  String routingKey();
}
