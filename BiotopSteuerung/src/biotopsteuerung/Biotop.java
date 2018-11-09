package biotopsteuerung;

import biotopsteuerung.log.BiotopsLogger;
import biotopsteuerung.steuerung.Controller;

public class Biotop {

	// schon relativ weit. trz noch gut was zutun
	// TODO Programmsteuerung gegen Nullpointer und ggf. Endlosschleifen absichern
	// TODO Menü überarbeiten und erweitern: gegen Nullpointer und ggf. Endlosschleifen absichern. Eingabemethode überarbeiten. 
	// TODO DBConnector gegen Nullpointer und ggf. Entdlosschleifen absichern. 
	// TODO Schnittstellen implementieren
	// TODO Biostopslogik implementieren
	// TODO Datenbank planen und implementieren
		
	public static void main(String[] args) {

		try {
			BiotopsLogger.setup();
		}catch (Exception e) {
			System.out.println(e.toString());
			throw new RuntimeException("Problems with creating the logfiles");
		}
		
		new Controller();

	}

}
