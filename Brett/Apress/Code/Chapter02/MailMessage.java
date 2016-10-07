public class MailMessage implements Cloneable {

  protected String sender;
  protected String recipient;
  protected StringBuffer messageText;

  public MailMessage(String from, String to, String message) {
    sender = from;
    recipient = to;
    messageText = new StringBuffer(message);
  }

  public StringBuffer getMessageText() {
    return messageText;
  }

  protected Object clone() throws CloneNotSupportedException {
    MailMessage mm = (MailMessage)(super.clone());
    mm.messageText = new StringBuffer(messageText.toString());
    return mm;
  }

  public String toString() {
    return "MailMessage[sender=" + sender + ", recipient=" + recipient +
           ", messageText=" + messageText + "]";
  }

}
