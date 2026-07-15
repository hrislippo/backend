package lippo.hris.system.recruitment.enumeration;

public enum EmployeeRequestFormResumeType {
    EXTEND("Extend"),
    RESET("Reset");

    private final String label;

    EmployeeRequestFormResumeType(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
