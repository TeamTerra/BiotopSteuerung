package biotopsteuerung.steuerung;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import biotopsteuerung.logik.funktion.BiotopFunktion;
import biotopsteuerung.logik.schnittstelle.GOIPInputSchnittstelle;
import biotopsteuerung.logik.schnittstelle.GOIPOutputSchnittstelle;
import biotopsteuerung.steuerung.menue.EntryToMenu;

public class Controller {

	private BiotopFunktion funktion;
	private List<Terrarium> terrariumList;
	private GOIPInputSchnittstelle inputSchnittstelle;
//	private Programmsteuerung instance; 
	
	//Muss als Klassenvar. in allen Klassen vorhanden sein in der der Logger verwendet werden soll
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Controller() {

		LOGGER.log(Level.SEVERE, "Alle Exceptions");
		LOGGER.log(Level.WARNING, "Alles was keine Exception ist, aber selbst abgefangen wurde z.B. den Ablauf eines Zyklus beendet");
		LOGGER.log(Level.FINE, "Alles was in das Überwachungspanel displayed werden soll z.B. Messwerte wenn eine Aktion durchgeführt würde");
		LOGGER.log(Level.INFO,"Sonstiges");
		
//		this.instance = this;
		terrariumList = new ArrayList<Terrarium>();
		funktion = new BiotopFunktion();
		this.inputSchnittstelle = new GOIPInputSchnittstelle();

		new EntryToMenu(this);

	}

	public boolean startTerrarium(String terraID) {
		boolean isOK = false;
		
		if (isTerrariumVorhanden(terraID) || "alle".equalsIgnoreCase(terraID)) {

			for (Terrarium t : this.terrariumList) {
				if (t.getTerraID().equals(terraID) || "alle".equalsIgnoreCase(terraID)) {
					t.start();
					isOK = true;
				}
			}
			
			this.inputSchnittstelle.start();

		}

		return isOK;
	}

	public boolean addTerrarium(String terraID) {
		boolean isOK = false;

		if (!isTerrariumVorhanden(terraID)) {

			Terrarium terrarium = new Terrarium(this.getGOIPOutputSchnittstelle(), funktion, terraID);
			this.terrariumList.add(terrarium);

			this.inputSchnittstelle.addObserver(terrarium);

			isOK = true;
		}

		return isOK;
	}

	public boolean isTerrariumVorhanden(String terraID) {

		for (Terrarium t : this.terrariumList) {

			if (t.getTerraID().equals(terraID)) {
				return true;
			}

		}

		return false;
	}

	public List<Terrarium> getTerrariumList() {
		return terrariumList;
	}

	private GOIPOutputSchnittstelle getGOIPOutputSchnittstelle() {

		// wie auch immer die aussieht

		return new GOIPOutputSchnittstelle();
	}

//	public static synchronized Programmsteuerung getInstance () {
//		
//		if (Programmsteuerung.instance == null) {
//			Programmsteuerung.instance = new Programmsteuerung();
//		}
//		return Programmsteuerung.instance;
//	}

}
