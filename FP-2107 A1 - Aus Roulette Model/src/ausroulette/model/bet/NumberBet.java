package ausroulette.model.bet;

/**
 * Represents a number bet in the Aussie Roulette game in the 
 * <b>Further Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface extends from {@link ausroulette.model.bet.Bet}.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.bet.NumberBetImpl} which is used to represent both
 * {@link BetType#NUMBER} and {@link BetType#ZERO} bets.
 * 
 * @see ausroulette.model.bet.NumberBetImpl
 * @see ausroulette.model.bet.Bet
 * @see ausroulette.model.bet.BetType
 * @see ausroulette.model.wheel.Pocket
 * 
 * @author Ross Nye
 */
public interface NumberBet extends Bet
{
	/**
	 * @return the pocket number the bet has been placed on
	 */
	public int getNumber();
}
