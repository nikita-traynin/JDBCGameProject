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

// Implemented by Evan McCauley

package cs4347.jdbcGame.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.services.GameService;
import cs4347.jdbcGame.util.DAOException;

public class GameServiceImpl implements GameService
{

    private DataSource dataSource;
    private GameDAOImpl dao;

    public GameServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.dao = new GameDAOImpl();
    }

    @Override
    public Game create(Game game) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	Game newgame;
    	try 
    	{
	    	// call GameDAO.create
	    	newgame = dao.create(connection, game);
	    	return newgame;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public Game retrieve(long gameID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	Game game;
    	try 
    	{
	    	// call GameDAO.retrieve
	    	game = dao.retrieve(connection, gameID);
	    	return game;
    	} 
    	finally
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int update(Game game) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try
    	{
	    	// call GameDAO.update
	    	rows = dao.update(connection, game);
	    	return rows;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public int delete(long gameID) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	int rows;
    	try 
    	{
	    	// call GameDAO.delete
	    	rows = dao.delete(connection, gameID);
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
	    	// call GameDAO.count
	    	rows = dao.count(connection);
	    	return rows;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<Game> result;
    	try 
    	{
	    	// call GameDAO.retrieveByTitle
	    	result = dao.retrieveByTitle(connection, titlePattern);
	    	return result;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }

    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
    	// get connection from dataSource
    	Connection connection = dataSource.getConnection();
    	List<Game> result;
    	try 
    	{
	    	// call GameDAO.retrieveByReleaseDate
	    	result = dao.retrieveByReleaseDate(connection, start, end);
	    	return result;
    	} 
    	finally 
    	{
    		// finally close connection
    		connection.close();
    	}
    }
}
