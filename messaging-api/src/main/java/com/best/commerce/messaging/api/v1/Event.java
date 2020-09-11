package com.best.commerce.messaging.api.v1;

/**
 * Event message for publish-subscribe channel. It must be in the API of the module that produces it
 *
 * @param <P> payload type
 */
public interface Event<P> {
  /** Event payload */
  P payload();

  /** Publish-subscribe channel name */
  String topic();
}
