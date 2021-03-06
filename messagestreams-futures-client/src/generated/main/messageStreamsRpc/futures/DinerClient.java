package futures;

@javax.annotation.Generated(
    value = "jauntsdn.com rpc compiler (version 1.1.3)",
    comments = "source: service.proto")
@com.jauntsdn.rsocket.Rpc.Generated(
    role = com.jauntsdn.rsocket.Rpc.Role.CLIENT,
    service = Diner.class)
public final class DinerClient implements Diner {
  private final com.jauntsdn.rsocket.MessageStreams streams;
  private final io.netty.buffer.ByteBufAllocator allocator;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<futures.Meal> orderInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<Void> payInstrumentation;
  private final com.jauntsdn.rsocket.Rpc.Codec rpcCodec;

  private DinerClient(com.jauntsdn.rsocket.MessageStreams streams, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
    this.streams = streams;
    this.allocator = streams.allocator().orElse(io.netty.buffer.ByteBufAllocator.DEFAULT);
    com.jauntsdn.rsocket.RpcInstrumentation i = instrumentation == null
      ? streams.attributes().attr(com.jauntsdn.rsocket.Attributes.RPC_INSTRUMENTATION)
      : instrumentation.orElse(null);
    if (i == null) {
      this.orderInstrumentation = null;
      this.payInstrumentation = null;
    } else {
      this.orderInstrumentation = i.instrument("client", Diner.SERVICE, Diner.METHOD_ORDER, true);
      this.payInstrumentation = i.instrument("client", Diner.SERVICE, Diner.METHOD_PAY, false);
    }
    com.jauntsdn.rsocket.Rpc.Codec codec = streams.attributes().attr(com.jauntsdn.rsocket.Attributes.RPC_CODEC);
    if (codec != null) {
      rpcCodec = codec;
      if (codec.isDisposable()) {
        streams.onClose().thenAccept(ignored -> codec.dispose());
      }
      return;
    }
    throw new IllegalArgumentException("MessageStreams " + streams.getClass() + " does not provide RPC codec");
  }

  public static DinerClient create(com.jauntsdn.rsocket.MessageStreams streams, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
    java.util.Objects.requireNonNull(streams, "streams");
    java.util.Objects.requireNonNull(instrumentation, "instrumentation");
    return new DinerClient(streams, instrumentation);
  }

  public static DinerClient create(com.jauntsdn.rsocket.MessageStreams streams) {
    java.util.Objects.requireNonNull(streams, "streams");
    return new DinerClient(streams, null);
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = futures.Meal.class)
  public java.util.concurrent.CompletionStage<futures.Meal> order(futures.Order message, io.netty.buffer.ByteBuf metadata) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    int localHeader = com.jauntsdn.rsocket.MessageMetadata.header(metadata);
    boolean isDefaultService = com.jauntsdn.rsocket.MessageMetadata.defaultService(localHeader);
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : Diner.SERVICE;
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, localHeader, service, Diner.METHOD_ORDER, false, Diner.METHOD_ORDER_IDEMPOTENT, dataSize, externalMetadataSize);
    encode(content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, Diner.METHOD_ORDER_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<futures.Meal> instrumentationListener = null;
    if (orderInstrumentation != null) {
      instrumentationListener = orderInstrumentation.create();
      instrumentationListener.onStart();
    }
    java.util.concurrent.CompletionStage<futures.Meal> order = streams.requestResponse(msg).thenApply(decode(futures.Meal.parser()));
    if (instrumentationListener != null) {
      order.whenComplete(instrumentationListener.onComplete());
    }
    return order;
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = com.google.protobuf.Empty.class)
  public java.util.concurrent.CompletionStage<Void> pay(futures.Payment message, io.netty.buffer.ByteBuf metadata) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    int localHeader = com.jauntsdn.rsocket.MessageMetadata.header(metadata);
    boolean isDefaultService = com.jauntsdn.rsocket.MessageMetadata.defaultService(localHeader);
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : Diner.SERVICE;
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, localHeader, service, Diner.METHOD_PAY, false, Diner.METHOD_PAY_IDEMPOTENT, dataSize, externalMetadataSize);
    encode(content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, Diner.METHOD_PAY_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<Void> instrumentationListener = null;
    if (payInstrumentation != null) {
      instrumentationListener = payInstrumentation.create();
      instrumentationListener.onStart();
    }
    java.util.concurrent.CompletionStage<Void> pay = streams.fireAndForget(msg);
    if (instrumentationListener != null) {
      pay.whenComplete(instrumentationListener.onComplete());
    }
    return pay;
  }

  private io.netty.buffer.ByteBuf encode(io.netty.buffer.ByteBuf content, final com.google.protobuf.MessageLite message) {
    int length = message.getSerializedSize();
    try {
      int writerIndex = content.writerIndex();
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(content.internalNioBuffer(writerIndex, length)));
      content.writerIndex(writerIndex + length);
      return content;
    } catch (Throwable t) {
      content.release();
      com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
      throw new com.jauntsdn.rsocket.exceptions.SerializationException("DinerClient: message serialization error", t);
    }
  }

  private static <T> java.util.function.Function<com.jauntsdn.rsocket.Message, T> decode(final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<com.jauntsdn.rsocket.Message, T>() {
      @Override
      public T apply(com.jauntsdn.rsocket.Message message) {
        try {
          io.netty.buffer.ByteBuf messageData = message.data();
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(messageData.internalNioBuffer(0, messageData.readableBytes()));
          return parser.parseFrom(is);
        } catch (Throwable t) {
          com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
          throw new com.jauntsdn.rsocket.exceptions.SerializationException("DinerClient: message deserialization error", t);
        } finally {
          message.release();
        }
      }
    };
  }
}
