package biotopsteuerung.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BiotopTools {

	public static Instant getZeitStempel() {
		return Instant.now();
	}
	
	public static LocalDateTime getLocalDateTime() {
		ZoneId z = ZoneId.of(Constants.BERLIN_TIMEZONE_ID);
		return LocalDateTime.now(z);
	}

	public static LocalDateTime instantZuLocalDateTime(Instant instant) {
		return LocalDateTime.ofInstant(instant, ZoneId.of(Constants.BERLIN_TIMEZONE_ID));
	}

	public static Instant localDateTimeZuInstant(LocalDateTime ldt) {
		return Instant.parse(ldt.toString());
	}
}
