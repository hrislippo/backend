package lippo.hris.system.recruitment.enumeration;

public enum RequisitionType {
    ADDITIONAL("Additional"),
    REPLACEMENT("Replacement"),;

    private final String label;

    RequisitionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
