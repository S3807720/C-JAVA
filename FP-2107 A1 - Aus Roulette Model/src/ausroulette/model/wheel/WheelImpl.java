package ausroulette.model.wheel;

import java.util.ArrayList;
import java.util.List;

public class WheelImpl implements Wheel {
	private List<Pocket> pockets = new ArrayList<>();
	private int pocketPosition = 0;
	
	public WheelImpl() {
		pockets = generateWheel();
	}

	@Override
	public int generateRandomNumber() {
		return (int) (Math.random() * LARGEST_NUMBER);
	}

	private boolean checkNum(int num) {
		for (int i = 0; Wheel.POCKET_NUMBERS.length > i; i++) {
			if (num == Wheel.POCKET_NUMBERS[i]) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Pocket moveToNumber(int number) throws IllegalArgumentException {
		if (!checkNum(number)) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; pockets.size() > i; i++) {
			if (pockets.get(i).getNumber() == number) {
				pocketPosition = i;
				return pockets.get(i);
			}
		}
		return null;
	}

	@Override
	public Pocket nextPocket() {
		//if position reaches the end, reset to start
		if (pocketPosition == NUMBER_OF_POCKETS) {
			pocketPosition = 0;
		} //return position then increment after
		return pockets.get(pocketPosition++);
	}

	public static java.util.List<Pocket> generateWheel() {
		int count = 0;
		List<Pocket> pockets = new ArrayList<>();
		while (NUMBER_OF_POCKETS > count) {
			//if 0 green
			if (POCKET_NUMBERS[count] == 0) {
				pockets.add(new PocketImpl(POCKET_NUMBERS[count], PocketColor.GREEN));
			} // even numbers are black, odd are red
			else if (count % 2 == 0) {
				pockets.add(new PocketImpl(POCKET_NUMBERS[count], PocketColor.BLACK));
			} else {
				pockets.add(new PocketImpl(POCKET_NUMBERS[count], PocketColor.RED));
			}
			count++;
		}
		return pockets;
	}
	
}
