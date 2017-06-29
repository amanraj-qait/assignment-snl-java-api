package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
		testBoard.registerPlayer("Dexter");
	}
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
	public void D_PlayerExistsException_for_same_regisistration()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException {
		testBoard.registerPlayer("Agatha");
		
	}

	@Test
	public void M_L_loadOptions() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, IOException {
		testBoard.registerPlayer("Richard");

	}

	@Test(expectedExceptions = GameInProgressException.class)
	public void N_GameInProgressException_for_already_occuring_game()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException, JSONException, InvalidTurnException {
		testBoard.rollDice((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));	
		testBoard.registerPlayer("Alfred");
	}
@Test
public void N_DeletePlayer_to_delete_a_player() throws FileNotFoundException, UnsupportedEncodingException, JSONException, NoUserWithSuchUUIDException{
	testBoard.deletePlayer((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));
}
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
	public void M_MaxPlayersReachedException_for_reaching_max_limit()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException {
		testBoard.registerPlayer("Maxwell");
		testBoard.registerPlayer("Brandon");
		
	
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
	public void r_deletePlayer_should_throw_NoUserWithSuchUUIDException_for_wrong_Uuid() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException{
		testBoard.deletePlayer(this.testBoard.getUUID());
	}
	/**
	 * 
	 * @throws InvalidTurnException
	 * @throws IOException 
	 * @throws MaxPlayersReachedExeption 
	 * @throws GameInProgressException 
	 * @throws PlayerExistsException 
	 */
	@Test(expectedExceptions=  InvalidTurnException.class)
	public void r_rollDice_should_throw_InvalidTurnException_for_wrong_turn() throws InvalidTurnException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException{
		((JSONObject) testBoard.getData().getJSONArray("players").get(0)).put("position",100);
		testBoard.rollDice((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));

	}
	@Test
	public void Check_Constructor_Board() throws IOException{
	   JSONObject  data1 = testBoard.data;
		Board newtest= new Board(testBoard.uuid);
	   JSONObject data2 = newtest.data;
		Assert.assertNotEquals(data1,data2);
		
	}
}
