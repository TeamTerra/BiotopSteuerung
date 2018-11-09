package biotopsteuerung.logik.data;

import java.time.Instant;

public class UmgebungsDaten {

	private Instant zeitstempl; //Primary Key
	private double temperatur;
	private double relativeLuftfeuchtigkeit;
	private int taktID;
	
	public int getTaktID() {
		return taktID;
	}
	public void setTaktID(int taktID) {
		this.taktID = taktID;
	}
	public Instant getZeitstempl() {
		return zeitstempl;
	}
	public void setZeitstempl(Instant zeitstempl) {
		this.zeitstempl = zeitstempl;
	}
	public double getTemperatur() {
		return temperatur;
	}
	public void setTemperatur(double temperatur) {
		this.temperatur = temperatur;
	}
	public double getRelativeLuftfeuchtigkeit() {
		return relativeLuftfeuchtigkeit;
	}
	public void setRelativeLuftfeuchtigkeit(double relativeLuftfeuchtigkeit) {
		this.relativeLuftfeuchtigkeit = relativeLuftfeuchtigkeit;
	}

}
