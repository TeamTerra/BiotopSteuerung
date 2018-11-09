package biotopsteuerung.steuerung.menue;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import biotopsteuerung.steuerung.Controller;

abstract class Menue {

	JTextField jTFInputField;
	JTextArea jTAMenuText;
	JTextArea jTAMenuOutput;
	JPanel panel;
	MenuTools menueTools;
	String input = "";
	File menueFile;
	Controller controller;
	String separator = System.getProperty("line.separator");

	/**
	 * 
	 * 
	 * @param panel
	 * @param menueTools
	 * @param controller
	 */
	protected Menue(JPanel panel, MenuTools menueTools, Controller controller) {
		Component[] components = panel.getComponents();
		this.panel = panel;
		this.menueTools = menueTools;
		this.controller = controller;

		for (Component c : components) {

			if (null != c.getName()) {

				if (c.getName().equals(MenuConstants.INPUT_TEXTFIELD_ID)) {
					jTFInputField = (JTextField) c;
					jTFInputField.requestFocusInWindow();
					break;
				}

				if (c.getName().equals(MenuConstants.JTA_MENUE_TEXT_ID)) {
					this.jTAMenuText = (JTextArea) c;
				}

				if (c.getName().equals(MenuConstants.JTA_CONSOL_OUTPUT_ID)) {
					this.jTAMenuOutput = (JTextArea) c;
				}

			}
		}

		this.buildMenue();
	}

	protected void buildMenue() {
		jTFInputField.addActionListener(this.erzeugeActionListener());
		this.buildMenuBody();
	};

	/**
	 * @param input
	 * @return
	 */
	protected String readActionParameterInput(String input) {
		String actionParameter = "";
		
		input = input.replace(" ", "");
		if(input.contains("(")) {
			actionParameter = input.substring(0, input.indexOf("("));
		}else {
			actionParameter = input;
		}
			
		
		return actionParameter;
	}
	
	/**
	 * Liest die Zusatzparameter aus.
	 * 
	 * @param input
	 * @return Liste mit allen Zusatzparametern.
	 */
	protected List<String> readParameterInput(String input) {
		String tempValue = "";
		String regex = "\\(.{1,}\\)";
		List<String> parameterList = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			String parameters = matcher.group(0);
			//Entfernt die Parameter-Klammern.
			parameters = parameters.substring(1, parameters.length() - 1);

			while (parameters.contains(",")) {
				tempValue = parameters.substring(0, parameters.indexOf(","));
				parameterList.add(tempValue);

				parameters = parameters.substring(parameters.indexOf(",") + 1);
			}

			parameterList.add(parameters);
		}

		return parameterList;
	}

	/**
	 * Schreib den Inhalt des Menuefiles mit der übergebenen ID in die JTAMenuText
	 * JTextArea. Falls kein Menue mit der ID gefunden wird, wird ein File not found
	 * Text angezeigt.
	 * 
	 * @param menueID
	 */
	protected void paintMenuText(String menueID) {

		File file = this.menueTools.getMenuFile(menueID);

		if (null != file) {
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = br.readLine();

				while (null != line) {

					this.jTAMenuText.append(line);
					this.jTAMenuText.append(this.separator);
					line = br.readLine();

				}
			} catch (IOException e) {

				this.jTAMenuText.setText(MenuConstants.MENUE_FILE_NOT_FOUND_TEXT);
			}

		} else {
			this.jTAMenuText.setText(MenuConstants.MENUE_FILE_NOT_FOUND_TEXT);
		}

	}

	/**
	 * Fügt eine Zeile in das jTAMenuOutput jTextArea ein. Bei mehr als 15 Zeilen
	 * werden alle Zeilen nach obengeschoben, die erste Zeile entfernt und fügt eine
	 * neue Zeile hinzu.
	 * 
	 * @param line
	 */
	protected void addLine(String line) {

		String content = "";
		String subContent = "";

		if (this.jTAMenuOutput.getLineCount() >= 15) {

			content = this.jTAMenuOutput.getText();
			this.jTAMenuOutput.setText("");
			subContent = content.substring(content.indexOf(this.separator) + this.separator.length());
			this.jTAMenuOutput.append(subContent);
			this.jTAMenuOutput.append(this.separator);
			this.jTAMenuOutput.append(line);

		} else {
			this.jTAMenuOutput.append(this.separator + line);
		}

	}

	protected abstract void buildMenuBody();

	protected abstract ActionListener erzeugeActionListener();
//Copy in erzeugeActionListener-Body

//	ActionListener actionListener = new ActionListener() {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			input = inputField.getText();
//
//			if (!input.equals("")) {
//
//				switch (input) {
//
//				default:
//					System.out.println("Falscher Input");
//				}
//			}
//
//			inputField.setText("");
//		}
//	};
//
//	return actionListener;
}
