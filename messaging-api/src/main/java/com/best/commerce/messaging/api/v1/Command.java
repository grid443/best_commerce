package com.best.commerce.messaging.api.v1;

/**
 * Command message for point-to-point channel. It must be in the API of the module that processes it
 *
 * @param <P> payload type
 */
public interface Command<P> {
  /** Command payload */
  P payload();

  /** Point-to-point channel name */
  String queue();
}
