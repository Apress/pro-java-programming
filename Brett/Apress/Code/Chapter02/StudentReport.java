public class StudentReport {

  public void printStudentGrades(Student[] students) {
    Student student;
    for (int i = 0; i < students.length; i++) {
      student = students[i];
      System.out.println("Final grade for " + student.getName() +
          " is " + student.getAverage());
    }
  }

}
