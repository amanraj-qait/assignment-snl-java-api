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
	BoardModel testBoardModel;

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
		testBoardModel = new BoardModel();
		testBoard.registerPlayer("Agatha");
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
		String name = (String) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("name");
		if (testBoard.getData().getJSONArray("players").length() >= 1) {
			testBoard.registerPlayer(name);
		}
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws MaxPlayersReachedExeption
	 * @throws IOException
	 */
	@Test
	public void M_L_load_registerPlayer() throws FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		String add_name = "Richard";
		testBoard.registerPlayer(add_name);
		if (testBoard.getData().getJSONArray("players").length() >= 1) {
			/**
			 * To check whether the new registration is saved at position other
			 * than 0
			 */
			Assert.assertNotEquals(((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("name"),
					add_name);
		}
	}

	/**
	 * 
	 * @throws MaxPlayersReachedExeption
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws IOException
	 * @throws JSONException
	 * @throws InvalidTurnException
	 */
	@Test(expectedExceptions = GameInProgressException.class)
	public void N_GameInProgressException_for_already_occuring_game()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException, JSONException, InvalidTurnException {
		testBoard.rollDice((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));
		testBoard.registerPlayer("Alfred");
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws JSONException
	 * @throws NoUserWithSuchUUIDException
	 */
	@Test
	public void N_DeletePlayer_to_delete_a_player()
			throws FileNotFoundException, UnsupportedEncodingException, JSONException, NoUserWithSuchUUIDException {
		if (testBoard.getData().getJSONArray("players").length() > 1) {
			String name = (String) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("name");
			testBoard
					.deletePlayer((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));
			String name2 = (String) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("name");
			Assert.assertNotEquals(name, name2);
		}
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
		String[] names = { "Maxwell", "Brandon", "Kane", "Augustus" };
		int noOfPlayers = testBoard.getData().getJSONArray("players").length();
		while (noOfPlayers < 4) {
			for (int nameloop = 0; nameloop < (4 - noOfPlayers); nameloop++) {
				testBoard.registerPlayer(names[nameloop]);
			}
		}
		// gives MaxPlayerreachedException on Execution
		testBoard.registerPlayer("Adrian");
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
	@Test(expectedExceptions = NoUserWithSuchUUIDException.class)
	public void R_deletePlayer_should_throw_NoUserWithSuchUUIDException_for_wrong_Uuid()
			throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException {
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
	@Test(expectedExceptions = InvalidTurnException.class)
	public void r_rollDice_should_throw_InvalidTurnException_for_wrong_turn() throws InvalidTurnException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		((JSONObject) testBoard.getData().getJSONArray("players").get(0)).put("position", 100);
		testBoard.rollDice((UUID) ((JSONObject) testBoard.getData().getJSONArray("players").get(0)).get("uuid"));
	}
/**
 * 
 * @throws FileNotFoundException
 * @throws UnsupportedEncodingException
 * @throws IOException
 * @throws PlayerExistsException
 * @throws GameInProgressException
 * @throws MaxPlayersReachedExeption
 * @throws JSONException
 * @throws InvalidTurnException
 */
	@Test
	public void get_incorrect_roll_of_dice_message()
			throws FileNotFoundException, UnsupportedEncodingException, IOException, PlayerExistsException,
			GameInProgressException, MaxPlayersReachedExeption, JSONException, InvalidTurnException {
		Board newTestBoard = new Board();
		String[] names = { "Maxwell", "Brandon", "Kane", "Augustus" };
		for (int nameloop = 0; nameloop < 4; nameloop++) {
			newTestBoard.registerPlayer(names[nameloop]);
		}
		for (int index = 0; index <= 3; index++) {
			newTestBoard.getData().getJSONArray("players").getJSONObject(index).put("position", 100);
		}
		JSONObject player_turn = (JSONObject) newTestBoard.getData().getJSONArray("players")
				.get(Integer.parseInt(newTestBoard.data.get("turn").toString()));
		// roll dice
		JSONObject output_json = newTestBoard.rollDice(UUID.fromString(player_turn.get("uuid").toString()));
		Assert.assertEquals(output_json.get("message"), "Incorrect roll of dice. Player did not move");
	}

	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void Check_Constructor_Board() throws IOException {
		JSONObject data1 = testBoard.data;
		Board newtest = new Board(testBoard.uuid);
		JSONObject data2 = newtest.data;
		Assert.assertNotEquals(data1, data2);

	}

	/**
	 * 
	 * @throws JSONException
	 * @throws InvalidTurnException
	 * @throws PlayerExistsException
	 * @throws GameInProgressException
	 * @throws MaxPlayersReachedExeption
	 * @throws IOException
	 * @throws NoUserWithSuchUUIDException
	 */
	@Test
	public void rr_Check_where_the_player_has_to_move_to_on_running_rollDice()
			throws JSONException, InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		Board newTest = new Board();
		newTest.registerPlayer("Gordon");
		((JSONObject)newTest.getData().getJSONArray("players").get(0)).put("position", 0);
		UUID uuid = (UUID) ((JSONObject) newTest.getData().getJSONArray("players").get(0)).get("uuid");
		Object current_position = ((JSONObject) newTest.getData().getJSONArray("players").get(0)).get("position");
		JSONObject obj = newTest.rollDice(uuid);
		Object new_position = ((JSONObject) newTest.getData().getJSONArray("players").get(0)).get("position");
		Object dice = obj.get("dice");
		Object message = obj.get("message");
		int number = (int) current_position + (int) dice;
		JSONObject steps = null;
		// dice roll
		steps = (JSONObject) newTest.getData().getJSONArray("steps").get((int) number);
		int type = (Integer) steps.get("type");
		if (type == 2) {
			Assert.assertNotEquals(message, "Player climbed a ladder, moved to " + new_position);
		} else if (type == 0) {
			Assert.assertEquals(message, "Player moved to " + new_position);
		} else if (type == 1) {
			Assert.assertNotEquals(message, "Player was bit by a snake, moved back to " + new_position);
		}
	}

	/**
	 * 
	 */
	@Test
	public void re_Check_wether_toString_method_is_returning_accurate_value() {
		String value = "UUID:" + testBoard.uuid.toString() + "\n" + testBoard.data.toString();
		Assert.assertEquals(value, testBoard.toString());
	}
}
