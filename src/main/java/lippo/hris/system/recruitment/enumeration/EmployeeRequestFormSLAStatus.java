package lippo.hris.system.recruitment.enumeration;

public enum EmployeeRequestFormSLAStatus {
    WITHIN("Within"),
    APPROACHING("Approaching"),
    OVERDUE("Overdue");

    private final String label;

    EmployeeRequestFormSLAStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
