package cs320.pattern;

public interface Observable {
	public enum event{
		SAVE,
	}
	
	boolean addObserver(Observer o);
	boolean removeObserver(Observer o);
	int notifyAllObservers(Observable that, Object o);
}
