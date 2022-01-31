package dgroomes;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static dgroomes.GraphqlUtil.getNonNullArg;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

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
        String timeZoneArg = getNonNullArg(env, "timezone");
        var zoneId = ZoneId.of(timeZoneArg);
        var localDateTime = LocalDateTime.now(zoneId);
        return HOUR_MINUTE_FORMATTER.format(localDateTime);
    }
}
