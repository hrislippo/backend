package lippo.hris.system.recruitment.enumeration;

import java.util.Arrays;

public enum EmployeeRequestFormActivityStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    FAILED;

    public static EmployeeRequestFormActivityStatus findStatus(String input) {
        return Arrays.stream(EmployeeRequestFormActivityStatus.values())
                .filter(s -> input.toLowerCase().contains(s.name().toLowerCase()))
                .findFirst().get();
    }
}
