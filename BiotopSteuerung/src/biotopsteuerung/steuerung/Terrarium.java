package biotopsteuerung.steuerung;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import biotopsteuerung.logik.data.UmgebungsDaten;
import biotopsteuerung.logik.datenbank.DBConnector;
import biotopsteuerung.logik.funktion.BiotopFunktion;
import biotopsteuerung.logik.schnittstelle.GOIPInputSchnittstelle;
import biotopsteuerung.logik.schnittstelle.GOIPOutputSchnittstelle;

public class Terrarium implements Observer{

	private GOIPOutputSchnittstelle ouputSchnittstelle;
	private BiotopFunktion funktion;
	private List<UmgebungsDaten> umgebungsSollDatenList;
	private DBConnector connector;
	private String terraID;
	private boolean verarbeiteInput;
	
	public Terrarium (GOIPOutputSchnittstelle output, BiotopFunktion funktion, String terraID) {
		
		this.funktion = funktion;
		this.connector = DBConnector.getInstance();
		this.ouputSchnittstelle = output;
		this.terraID = terraID;
		
	//	this.umgebungsSollDatenList = this.leseSollDatenAusDB();
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// here is where the magic happens
		
		if(this.verarbeiteInput) {
			GOIPInputSchnittstelle input = (GOIPInputSchnittstelle) o;
			System.out.println("Daten gelesen: " + input.getTaktID());
		}
		
	}

	public void start() {
		this.verarbeiteInput = true;
	}
	
	private List<UmgebungsDaten> leseSollDatenAusDB() {
		List<UmgebungsDaten> sollDatenList = new ArrayList<UmgebungsDaten>();
		
		Connection con = this.connector.getConnection();
		
		return sollDatenList;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("terraID: " + this.terraID);
		stringBuilder.append("GOIPInput: " + "GOIPInputID" );
		stringBuilder.append("GOIPOutput: " + "GOIPOutputID" );
		stringBuilder.append("Aktiv seit: " + "Timestamp");
		
		return stringBuilder.toString();
	}
	
	public String getTerraID() {
		return terraID;
	}
	
}
