package com.qainfotech.tap.training.snl.api;

import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.assertj.core.api.*;
import org.testng.annotations.BeforeTest;

/**
 * 
 * @author amanraj
 *
 */
public class BoardTest {
	Board testBoard;

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws MaxPlayersReachedExeption
	 * @throws GameInProgressException
	 * @throws PlayerExistsException
	 */
	@BeforeTest
	public void loadOptions1() throws FileNotFoundException, UnsupportedEncodingException, IOException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		testBoard = new Board();
		testBoard.registerPlayer("Sam");
		testBoard.registerPlayer("Agatha");
	}

	/*
	 * @Test public void
	 * registerPlayerMethodShouldReturnListOfRegisteredPlayers() {
	 * 
	 * }
	 */

	/**
	 * 
	 * @throws MaxPlayersReachedExeption
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws IOException
	 */
	@Test(expectedExceptions = PlayerExistsException.class)
	public void registerPlayer_should_throw_PlayerExistsException_for_same_regisistration()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException {
		testBoard.registerPlayer("Agatha");
	}

	@Test
	public void loadOptions() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, IOException {
		testBoard.registerPlayer("Richard");

	}
/*
	@Test
	public void registerPlayer_should_throw_GameInProgressException_for_already_occuring_game()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException {
		assertThat((String)"Hello").isEqualTo("Hello");
	}
*/
	/**
	 * 
	 * @throws MaxPlayersReachedExeption
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws IOException
	 */
	@Test(expectedExceptions = MaxPlayersReachedExeption.class)
	public void registerPlayer_should_throw_theMaxPlayersReachedException_for_reaching_max_limit()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException {
		testBoard.registerPlayer("Brandon");
		testBoard.registerPlayer("Maxwell");
	}

	/**
	 * 
	 * @throws NoUserWithSuchUUIDException 
	 * @throws MaxPlayersReachedExeption
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws IOException
	 */
	@Test(expectedExceptions= NoUserWithSuchUUIDException.class)
	public void the_deletePlayer_should_throw_NoUserWithSuchUUIDException_for_wrong_Uuid() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException{
		testBoard.deletePlayer(this.testBoard.getUUID());
	}
}
