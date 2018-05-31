package org.bihe.LocaYab.jdbc.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bihe.LocaYab.bean.Client;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.jdbc.DBManager;

/**
 * @author @Parham created 7/31/2017 8:25 PM
 *
 */

public class ClientDAO {

	// --------------------- Query Fields -------------------------
	private static final String INSERT_QUERY = "INSERT INTO t_client(clnNationalID , clnFirstName , clnLastName , clnTell , clnAdmin , clnPass  , clnAddress) VALUES (?, ?, ?, ?, ?, ? ,?)";

	private static final String VALID_USER_CHECK_BY_NATIONAL_ID = "SELECT clnNationalID FROM t_client WHERE clnNationalID = N?";

	private static final String SELECT_FILTER_BY_NATIONAL_ID = "SELECT * FROM t_client WHERE clnNationalID = ?";

	private static final String SELECT_FILTER_BY_ID = "SELECT * FROM t_client WHERE ID = ?";

	private static final String SIGN_IN_CHECK = "SELECT * FROM t_client Where clnNationalID = ? AND clnPass = N?";

	private static final String GET_HOUSES_PINNED_BY_USER = "SELECT pnHomeID FROM t_pinned WHERE pnClientID =  ?";

	private static final String UPDATE_CLIENT_KNOWN_BY_IT_ID = "UPDATE t_client SET clnNationalID = ? , clnFirstName = ? , clnLastName = ? , clnTell = ? , clnAdmin = ? , clnAddress = ? WHERE ID = ? ";

	private static final String UPDATE_CLIENT_PASS_BY_IT_ID = "UPDATE t_client SET clnPass = ?  WHERE ID = ? ";

	private static final String DELETE_PIN_QUERY = "DELETE FROM t_pinned WHERE  pnClientID = ? AND pnHomeID = ? ";

	private static final String DELETE_PIN_QUERY_ALL = "DELETE FROM t_pinned WHERE  pnClientID = ? ";

	private static final String ADD_PIN_QUERY = "INSERT INTO t_pinned (pnClientID , pnHomeID) VALUES (? , ?)";

	private static final String GET_ID_BY_NATIONAL_ID = "SELECT ID FROM t_client WHERE clnNationalID = ?";

	private static final String DELETE_CLIENT = "DELETE FROM t_client WHERE ID = ?";

	private static final String DELETE_REVIEW = "DELETE FROM t_review WHERE rwHomeID = ?";

	// ------------------------ Fields ----------------------------
	private static ClientDAO instance;

	// ---------------------- Constructor -------------------------

	// single tone
	private ClientDAO() {

	}

	// ------------------------ Methods ---------------------------

	public boolean deleteClient(int clientID, String natID) {

		boolean resultVal = false;

		// remove pins

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_PIN_QUERY_ALL;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, clientID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// remove review
		ArrayList<Home> clienthouse = HomeDAO.getInstance().getHousesFilterByOwnerId(clientID);

		for (Home home : clienthouse) {

			try {

				// make connection with database
				Connection con = DBManager.getConnection();

				// make statement for query
				String sql = DELETE_REVIEW;
				PreparedStatement pst = con.prepareStatement(sql);

				// add house id to query
				pst.setInt(1, home.getHomeID());

				// check if the house deleted successfully
				int nRecords = pst.executeUpdate();
				resultVal = nRecords == 1;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// remove houses
		HomeDAO.getInstance().deleteHouseByCleintID(clientID);

		// remove client
		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_CLIENT;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, clientID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * update the client
	 * 
	 * @param client
	 * @return
	 */
	public boolean updateClient(Client client) {

		// the boolean that show if the client added successfully or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = UPDATE_CLIENT_KNOWN_BY_IT_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// update home rooms number
			pst.setString(1, client.getNationalID());

			// first name
			pst.setString(2, client.getFirstName());

			// last name
			pst.setString(3, client.getLastName());

			// tell
			pst.setString(4, client.getTell());

			// administration
			pst.setBoolean(5, client.isAdmin());

			// address
			pst.setString(6, client.getAddress());

			// ID
			pst.setInt(7, client.getId());

			// run the query
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;

	}

	/**
	 * get the class
	 * 
	 * @author @Parham
	 * @return ClientDAO
	 */
	public static ClientDAO getInstance() {

		if (instance == null) {
			instance = new ClientDAO();
		}
		return instance;
	}

	/**
	 * this method add new client on the database
	 * 
	 * @author @Parham 8/1/2017 8:36 AM
	 * @return boolean (shows that if the client added successfully or not)
	 */
	public boolean addNewClient(Client newClient) {

		// show the situation of adding
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = INSERT_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			// national id statement
			pst.setString(1, newClient.getNationalID());

			// first name statement
			pst.setString(2, newClient.getFirstName());

			// last name statement
			pst.setString(3, newClient.getLastName());

			// tell statement
			pst.setString(4, newClient.getTell());

			// administrator statement
			pst.setBoolean(5, newClient.isAdmin());

			// password statement
			pst.setString(6, newClient.getPass());

			// add address
			pst.setString(7, newClient.getAddress());

			// checking results and set the boolean
			int nRecord = pst.executeUpdate();
			resultVal = nRecord == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method check if the national ID exist in data base or not
	 * 
	 * @author @Parham 8/1/2017 12:26 PM
	 * 
	 * @param nationalID
	 * 
	 * @return boolean is exist = true , in not exist = false
	 */
	public boolean isIDExist(String nationalID) {

		// the variable that show the existing situation
		boolean resultVal = false;
		;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = VALID_USER_CHECK_BY_NATIONAL_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national id to query
			pst.setString(1, nationalID);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			// check that if we have result from query
			if (rs.next()) {
				if (rs.getString("clnNationalID").equals(nationalID)) {
					resultVal = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method check that the user/pass is exist and correct in database or
	 * not
	 * 
	 * @author @Parham created 8/3/2017 12:54 PM
	 * 
	 * @param nationalID
	 * @param pass
	 * @return boolean show that if user/pass in correct or not
	 */
	public boolean signInCheck(String nationalID, String pass) {

		// variable show the validation of user/pass
		boolean validUser = false;

		try {
			// get connection from DB
			Connection con = DBManager.getConnection();

			// making query
			String sql = SIGN_IN_CHECK;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national ID to query
			pst.setString(1, nationalID);

			// adding password to query
			pst.setString(2, pass);

			// put result in resultSet
			ResultSet rs = pst.executeQuery();

			// if user exist in database
			if (rs.next()) {
				validUser = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return validUser;
	}

	/**
	 * this method get the client to you
	 * 
	 * @param nationalID
	 * @return
	 */
	public Client getClient(String nationalID) {

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = SELECT_FILTER_BY_NATIONAL_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national id to query
			pst.setString(1, nationalID);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				Client newClient = new Client(rs.getString("clnNationalID"), rs.getString("clnFirstName"),
						rs.getString("clnLastName"), rs.getString("clnTell"), rs.getString("clnPass"), rs.getInt("ID"),
						rs.getString("clnAddress"));
				newClient.setAdmin(rs.getBoolean("clnAdmin"));

				// add own houses
				newClient.setOwnHomes(HomeDAO.getInstance().getHousesFilterByOwnerId(newClient.getId()));

				// add pinned house
				newClient.setPinnedHome(getUserPinHouses(nationalID));
				return newClient;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 
	 * @param nationalId
	 * @return
	 */
	private ArrayList<Home> getUserPinHouses(String nationalID) {

		// ArrayList<String> houseID = new ArrayList<>();
		ArrayList<Home> pinnedHouse = new ArrayList<>();
		// get the house Id of user pins

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = GET_HOUSES_PINNED_BY_USER;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national id to query
			pst.setInt(1, getClientIDbyNationalId(nationalID));

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Home temp = HomeDAO.getInstance().getHousesFilterByHouseId(rs.getInt("pnHomeID"));

				if (temp != null) {
					pinnedHouse.add(temp);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pinnedHouse;

	}

	/**
	 * this method delete the pin
	 * 
	 * @author @Parham
	 * @param ID
	 *            of the house
	 * @return boolean show deleting is done or not
	 */
	public boolean deletePin(int clientID, int houseID) {

		// the boolean that show the house is deleted or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_PIN_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setInt(1, clientID);

			pst.setInt(2, houseID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method add new pin on the database
	 * 
	 * @author @Parham 8/1/2017 8:36 AM
	 * @return boolean (shows that if the pin added successfully or not)
	 */
	public boolean addNewPin(int clientID, int houseID) {

		// show the situation of adding
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = ADD_PIN_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			// national id statement
			pst.setInt(1, clientID);

			// first name statement
			pst.setInt(2, houseID);

			// checking results and set the boolean
			int nRecord = pst.executeUpdate();
			resultVal = nRecord == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	public int getClientIDbyNationalId(String nationalID) {

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = GET_ID_BY_NATIONAL_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national id to query
			pst.setString(1, nationalID);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			// check that if we have result from query
			if (rs.next()) {
				return rs.getInt("ID");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * this method get the client to you
	 * 
	 * @param nationalID
	 * @return
	 */
	public Client getClientWithOutdetails(int id) {

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = SELECT_FILTER_BY_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding national id to query
			pst.setInt(1, id);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				Client newClient = new Client(rs.getString("clnNationalID"), rs.getString("clnFirstName"),
						rs.getString("clnLastName"), rs.getString("clnTell"), rs.getString("clnTell"), rs.getInt("ID"),
						rs.getString("clnAddress"));
				newClient.setAdmin(rs.getBoolean("clnAdmin"));

				return newClient;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public boolean updateClientPass(int clientID, String pass) {

		// the boolean that show if the client updated successfully or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = UPDATE_CLIENT_PASS_BY_IT_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// Pass
			pst.setString(1, pass);

			// ID
			pst.setInt(2, clientID);

			// run the query
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;

	}

}
