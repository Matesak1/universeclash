package cz.spsejecna.universeclash;
import cz.spsejecna.universeclash.abilities.AbilityUseOne;
import cz.spsejecna.universeclash.abilities.AbilityUseTwo;
import cz.spsejecna.universeclash.abilities.EnemyAbility1;
import cz.spsejecna.universeclash.abilities.EnemyAbility2;
import cz.spsejecna.universeclash.cards.Card;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.cardManager.CardController;
import cz.spsejecna.universeclash.cardManager.CardPicker;
import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;
import cz.spsejecna.universeclash.items.Item;
import cz.spsejecna.universeclash.items.ItemController;
import cz.spsejecna.universeclash.items.ItemRepository;
import cz.spsejecna.universeclash.partyManager.PartyController;
import cz.spsejecna.universeclash.partyManager.PartyPicker;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class Events {
    private int coins = 0; //The games currency
    private boolean lms = false; // lms stands for Last Man Standing
    private int turn = 0; //turn count
    private int fifteenthTurn = 15; // every 15 turns, something can happen
    private int tenthTurn = 10; // every 10 turns, something can happen
    private int battle = 0; //battle count, once you hit game over, shows how many battles you lived
    private final Random rand = new Random();
    private final Scanner sc = new Scanner(System.in);
    private final ItemController itemController = new ItemController();
    private final ItemRepository itemRepository = new ItemRepository();
    private final PartyController partyController = new PartyController();
    private final PartyPicker partyPicker = new PartyPicker();
    private final CardController cardController = new CardController();
    private final CardPicker cardPicker = new CardPicker();
    private final EffectRepository effectRepository = new EffectRepository();
    private final Fight fight = new Fight();
    private final AbilityUseOne ability1 = new AbilityUseOne();
    private final AbilityUseTwo ability2 = new AbilityUseTwo();
    private final EnemyAbility1 enemyAbility1 = new EnemyAbility1();
    private final EnemyAbility2 enemyAbility2 = new EnemyAbility2();
    /**
     * Initiates the game by running a sequence of battles and shop interactions.
     * The game starts with three predefined battles, followed by an infinite loop
     * of general battles and shop phases.
     */
    public void gamestart() {
        firstBattle();
        secondBattle();
        thirdBattle();
        while (true) {
            battle();
            shop();
        }
    }
    /**
     * Conducts the first battle of the game.
     * Initializes the battle, adds one new party member and one new enemy.
     * The battle continues in turns until the player's party wins.
     * After the battle, a break time is initiated.
     */
    private void firstBattle() {
        startBattle();
        partyController.addNewMember(partyPicker.generatePicks(partyController.getParty(), true));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        do {
            startTurn();
            fightBattle();
            fightEnemyBattle();
            endTurn();
        } while (!partyController.wonBattle());
        breakTime();
    }
    /**
     * Conducts the second battle of the game.
     * Initializes the battle, adds one new party member and two new enemies.
     * The battle continues in turns until the player's party wins.
     * After the battle, a break time is initiated.
     */
    private void secondBattle() {
        startBattle();
        partyController.addNewMember(partyPicker.generatePicks(partyController.getParty(), true));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        do {
            startTurn();
            fightBattle();
            fightEnemyBattle();
            endTurn();
        } while (!partyController.wonBattle());
        breakTime();
    }
    /**
     * Conducts the third battle of the game.
     * Initializes the battle, adds one new party member and three new enemies.
     * The battle continues in turns until the player's party wins.
     * After the battle, a break time is initiated.
     */
    private void thirdBattle() {
        startBattle();
        partyController.addNewMember(partyPicker.generatePicks(partyController.getParty(), true));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        do {
            startTurn();
            fightBattle();
            fightEnemyBattle();
            endTurn();
        } while (!partyController.wonBattle());
        breakTime();
    }
    /**
     * Conducts a general battle.
     * Initializes the battle, potentially adds a new party member or a card effect if the party is full.
     * Adds four new enemies to the enemy party.
     * The first party member takes 5 damage at the start.
     * The battle continues in turns until the player's party wins.
     * After the battle, a break time is initiated.
     */
    private void battle() {
        startBattle();
        if (partyController.getParty().size() < 4) {
            partyController.addNewMember(partyPicker.generatePicks(partyController.getParty(), true));
        } else {
            cardController.addCard(cardPicker.generatePicks());
            switch (cardController.getCards().getLast().getCardId()) {
                case 0:
                    for (Character ch : partyController.getParty()) {
                        ch.changeMaxHP(1);
                    }
                    break;
                case 1:
                    for (Character ch : partyController.getParty()) {
                        ch.changeMaxHP(-1);
                        ch.changeMaxSP(1);
                    }
                    break;
                case 9:
                    for (Character ch : partyController.getParty()) {
                        ch.changeMaxHP(-1);
                    }
                    coins += 5;
                    break;
                case 5:
                    coins += 2;
                    break;
            }
        }
        for (int i = 0; i < 4; i++) {
            partyController.addNewEnemy(partyPicker.generatePicks(partyController.getEnemyParty(), false));
        }
        partyController.getParty().get(0).changeHP(-5);
        do {
            startTurn();
            fightBattle();
            fightEnemyBattle();
            endTurn();
        } while (!partyController.wonBattle());
        breakTime();

    }
    /**
     * Resets battle-specific variables at the start of a new battle.
     * Sets Last Man Standing status to false, resets turn count, clears enemy party,
     * and resets `fifteenthTurn` and `tenthTurn` counters.
     */
    private void startBattle() {
        lms = false;
        turn = 0;
        partyController.resetEnemies();
        fifteenthTurn = 15;
        tenthTurn = 10;
    }
    /**
     * Executes actions at the beginning of each turn.
     * Prints out the current fight status, then iterates through both player and enemy parties
     * to manage and remove temporary effects, specifically handling 'Pure' effect for debuff removal.
     */
    private void startTurn() {
        partyController.printOutTheFight();
        for (Character ch : partyController.getParty()) {
            for (Effect ef : ch.getEffects()) {
                if (ef.getId() == 4) {
                    ch.removeDebuffs();
                    break;
                }
            }
            ch.getEffects().removeIf(ef -> ef.getDuration() <= 0);
        }
        for (Character ch : partyController.getEnemyParty()) {
            for (Effect ef : ch.getEffects()) {
                if (ef.getId() == 4) {
                    ch.removeDebuffs();
                    break;
                }
            }
            ch.getEffects().removeIf(ef -> ef.getDuration() <= 0);
        }
    }
    /**
     * Executes actions at the end of each turn.
     * Decreases the duration of all active effects for both player and enemy characters,
     * applying damage for 'Fire' and 'Poison' effects.
     * Checks for and applies 'Snowballing' card effect.
     * Checks if any player character is alive and exits the game if not.
     * Increments the turn counter and triggers special events based on turn count (tenth, fifteenth, and third turns).
     * Also checks for Last Man Standing status and applies card effects accordingly.
     * Finally, prompts the user to use an item.
     */
    private void endTurn() {
        for (Character ch : partyController.getParty()) {
            for (Effect ef : ch.getEffects()) {
                ef.decreaseDuration();
                if (Objects.equals(ef.getName(), "Fire")) {
                    ch.changeHP(-1);
                } else if (Objects.equals(ef.getName(), "Poison")) {
                    if (ch.getCurrentHP() >= 2) {
                        ch.changeHP(-1);
                    }
                }
            }
        }
        for (Character ch : partyController.getEnemyParty()) {
            for (Effect ef : ch.getEffects()) {
                ef.decreaseDuration();
                if (Objects.equals(ef.getName(), "Fire")) {
                    ch.changeHP(-1);
                } else if (Objects.equals(ef.getName(), "Poison")) {
                    if (ch.getCurrentHP() >= 2) {
                        ch.changeHP(-1);
                    }
                }
            }
        }
        if (partyController.activateSnowballing()) {
            for (Card c : cardController.getCards()) {
                if (Objects.equals(c.getName(), "Snowballing")) {
                    partyController.getParty().get(rand.nextInt(partyController.getParty().size())).addEffect("Strong", 1);
                }
            }
        }
        if (!partyController.isAnyoneAlive()) {
            System.err.println("GG you survived " + battle + " battles!");
            System.exit(707);
        }
        turn++;
        if (turn == tenthTurn) {
            for (Character ch1 : partyController.getParty()) {
                for (Character ch2 : partyController.getParty()) {
                    if ((Objects.equals(ch1.getName(), "Tasque") || Objects.equals(ch2.getName(), "Tasque"))
                            && (Objects.equals(ch1.getName(), "007n7") || Objects.equals(ch2.getName(), "007n7"))) {
                        for (Character enemy : partyController.getEnemyParty()) {
                            switch (rand.nextInt(4)) {
                                case 0:
                                    enemy.getEffects().add(effectRepository.obtainEffect("Fire").copy().increaseDuration(1));
                                    break;
                                case 1:
                                    enemy.getEffects().add(effectRepository.obtainEffect("Poison").copy().increaseDuration(1));
                                    break;
                                case 2:
                                    enemy.getEffects().add(effectRepository.obtainEffect("Stun").copy().increaseDuration(1));
                                    break;
                                case 3:
                                    enemy.getEffects().add(effectRepository.obtainEffect("Bleed").copy().increaseDuration(1));
                                    break;
                            }
                        }
                    }
                    if ((Objects.equals(ch1.getName(), "Isaac") || Objects.equals(ch2.getName(), "Isaac"))
                            && (Objects.equals(ch1.getName(), "Doombringer") || Objects.equals(ch2.getName(), "Doombringer"))) {
                        for (Character ch : partyController.getParty()){
                            ch.getEffects().add(effectRepository.obtainEffect("Tough").copy().increaseDuration(1));
                        }
                    }
                    if ((Objects.equals(ch1.getName(), "Flutter") || Objects.equals(ch2.getName(), "Flutter"))
                            && (Objects.equals(ch1.getName(), "Leafy") || Objects.equals(ch2.getName(), "Leafy"))){
                        for (Character ch : partyController.getParty()){
                            ch.changeHP(1);
                        }
                    }
                    if ((Objects.equals(ch1.getName(), "Chance") || Objects.equals(ch2.getName(), "Chance"))
                            && (Objects.equals(ch1.getName(), "Default") || Objects.equals(ch2.getName(), "Default"))){
                        if(rand.nextInt(4)==0){
                            for (Character ch: partyController.getParty()){
                                ch.changeSP(1);
                                ch.getEffects().add(effectRepository.obtainEffect("Strong").copy().increaseDuration(1));
                            }
                        }
                    }
                }
                tenthTurn += 10;
            }
            if (turn == fifteenthTurn) {
                for (Character ch : partyController.getParty()) {
                    ch.changeHP(cardController.numberOfRegenerationCards());
                    ch.changeSP(cardController.numberOfRestorationCards());
                }
                fifteenthTurn += 15;
                } else if (turn == 3) {
                    for (Character ch : partyController.getParty()) {
                        ch.changeHP(cardController.numberOfGiftCards());
                    }
                }
                if (partyController.getParty().size() == 1 && !lms) {
                    lms = true;
                    partyController.getParty().get(0).changeHP(cardController.numberOfLastCards());
                }
            }
        useItem();
    }
    /**
     * Provides a "break time" between battles.
     * Heals all player characters to full health and clears all their effects.
     * Prints the full information of each player character, the current cards, and coin count.
     * Increments the battle counter and awards one coin.
     */
    private void breakTime () {
        for (Character ch : partyController.getParty()) {
            ch.maxOut();
            ch.getEffects().clear();
            System.out.println(ch.fullinfo());
        }
        System.out.println(cardController.getCards() + "\nCoins: [" + coins + ']');
        battle++;
        coins++;
    }
    /**
     * Manages the player's turn in a battle.
     * Iterates through each character in the player's party. If a character is not stunned,
     * it prompts the player to choose an attack type (basic attack or ability 1/2).
     * Applies damage or ability effects based on the chosen attack and character effects.
     */
    private void fightBattle () {
        for (Character ch : partyController.getParty()) {
            boolean notStunned = true;
            for (Effect e : ch.getEffects()) {
                if (Objects.equals(e.getName(), "Stun")) {
                    notStunned = false;
                    break;
                }
            }
            if (notStunned) {
                int attackType = fight.play(ch);
                switch (attackType) {
                    case 0:
                        Character target = fight.getTarget(partyController.getEnemyParty());
                        int targetHPChange = -1;
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Strong")) {
                                targetHPChange -= 1;
                                break;
                            }
                        }
                        for (Effect e : target.getEffects()) {
                            if (Objects.equals(e.getName(), "Nullify")) {
                                targetHPChange = 0;
                                break;
                            } else if (Objects.equals(e.getName(), "Tough")) {
                                targetHPChange += 1;
                            }
                        }
                        target.changeHP(targetHPChange);
                        break;
                    case 1:
                        ability1.useAbility(ch, partyController.getParty(), partyController.getEnemyParty());
                        break;
                    case 2:
                        ability2.useAbility(ch, partyController.getParty(), partyController.getEnemyParty());
                        break;
                }
            }
        }
    }
    /**
     * Manages the enemy's turn in a battle.
     * Iterates through each character in the enemy party. If an enemy is not stunned (effect ID 2),
     * it randomly chooses an attack type (basic attack or enemy ability 1/2).
     * Applies damage or ability effects to a random player character, considering active effects on both
     * the attacking enemy and the targeted player character.
     */
    private void fightEnemyBattle () {
        for (Character ch : partyController.getEnemyParty()) {
            ArrayList<Integer> effectsOnYou = new ArrayList<>();
            for (Effect ef : ch.getEffects()) {
                effectsOnYou.add(ef.getId());
            }
            if (!effectsOnYou.contains(2)) {
                int attackType = rand.nextInt(3);
                switch (attackType) {
                    case 0:
                        Character target = fight.getRandomTarget(partyController.getParty());
                        ArrayList<Integer> effectsOnTarget = new ArrayList<>();
                        for (Effect e : target.getEffects()) {
                            effectsOnTarget.add(e.getId());
                        }
                        if (!effectsOnTarget.contains(8) || !effectsOnTarget.contains(7)) {
                            target.changeHP(-1);
                        }
                        if (effectsOnYou.contains(6)) {
                            target.changeHP(-1);
                        }
                        break;
                    case 1:
                        enemyAbility1.useAbility(ch, partyController.getParty(), partyController.getEnemyParty());
                        break;
                    case 2:
                        enemyAbility2.useAbility(ch, partyController.getParty(), partyController.getEnemyParty());
                        break;
                }
            }
        }
    }
    /**
     * Prompts the player to use an item during a battle turn.
     * If the player has items and chooses to use one, it displays the available items,
     * asks for the item ID, and applies the corresponding effect to the party or enemies.
     * Used items are removed from the inventory.
     */
    private void useItem () {
        if (!itemController.getItems().isEmpty()) {
            System.out.println("Wanna use an item?");
            if (Objects.equals(sc.next(), "yes")) {
                System.out.println(itemController.getItems().toString());
                ArrayList<Integer> itemIds = new ArrayList<>();
                for (Item it : itemController.getItems()) {
                    itemIds.add(it.getItemId());
                }
                System.out.println("Type the id of the item you wanna use.");
                int itemId = sc.nextInt();
                if (itemIds.contains(itemId)) {
                    switch (itemId) {
                        case 0:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getParty()) {
                                ch.changeHP(10);
                            }
                            break;
                        case 1:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getParty()) {
                                ch.changeSP(10);
                            }
                            break;
                        case 2:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getEnemyParty()) {
                                ch.getEffects().add(effectRepository.obtainEffect("Fire").copy().increaseDuration(3));
                            }
                            break;
                        case 3:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getEnemyParty()) {
                                ch.getEffects().add(effectRepository.obtainEffect("Poison").copy().increaseDuration(3));
                            }
                            break;
                        case 4:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getEnemyParty()) {
                                ch.changeHP(-3);
                            }
                            break;
                        case 5:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getEnemyParty()) {
                                ch.getEffects().add(effectRepository.obtainEffect("Stun").copy().increaseDuration(1));
                            }
                            break;
                        case 6:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getEnemyParty()) {
                                ch.changeHP(rand.nextInt(5) + 1);
                            }
                            break;
                        case 7:
                            itemController.getItems().remove(itemId);
                            for (Character ch : partyController.getParty()) {
                                ch.getEffects().add(effectRepository.obtainEffect("Pure").copy().increaseDuration(2));
                            }
                            break;
                    }

                }
            }
        }
    }
    /**
     * Allows the player to purchase items from the shop if they have enough coins.
     * Displays available items and their IDs (each costing 5 coins).
     * If a valid item ID is entered and the player has sufficient coins, the item is added to inventory
     * and coins are deducted.
     */
    private void shop(){
        if(coins>=5) {
            System.out.println(itemRepository.getItems().toString());
            System.out.println("Type the id of the item you want, each costs 5 coins.");
            int chosen = sc.nextInt();
            if(chosen>=0&&chosen<=7){
                itemController.addNewItem(chosen);
                coins -= 5;
            }
        }
    }
}

