import java.util.Vector;

public class Roster {

  protected int capacity;
  protected Vector students;

  public Roster(int max) {
    capacity = max;
    students = new Vector();
  }

  /**
   *  Enrolls the student in this course.
   *
   *  @param  name    Name of the student to enroll.
   */
  public void enrollStudent(String name) {
    students.addElement(name);
  }

  /**
   *  Attempts to enroll a student in this course. The student is only
   *  added if the capacity limit for the course has not been reached.
   *
   *  @param  name    Name of the student to enroll.
   *  @return      <code>true</code> if the student was added
   *        to the list, <code>false</code> otherwise.
   */
  public boolean enrollStudentConditionally(String name) {
    boolean isEnrolled = false;
    if (students.size() < capacity) {
    enrollStudent(name);
    isEnrolled = true;
    }
    return isEnrolled;
  }

}