public class AddingMachine {

  /**
   *  Adds two integers together and returns the result.
   */
  public static int addIntegers(int first, int second) {
    return addIntegers(new int[] {first, second});
  }

  /**
   *  Adds some number of integers together and returns the result.
   */
  public static int addIntegers(int[] values) {
    int result = 0;
    for (int i = 0; i < values.length; i++) {
      result += values[i];
    }
    return result;
  }

}
