package biotopsteuerung.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import biotopsteuerung.common.Constants;

public class DisplayFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		StringBuffer buffer = new StringBuffer();

		if(record.getLevel().intValue() <= Level.SEVERE.intValue()) {

			buffer.append(record.getMessage());
			buffer.append(Constants.SYSTEM_SEPARATOR);
		}
		//else tu nix
		
		return buffer.toString();
	}

	private String calculateDate(long millisecs) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultDate = new Date(millisecs);
		return dateFormat.format(resultDate);
	}
	
}
