package ausroulette.model.wheel;

/**
 * Represents a wheel pocket in the Aussie Roulette game in the <b>Further
 * Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.wheel.PocketImpl}.
 * 
 * @see ausroulette.model.wheel.PocketImpl
 * @see ausroulette.model.wheel.PocketColor
 * @see ausroulette.model.wheel.Wheel
 * 
 * @author Ross Nye
 */
public interface Pocket
{
	/**
	 * A simple getter
	 * 
	 * @return the pocket number
	 */
	public int getNumber();
	
	/**
	 * A simple getter
	 * 
	 * @return the pocket color
	 */
	public PocketColor getColor();
	
	/**
	 * Can be used to determine equality between two pockets, which are equal if
	 * the pocket numbers and pocket colors match.
	 * 
	 * @param pocket
	 *            the {@link Pocket} object to compare this pocket with
	 * @return true if the pocket numbers and the pocket colors match, otherwise
	 *         false
	 */
	public boolean equals(Pocket pocket);
	
	/**
	 * @param obj
	 *            the Object to be used for comparison
	 * @return true if supplied Object is a {@link Pocket} and the two pockets
	 *         are equal, otherwise false
	 */
	public boolean equals(Object obj);
	
	/**
	 * @return a hash code value for this pocket based on all relevant
	 *         properties
	 */
	@Override
	public int hashCode();
	
	/**
	 * Generates a String representation of the pocket as seen in the supplied
	 * .log file.
	 * <p>
	 * The String must match <b>exactly</b> the expected String. This will be
	 * tested during marking.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * Pocket:  #0 Green
	 * Pocket:  #7 Red
	 * Pocket: #26 Black
	 * Pocket: #16 Red
	 * </pre>
	 * 
	 * @return a String representing the pocket
	 */
	@Override
	public String toString();
}
