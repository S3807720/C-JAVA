package ausroulette.model;

import java.util.Collection;

import ausroulette.model.bet.Bet;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.PocketColor;
import ausroulette.view.GameCallback;
import ausroulette.view.GameCallbackCollection;

/**
 * Represents a game engine in the Aussie Roulette game in the <b>Further
 * Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.GameEngineImpl}.
 * <p>
 * This is the main interface of the game.
 * 
 * @see ausroulette.model.GameEngineImpl
 * @see ausroulette.view.GameCallbackCollection
 * @see ausroulette.view.GameCallback
 * @see ausroulette.model.Player
 * @see ausroulette.model.bet.Bet
 * @see ausroulette.model.wheel.Wheel
 * 
 * @author Ross Nye
 */
public interface GameEngine extends GameCallbackCollection
{
	/**
	 * Constant used in the spin methods to determine the maximum delay
	 */
	public static final int MAX_SPIN_DELAY = 2000;
	
	/**
	 * Creates a player using the supplied information and adds the player to
	 * the game.
	 * <p>
	 * {@link GameCallback#playerAdded(Player)} is invoked on all registered
	 * callbacks once the player is successfully added.
	 * 
	 * @param id
	 *            the id of the player
	 * @param name
	 *            the name of the player
	 * @param initialPoints
	 *            the initial points the player should start with
	 * 			
	 * @return the {@link Player} that was added
	 * 			
	 * @throws NullPointerException
	 *             if supplied id or name are null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>supplied name is an empty String
	 *             <li>supplied initialPoints is not a positive number
	 *             <li>there is already a player using the supplied id
	 *             </ul>
	 */
	public Player addPlayer(String id, String name, int initialPoints);
	
	/**
	 * Removes a registered player with the matching id from the game.
	 * <p>
	 * Players can only be removed when they do not have any current bets.
	 * <p>
	 * {@link GameCallback#playerRemoved(Player)} is invoked on all registered
	 * callbacks once the player is successfully removed.
	 * 
	 * @param id
	 *            the id of the player to remove
	 * 			
	 * @return the player that was removed
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>the registered player has current bets
	 *             </ul>
	 */
	public Player removePlayer(String id);
	
	/**
	 * Returns an <em>unmodifiable</em> collection of players currently
	 * registered with the game engine.
	 * 
	 * @return an <em>unmodifiable</em> {@link Collection} of {@link Player}
	 */
	public Collection<Player> getAllPlayers();
	
	/**
	 * Add points to a registered player with the matching id.
	 * <p>
	 * {@link GameCallback#pointsAdded(Player, int)} is invoked on all
	 * registered callbacks once the points are successfully added.
	 * 
	 * @param id
	 *            the id of the player
	 * @param points
	 *            the points to be added
	 * 			
	 * @return the player which had the points added
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied points are not a positive number
	 *             </ul>
	 */
	public Player addPoints(String id, int points);
	
	/**
	 * Cancels the supplied bet.
	 * <p>
	 * {@link GameCallback#betCancelled(Bet)} is invoked on all registered
	 * callbacks once the points are successfully added.
	 * 
	 * @param bet
	 *            the valid {@link Bet} to be cancelled
	 * 			
	 * @throws NullPointerException
	 *             if supplied bet is null
	 * 			
	 * @throws IllegalStateException
	 *             in the following cases
	 *             <ul>
	 *             <li>the bet is not currently registered (accepted) with the
	 *             player its associated player
	 *             <li>if the bet is already finalised
	 *             </ul>
	 */
	public void cancelBet(Bet bet);
	
	/**
	 * Places a color bet using the supplied information.
	 * <p>
	 * Any existing color bet for the same player and color should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * <p>
	 * <b>Note: </b>{@link PocketColor#GREEN} is not valid for
	 * {@link ausroulette.model.bet.ColorBet}. If {@link PocketColor#GREEN} is
	 * supplied a {@link ausroulette.model.bet.BetType#ZERO}
	 * {@link ausroulette.model.bet.NumberBet} should be created.
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param color
	 *            the {@link PocketColor} to bet on
	 * @return the created bet
	 * 			
	 * @throws NullPointerException
	 *             if supplied id or color is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeColorBet(String id, int amount, PocketColor color);
	
	/**
	 * Places a number bet using the supplied information.
	 * <p>
	 * Any existing number bet for the same player and number should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param number
	 *            the {@link Pocket} number to bet on
	 * @return the created bet
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeNumberBet(String id, int amount, int number);
	
	/**
	 * Places a 2-way split bet using the supplied information.
	 * <p>
	 * Any existing split bet for the same player and numbers should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param number1
	 *            the first {@link Pocket} number to create a split bet with
	 * @param number2
	 *            the second (last) {@link Pocket} number to create a split bet
	 *            with
	 * @return the created bet
	 * 
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             <li>supplied numbers are invalid pocket numbers
	 *             <li>supplied numbers cannot be used to create a valid 2-way
	 *             split bet
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeSplitBet2(String id, int amount, int number1, int number2);
	
	/**
	 * Places a 2-way split bet using the supplied information.
	 * <p>
	 * Any existing split bet for the same player and numbers should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * <p>
	 * This method infers the second number in the 2-way split is one higher
	 * than the supplied base number.
	 * 
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param baseNumber
	 *            the first {@link Pocket} number to create a split bet with
	 * @return the created bet
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             <li>supplied/inferred numbers are invalid pocket numbers
	 *             <li>supplied/inferred numbers cannot be used to create a
	 *             valid 2-way split bet
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeSplitBet2(String id, int amount, int baseNumber);
	
	/**
	 * Places a 4-way split bet using the supplied information.
	 * <p>
	 * Any existing split bet for the same player and numbers should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param number1
	 *            the first {@link Pocket} number to create a split bet with
	 * @param number2
	 *            the second {@link Pocket} number to create a split bet with
	 * @param number3
	 *            the third {@link Pocket} number to create a split bet with
	 * @param number4
	 *            the forth (last) {@link Pocket} number to create a split bet
	 *            with
	 * 
	 * @return the created bet
	 * 
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             <li>supplied numbers are invalid pocket numbers
	 *             <li>supplied numbers cannot be used to create a valid 4-way
	 *             split bet
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeSplitBet4(String id, int amount, int number1, int number2, int number3,
			int number4);
	
	/**
	 * Places a 4-way split bet using the supplied information.
	 * <p>
	 * Any existing split bet for the same player and numbers should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * <p>
	 * This method infers the other numbers in the 4-way split based than the
	 * supplied base number, which is assumed to be the lowest number.
	 * 
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param baseNumber
	 *            the first {@link Pocket} number to create a split bet with
	 * @return the created bet
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             <li>supplied/inferred numbers are invalid pocket numbers
	 *             <li>supplied/inferred numbers cannot be used to create a
	 *             valid 4-way split bet
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeSplitBet4(String id, int amount, int baseNumber);
	
	/**
	 * Places a 2-way or 4-way split bet using the supplied information.
	 * <p>
	 * Any existing split bet for the same player and numbers should be combined
	 * into a single bet with the combined amounts and replace the existing bet.
	 * <p>
	 * {@link GameCallback#betAccepted(Player, Bet, Bet)} is invoked on all
	 * registered callbacks once the bet is successfully placed.
	 * 
	 * @param id
	 *            the id of the player
	 * @param amount
	 *            the amount of points to bet
	 * @param numbers
	 *            the array or 2 or 4 numbers to be used to create the bet
	 * @return the created bet
	 * 			
	 * @throws NullPointerException
	 *             if supplied id is null
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied id is an empty String
	 *             <li>there is no registered player using supplied id
	 *             <li>supplied amount is not a positive number
	 *             <li>supplied numbers array does not contain 2 or 4 values
	 *             <li>supplied numbers are invalid pocket numbers
	 *             <li>supplied numbers cannot be used to create a valid 2-way
	 *             or 4-way split bet
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             if the player doesn't have enough points to service the bet
	 */
	public Bet placeSplitBet(String id, int amount, int[] numbers);
	
	/**
	 * Basic spin method. No bets are finalised when invoking this method.
	 * <p>
	 * This method is designed for testing and to potentially be used in the
	 * final GUI for spinning the wheel without needing a result.
	 * <p>
	 * This method must do the following:
	 * <ul>
	 * <li>move the wheel to the supplied starting pocket number
	 * <li>invoke the {@link GameCallback#spinStart(int, Pocket)} method to
	 * notify all registered callbacks of the spin number and starting pocket
	 * <li><em>Tick over</em> the wheel to the next pocket the supplied number
	 * of ticks times
	 * <li>for each <em>tick</em> invoke the
	 * {@link GameCallback#spinTick(int, int, Pocket)} method to notify all
	 * registered callback or the spin number, the tick number and the current
	 * wheel pocket, except on the last tick three
	 * {@link GameCallback#spinResult(int, Pocket)} is used instead
	 * <li><em>between</em> each <em>tick</em> (including after the starting
	 * tick and before the return tick) a delay should be performed using the
	 * time (ms) specified by the delay parameter
	 * </ul>
	 * 
	 * @param ticks
	 *            the number of ticks of the wheel
	 * @param delay
	 *            the delay in ms between each tick
	 * @param startingNumber
	 *            the pocket number to start the spin on
	 * 			
	 * @return the winning (final/result) pocket
	 * 			
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied ticks is less than one
	 *             <li>supplied delay is less than 0
	 *             <li>supplied delay is greater than {@link #MAX_SPIN_DELAY}
	 *             </ul>
	 */
	public Pocket spin(int ticks, int delay, int startingNumber);
	
	/**
	 * Spin method. Bets <b>are</b> finalised when invoking this method.
	 * <p>
	 * This method can be used for testing as the outcome is predetermined.
	 * <p>
	 * This method must do the following:
	 * <ul>
	 * <li>move the wheel to the supplied starting pocket number
	 * <li>invoke the {@link GameCallback#spinStart(int, Pocket)} method to
	 * notify all registered callbacks of the spin number and starting pocket
	 * <li><em>Tick over</em> the wheel to the next pocket the supplied number
	 * of ticks times
	 * <li>for each <em>tick</em> invoke the
	 * {@link GameCallback#spinTick(int, int, Pocket)} method to notify all
	 * registered callback or the spin number, the tick number and the current
	 * wheel pocket, except on the last tick three
	 * {@link GameCallback#spinResult(int, Pocket)} is used instead
	 * <li><em>between</em> each <em>tick</em> (including after the starting
	 * tick and before the return tick) a delay should be performed using the
	 * time (ms) specified by the delay parameter
	 * <li>finalise the bets by calling {@link #applyWinLoss(int, Pocket)}
	 * </ul>
	 * 
	 * @param ticks
	 *            the number of ticks of the wheel
	 * @param delay
	 *            the delay in ms between each tick
	 * @param startingNumber
	 *            the pocket number to start the spin on
	 * 			
	 * @return the winning (final/result) pocket
	 * 			
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied ticks is less than one
	 *             <li>supplied delay is less than 0
	 *             <li>supplied delay is greater than {@link MAX_SPIN_DELAY}
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             in the following cases
	 *             <ul>
	 *             <li>There are no registered players
	 *             <li>There are no current bets
	 *             </ul>
	 * 
	 */
	public Pocket spinToWin(int ticks, int delay, int startingNumber);
	
	/**
	 * Spin method. Bets <b>are</b> finalised when invoking this method.
	 * <p>
	 * This method can be used for testing as the outcome is predetermined.
	 * <p>
	 * This method must do the following:
	 * <ul>
	 * <li>move the wheel to the random starting pocket number
	 * <li>invoke the {@link GameCallback#spinStart(int, Pocket)} method to
	 * notify all registered callbacks of the spin number and starting pocket
	 * <li><em>Tick over</em> the wheel to the next pocket the supplied number
	 * of ticks times
	 * <li>for each <em>tick</em> invoke the
	 * {@link GameCallback#spinTick(int, int, Pocket)} method to notify all
	 * registered callback or the spin number, the tick number and the current
	 * wheel pocket, except on the last tick three
	 * {@link GameCallback#spinResult(int, Pocket)} is used instead
	 * <li><em>between</em> each <em>tick</em> (including after the starting
	 * tick and before the return tick) a delay should be performed using the
	 * time (ms) specified by the delay parameter
	 * <li>the wheel must to at least one complete revolution
	 * <li>finalise the bets by calling {@link #applyWinLoss(int, Pocket)}
	 * </ul>
	 * 
	 * @param ticks
	 *            the number of ticks of the wheel
	 * @param delay
	 *            the delay in ms between each tick
	 * 			
	 * @return the winning (final/result) pocket
	 * 			
	 * @throws IllegalArgumentException
	 *             in the following cases
	 *             <ul>
	 *             <li>supplied ticks does not spin the wheel at least one
	 *             complete revolution
	 *             <li>supplied delay is less than 0
	 *             <li>supplied delay is greater than {@link MAX_SPIN_DELAY}
	 *             </ul>
	 * 
	 * @throws IllegalStateException
	 *             in the following cases
	 *             <ul>
	 *             <li>There are no registered players
	 *             <li>There are no current bets
	 *             </ul>
	 * 
	 */
	public Pocket spinToWin(int ticks, int delay);
	
	/**
	 * This method finalises bets after a spin and is mainly separated from the
	 * spin methods for cohesion, debugging and testing purposes.
	 * <p>
	 * For each registered player this method should
	 * <ul>
	 * <li>finalise each of the players bets
	 * <li>invoke {@link GameCallback#betResult(int, Player, Bet)} for each
	 * registered callback, notifying them of each of the finalised bets
	 * <li>reset the players bets - they can be removed once finalised
	 * <li>calculate the amount (if any) the player one on the spin
	 * <li>invoke
	 * {@link GameCallback#spinPlayerTotal(int, Player, Collection, int, int)}
	 * passing this information and the collections of bets to the registered
	 * callbacks
	 * </ul>
	 * The method should also calculate the total player wins and losses and
	 * pass this information to all the registered callbacks by invoking
	 * {@link GameCallback#spinHouseResult(int, int, int)}
	 * 
	 * @param spinNumber
	 *            the spin number - a counter for the times the wheel has been
	 *            spun
	 * @param winningPocket
	 *            the winning (final / result) pocket at the conclusion of the
	 *            spin for which these bets are being finalised
	 *            
	 * @see Bet#finaliseBet(Pocket)
	 * @see Player#applyBetOutcome(Bet)
	 */
	public void applyWinLoss(int spinNumber, Pocket winningPocket);
}
