package biotopsteuerung.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BiotopsLogger {

	static private FileHandler fileHandler;
	static private DisplayHandler consolHandler;

	static private DisplayFormatter displayFormatter;
	static private SimpleFormatter logFormatter;

	public static void setup() throws IOException {

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		logger.setLevel(Level.ALL);
		consolHandler = new DisplayHandler();
		displayFormatter = new DisplayFormatter();
		consolHandler.setFormatter(displayFormatter);
		logger.addHandler(consolHandler);

		fileHandler = new FileHandler("Log " + calculateDate(System.currentTimeMillis()) + ".txt");
		logFormatter = new SimpleFormatter();
		fileHandler.setFormatter(logFormatter);
		logger.addHandler(fileHandler);

	}

	private static String calculateDate(long millisecs) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		Date resultDate = new Date(millisecs);
		return dateFormat.format(resultDate);
	}
}
