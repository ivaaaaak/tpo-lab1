package org.example.domain;

import lombok.Data;

@Data
public class Fish {

    private Human currentOwner;
    private FishLocation location;
    private long usesNum;

    public Fish(Human human) {
        currentOwner = human;
        currentOwner.incrementFishesNum();
        location = FishLocation.POCKET;
        usesNum = 0;
    }

    public void setLocationAndOwner(FishLocation location, Human human) throws IllegalArgumentException {

        if (!currentOwner.equals(human)) {
            currentOwner.decrementFishesNum();
            human.incrementFishesNum();
        }

        if (this.location == FishLocation.EAR) {
            currentOwner.setAbleToSpeak(false);
        }

        if (location == FishLocation.EAR) {
            if (human.isAbleToSpeak()) {
                throw new IllegalArgumentException ("This human already has fish in their ear");
            }
            human.setAbleToSpeak(true);
            usesNum++;
        }

        this.location = location;
        currentOwner = human;
    }
}
