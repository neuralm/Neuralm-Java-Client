package net.neuralm.client;

import net.neuralm.client.messages.Message;
import java.nio.channels.CompletionHandler;

public class WriteHandler implements CompletionHandler<Integer, Message> {

  private final NeuralmClient client;

  WriteHandler(NeuralmClient client) {
    this.client = client;
  }

  @Override
  public void completed(Integer result, Message attachment) {
    System.out.println(String.format("Send %s", attachment.toString()));
    if (client.writeBufferHasRemaining()) {
      client.forceWriteBufferToCompletion();
      System.out.println("Forced write buffer to be clear");
    }
    if (client.hasMessagesInQueue()) {
      Message message = client.getMessageFromQueue();
      System.out.println("Received message from queue!");
      client.hardSend(message);
    } else {
      client.isWriting.set(false);
      System.out.println("isWriting is set to false");
    }
  }

  @Override
  public void failed(Throwable exc, Message attachment) {
    exc.printStackTrace();
  }
}