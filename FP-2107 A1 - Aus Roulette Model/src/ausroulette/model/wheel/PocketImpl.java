package ausroulette.model.wheel;

public class PocketImpl implements Pocket
{
	private int number;
	private PocketColor color;
	
	public PocketImpl(int number, PocketColor color) 
	{
		if (color == null) {
			throw new NullPointerException();
		}
		if (!checkNum(number)) {
			throw new IllegalArgumentException();
		}
		this.number = number;
		this.color = color;
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
	public int getNumber() 
	{
		return this.number;
	}

	@Override
	public PocketColor getColor() 
	{
		return this.color;
	}

	@Override
	public boolean equals(Pocket pocket) 
	{
		return ( pocket.getColor().equals(this.color) && (pocket.getNumber() == this.number) ) ;
	}

	public boolean equals(Object obj) 
	{
		return ( obj.equals(this) && obj.hashCode() == this.hashCode() ); // ?
	}
	

	@Override
	public int hashCode() 
	{
		return number + color.hashCode();
	}
	
	@Override
	public String toString() 
	{
		return String.format("Pocket: #%d %s", number, color);
		
	}
}
