/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.jdbcGame.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.util.DAOException;
import cs4347.jdbcGame.entity.CreditCard;

public class PlayerDAOImpl implements PlayerDAO
{

    private static final String insertSQL = "INSERT INTO player (firstName, lastName, join_date, email) " 
    									  + "VALUES (?, ?, ?, ?);";
    
    private static final String deleteSQL = "DELETE FROM player "
    									  + "WHERE id = ?;";
    
    private static final String selectSQL = "SELECT * FROM player "
    									  + "WHERE id = ?;";
    
    private static final String updateSQL = "UPDATE player "
    									  + "SET firstName = ?, lastName = ?, join_date = ?, email = ? "
    									  + "WHERE id = ?;";
    
    private static final String select_betweenSQL = "SELECT * FROM player "
    											  + "WHERE join_date BETWEEN ? AND ?;";
    
    @Override
    public Player create(Connection connection, Player player) throws SQLException, DAOException
    {
        if (player.getId() != null) {
            throw new DAOException("Trying to insert Player with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setDate(3, new java.sql.Date(player.getJoinDate().getTime()));
            ps.setString(4, player.getEmail());
            ps.executeUpdate();

            // Copy the assigned ID to the customer instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            player.setId((long) lastKey);
            return player;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    @Override
    public Player retrieve(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	Player player = null;
    	
    	try {
    		ps = connection.prepareStatement(selectSQL);
    		ps.setLong(1, playerID);
    		ResultSet playerRS = ps.executeQuery();
    		
    		//Create and return corresponding player object.
    		if(playerRS.next()) {
    			player = new Player();
	    		player.setId(playerID);
	    		player.setFirstName(playerRS.getString("firstName"));
	    		player.setLastName(playerRS.getString("lastName"));
	    		player.setJoinDate(playerRS.getDate("join_date"));
	    		player.setEmail(playerRS.getString("email"));
    		}
    	}
    	finally {
    		if(ps != null && !ps.isClosed()) {
    			ps.close();
    		}
    	}
        return player;
    }

    @Override
    public int update(Connection connection, Player player) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	int rowcount = 0;
    	
    	try {
    		ps = connection.prepareStatement(updateSQL);
    		ps.setString(1, player.getFirstName());
    		ps.setString(2, player.getLastName());
    		ps.setDate(3, new java.sql.Date(player.getJoinDate().getTime()));
    		ps.setString(4, player.getEmail());
    		ps.setLong(5, player.getId());
    		ps.executeUpdate();
    		
    		//Find how many rows were affected by the sql update (should be one)
    		Statement rowcount_stmt = connection.createStatement();
    		ResultSet rowcount_rs = rowcount_stmt.executeQuery("SELECT ROW_COUNT();");
    		if (rowcount_rs.next()) {
    			rowcount = rowcount_rs.getInt("ROW_COUNT()");
    		}
    		
    	}
    	finally {
    		if(ps != null && !ps.isClosed()) {
    			ps.close();
    		}
    	}
        return rowcount;
    }

    @Override
    public int delete(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	int rowcount = 0;
    	
    	try {
    		ps = connection.prepareStatement(deleteSQL);
    		ps.setLong(1, playerID);
    		ps.executeUpdate();
    		
    		//Find how many rows were deleted (should be one)
    		Statement rowcount_stmt = connection.createStatement();
    		ResultSet rowcount_rs = rowcount_stmt.executeQuery("SELECT  ROW_COUNT();");
    		rowcount_rs.next();
    		rowcount = rowcount_rs.getInt("ROW_COUNT()");
    	}
    	finally {
    		if(ps != null && !ps.isClosed()) {
    			ps.close();
    		}
    	}
        return rowcount;
    }

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
    	int rowcount = 0;
    	try {
	    	Statement stmt = connection.createStatement();
	    	ResultSet rowcount_rs = stmt.executeQuery("SELECT COUNT(*) FROM player;");
	    	rowcount_rs.next();
	    	rowcount = rowcount_rs.getInt("COUNT(*)");
	        
    	}
    	finally {

    	}
    	return rowcount;
    }

    @Override
    public List<Player> retrieveByJoinDate(Connection connection, Date start, Date end)
            throws SQLException, DAOException
    {
    	
    	PreparedStatement ps = null;
    	List<Player> player_list = new ArrayList<Player>();
    	List<CreditCard> card_list = new ArrayList<CreditCard>();
    	
    	try {
    		ps = connection.prepareStatement(select_betweenSQL);
    		ps.setDate(1, new java.sql.Date(start.getTime()));
    		ps.setDate(2, new java.sql.Date(end.getTime()));
    		ResultSet players_rs = ps.executeQuery();
    		
    		//Build List
    		while(players_rs.next()) {
    			//Get all of each player's attributes.
    			Long id = players_rs.getLong("id");
    			String firstName = players_rs.getString("firstName");
    			String lastName = players_rs.getString("lastName");
    			Date join_date = players_rs.getDate("join_date");
    			String email = players_rs.getString("email");
    			
    			//Find all credit cards belonging to the player
    			Statement stmt = connection.createStatement();
    			ResultSet cards_rs = stmt.executeQuery("SELECT * FROM CreditCard WHERE playerID = " + id.toString());
    			
				/*
				 * //Create credit card list while(cards_rs.next()) {
				 * 
				 * }
				 */
    			
    			//Build a player object 
    			Player player = new Player();
    			player.setId(id);
    			player.setFirstName(firstName);
    			player.setLastName(lastName);
    			player.setJoinDate(join_date);
    			player.setEmail(email);
    			
    			//Add it to the list
    			player_list.add(player);
    		}
    		
    	}
    	finally {
    		if(ps != null && !ps.isClosed()) {
    			ps.close();
    		}
    	}
        return player_list;
    }

}
