public class Employee {

  public final int employeeID;
  public final String firstName;
  public final String lastName;

  public Employee(int id, String first, String last) {
    employeeID = id;
    firstName = first;
    lastName = last;
  }

  public boolean equals(Object obj) {
    if ((obj != null) && (obj instanceof Employee)) {
      Employee emp = (Employee)obj;
      if (this.employeeID == emp.employeeID) {
        return true;
      }
    }
    return false;
  }

}
