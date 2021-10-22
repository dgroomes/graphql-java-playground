package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static dgroomes.Util.getNonNullArg;
import static java.time.temporal.ChronoField.*;

/**
 * Fetch the current time of day for the given time zone.
 */
public class LocalTimeDataFetcher implements DataFetcher<String> {

    // This formats a time to just the hour and minute. For example, "09:59PM".
    private static final DateTimeFormatter HOUR_MINUTE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(CLOCK_HOUR_OF_AMPM, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendText(ChronoField.AMPM_OF_DAY)
            .toFormatter();

    @Override
    public String get(DataFetchingEnvironment env) {
        TimeZone timeZoneArg = getNonNullArg(env, "timezone");

        var descriptor = switch (timeZoneArg) {
            case AMERICA_CHICAGO -> "America/Chicago";
            case EUROPE_LONDON -> "Europe/London";
            case ASIA_JAKARTA -> "Asia/Jakarta";
        };

        var zoneId = ZoneId.of(descriptor);
        var localDateTime = LocalDateTime.now(zoneId);
        return HOUR_MINUTE_FORMATTER.format(localDateTime);
    }
}
