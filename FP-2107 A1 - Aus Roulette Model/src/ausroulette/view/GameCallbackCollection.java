package ausroulette.view;

/**
 * Supporting interface used in the <b>Further Programming Assignment</b>
 * <p>
 * Provides methods for adding or removing a {@link GameCallback} from a
 * {@link java.util.Collection}.
 * 
 * @author Ross Nye
 */
public interface GameCallbackCollection
{
	/**
	 * Registers a {@link GameCallback}; the callback is placed in a
	 * {@link java.util.Collection}.
	 * 
	 * @param callback the {@link GameCallback} to be added to the collection.
	 * @return the number of {@link GameCallback} in the collection after adding
	 * @see GameCallback
	 */
	public int registerCallback(GameCallback callback);

	/**
	 * Removes supplied {@link GameCallback} from the {@link java.util.Collection}
	 * of registered callbacks.
	 * 
	 * @param callback the {@link GameCallback} to be removed.
	 * @return the number of {@link GameCallback} in the collection after removal
	 * @see GameCallback
	 */
	public int removeCallback(GameCallback callback);
}
