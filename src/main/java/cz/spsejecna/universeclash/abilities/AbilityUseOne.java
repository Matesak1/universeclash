package cz.spsejecna.universeclash.abilities;
import cz.spsejecna.universeclash.Fight;
import cz.spsejecna.universeclash.characters.Character;
import cz.spsejecna.universeclash.effects.Effect;
import cz.spsejecna.universeclash.effects.EffectRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class AbilityUseOne {
    private final EffectRepository effect = new EffectRepository();
    private final Fight targetPicker = new Fight();
    private final Random rand = new Random();
    /**
     * Executes the first ability of a given character. The specific effect of the ability
     * is determined by the character's name and may involve SP consumption,
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
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(enemyTeam);
                    int targetHPChange = -3;
                    for (Effect e : playAs.getEffects()) {
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
                }
                break;
            case "Leafy":
                if (playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getTarget(yourTeam);
                    int targetHPChange = 2;

                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Bleed")) {
                            targetHPChange = 0;
                        }
                        if(Objects.equals(e.getName(), "Fire")) {
                            target.getEffects().remove(e);
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Doombringer":
                if (playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getTarget(enemyTeam);
                    int targetHPChange = -3;
                    for (Effect e : playAs.getEffects()) {
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
                    target.getEffects().add(effect.obtainEffect("Stun").increaseDuration(1).copy());
                }
                break;
            case "Cyan":
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
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
                        if(rand.nextBoolean()){
                            ch.getEffects().add(effect.obtainEffect("Bleed").increaseDuration(2).copy());
                        }
                    }
                }
                break;
            case "Jane_Doe":
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    target = targetPicker.getTarget(yourTeam);
                    int targetHPChange = 5;
                    for (Effect e : target.getEffects()) {
                        if (Objects.equals(e.getName(), "Bleed")) {
                            targetHPChange = 0;
                            break;
                        }
                    }
                    target.changeHP(targetHPChange);
                }
                break;
            case "Onyx":
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(enemyTeam);
                    target.getEffects().add(effect.obtainEffect("Fire").increaseDuration(3).copy());
                }
                break;
            case "Viper":
                if(playAs.getCurrentSP()>=1) {
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(enemyTeam);
                    int targetHPChange = -1;
                    for (Effect e : playAs.getEffects()) {
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
                    target.getEffects().add(effect.obtainEffect("Poison").increaseDuration(2).copy());
                }
                break;
            case "007n7":
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    for (Character ch : enemyTeam) {
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
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(enemyTeam);
                    switch (rand.nextInt(4)){
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
                if(playAs.getCurrentSP()>=2){
                    playAs.changeSP(-2);
                    playAs.getEffects().add(effect.obtainEffect("Tough").increaseDuration(2).copy());
                    playAs.getEffects().add(effect.obtainEffect("Strong").increaseDuration(2).copy());
                }
                break;
            case "John_Doe":
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    ArrayList<Integer> effectsOnYou = new ArrayList<>();
                    for (Effect e : playAs.getEffects()) {effectsOnYou.add(e.getId());}
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
                if(playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    target = targetPicker.getTarget(yourTeam);
                    target.getEffects().add(effect.obtainEffect("Dodgy").increaseDuration(2).copy());
                }
                break;
            case "Chance":
                if (playAs.getCurrentSP()>=1){
                    playAs.changeSP(-1);
                    playAs.setMaxHP(rand.nextInt(33)+3);
                }
                break;
        }
    }
}
