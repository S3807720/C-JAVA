package ausroulette.model.bet;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;

/**
 * Represents a bet in the Aussie Roulette game in the <b>Further Programming
 * Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface is extended by
 * <ul>
 * <li>{@link ausroulette.model.bet.ColorBet}
 * <li>{@link ausroulette.model.bet.NumberBet}
 * <li>{@link ausroulette.model.bet.SplitBet}.
 * </ul>
 * <p>
 * You're encouraged to think about the common functionality that exists between
 * them.
 * 
 * @see ausroulette.model.bet.BetType
 * @see ausroulette.model.bet.ColorBet
 * @see ausroulette.model.bet.NumberBet
 * @see ausroulette.model.bet.SplitBet
 * @see java.lang.Comparable
 * 
 * @author Ross Nye
 */
public interface Bet
{
	
	/**
	 * A simple getter
	 * 
	 * @return the player who placed this bet
	 */
	public Player getPlayer();
	
	/**
	 * A simple getter
	 * 
	 * @return the amount bet
	 */
	public int getAmount();
	
	/**
	 * A simple getter
	 * 
	 * @return the {@link BetType} for the bet
	 */
	public BetType getBetType();
	
	/**
	 * A simple getter
	 * 
	 * @return the winning pocket for finalised bet, or null if not finalised
	 */
	Pocket getWinningPocket();
	
	/**
	 * Called when finalising a bet.
	 * <p>
	 * This methods should be used change the state of a bet from being placed
	 * (outcome unknown) to being either a win or loss. This state should be
	 * able to be retrieved after the bet has been finalised (which would
	 * usually occur at the end of a spin).
	 * 
	 * @param winningPocket
	 *            the winning (final) wheel pocket at the conclusion of a spin
	 * @return the amount won by the bet, or zero if result was a loss.
	 * 
	 * @see Player#acceptBet(Bet)
	 * @see Player#applyBetOutcome(Bet)
	 * @see GameEngine#applyWinLoss(int, Pocket)
	 */
	public int finaliseBet(Pocket winningPocket);
	
	/**
	 * Indicates if a finalised bet resulted in a win or not.
	 * 
	 * @return true if the bet is finalised and the bet won, otherwise false
	 */
	public boolean isWin();
	
	/**
	 * Returns the amount won from a finalised bet. This will be zero if the bet
	 * resulted in a loss or the bet is not yet finalised.
	 *
	 * @return the amount won or 0 if a bet was a loss or is not yet finalised
	 */
	public int getAmountWon();
	
	/**
	 * Compares two bets to determine if they're equal.
	 * <p>
	 * Equality should be based on all relevant information of the bets;
	 * player, amount, bet type as well as the data type of the {@link Bet}
	 * object itself and properties relevant to each sub-class.
	 * 
	 * @param bet
	 *            to be used for comparison
	 * @return true is all the properties of the two bets are equal
	 */
	public boolean equals(Bet bet);
	
	/**
	 * @param obj
	 *            the Object to be used for comparison
	 * @return true if supplied Object is a {@link Bet} and the two bets are
	 *         equal, otherwise false
	 */
	public boolean equals(Object obj);
	
	/**
	 * @return a hash code value for this bet based on all relevant properties
	 */
	@Override
	public int hashCode();
	
	/**
	 * Generates a String representation of the {@link Bet} as seen in the supplied .log
	 * file.
	 * <p>
	 * The String must match <b>exactly</b> the expected String. This will be
	 * tested during marking.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * P1 bet 12 on Black
	 * P2 bet 6 on Zero
	 * P2 bet 3 on Number #11
	 * P1 bet 3 on 2-way Split [1, 2]
	 * P1 bet 4 on 4-way Split [28, 29, 31, 32]
	 * </pre>
	 * 
	 * @return a String representing the {@link Bet}
	 */
	@Override
	public String toString();
	
}
