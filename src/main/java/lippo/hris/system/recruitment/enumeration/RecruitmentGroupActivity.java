package lippo.hris.system.recruitment.enumeration;

public enum RecruitmentGroupActivity {
    EVALUATION("Evaluation", 1),
    OFFERING("Offering", 2),
    PRE_EMPLOYMENT_CHECK("Pre-employment Check", 3),
    SIGN_AGREEMENT("Sign Agreement", 4),
    ONBOARDING("Onboarding", 5);

    private final String label;
    private final Integer order;

    RecruitmentGroupActivity(String label, Integer order) {
        this.label = label;
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public Integer getOrder() {
        return order;
    }
}
