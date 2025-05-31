package cz.spsejecna.universeclash.abilities;
import cz.spsejecna.universeclash.Fight;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class AbilityUseTwo {

    private final EffectRepository effect = new EffectRepository();
    private final Random rand = new Random();
    private final Fight targetPicker = new Fight();

    /**
     * Executes the second ability of a given character. The effect of the ability
     * varies based on the character's name and may involve SP consumption,
     * modifying HP, applying or removing effects, or targeting allies or enemies.
     * Before applying damage/healing, it checks for "Strong", "Nullify", and "Tough" effects
     * on the caster and target, respectively, to adjust the outcome.
     *
     * @param playAs The character attempting to use this ability.
     * @param yourTeam An `ArrayList` of `Character` objects representing the player's team.
     * @param enemyTeam An `ArrayList` of `Character` objects representing the opposing team.
     */
    public void useAbility(Character playAs, ArrayList<Character> yourTeam, ArrayList<Character> enemyTeam){
        Character target;
        switch (playAs.getName()){
            case "Default":
                // Heals an ally for 3 HP, unless they are bleeding. Consumes 1 SP.
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(yourTeam); // Player chooses target from their team
                    int targetHPChange = 3;
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Bleed")) {
                            targetHPChange = 0; // Bleeding targets cannot be healed by this ability
                            break;
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Leafy":
                // Applies "Nullify" effect to self for 1 turn. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    playAs.getEffects().add(effect.obtainEffect("Nullify").increaseDuration(1).copy());
                }
                break;
            case "Doombringer":
                // Deals heavy damage to a single enemy, affected by "Strong", "Nullify", and "Tough". Consumes 3 SP.
                if (playAs.getCurrentSP()>=3){
                    playAs.changeSP(-3);
                    target = targetPicker.getTarget(enemyTeam); // Player chooses target from enemy team
                    int targetHPChange = -5;
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
            case "Cyan":
                // Applies "Dodgy" effect to self for 3 turns. Consumes 2 SP.
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    playAs.getEffects().add(effect.obtainEffect("Dodgy").increaseDuration(3).copy());
                }
                break;
            case "Jane_Doe":
                // Restores 1 SP to self. No SP cost for this ability.
                playAs.changeSP(1);
                break;
            case "Onyx":
                // Heals all allies for 1 HP for each "Fire" effect on enemies,
                // but only if the ally is not bleeding. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    int targetHPChange = 0;
                    for (Character enemy : enemyTeam){
                        for (Effect e : enemy.getEffects()) {
                            if (Objects.equals(e.getName(), "Fire")) {
                                targetHPChange += 1; // Calculate total healing based on enemy "Fire" effects
                            }
                        }
                    }
                    for(Character ally : yourTeam){
                        // Apply healing to allies not suffering from "Bleed"
                        if(ally.getEffects().stream().noneMatch(e -> Objects.equals(e.getName(), "Bleed"))){
                            ally.changeHP(targetHPChange);
                        }
                    }
                }
                break;
            case "Viper":
                // Deals bonus damage to a single enemy based on "Poison" effects on all enemies. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(enemyTeam); // Player chooses target from enemy team
                    int targetHPChange = 0;
                    for (Character enemy : enemyTeam){
                        for (Effect e : enemy.getEffects()) {
                            if (Objects.equals(e.getName(), "Poison")) {
                                targetHPChange -= 1; // Deals 1 bonus damage for each "Poison" on any enemy
                            }
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "007n7":
                // Heals all allies for 3 HP, unless they are bleeding. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    for (Character ch : yourTeam) {
                        int targetHPChange = 3;
                        for (Effect e : ch.getEffects()) {
                            if (Objects.equals(e.getName(), "Bleed")) {
                                targetHPChange = 0; // Bleeding allies cannot be healed by this ability
                                break;
                            }
                        }
                        ch.changeHP(targetHPChange);
                    }
                }
                break;
            case "Tasque":
                // Applies a random positive effect ("Pure", "Dodgy", "Tough", or "Strong") to an ally for 2 turns. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(yourTeam); // Player chooses target from their team
                    switch (rand.nextInt(4)){ // Randomly apply one of four effects
                        case 0:
                            target.getEffects().add(effect.obtainEffect("Pure").increaseDuration(2).copy());
                            break;
                        case 1:
                            target.getEffects().add(effect.obtainEffect("Dodgy").increaseDuration(2).copy());
                            break;
                        case 2:
                            target.getEffects().add(effect.obtainEffect("Tough").increaseDuration(2).copy());
                            break;
                        case 3:
                            target.getEffects().add(effect.obtainEffect("Strong").increaseDuration(2).copy());
                            break;
                    }
                }
                break;
            case "Isaac":
                // Applies "Tough" effect to an ally for 3 turns. Consumes 1 SP.
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(yourTeam); // Player chooses target from their team
                    target.getEffects().add(effect.obtainEffect("Tough").increaseDuration(3).copy());
                }
                break;
            case "John_Doe":
                // Deals moderate damage to a single enemy, affected by "Strong", "Nullify", and "Tough". Consumes 2 SP.
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getTarget(enemyTeam); // Player chooses target from enemy team
                    int targetHPChange = -4;

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
            case "Flutter":
                // Applies "Nullify" effect to all allies for 1 turn. Consumes 4 SP.
                if(playAs.getCurrentSP()>=4){
                    playAs.changeSP(-4);
                    for (Character ch : yourTeam){
                        ch.getEffects().add(effect.obtainEffect("Nullify").increaseDuration(1).copy());
                    }
                }
                break;
            case "Chance":
                // Has a 50% chance to damage self for 3 HP, or a 50% chance to deal heavy damage to a random enemy. Consumes 1 SP.
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getRandomTarget(enemyTeam); // Randomly selects an enemy target
                    if(rand.nextBoolean()){ // 50% chance for self-damage
                        playAs.changeHP(-3);
                    }else{ // 50% chance for enemy damage
                        int targetHPChange = -6;
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
                }
                break;
        }
    }
}