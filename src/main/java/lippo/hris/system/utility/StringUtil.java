package lippo.hris.system.utility;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class StringUtil {

    public static String properCase(String input) {
        return Arrays.stream(input.toLowerCase().trim().split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            return phone;
        }
        phone = phone.replaceAll("[\\s-]", "");
        if (phone.startsWith("0")) {
            return "+62" + phone.substring(1);
        }
        if (phone.startsWith("62")) {
            return "+" + phone;
        }
        return phone;
    }
}
