public class TrueFalseQuestion {

  protected final String question;
  protected boolean answer;

  public TrueFalseQuestion(String quest) {
    question = quest;
  }

  public String getQuestion() {
    return question;
  }

  public boolean getAnswer() {
    return answer;
  }

  public void setAnswer(boolean ans) {
    answer = ans;
  }

  public String toString() {
    return question + " = " + answer;
  }

}
