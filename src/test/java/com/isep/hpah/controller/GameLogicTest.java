package com.isep.hpah.controller;

import com.isep.hpah.model.constructors.*;
import com.isep.hpah.model.constructors.character.*;
import com.isep.hpah.model.constructors.character.Character;
import com.isep.hpah.model.constructors.spells.*;
import com.isep.hpah.views.console.DungeonOutput;
import com.isep.hpah.views.console.SafeScanner;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    private final GameLogic gameLogic = new GameLogic();
    private final GameEngine gameEngine = new GameEngine();
    private final AllSpellsFunction spfnc = new AllSpellsFunction();
    private final AllPotionsFunction popofnc = new AllPotionsFunction();
    private final Setup stp = new Setup();
    private final SafeScanner sc = new SafeScanner(System.in);
    private final DungeonOutput dngout = new DungeonOutput();

    private final Wizard player = Wizard.builder()
            .name("Test player")
            .desc("Test description")
            .health(100)
            .exp(0)
            .att(15)
            .def(10)
            .dex(10)
            .level(1)
            .wand(new Wand("Test wand", 10, Core.PHOENIX_FEATHER))
            .pet(Pet.CAT)
            .house(House.GRYFFINDOR)
            .knownSpells(new ArrayList<>())
            .potionsOwned(new ArrayList<>())
            .corruptionGauge(50)
            .maxMana(100)
            .mana(100)
            .typeGame("console")
            .build();

    private final Boss basilisk = Boss.builder()
            .name("Basilisk")
            .desc("Huge poisoned snake, that can kill you instantly !")
            .health(200)
            .exp(50)
            .att(40)
            .def(10)
            .dex(5)
            .build();

    private Wizard voldemort = Wizard.builder()
            .name("Lord Voldemort")
            .desc("\"You - Know - Who\", \"He Who Must " +
                    "Not Be Named\", or \"the Dark Lord\", one of the brightest Hogwarts have ever seen, but he's obsessed with " +
                    "blood purity and he won't stop at anything to achieve it !")
            .health(1000)
            .exp(50)
            .att(50)
            .def(10)
            .dex(-50)
            .level(100)
            .wand(new Wand("Tom Jedusor's wand", 33.75, Core.PHOENIX_FEATHER))
            .pet(Pet.CAT)
            .house(House.SLYTHERIN)
            .knownSpells(new ArrayList<>())
            .potionsOwned(new ArrayList<>())
            .corruptionGauge(0)
            .maxMana(300)
            .mana(300)
            .build();

    private final Spell dmgSpell = Spell.builder()
            .name("dmgSpell")
            .num(20)
            .desc("Test dmg SPell")
            .level(0)
            .cooldown(5)
            .mana(80)
            .type("DMG")
            .build();

    private final List<Character> enemies = new ArrayList<>();

    @Test
    public void testCorruptionCheckWithCorruptedPlayer() {
        // Arrange
        player.setCorruptionGauge(100);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        gameLogic.corruptionCheck(player);

        // Assert
        assertEquals(0, player.getHealth());
        assertTrue(outContent.toString().contains("You've casted too many forbidden spells and you're now consumed by the darkness"));
        assertTrue(outContent.toString().contains("You have been defeated."));
        player.setCorruptionGauge(50);
        player.setHealth(100);
    }

    @Test
    public void testCorruptionCheckWithNonCorruptedPlayer() {
        // Arrange
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        gameLogic.corruptionCheck(player);

        // Assert
        assertEquals(100, player.getHealth());
        assertFalse(outContent.toString().contains("You've casted too many forbidden spells and you're now consumed by the darkness"));
        assertFalse(outContent.toString().contains("You have been defeated."));
    }

    @Test
    public void testCheckUmbridgeWinCon() {
        // create a new List of Characters with Umbridge as the first enemy
        List<Character> enemies = Arrays.asList(stp.getUmbridge());

        // redirect standard output to a stream we can check
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // run the method with round 10
        DungeonOutput dngout = new DungeonOutput();
        gameLogic.checkUmbridgeWinCon(player, enemies, 10);

        // check that the expected message was printed
        assertEquals("You survived long enough against Umbridge and you succeeded in tempoying her long enough to win !\n", outContent.toString());

        // check that Umbridge's health was set to 0
        assertEquals(0, enemies.get(0).getHealth());
    }

    @Test
    void testReducingCorruption() {
        gameLogic.reducingCorruption(player);
        assertEquals(45, player.getCorruptionGauge());
        player.setCorruptionGauge(50);
    }

    @Test
    void testBasicChoice3DEF() {
        AbstractSpell spell = new Spell("Protego", 10, "Test", 1, 0, 0, "DEF");
        assertTrue(gameLogic.basicChoice3(spell, player, enemies));
    }

    @Test
    void testChoice4PotionsEmpty() {
        assertFalse(gameLogic.choice4(player.getPotionsOwned(), player));
    }

    @Test
    void testGryffindorSword() {
        enemies.add(basilisk);
        gameLogic.gryffindorSword(player, enemies);
        assertEquals(30, player.getAtt());
        enemies.remove(basilisk);
    }

    @Test
    void testCheckVoldemortCoreRand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        enemies.add(voldemort);
        gameLogic.checkVoldemortCore(player, enemies);

        if (player.getHealth() == 80 && player.getDef() == 10 && player.getDex() == 10){
            assertTrue(outContent.toString().contains("Having the same wand core as Voldemort, you suffered some damage and took 20 health !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !"));
        }

        else if (player.getDef() == 0 && player.getPotionDefBoost() == -10){
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you suffered some damage and took 20 health !"));
            assertTrue(outContent.toString().contains("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !"));

        }

        else if (player.getDex() == 5 && player.getPotionDexBoost() == -5){
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you suffered some damage and took 20 health !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !"));
            assertTrue(outContent.toString().contains("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !"));
        } else {
            assertEquals(100, player.getHealth());
            assertEquals(0, player.getPotionDefBoost());
            assertEquals(0, player.getPotionDexBoost());
            assertEquals(10, player.getDef());
            assertEquals(10, player.getDex());

            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you suffered some damage and took 20 health !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel weak and loss 10 def for this battle !"));
            assertFalse(outContent.toString().contains("Having the same wand core as Voldemort, you feel slowed by a force and loss 5 dexterity for this battle !"));
        }

        enemies.remove(voldemort);
        player.setHealth(100);
        player.setPotionDefBoost(0);
        player.setDef(10);
        player.setPotionDexBoost(0);
        player.setDex(10);
    }

    @Test
    void testCheckDefBoost() {
        player.setDefSpell(80);
        player.setDef(player.getDef() + player.getDefSpell());
        gameLogic.checkDefBoost(player);
        assertEquals(0, player.getDefSpell());
        assertEquals(10, player.getDef());
    }

    @Test
    void testAttackingTrue() {
        enemies.add(basilisk);

        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        SafeScanner sc = new SafeScanner(in);

        // Test when target index is within range
        boolean result = gameLogic.attacking(enemies, player, sc);

        assertTrue(result);
        assertEquals(195, enemies.get(0).getHealth());

        enemies.remove(0);
    }

    @Test
    void testAttackingFalseBack(){
        enemies.add(basilisk);

        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        SafeScanner sc = new SafeScanner(in);

        // Test when target index is within range
        boolean result = gameLogic.attacking(enemies, player, sc);

        assertFalse(result);
        assertEquals(200, enemies.get(0).getHealth());

        enemies.remove(0);
    }

    @Test
    void testBasicChoice3DMG() {

        List<AbstractSpell> spells = player.getKnownSpells();
        enemies.add(basilisk);

        for (int i=0; i <= spells.size(); i++){
            spells.remove(i);
        }

        spells.add(dmgSpell);

        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        SafeScanner sc = new SafeScanner(in);

        int spellIndex = gameLogic.chooseSpell(player, spells, sc);

        AbstractSpell spell = spells.get(spellIndex);

        gameLogic.basicChoice3(spell, player, enemies);

    }

    @Test
    void processDmgSpell() {
    }

    @Test
    void processDefSpell() {
    }

    @Test
    void processUtlSpell() {
    }

    @Test
    void selectPotion() {
    }

    @Test
    void allyDeathEater() {
    }

    @Test
    void endDungeon() {
    }

    @Test
    void removeGryffindorSword() {
    }

    @Test
    void checkLevelUp() {
    }

    @Test
    void giveNewSpell() {
    }

    @Test
    void giveNewPotions() {
    }

    @Test
    void checkPotionBoost() {
    }

    @Test
    void corruptionHalf() {
    }
}
