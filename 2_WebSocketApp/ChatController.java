@Data
public class ChatMessage {
  private String from;
  private String text;
}

@MessageMapping("/chat")
@SendTo("/topic/messages")
public ChatMessage handle(ChatMessage msg) {
  return msg;
}
