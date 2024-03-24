package org.example;

import org.example.domain.Fish;
import org.example.domain.FishLocation;
import org.example.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {

    private Human h1;
    private Human h2;
    private Human h3;
    private Fish f1;
    private Fish f2;

    @BeforeEach
    public void init() {
        h1 = new Human("Ivan", 10);
        h2 = new Human("Masha", 5);
        h3 = new Human("Pasha", 2);

        f1 = new Fish(h1);
        f2 = new Fish(h1);
        new Fish(h2);
        new Fish(h3);
        new Fish(h3);
    }

    @Test
    public void testInit() {
        assertEquals(2, h1.getFishesNum());
        assertEquals(1, h2.getFishesNum());

        assertThrows(IllegalStateException.class, () -> new Fish(h3));
        assertEquals(2, h3.getFishesNum());
    }

    @Test
    public void testDecrementFishesNum() {
        h1.decrementFishesNum();
        h1.decrementFishesNum();
        assertEquals(0, h1.getFishesNum());
        assertThrows(IllegalStateException.class, () -> h1.decrementFishesNum());
    }

    @Test
    public void testSetLocationAndOwner() {
        f1.setLocationAndOwner(FishLocation.HANDS, f1.getCurrentOwner());
        assertEquals(FishLocation.HANDS, f1.getLocation());

        f1.setLocationAndOwner(FishLocation.EAR, f1.getCurrentOwner());
        assertEquals(FishLocation.EAR, f1.getLocation());
        assertEquals(1, f1.getUsesNum());
        assertTrue(f1.getCurrentOwner().isAbleToSpeak());

        f1.setLocationAndOwner(FishLocation.EAR, h2);
        assertEquals(2, f1.getUsesNum());
        assertEquals(h2, f1.getCurrentOwner());
        assertEquals(2, f1.getCurrentOwner().getFishesNum());
        assertTrue(f1.getCurrentOwner().isAbleToSpeak());

        f1.setLocationAndOwner(FishLocation.POCKET, f1.getCurrentOwner());
        assertEquals(FishLocation.POCKET, f1.getLocation());
        assertFalse(f1.getCurrentOwner().isAbleToSpeak());

        assertThrows(IllegalStateException.class, () -> f1.setLocationAndOwner(FishLocation.HANDS, h3));
        assertEquals(2, h3.getFishesNum());
    }

    @Test
    public void testAlreadySpeaking() {
        f1.setLocationAndOwner(FishLocation.EAR, f1.getCurrentOwner());
        assertThrows(IllegalArgumentException.class, () -> f2.setLocationAndOwner(FishLocation.EAR, f1.getCurrentOwner()));
    }

    @Test
    public void testTakeFishInHandsFromEarOrPocket() {
        assertFalse(h2.takeFishInHands(f1));
        assertEquals(h1, f1.getCurrentOwner());
        assertEquals(FishLocation.POCKET, f1.getLocation());

        assertFalse(h3.takeFishInHands(f1));
        assertEquals(h1, f1.getCurrentOwner());
        assertEquals(FishLocation.POCKET, f1.getLocation());

        f1.setLocationAndOwner(FishLocation.EAR, f1.getCurrentOwner());

        assertFalse(h2.takeFishInHands(f1));
        assertEquals(h1, f1.getCurrentOwner());
        assertEquals(FishLocation.EAR, f1.getLocation());

        assertFalse(h3.takeFishInHands(f1));
        assertEquals(h1, f1.getCurrentOwner());
        assertEquals(FishLocation.EAR, f1.getLocation());

        assertTrue(h1.takeFishInHands(f1));
        assertEquals(FishLocation.HANDS, f1.getLocation());
    }

    @Test
    public void testTakeFishInHandsFromHands() {
        f1.setLocationAndOwner(FishLocation.HANDS, f1.getCurrentOwner());

        assertTrue(h1.takeFishInHands(f1));
        assertEquals(FishLocation.HANDS, f1.getLocation());
        assertEquals(h1, f1.getCurrentOwner());

        assertTrue(h2.takeFishInHands(f1));
        assertEquals(FishLocation.HANDS, f1.getLocation());
        assertEquals(h2, f1.getCurrentOwner());
    }

    @Test
    public void putFishInEarOrPocketNotOwner() {
        assertThrows(IllegalArgumentException.class, () -> h2.putFishInEarOrPocket(h1, f1, FishLocation.EAR));
        assertThrows(IllegalArgumentException.class, () -> h2.putFishInEarOrPocket(h1, f1, FishLocation.POCKET));
    }

    @Test
    public void putFishInEarOrPocketWrongLocation() {
        assertThrows(IllegalArgumentException.class, () -> h1.putFishInEarOrPocket(h2, f1, FishLocation.HANDS));
    }

    @Test
    public void putFishInEarOrPocketOwner() {
        assertThrows(IllegalStateException.class, () -> h1.putFishInEarOrPocket(h3, f1, FishLocation.EAR));

        h1.putFishInEarOrPocket(h2, f1, FishLocation.POCKET);

        assertEquals(h2, f1.getCurrentOwner());
        assertEquals(FishLocation.POCKET, f1.getLocation());

        h2.putFishInEarOrPocket(h2, f1, FishLocation.EAR);

        assertEquals(h2, f1.getCurrentOwner());
        assertEquals(FishLocation.EAR, f1.getLocation());
        assertTrue(h2.isAbleToSpeak());

        h2.putFishInEarOrPocket(h1, f1, FishLocation.EAR);

        assertEquals(h1, f1.getCurrentOwner());
        assertEquals(FishLocation.EAR, f1.getLocation());
        assertEquals(2, f1.getUsesNum());

        assertTrue(h1.isAbleToSpeak());
        assertFalse(h2.isAbleToSpeak());
    }

    @Test
    public void testSpeakNonNativeLanguage() {
        assertEquals(h1.getName() + " is talking complete nonsense", h1.speakNonNativeLanguage());
        h1.setAbleToSpeak(true);
        assertEquals(h1.getName() + " says: 'Hello friends!'", h1.speakNonNativeLanguage());
    }
}
