package ausroulette.model;

import java.util.Collection;

import ausroulette.model.bet.Bet;

/**
 * Represents a player in the Aussie Roulette game in the <b>Further Programming
 * Assignment</b>
 * <p>
 * Is to be implemented as {@link ausroulette.model.PlayerImpl}.
 * <p>
 * 
 * @see ausroulette.model.GameEngine
 * @see ausroulette.model.GameEngine#addPlayer(String, String, int)
 * 
 * @author Ross Nye
 */
public interface Player extends Comparable<Player>
{
	
	/**
	 * Simple getter for player's id
	 * 
	 * @return the player's id
	 */
	public String getId();
	
	/**
	 * Simple getter for player's name
	 * 
	 * @return the player's name
	 */
	public String getName();
	
	/**
	 * Simple getter for player's initial points
	 * 
	 * @return the player's initial points
	 */
	public int getInitialPoints();
	
	/**
	 * Simple getter for player's current points
	 * 
	 * @return the player's current points
	 */
	public int getPoints();
	
	/**
	 * Returns the available total points for a player, which excludes any
	 * amount currently bet.
	 * <p>
	 * <b>Hint:</b> If the player doesn't have a current bet this method should
	 * return the current points, otherwise the amount returned should exclude
	 * the bet amount.
	 * 
	 * @return the player's current available points.
	 */
	public int getAvailablePoints();
	
	/**
	 * A mutator method to <b>add</b> to the current points
	 * 
	 * @param points
	 *            the points to be added
	 * @throws IllegalArgumentException
	 *             if points are not positive number
	 */
	public void addPoints(int points);
	
	/**
	 * Returns the amount currently bet by the player.
	 * 
	 * @return the current amount that is bet by the player
	 */
	public int getCurrentBetTotal();
	
	/**
	 * A method for the accepting a new bet by the player.
	 * <p>
	 * The accepted bet should be stored and the relevant properties of the
	 * player updated.
	 * <p>
	 * <b>Hint:</b> The bet should be processed by the
	 * {@link ausroulette.model.GameEngine} before being passed to this method.
	 * 
	 * @param bet
	 *            the valid {@link Bet} to be accepted
	 * 			
	 * @throws NullPointerException
	 *             if bet is null
	 * @throws IllegalArgumentException
	 *             if the bet is associated with another player
	 */
	public void acceptBet(Bet bet);
	
	/**
	 * A method for the canceling a bet for the player.
	 * <p>
	 * The accepted bet should be removed and the relevant properties of the
	 * player updated.
	 * 
	 * @param bet
	 *            the valid {@link Bet} to be cancelled
	 * 			
	 * @throws NullPointerException
	 *             if bet is null
	 * 			
	 * @throws IllegalStateException
	 *             in the following cases
	 *             <ul>
	 *             <li>the bet is not associated with this player
	 *             <li>the bet is not currently registered (accepted) with the
	 *             player
	 *             <li>if bet is already finalised
	 *             </ul>
	 */
	public void cancelBet(Bet bet);
	
	/**
	 * Applies the supplied finalised bet outcome within this player.
	 * <p>
	 * Takes a bet finalised at the completion on the spin and applies changes
	 * to the player's point, current bet total, and available points. The
	 * {@link Bet} is also no longer stored by the {@link Player}.
	 * <p>
	 * <b>Note: </b>Bet amounts are not actually deducted from the player's
	 * point's balance until a bet is lost and finalised. When bet is won the
	 * amount bet becomes available again and the points balance is increased by
	 * the amount won from a bet.
	 * <p>
	 * e.g. Winning a 1 Red color bet, the bet amount (1) becomes available
	 * again and the points balance is also increased by the win amount (1). The
	 * overall result is that the balance is increased by one.
	 * 
	 * @param bet the bet to be finalised
	 * 
	 * @throws NullPointerException
	 *             if supplied bet is null
	 * 			
	 * @throws IllegalStateException
	 *             in the following cases
	 *             <ul>
	 *             <li>the bet is not associated with this player
	 *             <li>the bet is not currently registered (accepted) with the
	 *             player
	 *             <li>if bet is not finalised
	 *             </ul>
	 *             
	 * @see Bet#finaliseBet(ausroulette.model.wheel.Pocket)
	 * @see GameEngine#applyWinLoss(int, ausroulette.model.wheel.Pocket)
	 */
	public void applyBetOutcome(Bet bet);
	
	/**
	 * Resets the amount currently bet by the player to zero and removes all
	 * bets stored.
	 */
	public void resetBets();
	
	/**
	 * Returns an <em>unmodifiable</em> collection of bets containing the
	 * player's current bets.
	 * 
	 * @return an <em>unmodifiable</em> {@link Collection} of {@link Bet}
	 */
	public Collection<Bet> getBets();
	
	/**
	 * Compares two player objects based on their respective player names
	 * 
	 * @return a negative integer, zero, or a positive integer based on the
	 *         player names
	 */
	@Override
	public int compareTo(Player player);
	
	/**
	 * Can be used to determine equality between two players, which are
	 * considered equal if the player's ids are equal.
	 * 
	 * @param player
	 *            the {@link Player} object to compare this player with
	 * @return true if the player ids are equal, otherwise false
	 */
	public boolean equals(Player player);
	
	/**
	 * @param obj
	 *            the Object to be used for comparison
	 * @return true if supplied Object is a {@link Player} and the two players
	 *         are equal, otherwise false
	 */
	public boolean equals(Object obj);
	
	/**
	 * @return a hash code value for this player based on all relevant
	 *         properties
	 */
	@Override
	public int hashCode();
	
	/**
	 * Generates a String representation of the player as seen in the supplied
	 * .log file.
	 * <p>
	 * The String must match <b>exactly</b> the expected String. This will be
	 * tested during marking.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * Player ID: "P1", Name: "Mr Dollarbucks"
	 * Points: 100, Available Points: 84
	 * Current Bets: 2, Total Bet: 16
	 * </pre>
	 * 
	 * <pre>
	 * Player ID: "P2", Name: "Bingo"
	 * Points: 38, Available Points: 38
	 * Current Bets: 0, Total Bet: 0
	 * </pre>
	 * 
	 * @return a String representing the player
	 */
	@Override
	public String toString();
	
}
