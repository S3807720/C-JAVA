
package ausroulette.view;

import java.util.Collection;

import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.model.wheel.Pocket;

/**
 * The callback interface in <b>Further Programming Assignment</b>
 * <p>
 * The methods is this interface are called upon as specified in the
 * {@link ausroulette.model.GameEngine} interface
 * <p>
 * In Assignment 1 this class is implemented as {@link LoggerCallback}.
 * 
 * @see ausroulette.model.GameEngine
 * 
 * @author Ross Nye
 */
public interface GameCallback
{
	/**
	 * Updates view when a player is added to the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param player
	 *            the player added
	 * 			
	 * @see ausroulette.model.GameEngine#addPlayer(String, String, int)
	 */
	public void playerAdded(Player player);
	
	/**
	 * Updates view when a player is removed from the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param player
	 *            the player removed
	 * 			
	 * @see ausroulette.model.GameEngine#removePlayer(String)
	 */
	public void playerRemoved(Player player);
	
	/**
	 * Updates a view when points are added to a player in the
	 * {@link ausroulette.model.GameEngine} (though not when bets are finalised)
	 * 
	 * @param player
	 *            the player which had points added
	 * @param points
	 *            the amount of points that were added
	 * 			
	 * @see ausroulette.model.GameEngine#addPoints(String, int)
	 */
	public void pointsAdded(Player player, int points);
	
	/**
	 * Updates the view when a player successfully places a bet in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param player
	 *            the player who placed the bet
	 * @param bet
	 *            the bet that was placed
	 * @param existingBet
	 *            the existing bet this bet replaces - or null if there was no
	 *            such bet
	 * 
	 * @see ausroulette.model.GameEngine#placeColorBet(String, int,
	 *      ausroulette.model.wheel.PocketColor)
	 * @see ausroulette.model.GameEngine#placeNumberBet(String, int, int)
	 * @see ausroulette.model.GameEngine#placeSplitBet(String, int, int[])
	 * @see ausroulette.model.GameEngine#placeSplitBet2(String, int, int)
	 * @see ausroulette.model.GameEngine#placeSplitBet2(String, int, int, int)
	 * @see ausroulette.model.GameEngine#placeSplitBet4(String, int, int)
	 * @see ausroulette.model.GameEngine#placeSplitBet4(String, int, int, int,
	 *      int, int)
	 */
	public void betAccepted(Player player, Bet bet, Bet existingBet);
	
	/**
	 * Updates the view when a bet is successfully cancelled in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param bet
	 *            the cancelled bet
	 */
	public void betCancelled(Bet bet);
	
	/**
	 * Updates the view when a spin starts in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param pocket
	 *            the starting {@link Pocket}
	 * 			
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 * @see ausroulette.model.GameEngine#spin(int, int, int)
	 */
	public void spinStart(int spinNumber, Pocket pocket);
	
	/**
	 * Updates the view each intermediate tick of the wheel during a spin in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param tick
	 *            the tick number (increments each tick during a spin)
	 * 			
	 * @param pocket
	 *            the current {@link Pocket}
	 * 			
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 * @see ausroulette.model.GameEngine#spin(int, int, int)
	 */
	public void spinTick(int spinNumber, int tick, Pocket pocket);
	
	/**
	 * Updates the view the winning (final / result) {@link Pocket} at the
	 * completion of a spin in the {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param pocket
	 *            the winning {@link Pocket}
	 * 			
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 * @see ausroulette.model.GameEngine#spin(int, int, int)
	 */
	public void spinResult(int spinNumber, Pocket pocket);
	
	/**
	 * Updates the view of the result of a finalised {@link Bet} in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param player
	 *            the {@link Player} who placed the bet
	 * 			
	 * @param bet
	 *            the finalised {@link Bet}
	 * 			
	 * @see ausroulette.model.GameEngine#applyWinLoss(int, Pocket)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 */
	public void betResult(int spinNumber, Player player, Bet bet);
	
	/**
	 * Updates the view of the {@link Player}'s totals and finalised
	 * {@link Bet}s at the conclusion of the spin in the
	 * {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param player
	 *            the {@link Player} whose bets were all finalised
	 * @param bets
	 *            all the {@link Player}'s finalised {@link Bet}s
	 * @param amountWon
	 *            the total amount won by the {@link Player} for the spin (or 0)
	 * @param amountLost
	 *            the total amount lost by the {@link Player} for the spin (or
	 *            0)
	 * 
	 * 
	 * @see ausroulette.model.GameEngine#applyWinLoss(int, Pocket)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 */
	public void spinPlayerTotal(int spinNumber, Player player, Collection<Bet> bets, int amountWon,
			int amountLost);
	
	/**
	 * Updates the view of the house's total wins and losses at the conclusion
	 * of the spin in the {@link ausroulette.model.GameEngine}
	 * 
	 * @param spinNumber
	 *            the spin number (a counter)
	 * @param allPlayerTotalWin
	 *            the total won by all players
	 * @param allPlayerTotalLoss
	 *            the total lost by all players (bets that didn't win)
	 * 			
	 * @see ausroulette.model.GameEngine#applyWinLoss(int, Pocket)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int)
	 * @see ausroulette.model.GameEngine#spinToWin(int, int, int)
	 */
	public void spinHouseResult(int spinNumber, int allPlayerTotalWin, int allPlayerTotalLoss);
	
}