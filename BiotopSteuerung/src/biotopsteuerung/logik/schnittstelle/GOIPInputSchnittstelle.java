package biotopsteuerung.logik.schnittstelle;

import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import biotopsteuerung.common.BiotopTools;
import biotopsteuerung.logik.data.UmgebungsDaten;

public class GOIPInputSchnittstelle extends Observable {

	private GOIPInputSchnittstelle that;
	private boolean testDaten = false;
	private int taktID = 0;
	private boolean gibTakt;
	private int takt;
	private Thread t;
	private UmgebungsDaten umgebungsDaten;
	
	public GOIPInputSchnittstelle() {

		this.takt = 1000;
		this.gibTakt = false;
		this.initThread();
	}

	public GOIPInputSchnittstelle(boolean leseDaten, int takt, boolean testDaten) {

		this.that = this;
		this.takt = takt;
		this.gibTakt = leseDaten;
		this.testDaten = testDaten;
		this.initThread();

		if (this.gibTakt) {

			t.start();
		}
	}

	private void initThread() {

		t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (gibTakt) {

					if (!testDaten) {
						leseSensoren();
					} else {
						generiereTestDaten();
					}

					setChanged();
					taktID++;
					if (hasChanged()) {

						notifyObservers(that);
					}
					clearChanged();

					if (takt > 100) {

						try {
							Thread.sleep(takt);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
					} else {
						break;
					}

				}
			}
		});
	}

	public void start() {
		this.gibTakt = true;
		if (!t.isAlive()) {
			this.t.start();
			;
		}
	}

	public void stopDatenLesen() {
		this.gibTakt = false;
	}

	public void setTakt(int takt) {
		this.takt = takt;
	}

	public int getTaktID() {
		return taktID;
	}

	public UmgebungsDaten getUmgebungsDaten() {
		return umgebungsDaten;
	}

	private void generiereTestDaten() {
		this.umgebungsDaten = new UmgebungsDaten();

		this.umgebungsDaten.setZeitstempl(BiotopTools.getZeitStempel());
		double temperatur = 25.0d;
		temperatur = ThreadLocalRandom.current().nextDouble(10.0d, 40.0d);
		temperatur = Math.round(temperatur * 10.0d) / 10.0d;
		this.umgebungsDaten.setTemperatur(temperatur);
		this.umgebungsDaten.setRelativeLuftfeuchtigkeit(ThreadLocalRandom.current().nextInt(0, 100));
		this.umgebungsDaten.setTaktID(this.taktID);

	}

	private void leseSensoren() {

		this.umgebungsDaten = new UmgebungsDaten();
		this.umgebungsDaten.setTaktID(this.taktID);

		// Sensordaten auf Objekt mappen

	}

}
