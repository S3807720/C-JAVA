package ausroulette.model.bet;

/**
 * Represents a split bet in the Aussie Roulette game in the <b>Further
 * Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface extends from {@link ausroulette.model.bet.Bet}.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.bet.SplitBetImpl} which is used to represent both
 * {@link BetType#SPLIT_2} and {@link BetType#SPLIT_4} bets.
 * <p>
 * <b>Note: </b>When a split bet is created the implementation should arrange
 * the numbers in using natural order (lower numbers before higher ones) so
 * expected results can be returned from the split bet methods.
 * 
 * @see ausroulette.model.bet.SplitBetImpl
 * @see ausroulette.model.bet.Bet
 * @see ausroulette.model.bet.BetType
 * @see ausroulette.model.wheel.Pocket
 * 
 * @author Ross Nye
 */
public interface SplitBet extends Bet
{
	/**
	 * An enum to aid referring to numbers in a split bet
	 */
	public enum SplitPos
	{
		/**
		 * The first (or base) number. The lowest number in the split.
		 */
		FIRST,
		/** The second number in a split. */
		SECOND,
		/**
		 * The third number in a split. This is not valid for 2-way splits.
		 */
		THIRD,
		/**
		 * The forth number in a split. This is not valid for 2-way splits.
		 */
		FORTH,
		/**
		 * The last number in a split. The same as SECOND in a 2-way split. The
		 * same as FORTH in a 4-way split.
		 * 
		 */
		LAST;
	}
	
	/**
	 * @return the first number in a split bet sequence
	 */
	public int getFirstNumber();
	
	/**
	 * @return the last number in a split bet sequence
	 */
	public int getLastNumber();
	
	/**
	 * @param pos
	 *            the position in the split bet sequence to return
	 * @return the number at the specified position in the split bet sequence
	 * 			
	 * @throws IllegalArgumentException
	 *             if {@link SplitPos#THIRD} or {@link SplitPos#FORTH} supplied
	 *             for a {@link BetType#SPLIT_2} bet
	 */
	public int getNumber(SplitPos pos);
}
