package org.example.domain;

import lombok.Data;

import java.util.Locale;


@Data
public class Human {

    private String name;
    private boolean ableToSpeak;
    private final long maxFishesNum;
    private long fishesNum;

    public Human(String name, long maxFishesNum) {
        this.name = name;
        this.ableToSpeak = false;
        this.maxFishesNum = maxFishesNum;
        this.fishesNum = 0;
    }

    public void incrementFishesNum() throws IllegalStateException {
        if (fishesNum == maxFishesNum) {
            throw new IllegalStateException("The number of fishes cannot be more than " + maxFishesNum);
        }
        this.fishesNum++;
    }

    public void decrementFishesNum() throws IllegalStateException {
        if (fishesNum == 0) {
            throw new IllegalStateException("The number of fishes cannot be negative");
        }
        this.fishesNum--;
    }

    public boolean takeFishInHands(Fish fish) {
        boolean isOwner = fish.getCurrentOwner().equals(this);

        switch (fish.getLocation()) {

            case EAR:
            case POCKET:
                if (isOwner) {
                    fish.setLocationAndOwner(FishLocation.HANDS, this);
                }
                return isOwner;

            case HANDS:
                fish.setLocationAndOwner(FishLocation.HANDS, this);
                return true;

            default:
                return false;
        }
    }

    public void putFishInEarOrPocket(Human human, Fish fish, FishLocation location) throws IllegalArgumentException {

        if (!fish.getCurrentOwner().equals(this)) {
            throw new IllegalArgumentException("This fish doesn't belong to a person who tries to put it in a ear");
        }

        if (location == FishLocation.HANDS) {
            throw new IllegalArgumentException("Location can be only pocket or ear");
        }

        switch (fish.getLocation()) {

            case HANDS:
                fish.setLocationAndOwner(location, human);
                break;

            case POCKET:
            case EAR:
                if (takeFishInHands(fish)) {
                    fish.setLocationAndOwner(location, human);
                }
                break;
        }
    }

    public String speakNonNativeLanguage() {
        if (ableToSpeak) {
            return this.name + " says: 'Hello friends!'";
        }
        return this.name + " is talking complete nonsense";
    }
}
