package org.bihe.LocaYab.jdbc.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.bean.HomeReview;
import org.bihe.LocaYab.jdbc.DBManager;

import com.teamdev.jxmaps.LatLng;

/**
 * @author @Parham created 7/31/2017 8:25 PM
 * 
 */
public class HomeDAO {

	// --------------------- Query Fields -------------------------

	// add new house to data base
	private static final String INSERT_QUERY = "INSERT INTO t_home (ClientID , homCost , homRooms , homMeter , homCity , homAddress , homOption, homLocation , homPhotoID ) VALUES (? , ? , ? , ? , N? , N? , N? , ? , ?)";

	// get the houses filter by city
	private static final String SELECT_QUERY_FILTER_BY_CITY = "SELECT * FROM t_home WHERE homCity = N? ";

	// get the houses filter by house id
	private static final String SELECT_QUERY_FILTER_BY_HOUSE_ID = "SELECT * FROM t_home WHERE ID = ?";

	// get the houses filter by city and option
	private static final String SELECT_QUERY_FILTER_BY_CITY_AND_OPTION = "SELECT * FROM t_home WHERE homOption = N?  AND homCity = N? ";

	private static final String SELECT_QUERY_FILTER_BY_OPTION = "SELECT * FROM t_home WHERE homOption = N? ";

	// get the houses filter by owner id
	private static final String SELECT_QUERY_FILTER_BY_OWNER_ID = "SELECT * FROM t_home WHERE ClientID = ? ";

	// update the house details in data base
	private static final String UPDATE_QUERY = "UPDATE t_home SET homRooms = ?, homCost = ? ,homMeter = ? , homCity = N? ,homAddress = N? , homOption = N? , homLocation = ?  WHERE ID = ?";

	// delete the house
	private static final String DELETE_QUERY = "DELETE FROM t_home WHERE ID = ?";

	private static final String DELETE_QUERY_BY_OWNER = "DELETE FROM t_home WHERE ClientID = ?";

	private static final String SELECT_REVIEW_QUERY_FILTER_BY_HOUSE_ID = "SELECT * FROM t_review WHERE rwHomeID = ?";

	private static final String DELETE_REVIEW_QUERY_FILTER_BY_HOUSE_ID = "DELETE FROM t_review WHERE rwHomeID = ?";

	private static final String INSERT_NEW_REVIEW = "INSERT INTO t_review (rwHomeID , rwText , rwName) VALUES (? , ? , ?)";

	// ------------------------ Fields ----------------------------
	private static HomeDAO instance;

	// ---------------------- Constructor -------------------------

	// single tone
	private HomeDAO() {
	}

	// ------------------------ Methods ---------------------------
	/**
	 * get homeDAO
	 * 
	 * @author @Parham
	 * @return HomeDAO
	 */
	public static HomeDAO getInstance() {

		if (instance == null) {
			instance = new HomeDAO();
		}
		return instance;
	}

	/**
	 * this method add new home to dataBase
	 * 
	 * @author @Parham 8/9/2017 9:23 AM
	 * 
	 * @param newHome
	 * @return boolean that show if the process of adding to database is
	 *         successfully or not
	 * 
	 */
	public boolean addNewHome(int ownerID, int cost, int roomNumb, int meter, String city, String address,
			String option, String location, String photoID) {

		// the boolean that show if the home added successfully or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = INSERT_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house client ID
			pst.setInt(1, ownerID);

			// add house price
			pst.setInt(2, cost);

			// add home room numbers
			pst.setInt(3, roomNumb);

			// add house meter
			pst.setInt(4, meter);

			// add house city located
			pst.setString(5, city);

			// add house address
			pst.setString(6, address);

			// add house option
			pst.setString(7, option);

			// add house location
			pst.setString(8, location);

			// add photo id
			pst.setString(9, photoID);

			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;

	}

	/**
	 * this method return list of the houses that located in selected city
	 * 
	 * @author @Parham
	 * 
	 * @param cityName
	 * @return ArrayList<Home> houses list
	 */
	public ArrayList<Home> getHousesFilterByCity(String cityName) {

		// house lists
		ArrayList<Home> houseList = new ArrayList<>();

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = editQueryByFilter(SELECT_QUERY_FILTER_BY_CITY);
			PreparedStatement pst = con.prepareStatement(sql);

			// adding city to query
			pst.setString(1, cityName);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			// get the filtered houses
			houseList = runSelectQueriesForGettingHouses(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return houseList;
	}

	/**
	 * this method return list of the houses that located in selected option
	 * 
	 * @author @Parham
	 * @param option
	 * @return ArrayList<Home> the houses list
	 */
	public ArrayList<Home> getHousesFilterByOptionAndCity(String option, String city) {

		if (city.equals("ایران")) {

			// house lists
			ArrayList<Home> houseList = new ArrayList<>();

			try {

				// make connection with database
				Connection con = DBManager.getConnection();

				// make statement for query
				String sql = editQueryByFilter(SELECT_QUERY_FILTER_BY_OPTION);
				PreparedStatement pst = con.prepareStatement(sql);

				// adding option to query
				pst.setString(1, option);

				// put query result in resultSet
				ResultSet rs = pst.executeQuery();

				// get the filtered houses
				houseList = runSelectQueriesForGettingHouses(rs);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return houseList;

		} else {

			// house lists
			ArrayList<Home> houseList = new ArrayList<>();

			try {

				// make connection with database
				Connection con = DBManager.getConnection();

				// make statement for query
				String sql = editQueryByFilter(SELECT_QUERY_FILTER_BY_CITY_AND_OPTION);
				PreparedStatement pst = con.prepareStatement(sql);

				// adding option to query
				pst.setString(1, option);

				// adding city to query
				pst.setString(2, city);

				// put query result in resultSet
				ResultSet rs = pst.executeQuery();

				// get the filtered houses
				houseList = runSelectQueriesForGettingHouses(rs);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return houseList;

		}

	}

	/**
	 * this method generate Select Queries of house filtering
	 * 
	 * @author @Parham
	 * @param ResultSet
	 *            rs (query result)
	 * @return ArrayList<Home> make home object from query result
	 */
	private ArrayList<Home> runSelectQueriesForGettingHouses(ResultSet rs) {

		ArrayList<Home> houseList = new ArrayList<>();

		try {
			while (rs.next()) {

				// make new object from house that gotten from data base
				Home result = new Home(rs.getInt("ID"), rs.getInt("homCost"), rs.getInt("homMeter"),
						rs.getString("homCity"), rs.getString("homAddress"), rs.getString("homOption"),
						rs.getInt("homRooms"), rs.getInt("ClientID"),
						convertStringToLatLng(rs.getString("homLocation")), rs.getString("homPhotoID"));

				result.setReviews(getHouseReviews(result.getHomeID()));

				// add to the result list
				if (result != null) {

					houseList.add(result);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return houseList;
	}

	/**
	 * this method update the house data in the data base
	 * 
	 * @author @Parham
	 * @param home
	 * @return show if update done or not
	 */
	public boolean updateHouseInformation(Home home) {

		// the boolean that show if the home added successfully or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = UPDATE_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			// update home rooms number
			pst.setInt(1, home.getRoomNumb());

			// update home price
			pst.setInt(2, home.getCost());

			// update home meter
			pst.setInt(3, home.getMeter());

			// update home city
			pst.setString(4, home.getCity());

			// update home address
			pst.setString(5, home.getAddress());

			// update home option
			pst.setString(6, home.getOption());

			// update home location
			pst.setString(7, home.getLocation() + "");

			// set the id of the house that should be updated
			pst.setInt(8, home.getHomeID());

			// run the query
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;

	}

	/**
	 * this method delete the house
	 * 
	 * @author @Parham
	 * @param ID
	 *            of the house
	 * @return boolean show deleting is done or not
	 */
	public boolean deleteHouse(int ID) {

		deleteReviewByHouseID(ID);

		// the boolean that show the house is deleted or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_QUERY;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, ID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method delete the house
	 * 
	 * @author @Parham
	 * @param ID
	 *            of the house
	 * @return boolean show deleting is done or not
	 */
	public boolean deleteHouseByCleintID(int ClientID) {

		ArrayList<Home> h = getHousesFilterByOwnerId(ClientID);

		for (Home home : h) {

			deleteReviewByHouseID(home.getHomeID());

		}

		// the boolean that show the house is deleted or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_QUERY_BY_OWNER;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, ClientID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method return list of the houses filter by owner id
	 * 
	 * @author @Parham
	 * 
	 * @param Owner
	 *            id
	 * @return ArrayList<Home> houses list
	 */
	public ArrayList<Home> getHousesFilterByOwnerId(int ownerId) {

		// house lists
		ArrayList<Home> houseList = new ArrayList<>();

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = editQueryByFilter(SELECT_QUERY_FILTER_BY_OWNER_ID);
			PreparedStatement pst = con.prepareStatement(sql);

			// adding city to query
			pst.setInt(1, ownerId);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			// get the filtered houses
			houseList = runSelectQueriesForGettingHouses(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return houseList;
	}

	/**
	 * this method return list of the houses filter by houseID
	 * 
	 * @author @Parham
	 * 
	 * @param House
	 *            ID
	 * @return Home house
	 */
	public Home getHousesFilterByHouseId(int houseID) {

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = editQueryByFilter(SELECT_QUERY_FILTER_BY_HOUSE_ID);
			PreparedStatement pst = con.prepareStatement(sql);

			// adding city to query
			pst.setInt(1, houseID);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			// get the filtered house
			if (rs.next()) {

				// make new object from house that gotten from data base
				Home result = new Home(rs.getInt("ID"), rs.getInt("homCost"), rs.getInt("homMeter"),
						rs.getString("homCity"), rs.getString("homAddress"), rs.getString("homOption"),
						rs.getInt("homRooms"), rs.getInt("ClientID"),
						convertStringToLatLng(rs.getString("homLocation")), rs.getString("homPhotoID"));

				result.setReviews(getHouseReviews(houseID));

				return result;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * this method give the houses review by the house id
	 * 
	 * @param homeID
	 * @return
	 */
	public ArrayList<HomeReview> getHouseReviews(int homeID) {

		// house lists
		ArrayList<HomeReview> reviews = new ArrayList<>();

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = SELECT_REVIEW_QUERY_FILTER_BY_HOUSE_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// adding city to query
			pst.setInt(1, homeID);

			// put query result in resultSet
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				HomeReview rw = new HomeReview(rs.getInt("ID"), rs.getString("rwName"), rs.getString("rwText"));

				if (rw != null) {
					reviews.add(rw);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviews;
	}

	/**
	 * this method delete all of the house reviews
	 * 
	 * @param houseID
	 * @return
	 */
	public boolean deleteReviewByHouseID(int houseID) {

		// the boolean that show the house is deleted or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = DELETE_REVIEW_QUERY_FILTER_BY_HOUSE_ID;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, houseID);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	/**
	 * this method add new review
	 * 
	 * @param houseID
	 * @param name
	 * @param text
	 * @return
	 */
	public boolean addNewReview(int houseID, String name, String text) {

		// the boolean that show the house is deleted or not
		boolean resultVal = false;

		try {

			// make connection with database
			Connection con = DBManager.getConnection();

			// make statement for query
			String sql = INSERT_NEW_REVIEW;
			PreparedStatement pst = con.prepareStatement(sql);

			// add house id to query
			pst.setInt(1, houseID);

			pst.setString(2, text);

			pst.setString(3, name);

			// check if the house deleted successfully
			int nRecords = pst.executeUpdate();
			resultVal = nRecords == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultVal;
	}

	// ------------------------- Methods ---------------------------
	/**
	 * this method convert String to LatLng
	 * 
	 * @author @Parham 8/22/2017
	 * @param position
	 * @return
	 */
	public static LatLng convertStringToLatLng(String position) {

		String[] temp = position.split(",");

		double lat = Double.parseDouble(temp[0].substring(1));
		double lng = Double.parseDouble(temp[1].substring(0, temp[1].length() - 1));

		return new LatLng(lat, lng);
	}

	/*
	 * this method set the query by the filter that user searched
	 */
	private static String editQueryByFilter(String query) {

		String tabel = "";

		if (BeanResources.getInstance().getOrderFilterSearch().trim().equals("قیمت")) {

			tabel = "homCost ";

		} else if (BeanResources.getInstance().getOrderFilterSearch().trim().equals("متراژ")) {

			tabel = "homMeter ";

		} else if (BeanResources.getInstance().getOrderFilterSearch().trim().equals("تعداد خواب")) {

			tabel = "homRooms ";

		}

		if (BeanResources.getInstance().isAscDESC()) {

			query += " ORDER BY " + tabel;

		} else {

			query += " ORDER BY " + tabel + " DESC ";
		}

		return query;
	}

}
