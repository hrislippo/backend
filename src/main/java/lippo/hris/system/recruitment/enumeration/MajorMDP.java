package lippo.hris.system.recruitment.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum MajorMDP {
    FINANCE_ACCOUNTING_ECONOMICS(List.of("FINANCE", "KEUANGAN", "ACCOUNT", "AKUNTAN", "ECONOMIC", "EKONOMI")),
    BUSINESS_MANAGEMENT_MARKETING_HR(List.of("BUSINESS", "BISNIS", "MANAGEMENT", "MANAJEMEN", "MARKETING", "HUMAN RESOURCE", "HR", "SUMBER DAYA MANUSIA", "SDM")),
    ENGINEERING(List.of("TEKNIK", "ENGINEER"));

    private final List<String> values;

    MajorMDP(List<String> values) {
        this.values = values;
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }

        return values.stream()
                .anyMatch(label -> value.toUpperCase().contains(label));
    }

    public static MajorMDP find(String value) {
        if (value == null) {
            return null;
        }

        for (MajorMDP field : values()) {
            if (field.contains(value)) {
                return field;
            }
        }

        return null;
    }
}
