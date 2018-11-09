package biotopsteuerung.log;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;

/**
 * @author Thomas
 *
 */
public class DisplayHandler extends ConsoleHandler {

	/**
	 * Create a ConsoleHandler for System.err. 
	 * The ConsoleHandler is configured based on LogManager properties (or their default values).
	 */
	public DisplayHandler() {
		super();
	}
	
	/**
	 * Aendert den Outputstream den dieses Log verwendet.
	 * 
	 * @param out
	 */
	public void setOuputStream(OutputStream out) {

		if (null != out) {
			this.setOutputStream(out);

		}
	}
}
