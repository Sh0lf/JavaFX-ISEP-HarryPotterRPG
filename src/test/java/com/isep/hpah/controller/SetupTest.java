package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.spells.AbstractSpell;
import com.isep.hpah.model.constructors.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetupTest {

    Setup stp = new Setup();

    @Test
    public void testGenerateRandomCore() {
        // Test that generateRandomCore returns a valid output
        Core core = stp.generateRandomCore();
        assertNotNull(core);

        // Test that generateRandomCore returns a valid enum value
        boolean isValid = false;
        for (Core value : Core.values()) {
            if (value == core) {
                isValid = true;
                break;
            }
        }
        assertTrue(isValid);
    }

    @Test
    public void testGenerateRandomPet() {
        // Test that generateRandomPet returns a valid output
        Pet pet = stp.generateRandomPet();
        assertNotNull(pet);

        // Test that generateRandomPet returns a valid enum value
        boolean isValid = false;
        for (Pet value : Pet.values()) {
            if (value == pet) {
                isValid = true;
                break;
            }
        }
        assertTrue(isValid);
    }

    @Test
    public void testRandomness() {
        // Test that the probability distribution of the returned values is roughly uniform
        int numIterations = 100000;
        Map<Core, Integer> coreCounts = new HashMap<>();
        Map<Pet, Integer> petCounts = new HashMap<>();

        // Initialize the counts to 0
        for (Core core : Core.values()) {
            coreCounts.put(core, 0);
        }
        for (Pet pet : Pet.values()) {
            petCounts.put(pet, 0);
        }

        // Call the functions multiple times and count the returned values
        for (int i = 0; i < numIterations; i++) {
            Core core = stp.generateRandomCore();
            Pet pet = stp.generateRandomPet();
            coreCounts.put(core, coreCounts.get(core) + 1);
            petCounts.put(pet, petCounts.get(pet) + 1);
        }

        // Verify that the counts are roughly equal for each enum value
        int expectedCount = numIterations / Core.values().length;
        for (Core core : Core.values()) {
            assertTrue(Math.abs(coreCounts.get(core) - expectedCount) < 0.05 * expectedCount);
        }
        expectedCount = numIterations / Pet.values().length;
        for (Pet pet : Pet.values()) {
            assertTrue(Math.abs(petCounts.get(pet) - expectedCount) < 0.05 * expectedCount);
        }
    }

    @Test
    public void testStartingSpellList() {
        List<AbstractSpell> knownSpells = stp.startingSpellList();
        List<AbstractSpell> expectedSpells = Arrays.asList(stp.getFireball(), stp.getLightningBolt(), stp.getWingardiumLeviosa());

        assertEquals(expectedSpells.size(), knownSpells.size());
        assertEquals(expectedSpells.get(0), knownSpells.get(0));
        assertEquals(expectedSpells.get(1), knownSpells.get(1));
        assertEquals(expectedSpells.get(2), knownSpells.get(2));
    }

    @Test
    public void testAllObtainableSpells() {
        List<AbstractSpell> expected = new ArrayList<>();
        expected.add(stp.getAccio());
        expected.add(stp.getExpectoPatronum());
        expected.add(stp.getSectumsempra());
        expected.add(stp.getExpelliarmus());
        expected.add(stp.getAvadaKedavra());

        List<AbstractSpell> actual = stp.allObtainableSpells();

        assertEquals(expected, actual);
    }

    @Test
    public void testVoldemortSpells() {
        List<AbstractSpell> expected = new ArrayList<>();
        expected.add(stp.getAvadaKedavra());
        expected.add(stp.getFireball());
        expected.add(stp.getLightningBolt());

        List<AbstractSpell> actual = stp.voldemortSpells();

        assertEquals(expected, actual);
    }

    @Test
    public void testPettigrowSpells() {
        List<AbstractSpell> expected = new ArrayList<>();
        expected.add(stp.getFireball());
        expected.add(stp.getLightningBolt());

        List<AbstractSpell> actual = stp.pettigrowSpells();

        assertEquals(expected, actual);
    }

    @Test
    public void testOmbrageSpells() {
        List<AbstractSpell> expected = new ArrayList<>();
        expected.add(stp.getFireball());
        expected.add(stp.getLightningBolt());

        List<AbstractSpell> actual = stp.ombrageSpells();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon1() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getTroll());

        List<Character> actual = stp.dungeon1();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon2() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getBasilisk());

        List<Character> actual = stp.dungeon2();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon3() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getDementor());
        expected.add(stp.getDementor());
        expected.add(stp.getDementor());
        expected.add(stp.getDementor());

        List<Character> actual = stp.dungeon3();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon4() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getVoldemort());
        expected.add(stp.getPettigrow());

        List<Character> actual = stp.dungeon4();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon5() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getUmbridge());

        List<Character> actual = stp.dungeon5();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon6() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getDeatheater());
        expected.add(stp.getDeatheater());
        expected.add(stp.getDeatheater());
        expected.add(stp.getDeatheater());

        List<Character> actual = stp.dungeon6();

        assertEquals(expected, actual);
    }

    @Test
    public void testDungeon7() {
        List<Character> expected = new ArrayList<>();
        expected.add(stp.getVoldemort());
        expected.add(stp.getBellatrix());

        List<Character> actual = stp.dungeon7();

        assertEquals(expected, actual);
    }

    @Test
    public void testAllDungeon(){
        //ArrayList creation for better organisation
        List<Dungeon> expected = new ArrayList<>();
        //Dungeons adding to list
        expected.add(stp.getPhilosopherStone());
        expected.add(stp.getChamberofSecrets());
        expected.add(stp.getPrisonnerofAzkaban());
        expected.add(stp.getGobletofFire());
        expected.add(stp.getOrderofPhoenix());
        expected.add(stp.getHalfBloodedPrince());
        expected.add(stp.getDeathlyHallows());

        List<Dungeon> actual = stp.allDungeon();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmpty() {
        List<Potion> potions = stp.empty();
        assertTrue(potions.isEmpty());
    }

    @Test
    public void testEnemyPots() {
        List<Potion> expected = new ArrayList<>();
        expected.add(stp.getMediumHealthPotion());
        expected.add(stp.getMediumDefPotion());
        expected.add(stp.getMediumDexPotion());

        List<Potion> actual = stp.enemyPots();

        assertEquals(expected, actual);
    }

    @Test
    public void testAllPotions() {
        List<Potion> expected = Arrays.asList(
                stp.getSmallHealthPotion(),
                stp.getMediumHealthPotion(),
                stp.getLargeHealthPotion(),
                stp.getSmallDefPotion(),
                stp.getMediumDefPotion(),
                stp.getLargeDefPotion(),
                stp.getSmallDexPotion(),
                stp.getMediumDexPotion(),
                stp.getLargeDexPotion()
        );

        List<Potion> actual = stp.allPotions();

        assertEquals(expected, actual);
    }

    @Test
    public void testPoss() {
        List<String> expected = Arrays.asList(
                "Attack",
                "Defend",
                "Use spells",
                "Use potions"
        );

        List<String> actual = stp.poss();

        assertEquals(expected, actual);
    }
}
