package biotopsteuerung.steuerung.menue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class MenuTools {
	//TODO Relativ machen
	private final String MENUE_TEXT_PATH = "E:\\Proggen\\Projekte\\Terarium\\BiotopSteuerung\\src\\Menues";
	private Map<String, File> menuFileMap = null;

	protected MenuTools() {
		this.menuFileMap = this.loadMenueFiles();

	}

	private Map<String, File> loadMenueFiles() {

		Map<String, File> menueFileList = new HashMap<String, File>();

		File verzeichnis = new File(MENUE_TEXT_PATH);
		File menueText[];

		if (verzeichnis.exists()) {
			verzeichnis.mkdirs();
			menueText = verzeichnis.listFiles();

			if (menueText.length > 0) {

				for (File f : menueText) {

					menueFileList.put(f.getName().replaceAll(".mtf", ""), f);

				}

			}

		}
		return menueFileList;
	}

	protected File getMenuFile(String menuID) {
		
		File file = menuFileMap.get(menuID);
		
		return file;
	}
	
//	protected void zeichneMenue(String menueID) {
//
//		File file = menueFileMap.get(menueID);
//
//		if (null != file) {
//			BufferedReader br;
//			try {
//				br = new BufferedReader(new FileReader(file));
//				String zeile = br.readLine();
//
//				while (null != zeile) {
//
//					System.out.println(zeile);
//					zeile = br.readLine();
//
//				}
//			} catch (IOException e) {
//
//				System.out.println(MenueConstants.MENUE_FILE_NOT_FOUND_TEXT);
//			}
//
//		} else {
//			System.out.println(MenueConstants.MENUE_FILE_NOT_FOUND_TEXT);
//		}
//
//	}

}
