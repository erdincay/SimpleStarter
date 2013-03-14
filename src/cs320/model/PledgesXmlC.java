package cs320.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs320.pattern.Observable;
import cs320.pattern.Observer;

public class PledgesXmlC extends HashMap<String, PledgeD> implements PledgesI, Observable{

	private static final long serialVersionUID = 5554800509631023893L;
	
	private List<Observer> obs;
	
	public PledgesXmlC() {
		obs = new ArrayList<Observer>();
	}
	
	public int totalPledges() {
		int total = 0;
		for(PledgeD plg : this.values()) {
			total += plg.getPledged();
		}
		return total;
	}
	
	public boolean checkPledgeByUser(String key) {
		return this.containsKey(key);
	}
	
	public HashMap<String, PledgeD> generatePledges() {
		return this;
	}
	
	public void savePledge(UserD usr, PledgeD pl) {
		this.put(usr.getName(), pl);
		notifyAllObservers(this,event.SAVE);
	}
	
	@Override
	public boolean addObserver(Observer o) {
		return obs.add(o);
	}

	@Override
	public boolean removeObserver(Observer o) {
		return obs.remove(o);
	}

	@Override
	public int notifyAllObservers(Observable that, Object o) {
		for(Observer ob : obs) {
			ob.update(that, o);
		}
		return 0;
	}
}
