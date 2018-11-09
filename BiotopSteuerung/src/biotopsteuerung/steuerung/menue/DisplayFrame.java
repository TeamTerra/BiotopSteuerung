package biotopsteuerung.steuerung.menue;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import biotopsteuerung.log.DisplayHandler;

class DisplayFrame {

	static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	JTextArea jTAConsolOutPut = null;
	
	protected DisplayFrame() {

		JPanel panel = buildPanel();

		JFrame frame = new JFrame();

		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	private JPanel buildPanel() {
		JPanel panel = new JPanel();
		// panel.setName(MenueConstants.PANEL_ID);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		jTAConsolOutPut = new JTextArea(24, 80);
		jTAConsolOutPut.setBackground(Color.BLACK);
		jTAConsolOutPut.setForeground(Color.LIGHT_GRAY);
		jTAConsolOutPut.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		jTAConsolOutPut.setForeground(Color.green);
		jTAConsolOutPut.setEditable(false);
		jTAConsolOutPut.setName(MenuConstants.JTA_CONSOL_OUTPUT_ID);

		Handler[] handlers =  LOGGER.getHandlers();
		
		for(Handler h : handlers) {
			
			if(h instanceof DisplayHandler) {
				((DisplayHandler) h).setOuputStream(new OutputStream() {
					
					@Override
					public void write(int b) throws IOException {
						jTAConsolOutPut.append(String.valueOf((char) b));			
					}
				});
			}
		}
		
		panel.add(jTAConsolOutPut);

		return panel;
	}


	
}
