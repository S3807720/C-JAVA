package ausroulette.client;

import ausroulette.model.GameEngine;
import ausroulette.model.GameEngineImpl;
import ausroulette.model.bet.Bet;
import ausroulette.model.wheel.PocketColor;
import ausroulette.view.LoggerCallback;

/**
 * A rudimentary testing class for Aussie Roulette game in the <b>Further
 * Programming Assignment</b>
 * <p>
 * It is recommended that you <b>do not</b> modify this class. You should write
 * you own test classes instead.
 * <p>
 * You can (and should) use the GameEngine.log file or log written to the
 * console when this class is run to compare your implementation's output to the
 * supplied expected output.
 * 
 * @see ausroulette.model.GameEngine
 * @see ausroulette.model.GameEngineImpl
 * @see ausroulette.model.Player
 * @see ausroulette.model.PlayerImpl
 * 
 * @author Ross Nye
 */
public class GameEngineTest
{
	
	public static void main(String[] args)
	{
		GameEngine ge = new GameEngineImpl();
		
		// Registers the logger
		// Note: the class is supplied in the JAR
		// What happens if you register two??
		ge.registerCallback(new LoggerCallback());
		
		// Player 1
		ge.addPlayer("P1", "Mr Dollarbucks", 100);
		
		ge.placeColorBet("P1", 10, PocketColor.BLACK);
		ge.placeColorBet("P1", 2, PocketColor.BLACK);
		ge.placeColorBet("P1", 2, PocketColor.BLACK);
		
		ge.placeColorBet("P1", 2, PocketColor.RED);
		
		ge.placeColorBet("P1", 4, PocketColor.GREEN);
		
		ge.placeNumberBet("P1", 1, 18);
		
		ge.placeSplitBet("P1", 6, new int[] { 28, 29 });
		ge.placeSplitBet2("P1", 5, 28, 29);
		ge.placeSplitBet2("P1", 1, 16, 19);
		ge.placeSplitBet2("P1", 3, 1, 4);
		ge.placeSplitBet2("P1", 3, 1);
		
		ge.placeSplitBet("P1", 1, new int[] { 28, 29, 31, 32 });
		ge.placeSplitBet4("P1", 6, 28, 29, 31, 32);
		ge.placeSplitBet4("P1", 7, 31);
		ge.placeSplitBet4("P1", 8, 32);
		
		// These should fail when tested
		// ge.placeSplitBet4("P1", 3, 16, 17, 20, 21);
		// ge.placeSplitBet4("P1", 3, 18, 19, 21, 22);
		// ge.placeSplitBet4("P1", 3, 16, 17, 22, 23);
		// ge.placeSplitBet4("P1", 3, 16, 17, 19, 21);
		// ge.placeSplitBet4("P1", 3, 33);
		// ge.placeSplitBet4("P1", 3, 34);
		// ge.placeSplitBet2("P1", 3, 3, 4);
		// ge.placeSplitBet2("P1", 3, 8, 14);
		// ge.placeSplitBet2("P1", 3, 0, 1);
		// ge.placeSplitBet2("P1", 3, 3, 3);
		
		// Player 2
		ge.addPlayer("P2", "Bingo", 25);
		ge.addPoints("P2", 5);
		
		ge.placeColorBet("P2", 6, PocketColor.BLACK);
		ge.placeColorBet("P2", 1, PocketColor.RED);
		ge.placeColorBet("P2", 3, PocketColor.GREEN);
		ge.placeNumberBet("P2", 3, 0);
		
		Bet bet = ge.placeNumberBet("P2", 3, 11);
		ge.cancelBet(bet);
		
		ge.spinToWin(40, 125, 0);
	}
	
}
