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

import cs4347.jdbcGame.dao.impl.GamesPlayedDAOImpl;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.services.GamesPlayedService;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedServiceImpl implements GamesPlayedService
{
    private DataSource dataSource;
    private GamesPlayedDAOImpl dao;

    public GamesPlayedServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.dao = new GamesPlayedDAOImpl();
    }

    @Override
    public GamesPlayed create(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	GamesPlayed newPlayed;
    	try {
    		// call GamesPlayedDAO.create
    		newPlayed = dao.create(connection, gamesPlayed);
    		return newPlayed;
    	}
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public GamesPlayed retrieveByID(long gamePlayedID) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	GamesPlayed result;
    	try {
    		// call GamesPlayedDAO.retrieveID
    		result = dao.retrieveID(connection, gamePlayedID);
    		return result;
    	}
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<GamesPlayed> result;
    	try {
    		// call GamesPlayedDAO.retrieveByPlayerGameID
    		result = dao.retrieveByPlayerGameID(connection, playerID, gameID);
    		return result;
    	}
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<GamesPlayed> retrieveByGame(long gameID) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<GamesPlayed> result;
    	try {
    		// call GamesPlayedDAO.retrieveByGame
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
    public List<GamesPlayed> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
        // get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<GamesPlayed> result;
    	try {
    		// call GamesPlayedDAO.retrieveByPlayer
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
    public int update(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try
    	{
	    	// call GamesPlayedDAO.update
	    	rows = dao.update(connection, gamesPlayed);
	    	return rows;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int delete(long gamePlayedID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try 
    	{
	    	// call GamesPlayedDAO.delete
	    	rows = dao.delete(connection, gamePlayedID);
	    	return rows;
    	} 
    	finally
    	{
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
    	try 
    	{
	    	// call GamesPlayedDAO.count
	    	rows = dao.count(connection);
	    	return rows;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }
}
