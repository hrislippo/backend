package lippo.hris.system.emailengine.service;

import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class IcsService {

    public String createIcs(
            String uid,
            String title,
            String description,
            String location,
            ZonedDateTime start,
            ZonedDateTime end,
            String email,
            Integer sequence
    ) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "METHOD:REQUEST\n" +
                "BEGIN:VEVENT\n" +
                "UID:" + uid + "\n" +
                "SEQUENCE:" + sequence + "\n" +
                "DTSTAMP:" + ZonedDateTime.now(ZoneOffset.UTC).format(formatter) + "\n" +
                "DTSTART:" + start.withZoneSameInstant(ZoneOffset.UTC).format(formatter) + "\n" +
                "DTEND:" + end.withZoneSameInstant(ZoneOffset.UTC).format(formatter) + "\n" +
                "SUMMARY:" + title + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "LOCATION:" + location + "\n" +
                "ORGANIZER:MAILTO:hrislippo2025@gmail.com" + "\n" +
                "ATTENDEE;RSVP=TRUE:MAILTO:" + email + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }

    public String cancelIcs(
            String uid,
            String email,
            String title,
            String description,
            Integer sequence,
            ZonedDateTime start
    ) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

//        return "METHOD:CANCEL\n" +
//                "UID:"+uid+"\n" +
//                "SEQUENCE:2+\n" +
//                "STATUS:CANCELLED\n" +
//                "DTSTAMP:" + ZonedDateTime.now(ZoneOffset.UTC).format(formatter);

        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "METHOD:CANCEL\n" +
                "BEGIN:VEVENT\n" +
                "UID:" + uid + "\n" +
                "SEQUENCE:" + sequence + "\n" +
                "DTSTAMP:" + ZonedDateTime.now(ZoneOffset.UTC).format(formatter) + "\n" +
                "DTSTART:" + start.withZoneSameInstant(ZoneOffset.UTC).format(formatter) + "\n" +
                "SUMMARY:" + title + "\n" +
                "DESCRIPTION:" + description + "\n" +
                "ORGANIZER:MAILTO:hrislippo2025@gmail.com" + "\n" +
                "ATTENDEE:MAILTO:" + email + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }
}
