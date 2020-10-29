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

// Implemented by Cody Lu

package cs4347.jdbcGame.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.impl.GamesOwnedDAOImpl;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.services.GamesOwnedService;
import cs4347.jdbcGame.util.DAOException;

public class GamesOwnedServiceImpl implements GamesOwnedService
{
    private DataSource dataSource;
    private GamesOwnedDAOImpl dao;

    public GamesOwnedServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.dao = new GamesOwnedDAOImpl();
    }

    @Override
    public GamesOwned create(GamesOwned gamesOwned) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	GamesOwned newOwned = null;
    	try {
    		// call GamesOwnedDAO.create
    		newOwned = dao.create(connection, gamesOwned);
    	}
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    	return newOwned;
    }

    @Override
    public GamesOwned retrieveByID(long gamesOwnedID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	GamesOwned result;
    	try {
	    	// call GamesOwnedDAO.retrieveID
	    	result = dao.retrieveID(connection, gamesOwnedID);
	    	return result;
    	} 
    	finally
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public GamesOwned retrievePlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	GamesOwned result;
    	try {
	    	// call GamesOwnedDAO.retrievePlayerGameID
	    	result = dao.retrievePlayerGameID(connection, playerID, gameID);
	    	return result;
    	} 
    	finally
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<GamesOwned> retrieveByGame(long gameID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<GamesOwned> result;
    	try {
	    	// call GamesOwnedDAO.retrieveByGame
	    	result = dao.retrieveByGame(connection, gameID);
	    	return result;
    	} 
    	finally
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<GamesOwned> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<GamesOwned> result;
    	try {
	    	// call GamesOwnedDAO.retrieveByPlayer
	    	result = dao.retrieveByPlayer(connection, playerID);
	    	return result;
    	} 
    	finally
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int update(GamesOwned gamesOwned) throws DAOException, SQLException
    {
        //get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try {
    		//call GamesOwnedDAO.update
    		rows = dao.update(connection, gamesOwned);
    		return rows;
    	}
    	finally {
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int delete(long gameOwnedID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try {
	    	// call GamesOwnedDAO.delete
	    	rows = dao.delete(connection, gameOwnedID);
	    	return rows;
    	} finally {
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int count() throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try {
	    	// call GamesOwnedDAO.count
	    	rows = dao.count(connection);
	    	return rows;
    	} finally {
    		// finally close connection
    		connection.close();
    	}
    }
}
