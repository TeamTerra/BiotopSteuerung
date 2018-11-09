package biotopsteuerung.logik.datenbank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import biotopsteuerung.common.Constants;

public class DBConnector {

	public static DBConnector instance;

	public DBConnector() {
		instance = this;
	}

	public Connection getConnection() {

		String url = Constants.DATENBANK_TREIBER;
//		String user = "admin";
//		String passwort = "admin";
		String DBpfad = "C:/Users/Thomas/Desktop/WetterDB"; // TODO aus ner Property Datei ziehen

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(url + DBpfad);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	public void setUpdate(String prepStmtID, Connection connection, String... values)
			throws IOException, URISyntaxException, SQLException {
		List<String> prepDataTyps;
		String prepStmtString = getPrepStmtFromFile(prepStmtID);
		;

		if (null != prepStmtString && !"".equals(prepStmtString)) {

			String prepSQL = this.extrahiereSQL(prepStmtString);
			prepDataTyps = this.extrahiereDatatyps(prepStmtString);

			PreparedStatement prepStmt = connection.prepareStatement(prepSQL);

			for (int index = 0; index < prepDataTyps.size(); index++) {

				if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_INTEGER)) {
					int value = Integer.parseInt(values[index]);
					prepStmt.setInt(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_STRING)) {
					String value = values[index];
					prepStmt.setString(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_DOUBLE)) {
					Double value = Double.valueOf(values[index]);
					prepStmt.setDouble(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_DATE)) {
					// TODO TimeStamp einbinden
				} // else if für andere Typen

			}

			int resultSet = prepStmt.executeUpdate();
			System.out.println("resultSet: " + resultSet);

		}
	}

	/**
	 * Setzt das Select des PreparedStatements ab welches der übergebenen ID
	 * entspricht.
	 * 
	 * @param prepStmtID
	 * @param connection
	 * @param value
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public ResultSet setSelect(String prepStmtID, Connection connection, String... values)
			throws SQLException, IOException, URISyntaxException {
		ResultSet resultSet = null;
		List<String> prepDataTyps;
		String prepStmtString = getPrepStmtFromFile(prepStmtID);
		;

		if (null != prepStmtString && !"".equals(prepStmtString)) {

			String prepSQL = this.extrahiereSQL(prepStmtString);
			prepDataTyps = this.extrahiereDatatyps(prepStmtString);

			PreparedStatement prepStmt = connection.prepareStatement(prepSQL);

			System.out.println(prepStmt.isClosed());

			for (int index = 0; index < prepDataTyps.size(); index++) {

				if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_INTEGER)) {
					int value = Integer.parseInt(values[index]);
					prepStmt.setInt(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_STRING)) {
					String value = values[index];
					prepStmt.setString(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_DOUBLE)) {
					double value = Double.parseDouble(values[index]);
					prepStmt.setDouble(index + 1, value);

				} else if (prepDataTyps.get(index).equalsIgnoreCase(PreparedStatementConstants.DATATYP_DATE)) {
					// TODO TimeStamp einbinden
				}

			}

			resultSet = prepStmt.executeQuery();
			System.out.println(resultSet.isClosed());
			int i = resultSet.getInt(1);
			System.out.println(i);
		}

		return resultSet;
	}

	/**
	 * Extrahiert aus dem Text die Datatypes
	 * 
	 * @param prepStmtString
	 * @return
	 */
	private List<String> extrahiereDatatyps(String prepStmtString) {
		List<String> prepDataTyps = new ArrayList<String>();
		String typeString;
		String tempStmt = prepStmtString
				.substring(prepStmtString.lastIndexOf(PreparedStatementConstants.DATATYP_DATATYP_MARK)
						+ PreparedStatementConstants.DATATYP_DATATYP_MARK.length());

		while (tempStmt.contains(",")) {
			typeString = tempStmt.substring(0, tempStmt.indexOf(","));
			prepDataTyps.add(typeString);

			tempStmt = tempStmt.substring(tempStmt.indexOf(",") + 1);

		}

		prepDataTyps.add(tempStmt);

		return prepDataTyps;
	}

	/**
	 * Scannt den PrepStmt-SQL-Ordner nach dem SQL mit der gegebenen ID und Gibt
	 * diesen dann als String zurück.
	 * 
	 * @param prepStmtID
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private String getPrepStmtFromFile(String prepStmtID) throws IOException, URISyntaxException {
		String prepStmtString = "";
		File prepStmtSQLFile = null;

		File prepStmtVerzeichnis = new File(PreparedStatementConstants.PREPSTMT_VERZEICHNIS_PFAD);
		File prepStmtFiles[];

		// Verzeichnis finden
		if (prepStmtVerzeichnis.exists()) {
			prepStmtVerzeichnis.mkdirs();
			prepStmtFiles = prepStmtVerzeichnis.listFiles();

			if (prepStmtFiles.length > 0) {

				for (int i = 0; i < prepStmtFiles.length; i++) {

					// File gefunden mit der übergebnen ID
					if ((prepStmtID + ".sql").equals(prepStmtFiles[i].getName())) {

						prepStmtSQLFile = prepStmtFiles[i];
						break;

					}
				}

				// Datei auslesen;

				if (null != prepStmtSQLFile) {

					FileReader fr = new FileReader(prepStmtSQLFile);
					BufferedReader br = new BufferedReader(fr);

					String nextLine = br.readLine();

					while (nextLine != null) {

						prepStmtString += nextLine;
						nextLine = br.readLine();

					}

					br.close();
					fr.close();

				} else {
					throw new FileNotFoundException("Es wurde kein PreparedStatement mit der ID: " + prepStmtID + " in "
							+ prepStmtVerzeichnis.getPath() + " gefunden");
				}
			}
		}

		return prepStmtString;
	}

	/**
	 * Extrahiert aus dem Text das SQL-Stmt
	 * 
	 * @param prepStmtString
	 * @return
	 */
	private String extrahiereSQL(String prepStmtString) {
		String prepSQL = null;
		int index = prepStmtString.indexOf(PreparedStatementConstants.DATATYP_DATATYP_MARK);

		if (index != -1) {

			prepSQL = prepStmtString.substring(0, index);
		}

		return prepSQL;
	}
	
	public static synchronized DBConnector getInstance() {

		if (DBConnector.instance == null) {
			DBConnector.instance = new DBConnector();
		}
		return DBConnector.instance;
	}

}
