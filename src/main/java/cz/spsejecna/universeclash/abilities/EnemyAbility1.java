package cz.spsejecna.universeclash.abilities;

import cz.spsejecna.universeclash.Fight;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class EnemyAbility1 {
    private final EffectRepository effect = new EffectRepository();
    private final Fight targetPicker = new Fight();
    private final Random rand = new Random();

    /**
     * Executes the first ability of an enemy character. The specific effect of the ability
     * is determined by the character's name and may involve SP consumption,
     * modifying HP, applying or removing effects, or targeting allies or enemies randomly.
     * Before applying damage or healing, it checks for "Strong", "Nullify", and "Tough" effects
     * on the caster and target, respectively, to adjust the outcome.
     *
     * @param playAs The enemy character attempting to use this ability.
     * @param enemyTeam An `ArrayList` of `Character` objects representing the enemy team.
     * @param yourTeam An `ArrayList` of `Character` objects representing the player's team.
     */
    public void useAbility(Character playAs, ArrayList<Character> enemyTeam, ArrayList<Character> yourTeam){
        Character target;
        switch (playAs.getName()){
            case "Default":
                // Deals 3 damage to a random enemy. Damage is increased by "Strong" effect on self
                // and reduced by "Tough" or nullified by "Nullify" on target. Consumes 1 SP.
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly chooses target from enemy team
                    int targetHPChange = -3;
                    for (Effect e : playAs.getEffects()) {
                        if (Objects.equals(e.getName(), "Strong")) {
                            targetHPChange -= 1; // Increase damage if "Strong"
                            break;
                        }
                    }
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Nullify")) {
                            targetHPChange = 0; // Nullify prevents damage
                            break;
                        } else if (Objects.equals(e.getName(), "Tough")) {
                            targetHPChange += 1; // Tough reduces damage
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Leafy":
                // Heals a random ally for 2 HP and removes all effects from them. Consumes 2 SP.
                if (playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getRandomTarget(yourTeam); // Randomly chooses target from player's team
                    int targetHPChange = 2;

                    // Note: The original code iterated through effects and removed them.
                    // If an effect named "Bleed" was found, healing was nullified,
                    // but the loop continued and removed other effects.
                    // This version removes all effects on the target.
                    for (int i = target.getEffects().size() - 1; i >= 0; i--) {
                        Effect e = target.getEffects().get(i);
                        if (Objects.equals(e.getName(), "Bleed")) {
                            targetHPChange = 0; // Bleeding targets cannot be healed by this ability
                        }
                        target.getEffects().remove(i); // Remove all effects
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Doombringer":
                // Deals 3 damage to a random enemy and applies "Stun" for 1 turn.
                // Damage is affected by "Strong", "Nullify", and "Tough". Consumes 2 SP.
                if (playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly chooses target from enemy team
                    int targetHPChange = -3;
                    for (Effect e : playAs.getEffects()) {
                        if (Objects.equals(e.getName(), "Strong")) {
                            targetHPChange -= 1; // Increase damage if "Strong"
                            break;
                        }
                    }
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Nullify")) {
                            targetHPChange = 0; // Nullify prevents damage
                            break;
                        } else if (Objects.equals(e.getName(), "Tough")) {
                            targetHPChange += 1; // Tough reduces damage
                        }
                    }
                    target.changeHP(targetHPChange);
                    target.getEffects().add(effect.obtainEffect("Stun").increaseDuration(1).copy());
                }
                break;
            case "Cyan":
                // Deals 1 damage to all enemies with a 50% chance to apply "Bleed" for 2 turns.
                // Damage is affected by "Strong", "Nullify", and "Tough". Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    for (Character ch : enemyTeam){ // Iterates through all enemies
                        int targetHPChange = -1;
                        for (Effect e : playAs.getEffects()) {
                            if (Objects.equals(e.getName(), "Strong")) {
                                targetHPChange -= 1; // Increase damage if "Strong"
                                break;
                            }
                        }
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Nullify")) {
                                targetHPChange = 0; // Nullify prevents damage
                                break;
                            } else if (Objects.equals(e.getName(), "Tough")) {
                                targetHPChange += 1; // Tough reduces damage
                            }
                        }
                        ch.changeHP(targetHPChange);
                        if(rand.nextBoolean()){ // 50% chance to apply "Bleed"
                            ch.getEffects().add(effect.obtainEffect("Bleed").increaseDuration(2).copy());
                        }
                    }
                }
                break;
            case "Jane_Doe":
                // Heals a random ally for 5 HP, unless they are bleeding. Consumes 2 SP.
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getRandomTarget(yourTeam); // Randomly chooses target from player's team
                    int targetHPChange = 5;
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Bleed")) {
                            targetHPChange = 0; // Bleeding targets cannot be healed by this ability
                            break;
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Onyx":
                // Applies "Fire" effect to a random enemy for 3 turns. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly chooses target from enemy team
                    target.getEffects().add(effect.obtainEffect("Fire").increaseDuration(3).copy());
                }
                break;
            case "Viper":
                // Deals 1 damage to a random enemy and applies "Poison" for 2 turns.
                // Damage is affected by "Strong", "Nullify", and "Tough". Consumes 1 SP.
                if(playAs.getCurrentSP()>=1) {
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly chooses target from enemy team
                    int targetHPChange = -1;
                    for (Effect e : playAs.getEffects()) {
                        if (Objects.equals(e.getName(), "Strong")) {
                            targetHPChange -= 1; // Increase damage if "Strong"
                            break;
                        }
                    }
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Nullify")) {
                            targetHPChange = 0; // Nullify prevents damage
                            break;
                        } else if (Objects.equals(e.getName(), "Tough")) {
                            targetHPChange += 1; // Tough reduces damage
                        }
                    }
                    target.changeHP(targetHPChange);
                    target.getEffects().add(effect.obtainEffect("Poison").increaseDuration(2).copy());
                }
                break;
            case "007n7":
                // Deals 1 damage to all enemies. Damage is affected by "Strong", "Nullify", and "Tough". Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    for (Character ch : enemyTeam) { // Iterates through all enemies
                        int targetHPChange = -1;
                        for (Effect e : playAs.getEffects()) {
                            if (Objects.equals(e.getName(), "Strong")) {
                                targetHPChange -= 1;
                                break;
                            }
                        }
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Nullify")) {
                                targetHPChange = 0;
                                break;
                            } else if (Objects.equals(e.getName(), "Tough")) {
                                targetHPChange += 1;
                            }
                        }
                        ch.changeHP(targetHPChange);
                    }
                }
                break;
            case "Tasque":
                // Applies a random negative effect ("Fire", "Poison", "Stun", or "Bleed") to a random enemy for 2 turns. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly chooses target from enemy team
                    switch (rand.nextInt(4)){ // Randomly apply one of four effects
                        case 0:
                            target.getEffects().add(effect.obtainEffect("Fire").increaseDuration(2).copy());
                            break;
                        case 1:
                            target.getEffects().add(effect.obtainEffect("Poison").increaseDuration(2).copy());
                            break;
                        case 2:
                            target.getEffects().add(effect.obtainEffect("Stun").increaseDuration(2).copy());
                            break;
                        case 3:
                            target.getEffects().add(effect.obtainEffect("Bleed").increaseDuration(2).copy());
                            break;
                    }
                }
                break;
            case "Isaac":
                // Applies "Tough" and "Strong" effects to self for 2 turns. Consumes 2 SP.
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    playAs.getEffects().add(effect.obtainEffect("Tough").increaseDuration(2).copy());
                    playAs.getEffects().add(effect.obtainEffect("Strong").increaseDuration(2).copy());
                }
                break;
            case "John_Doe":
                // Deals 1 damage to all characters (enemies and allies).
                // Damage is affected by "Strong", "Nullify", and "Tough". Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    // No practical use for effectsOnYou in the original code, removed for clarity.
                    // Iterate through all enemies
                    for (Character ch : enemyTeam){
                        int targetHPChange = -1;
                        for (Effect e : playAs.getEffects()) {
                            if (Objects.equals(e.getName(), "Strong")) {
                                targetHPChange -= 1;
                                break;
                            }
                        }
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Nullify")) {
                                targetHPChange = 0;
                                break;
                            } else if (Objects.equals(e.getName(), "Tough")) {
                                targetHPChange += 1;
                            }
                        }
                        ch.changeHP(targetHPChange);
                    }
                    // Iterate through all allies
                    for (Character ch : yourTeam){
                        int targetHPChange = -1;
                        for (Effect e : playAs.getEffects()) {
                            if (Objects.equals(e.getName(), "Strong")) {
                                targetHPChange -= 1;
                                break;
                            }
                        }
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Nullify")) {
                                targetHPChange = 0;
                                break;
                            } else if (Objects.equals(e.getName(), "Tough")) {
                                targetHPChange += 1;
                            }
                        }
                        ch.changeHP(targetHPChange);
                    }
                }
                break;
            case "Flutter":
                // Applies "Dodgy" effect to a random ally for 2 turns. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(yourTeam); // Randomly chooses target from player's team
                    target.getEffects().add(effect.obtainEffect("Dodgy").increaseDuration(2).copy());
                }
                break;
            case "Chance":
                // Randomly sets self's maximum HP to a value between 3 and 35 (inclusive). Consumes 1 SP.
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    playAs.setMaxHP(rand.nextInt(33)+3); // Sets max HP to a random value from 3 to 35
                }
                break;
        }
    }
}