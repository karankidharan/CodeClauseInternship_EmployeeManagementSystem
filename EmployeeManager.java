import java.util.ArrayList;

public class EmployeeManager {
    private ArrayList<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }

    public void deleteEmployee(int index) {
        if (index >= 0 && index < employees.size()) {
            employees.remove(index);
        }
    }

    public void updateEmployee(int index, String name, String department) {
        if (index >= 0 && index < employees.size()) {
            Employee emp = employees.get(index);
            emp.setName(name);
            emp.setDepartment(department);
        }
    }

    public void markAttendance(int index) {
        if (index >= 0 && index < employees.size()) {
            employees.get(index).markPresent();

        }
    }

    public void updatePayroll(int index, double bonus) {
        if (index >= 0 && index < employees.size()) {
            employees.get(index).setBonus(bonus);
        }
    }
}
