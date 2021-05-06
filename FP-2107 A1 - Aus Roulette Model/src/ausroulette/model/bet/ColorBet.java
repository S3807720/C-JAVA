package ausroulette.model.bet;

import ausroulette.model.wheel.PocketColor;

/**
 * Represents a color bet in the Aussie Roulette game in the 
 * <b>Further Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface extends from {@link ausroulette.model.bet.Bet}.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.bet.ColorBetImpl}.
 * 
 * @see ausroulette.model.bet.ColorBetImpl
 * @see ausroulette.model.bet.Bet
 * @see ausroulette.model.bet.BetType
 * @see ausroulette.model.wheel.Pocket
 * @see ausroulette.model.wheel.PocketColor
 * 
 * @author Ross Nye
 */
public interface ColorBet extends Bet
{
	/**
	 * Returns the {@link PocketColor} associated with this color bet.
	 * 
	 * @return the relevant {@link PocketColor}
	 * 
	 * @see ausroulette.model.wheel.PocketColor
	 */
	public PocketColor getColor();
}