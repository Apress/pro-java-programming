public class LoggingException extends Exception {

  protected Exception originalException;

  public LoggingException(String message, Exception trigger) {
    super(message);
    originalException = trigger;
  }

  public Exception getOriginalException() {
    return originalException;
  }

  public void printStackTrace(java.io.PrintStream ps) {
    if (originalException == null) {
      super.printStackTrace(ps);
    } else {
      ps.println(this);
      originalException.printStackTrace(ps);
    }
  }

  public void printStackTrace(java.io.PrintWriter pw) {
    if (originalException == null) {
      super.printStackTrace(pw);
    } else {
      pw.println(this);
      originalException.printStackTrace(pw);
    }
  }

  public void printStackTrace() {
    printStackTrace(System.err);
  }

  public String getMessage() {
    if (originalException == null) {
      return super.getMessage();
    } else {
      return super.getMessage() + "; nested exception is: \n\t" +
        originalException.toString();
    }
  }

}
