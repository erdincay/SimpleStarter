package cs320.model;

import java.util.Collections;
import java.util.LinkedList;

public abstract class RewardsC extends LinkedList<RewardD> {

    private static final long serialVersionUID = 5178743869134443855L;

    public RewardD getRewardByAmount(int amount) {
        int i = 0;
        for (RewardD rd : this) {
            if (amount == rd.getpAmount()) {
                return rd;
            } else if (amount < rd.getpAmount()) {
                if (i > 0) {
                    return this.get(i - 1);
                } else {
                    break;
                }
            }
            i++;
        }
        return null;
    }

    protected void sortByAmount() {
        Collections.sort(this);
    }

    public RewardsC generateRewards() {
        sortByAmount();
        return this;
    }

    public abstract void saveReward(RewardD rd);
}
