import javax.swing.*;
import java.awt.*;

public class EmployeeManagementApp extends JFrame {
    private EmployeeManager manager = new EmployeeManager();
    private DefaultListModel<Employee> listModel = new DefaultListModel<>();
    private JList<Employee> employeeJList = new JList<>(listModel);

    public EmployeeManagementApp() {
        setTitle("Employee Management System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton markPresentBtn = new JButton("Mark Present");
        JButton editAttendanceBtn = new JButton("Edit Attendance");
        JButton viewPayrollBtn = new JButton("View Payroll");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(markPresentBtn);
        buttonPanel.add(editAttendanceBtn);
        buttonPanel.add(viewPayrollBtn);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(employeeJList), BorderLayout.CENTER);

        // Add Employee
        addBtn.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField deptField = new JTextField();
            JTextField salaryField = new JTextField();

            Object[] input = {
                    "ID:", idField,
                    "Name:", nameField,
                    "Department:", deptField,
                    "Base Salary:", salaryField
            };

            int option = JOptionPane.showConfirmDialog(null, input, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    double baseSalary = Double.parseDouble(salaryField.getText());
                    Employee emp = new Employee(idField.getText(), nameField.getText(), deptField.getText(), baseSalary);
                    manager.addEmployee(emp);
                    listModel.addElement(emp);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid salary input.");
                }
            }
        });

        // Edit Employee
        editBtn.addActionListener(e -> {
            int index = employeeJList.getSelectedIndex();
            if (index != -1) {
                Employee emp = listModel.get(index);
                JTextField nameField = new JTextField(emp.getName());
                JTextField deptField = new JTextField(emp.getDepartment());

                Object[] input = {
                        "Name:", nameField,
                        "Department:", deptField
                };

                int option = JOptionPane.showConfirmDialog(null, input, "Edit Employee", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    emp.setName(nameField.getText());
                    emp.setDepartment(deptField.getText());
                    listModel.set(index, emp);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to edit.");
            }
        });

        // Delete Employee
        deleteBtn.addActionListener(e -> {
            int index = employeeJList.getSelectedIndex();
            if (index != -1) {
                manager.deleteEmployee(index);
                listModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
            }
        });

        // Mark Attendance
        markPresentBtn.addActionListener(e -> {
            int index = employeeJList.getSelectedIndex();
            if (index != -1) {
                manager.markAttendance(index);
                listModel.set(index, listModel.get(index));
                JOptionPane.showMessageDialog(this, "✅ Attendance marked for: " + listModel.get(index).getName());
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Please select an employee to mark attendance.");
            }
        });

        // Edit Attendance
        editAttendanceBtn.addActionListener(e -> {
            int index = employeeJList.getSelectedIndex();
            if (index != -1) {
                Employee emp = listModel.get(index);
                String input = JOptionPane.showInputDialog(this,
                        "Enter new number of present days:",
                        emp.getDaysPresent());

                if (input != null) {
                    try {
                        int newDays = Integer.parseInt(input);
                        if (newDays >= 0 && newDays <= 31) {
                            emp.setDaysPresent(newDays);
                            listModel.set(index, emp);
                            JOptionPane.showMessageDialog(this, "✅ Attendance updated.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Enter a number between 0 and 31.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number entered.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to edit attendance.");
            }
        });

        // View Payroll (with absent days)
        viewPayrollBtn.addActionListener(e -> {
            int index = employeeJList.getSelectedIndex();
            if (index != -1) {
                Employee emp = listModel.get(index);

                JTextField bonusField = new JTextField(String.valueOf(emp.getBonus()));
                JTextField leaveField = new JTextField(String.valueOf(emp.getLeaveDays()));

                Object[] input = {
                        "Base Salary (₹): " + emp.getBaseSalary(),
                        "Bonus (₹):", bonusField,
                        "Leave Days:", leaveField
                };

                int option = JOptionPane.showConfirmDialog(null, input, "Payroll Details for " + emp.getName(), JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        double bonus = Double.parseDouble(bonusField.getText());
                        int leaves = Integer.parseInt(leaveField.getText());

                        emp.setBonus(bonus);
                        emp.setLeaveDays(leaves);

                        double salaryPerDay = emp.getSalaryPerDay();
                        int payableDays = emp.getPayableDays();
                        double gross = emp.getGrossPay();
                        double net = emp.getNetPay();

                        String breakdown = "📊 Payroll Breakdown for " + emp.getName() + ":\n\n"
                                + "📅 Days Present: " + emp.getDaysPresent() + "\n"
                                + "🏖️ Leave Days: " + leaves + "\n"
                                + "❌ Absent Days: " + emp.getAbsentDays() + "\n"
                                + "🧮 Payable Days: " + payableDays + "\n"
                                + "💸 Salary Per Day: ₹" + String.format("%.2f", salaryPerDay) + "\n"
                                + "📈 Gross Pay: ₹" + String.format("%.2f", gross) + "\n"
                                + "💎 Bonus: ₹" + bonus + "\n"
                                + "✅ Net Pay: ₹" + String.format("%.2f", net);

                        JOptionPane.showMessageDialog(this, breakdown);
                        listModel.set(index, emp);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Enter valid numbers.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee to view payroll.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagementApp().setVisible(true));
    }
}
