package cs320.model;

import java.util.ArrayList;
import java.util.List;

import cs320.pattern.Observable;
import cs320.pattern.Observer;

public class RewardsXmlC extends RewardsC implements Observable {

    private static final long serialVersionUID = -1982516790936318271L;

    private List<Observer> obs;

    public RewardsXmlC() {
        obs = new ArrayList<Observer>();
    }

    public void saveReward(RewardD rd) {
        this.add(rd);
        notifyAllObservers(this, event.SAVE);
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
        for (Observer ob : obs) {
            ob.update(that, o);
        }
        return 0;
    }
}
