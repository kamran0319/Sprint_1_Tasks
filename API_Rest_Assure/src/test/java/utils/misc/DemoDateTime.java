package utils.misc;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DemoDateTime {


    public static void main(String[] args) {


        LocalDate today = LocalDate.now();
        System.out.println(today);

        LocalDate date1 = LocalDate.of(2023, 7, 28);
        System.out.println(date1);

        LocalDate date2 = LocalDate.of(2023, Month.JANUARY, 1);
        System.out.println(date2);

        LocalDate date3 = LocalDate.parse("2023-09-04");
        System.out.println(date3);


        LocalDate updated = date3.plusDays(20).plusMonths(1).minusYears(2);

        System.out.println(updated);


        if (updated.isBefore(LocalDate.now())){
            System.out.println("Older");
        }else{
            System.out.println("Newer");
        }


        if (updated.isAfter(LocalDate.parse("2020-03-14"))){
            System.out.println("After");
        }else{
            System.out.println("Before");
        }

        System.out.println(updated.isLeapYear());

        // LocalTime

        LocalTime now = LocalTime.now();

        System.out.println(now); //13:24:57.514074600

        LocalTime localTime = LocalTime.of(23, 59, 59);
        System.out.println(localTime);

        LocalTime truncatedTo = now.truncatedTo(ChronoUnit.MINUTES);

        System.out.println(truncatedTo);

        LocalTime parse = LocalTime.parse("22:59");

        System.out.println(parse);

        //LocalDateTime

        LocalDateTime now1 = LocalDateTime.now();

        System.out.println(now1);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 31, 23, 3);
        System.out.println(localDateTime);

        LocalDateTime myDate = LocalDateTime.parse("2023-01-01T00:00:00");
        System.out.println(myDate);

        LocalDateTime start = LocalDateTime.of(2023,1,1,0,0);
        LocalDateTime end = LocalDateTime.of(2023,12,31,23,59);

        if(myDate.isAfter(start) && myDate.isBefore(end)){
            System.out.println("Within the range non-inclusive");
        }

        if(!myDate.isAfter(end) && !myDate.isBefore(start)){
            System.out.println("Within the range inclusive");
        }


        //ZonedDateTime

        ZonedDateTime now2 = ZonedDateTime.now(); //2023-08-05T13:49:04.883338500-04:00[America/New_York]

        System.out.println(now2);

        LocalDateTime now3 = LocalDateTime.now();


        ZonedDateTime newYorkTime = ZonedDateTime.of(now3, ZoneId.of("America/New_York"));
        ZonedDateTime arizonaDateTime = ZonedDateTime.of(now3, ZoneId.of("America/Chicago"));
        System.out.println(arizonaDateTime);

        System.out.println(ZoneId.getAvailableZoneIds());

        // convert new york time to chicago
        ZonedDateTime chicagoTime = newYorkTime.withZoneSameInstant(ZoneId.of("America/Chicago"));

        System.out.println(chicagoTime);


        // Duration

        LocalTime time1 = LocalTime.of(6, 15);
        LocalTime time2 = LocalTime.of(8, 10);
        Duration duration = Duration.between(time1, time2); // PT1H55M
        System.out.println(duration);

        LocalDate date4 = LocalDate.of(2022, 1, 1);
        LocalDate date5 = LocalDate.of(2023, 4, 5);
        Period period = Period.between(date4, date5); // P1Y3M4D
        System.out.println(period);


        String currentDateTime = LocalDateTime.now().toString();
        LocalDateTime convertedBack = LocalDateTime.parse(currentDateTime);


        LocalDate now4 = LocalDate.now();
        // 08/05/2023

//        String formatted = now4.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String formatted = now4.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println(formatted);

    }
}
