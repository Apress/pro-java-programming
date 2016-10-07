public class Student {

  protected TestScore[] testScores;
  protected String name;

  public String getName() {
    return name;
  }

  public TestScore[] getTestScores() {
    return testScores;
  }

  public int getAverage() {
    int total = 0;
    for (int i = 0; i < testScores.length; i++) {
      total += testScores[i].getPercentCorrect();
    }
    return total / testScores.length;
  }

}
