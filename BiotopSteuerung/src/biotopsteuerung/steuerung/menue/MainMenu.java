package biotopsteuerung.steuerung.menue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import biotopsteuerung.steuerung.Controller;

/**
 * Main-Menu. Muss noch umangepasst werden wenn die eig. Menüs erstellt werden und von dieses Klasse aus aufrufbar gemacht werden
 * 
 * @author Thomas
 *
 */
class MainMenu extends Menue {

	String terraID = "";
	boolean eingabe = false;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected MainMenu(JPanel panel, MenuTools menueTools, Controller steuerung) {
		super(panel, menueTools, steuerung);
		this.jTFInputField.setText("");
	}

	@Override
	protected void buildMenuBody() {

		this.jTAMenuText.setText("");
		super.paintMenuText(MenuConstants.MAIN_MENUE_ID);
	}

	@Override
	protected ActionListener erzeugeActionListener() {

		ActionListener actionListener = new ActionListener() {
		
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(jTFInputField.getText());
				
				input = readActionParameterInput(jTFInputField.getText());
				
				readParameterInput(jTFInputField.getText());

				if (!input.equals("")) {

					switch (input) {

					case "1":
						LOGGER.log(Level.SEVERE, "doOption1");
						doOption1();
						break;

					case "2":
						doOption2();
						break;

					case "e":
						doOptionExit();
						break;
					case "j":
						starteTerrarien();
						eingabe = false;
						break;

					case "l":
					
						LOGGER.log(Level.SEVERE, "LOG DAT SHIT");
						LOGGER.log(Level.FINE, "fine");
						LOGGER.log(Level.WARNING, "warning");
						break;
					default:
						System.out.println("Falscher Input");
					}
				}
			}
		};

		return actionListener;
	}

	private void doOption1() {

		new RegisterMenue(panel, menueTools, this, controller);
	}

	private void doOption2() {

		new DisplayFrame();
		
//		this.eingabe = true;
//		
//		System.out.println(this.seperator + "Terrarium starten" + this.seperator);
//		this.terraID = this.fordereInput("TerraID eingeben. \"Alle\" für alle.");
//
//		System.out.println("TerraID: " + this.terraID);
//		System.out.println("Terrarium starten: n = nein j = ja");

	}

	private void starteTerrarien() {
		
			
			buildMenuBody();
			if(controller.startTerrarium(this.terraID)) {
			
				System.out.println(	System.getProperty("line.separator"));
				System.out.println("Terrarium gestartet.");
				
			}else {
				
				System.out.println("ID konnte nicht gestartet werden.");
				
			}
	}
	
	private void doOptionExit() {
		System.exit(0);
	}

}
