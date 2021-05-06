package ausroulette.model.wheel;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 
 * Represents a color bet in the Aussie Roulette game in the <b>Further
 * Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter this interface.
 * <p>
 * This interface should be implemented as
 * {@link ausroulette.model.wheel.WheelImpl}.
 * <p>
 * Maintains a collection of {@link Pocket} and using the methods described in
 * this interface provides functionality of turning wheel from one pocket to the
 * next, returning to the first pocket (number 0, Green) after the last pocket
 * is reached
 * 
 * @see ausroulette.model.wheel.WheelImpl
 * @see ausroulette.model.wheel.Pocket
 * @see ausroulette.model.wheel.PocketColor
 * 
 * @author Ross Nye
 */
public interface Wheel
{
	/**
	 * An ordered array populated with the pocket numbers of the wheel
	 */
	public static final int[] POCKET_NUMBERS = new int[] { 0, 32, 15, 19, 04, 21, 02, 25, 17, 34,
			06, 27, 13, 36, 11, 30, 8, 23, 10, 05, 24, 16, 33, 01, 20, 14, 31, 9, 22, 18, 29, 07,
			28, 12, 35, 03, 26 };
	
	/**
	 * A constant that defines the number of pockets in the wheel
	 */
	public static final int NUMBER_OF_POCKETS = POCKET_NUMBERS.length;
	
	/**
	 * A constant the defines the maximum pocket number in the wheel's pockets
	 */
	public static final int LARGEST_NUMBER = Collections
			.max(Arrays.stream(POCKET_NUMBERS).boxed().collect(Collectors.toList()));
	
	/**
	 * Generates a random pocket number. Returned number is between 0 and
	 * {@link #LARGEST_NUMBER} inclusive.
	 * 
	 * @return a valid random pocket number
	 */
	public int generateRandomNumber();
	
	/**
	 * Moves wheel the supplied pocket number and returns the pocket object
	 * 
	 * @param number
	 *            the pocket number to move to
	 * @return the {@link Pocket} with the specified pocket number
	 * 			
	 * @throws IllegalArgumentException
	 *             if the supplied number is not a valid pocket number
	 * 
	 * @see ausroulette.model.wheel.Pocket
	 */
	public Pocket moveToNumber(int number) throws IllegalArgumentException;
	
	/**
	 * Advances the wheel to the next pocket and returns the pocket at that
	 * position.
	 * 
	 * @return the {@link Pocket} at the updated wheel position
	 * 
	 * @see ausroulette.model.wheel.Pocket
	 */
	public Pocket nextPocket();
}
