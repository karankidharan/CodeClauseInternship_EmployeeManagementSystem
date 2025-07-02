public class Employee {
    private String id;
    private String name;
    private String department;
    private double baseSalary;
    private double bonus;
    private int daysPresent;
    private int leaveDays;
    public int getAbsentDays() {
        return Math.max(0, 30 - (daysPresent + leaveDays));
    }


    public Employee(String id, String name, String department, double baseSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
        this.bonus = 0;
        this.daysPresent = 0;
        this.leaveDays = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getBaseSalary() { return baseSalary; }
    public double getBonus() { return bonus; }
    public int getDaysPresent() { return daysPresent; }
    public int getLeaveDays() { return leaveDays; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    public void setBonus(double bonus) { this.bonus = bonus; }
    public void setLeaveDays(int leaveDays) { this.leaveDays = leaveDays; }

    public void markPresent() { daysPresent++; }
    public void setDaysPresent(int daysPresent) {
        this.daysPresent = daysPresent;
    }


    public double getSalaryPerDay() {
        return baseSalary / 30.0;
    }

    public int getPayableDays() {
        int payable = daysPresent - leaveDays;
        return Math.max(payable, 0);
    }

    public double getGrossPay() {
        return getPayableDays() * getSalaryPerDay();
    }

    public double getNetPay() {
        return getGrossPay() + bonus;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + department + ") | Present: " + daysPresent + " | Leaves: " + leaveDays;
    }
}
