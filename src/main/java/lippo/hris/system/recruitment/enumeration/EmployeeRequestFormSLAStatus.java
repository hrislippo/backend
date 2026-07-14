package lippo.hris.system.recruitment.enumeration;

public enum EmployeeRequestFormSLAStatus {
    NO_SLA("No SLA"),
    EXPIRED("Expired"),
    WARNING("Warning"),
    MODERATE("Moderate"),
    SAFE("Safe");

    private final String label;

    EmployeeRequestFormSLAStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
