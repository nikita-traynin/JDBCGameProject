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
import java.util.List;
import java.util.ArrayList;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.util.DAOException;

public class CreditCardDAOImpl implements CreditCardDAO
{
    private static final String insertSQL = "INSERT INTO creditcard(ccName, ccNumber, expDate, securityCode, playerID) "
            							  + "VALUES(?,?,?,?,?);";
    
    private static final String selectSQL = "SELECT * FROM CreditCard " 
    									  + "WHERE id = ?;";
    
    private static final String select_by_playerSQL = "SELECT * FROM CreditCard "
    												+ "WHERE playerID = ?;";
    
    private static final String updateSQL = "UPDATE CreditCard "
    									  + "SET ccName = ?, ccNumber = ?, expDate = ?, securityCode = ?, playerID = ? "
    									  + "WHERE id = ?;";
    
    private static final String deleteSQL = "DELETE FROM CreditCard "
    									  + "WHERE id = ?;";
    
    private static final String delete_by_playerSQL = "DELETE FROM CreditCard " 
    												+ "WHERE playerID = ?;";

    @Override
    public CreditCard create(Connection connection, CreditCard creditCard, Long playerID)
            throws SQLException, DAOException
    {
        if (creditCard.getId() != null) {
            throw new DAOException("Trying to insert CreditCard with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, creditCard.getCcName());
            ps.setString(2, creditCard.getCcNumber());
            ps.setString(3, creditCard.getExpDate());
            ps.setInt(4, creditCard.getSecurityCode());
            ps.setLong(5, playerID);
            ps.executeUpdate();

            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            creditCard.setId((long) lastKey);
            creditCard.setPlayerID(playerID);
            return creditCard;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    @Override
    public CreditCard retrieve(Connection connection, Long ccID) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	CreditCard card = null;
    	try {
	    	ps = connection.prepareStatement(selectSQL);
	    	ps.setLong(1, ccID);
	    	ResultSet card_rs = ps.executeQuery();
	    	
	    	//Create card 
	    	if(card_rs.next()) {
	    		card = new CreditCard();
	    		card.setCcName(card_rs.getString("ccName"));
	    		card.setCcNumber(card_rs.getString("ccNumber"));
	    		card.setExpDate(card_rs.getString("expDate"));
	    		card.setId(card_rs.getLong("id"));
	    		card.setPlayerID(card_rs.getLong("playerID"));
	    		card.setSecurityCode(card_rs.getInt("securityCode"));
	    	}
    	}
    	finally {
    		if (ps != null && !ps.isClosed()) {
                ps.close();
            }
    	}
        return card;
    }

    @Override
    public List<CreditCard> retrieveCreditCardsForPlayer(Connection connection, Long playerID)
            throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	List<CreditCard> card_list = new ArrayList<CreditCard>();
    	
    	try {
    		ps = connection.prepareStatement(select_by_playerSQL);
    		ps.setLong(1, playerID);
    		ResultSet cards_rs = ps.executeQuery();
    		
    		//Retrieve every card and put it in the list
    		while(cards_rs.next()) {
    			Long ccID = cards_rs.getLong("id");
    			CreditCard card = retrieve(connection, ccID);
    			card_list.add(card);
    		}
    	}
    	finally {
    		if (ps != null && !ps.isClosed()) {
                ps.close();
            }
    	}
        return card_list;
    }

    @Override
    public int update(Connection connection, CreditCard creditCard) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	int rowcount = 0;
    	
    	try {
    		ps = connection.prepareStatement(updateSQL);
    		ps.setString(1, creditCard.getCcName());
    		ps.setString(2, creditCard.getCcNumber());
    		ps.setString(3, creditCard.getExpDate());
    		ps.setInt(4, creditCard.getSecurityCode());
    		ps.setLong(5, creditCard.getPlayerID());
    		ps.setLong(6, creditCard.getId());
    		ps.executeUpdate();
    		
    		//Get number of rows affected
    		Statement row_count_stmt = connection.createStatement();
    		ResultSet row_count_rs = row_count_stmt.executeQuery("SELECT ROW_COUNT();");
    		if(row_count_rs.next()) {
    			rowcount = row_count_rs.getInt("ROW_COUNT()");
    		}
    	}
    	finally {
    		if (ps != null && !ps.isClosed()) {
                ps.close();
            }
    	}
        return rowcount;
    }

    @Override
    public int delete(Connection connection, Long ccID) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	int rowcount = 0;
    	
    	try {
    		ps = connection.prepareStatement(deleteSQL);
    		ps.setLong(1, ccID);
    		ps.executeUpdate();
    		
    		//Get number of rows affected
    		Statement row_count_stmt = connection.createStatement();
    		ResultSet row_count_rs = row_count_stmt.executeQuery("SELECT ROW_COUNT();");
    		if(row_count_rs.next()) {
    			rowcount = row_count_rs.getInt("ROW_COUNT()");
    		}
    	}
    	finally {
    		if (ps != null && !ps.isClosed()) {
                ps.close();
            }
    	}
        return rowcount;
    }

    @Override
    public int deleteForPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	PreparedStatement ps = null;
    	int rowcount = 0;	
    	
    	try {
    		ps = connection.prepareStatement(delete_by_playerSQL);
    		ps.setLong(1, playerID);
    		ResultSet cards_rs = ps.executeQuery();
    		
    		//Delete every card under that person's name
    		while(cards_rs.next()) {
    			Long ccID = cards_rs.getLong("id");
    			rowcount += delete(connection, ccID);
    		}
    	}
    	finally {
    		if (ps != null && !ps.isClosed()) {
                ps.close();
            }
    	}
        return rowcount;
    }

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
    	Statement stmt = null;
    	int rowcount = 0;
    	
    	try {
    		stmt = connection.createStatement();
    		ResultSet row_count_rs = stmt.executeQuery("SELECT COUNT(*) FROM CreditCard;");
    		if(row_count_rs.next()) {
            	rowcount = row_count_rs.getInt("COUNT(*)");
            }
    	}
    	finally {
    		if (stmt != null && !stmt.isClosed()) {
    			stmt.close();
    		}
    	}
        return rowcount;
    }

}
