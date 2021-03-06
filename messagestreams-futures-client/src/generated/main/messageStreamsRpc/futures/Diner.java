package futures;

@javax.annotation.Generated(
    value = "jauntsdn.com rsocket-rpc compiler (version 1.1.3)",
    comments = "source: service.proto")
public interface Diner {
  String SERVICE = "futures.Diner";
  Class<?> SERVICE_TYPE = futures.Diner.class;

  String METHOD_ORDER = "order";
  boolean METHOD_ORDER_IDEMPOTENT = false;
  int METHOD_ORDER_RANK = 0;

  String METHOD_PAY = "pay";
  boolean METHOD_PAY_IDEMPOTENT = false;
  int METHOD_PAY_RANK = 0;

  java.util.concurrent.CompletionStage<futures.Meal> order(futures.Order message, io.netty.buffer.ByteBuf metadata);

  java.util.concurrent.CompletionStage<Void> pay(futures.Payment message, io.netty.buffer.ByteBuf metadata);

  default java.util.concurrent.CompletionStage<futures.Meal> order(futures.Order message) {
    return order(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }

  default java.util.concurrent.CompletionStage<Void> pay(futures.Payment message) {
    return pay(message, io.netty.buffer.Unpooled.EMPTY_BUFFER);
  }
}
