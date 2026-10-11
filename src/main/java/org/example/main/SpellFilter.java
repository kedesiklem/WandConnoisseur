package org.example.main;

import static org.example.localization.LanguageManager.Language;
import org.example.localization.LocalizedException;
import org.example.localization.LocalizedText;
import org.example.main.Global.DamageType;
import org.example.projectiles.Projectile;
import org.example.spells.Spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static java.util.Map.entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Operator{GREATER, GREATER_OR_EQUAL, LOWER, LOWER_OR_EQUAL, EQUALS, DIFFERENT}
enum LogicGate{AND, OR}
enum PropertyType{BOOLEAN, INT, DOUBLE}
enum State{TRUE, FALSE, INVALID}
class Result{
    private State state;
    private LocalizedText error;
    private String[] args;

    public Result(State state){
        this.state = state;
        this.error = null;
        this.args = new String[0];
    }

    public Result(State state, LocalizedText error, String[] args){
        this.state = state;
        if(state == State.INVALID){
            this.error = error;
            this.args = args;
        }else{
            this.error = null;
            this.args = new String[0];
        }
    }

    public Result(State state, LocalizedText error, String arg){
        this.state = state;
        if(state == State.INVALID){
            this.error = error;
            this.args = new String[]{arg};
        }else{
            this.error = null;
            this.args = new String[0];
        }
    }

    public Result(State state, LocalizedText error){
        this.state = state;
        this.error = state == State.INVALID ? error : null;
        this.args = new String[0];
    }

    // getters
    public State getState(){
        return this.state;
    }

    public LocalizedText getError(){
        return this.error;
    }

    public String getErrorString(Locale language){
        return this.error == null ? "" : this.error.get(language);
    }

    public String getErrorString(Language language){
        return this.error == null ? "" : this.error.get(language);
    }

    public String[] getArgs(){
        return this.args;
    }

    // setters
    public void setState(State state){
        this.state = state;
        if(state != State.INVALID){
            this.error = null;
            this.args = new String[0];
        }
    }

    public void setState(State state, LocalizedText error, String[] args){
        this.state = state;
        if(state == State.INVALID){
            this.error = error;
            this.args = args;
        }else{
            this.error = null;
            this.args = new String[0];
        }
    }

    public void setState(State state, LocalizedText error, String arg){
        this.state = state;
        if(state == State.INVALID){
            this.error = error;
            this.args = new String[]{arg};
        }else{
            this.error = null;
            this.args = new String[0];
        }
    }

    public void setState(State state, LocalizedText error){
        this.state = state;
        this.error = state == State.INVALID ? error : null;
        this.args = new String[0];
    }

    public void setError(LocalizedText error, String[] args){
        if(this.state == State.INVALID){
            this.error = error;
            this.args = args;
        }
    }

    public void setError(LocalizedText error, String arg){
        if(this.state == State.INVALID){
            this.error = error;
            this.args = new String[]{arg};
        }
    }

    public void setError(LocalizedText error){
        if(this.state == State.INVALID){
            this.error = error;
        }
    }
}
record TypedProperty(PropertyType type, Object value){}

class Expression{
    private static final LocalizedText ERROR_CONDITION_INVALID_BOOLEAN = Global.getLanguageManager().get("ERROR_CONDITION_INVALID_BOOLEAN");
    private static final LocalizedText ERROR_CONDITION_INVALID_INTEGER = Global.getLanguageManager().get("ERROR_CONDITION_INVALID_INTEGER");
    private static final LocalizedText ERROR_CONDITION_INVALID_NUMBER = Global.getLanguageManager().get("ERROR_CONDITION_INVALID_NUMBER");
    private static final LocalizedText ERROR_CONDITION_INVALID_OPERATOR_BOOLEAN = Global.getLanguageManager().get("ERROR_CONDITION_INVALID_OPERATOR_BOOLEAN");
    private static final LocalizedText ERROR_UNKNOWN = Global.getLanguageManager().get("ERROR_UNKNOWN");
    private static final LocalizedText ERROR_UNKNOWN_PROPERTY = Global.getLanguageManager().get("ERROR_UNKNOWN_PROPERTY");
    private final String value1;
    private final String value2;
    private final Operator operator;
    private final LogicGate logicGate;
    private final Expression left;
    private final Expression right;

    public Expression(String value1, Operator operator, String value2) {
        this.value1 = value1.toLowerCase().trim();
        this.operator = operator;
        this.value2 = value2.toLowerCase().trim();
        this.logicGate = null;
        this.left = null;
        this.right = null;
    }

    public Expression(Expression left, LogicGate logicGate, Expression right){
        this.value1 = null;
        this.operator = null;
        this.value2 = null;
        this.left = left;
        this.logicGate = logicGate;
        this.right = right;
    }

    public boolean isLeaf(){
        return this.logicGate == null;
    }

    public Result evaluate(Spell spell){
        if(!this.isLeaf()){
            Result leftResult = this.left.evaluate(spell);

            if(leftResult.getState() == State.INVALID || leftResult.getState() == State.TRUE && this.logicGate == LogicGate.OR || leftResult.getState() == State.FALSE && this.logicGate == LogicGate.AND){
                return leftResult;
            }

            return this.right.evaluate(spell);
        }

        return evaluateLeaf(spell);
    }

    private Result evaluateLeaf(Spell spell){
        Function<Spell, TypedProperty> resolver = SpellFilter.PROPERTY_RESOLVERS.get(this.value1);
        Result result = new Result(State.INVALID, ERROR_UNKNOWN);

        if(resolver == null){
            result.setState(State.INVALID, ERROR_UNKNOWN_PROPERTY, new String[]{this.toString(), this.value1});
            return result;
        }

        TypedProperty typedProperty = resolver.apply(spell);
        if(typedProperty == null){
            return result;
        }

        if(typedProperty.value() == null){
            result.setState(State.FALSE);
            return result;
        }

        switch(typedProperty.type()){
            case BOOLEAN -> {
                if(this.operator != Operator.EQUALS && this.operator != Operator.DIFFERENT){
                    result.setError(ERROR_CONDITION_INVALID_OPERATOR_BOOLEAN, new String[]{this.toString(), operatorToString(this.operator)});
                    return result;
                }

                boolean valueBoolean = (Boolean)typedProperty.value();
                switch(this.value2){
                    case "true" -> result.setState((this.operator == Operator.EQUALS) == valueBoolean ? State.TRUE : State.FALSE);
                    case "false" -> result.setState((this.operator == Operator.DIFFERENT) == valueBoolean ? State.TRUE : State.FALSE);
                    default -> result.setError(ERROR_CONDITION_INVALID_BOOLEAN, new String[]{this.toString(), this.value2});
                }
            }
            case INT -> {
                int actual;
                int expected;
                try{
                    actual = (Integer)typedProperty.value();
                    expected = Integer.parseInt(this.value2);
                }catch(NumberFormatException e){
                    result.setError(ERROR_CONDITION_INVALID_INTEGER, new String[]{this.toString(), this.value2});
                    return result;
                }

                result.setState(
                    switch(this.operator){
                        case GREATER -> actual > expected ? State.TRUE : State.FALSE;
                        case GREATER_OR_EQUAL -> actual >= expected ? State.TRUE : State.FALSE;
                        case LOWER -> actual < expected ? State.TRUE : State.FALSE;
                        case LOWER_OR_EQUAL -> actual <= expected ? State.TRUE : State.FALSE;
                        case EQUALS -> actual == expected ? State.TRUE : State.FALSE;
                        case DIFFERENT -> actual != expected ? State.TRUE : State.FALSE;
                    }
                );
            }
            case DOUBLE -> {
                double actual;
                double expected;
                try{
                    actual = (Double)typedProperty.value();
                    expected = Double.parseDouble(this.value2);
                }catch(NumberFormatException e){
                    result.setError(ERROR_CONDITION_INVALID_NUMBER, new String[]{this.toString(), this.value2});
                    return result;
                }

                result.setState(
                    switch(this.operator){
                        case GREATER -> actual > expected ? State.TRUE : State.FALSE;
                        case GREATER_OR_EQUAL -> actual >= expected ? State.TRUE : State.FALSE;
                        case LOWER -> actual < expected ? State.TRUE : State.FALSE;
                        case LOWER_OR_EQUAL -> actual <= expected ? State.TRUE : State.FALSE;
                        case EQUALS -> actual == expected ? State.TRUE : State.FALSE;
                        case DIFFERENT -> actual != expected ? State.TRUE : State.FALSE;
                    }
                );
            }
        }

        return result;
    }

    public String toString(){
        if(isLeaf()){
            return this.value1 + " " + operatorToString(this.operator) + " " + this.value2;
        }

        return "(" + this.left + " " + this.logicGate + " " + this.right + ")";
    }

    private static String operatorToString(Operator operator){
        return switch(operator){
            case GREATER -> ">";
            case GREATER_OR_EQUAL -> ">=";
            case LOWER -> "<";
            case LOWER_OR_EQUAL -> "<=";
            case EQUALS -> "=";
            case DIFFERENT -> "!=";
        };
    }
}

class ExpressionParser{
    private static final LocalizedText ERROR_CONDITION_PARENTHESIS = Global.getLanguageManager().get("ERROR_CONDITION_PARENTHESIS");
    private static final LocalizedText ERROR_CONDITION_SYNTAX = Global.getLanguageManager().get("ERROR_CONDITION_SYNTAX");
    private static final LocalizedText ERROR_UNKNOWN = Global.getLanguageManager().get("ERROR_UNKNOWN");
    private static final Pattern pattern = Pattern.compile("^ *(?i)([a-z0-9_]+) *([><]=?|!?=) *([+-]?[0-9]+(?:\\.[0-9]+)?|true|false) *$");
    private final String[] tokens;
    private int pos = 0;

    public ExpressionParser(String input){
        this.tokens = tokenize(input);
    }

    public Expression parse(){
        this.pos = 0;
        return this.parseOr();
    }

    private Expression parseOr() {
        Expression left = parseAnd();

        while(pos < tokens.length && tokens[pos].equals("or")){
            pos++;
            Expression right = parseAnd();
            left = new Expression(left, LogicGate.OR, right);
        }

        return left;
    }

    private Expression parseAnd(){
        Expression left = parsePrimary();

        while(pos < tokens.length && tokens[pos].equals("and")){
            pos++;
            Expression right = parsePrimary();
            left = new Expression(left, LogicGate.AND, right);
        }

        return left;
    }

    private Expression parsePrimary(){
        if(pos >= tokens.length){
            throw new LocalizedException(ERROR_UNKNOWN);
        }

        if(tokens[pos].equals("(")){
            pos++; // skip "("
            Expression expr = parseOr();
            if(pos >= tokens.length || !tokens[pos].equals(")")){
                throw new LocalizedException(ERROR_CONDITION_PARENTHESIS, String.join(" ", this.tokens));
            }
            pos++; // skip ")"
            return expr;
        }else{
            return parseExpression();
        }
    }

    private Expression parseExpression(){
        if(pos >= tokens.length){
            throw new LocalizedException(ERROR_UNKNOWN);
        }

        String expr = tokens[pos++];

        Matcher m = pattern.matcher(expr);
        if(m.find()){
            Operator operator = switch(m.group(2)){
                case ">" -> Operator.GREATER;
                case ">=" -> Operator.GREATER_OR_EQUAL;
                case "<" -> Operator.LOWER;
                case "<=" -> Operator.LOWER_OR_EQUAL;
                case "!=" -> Operator.DIFFERENT;
                default -> Operator.EQUALS;
            };
            return new Expression(m.group(1), operator, m.group(3));
        }else{
            throw new LocalizedException(ERROR_CONDITION_SYNTAX, expr);
        }
    }

    private String[] tokenize(String input){
        List<String> resultList = new ArrayList<>();
        String currentToken = "";
        boolean previousIsToken = true;
        int nbParenthesis = 0;

        for(int i=0; i < input.length(); i++){
            char c = input.charAt(i);
            switch(c){
                case '(' -> {
                    nbParenthesis++;
                    if(!currentToken.equals("")){
                        if(previousIsToken){
                            resultList.add(currentToken);
                        }else{
                            resultList.set(resultList.size() - 1, resultList.get(resultList.size() - 1) + " " + currentToken);
                        }
                    }
                    resultList.add(String.valueOf(c));
                    currentToken = "";
                    previousIsToken = true;
                }
                case ')' -> {
                    nbParenthesis--;
                    if(nbParenthesis < 0){
                        throw new LocalizedException(ERROR_CONDITION_PARENTHESIS, input);
                    }
                    if(!currentToken.equals("")){
                        if(previousIsToken){
                            resultList.add(currentToken);
                        }else{
                            resultList.set(resultList.size() - 1, resultList.get(resultList.size() - 1) + " " + currentToken);
                        }
                    }
                    resultList.add(String.valueOf(c));
                    currentToken = "";
                    previousIsToken = true;
                }
                case ' ' -> {
                    switch(currentToken){
                        case "and", "or", "(", ")":
                            resultList.add(currentToken);
                            currentToken = "";
                            previousIsToken = true;
                            break;
                        case "":
                            break;
                        default:
                            if(previousIsToken){
                                resultList.add(currentToken);
                            }else{
                                resultList.set(resultList.size() - 1, resultList.get(resultList.size() - 1) + " " + currentToken);
                            }
                            currentToken = "";
                            previousIsToken = false;
                            break;
                    }
                }
                default -> currentToken += c;
            }
        }
        if(!currentToken.equals("")){
            if(previousIsToken){
                resultList.add(currentToken);
            }else{
                resultList.set(resultList.size() - 1, resultList.get(resultList.size() - 1) + " " + currentToken);
            }
        }
        if(nbParenthesis != 0){
            throw new LocalizedException(ERROR_CONDITION_PARENTHESIS, input);
        }

        return resultList.toArray(new String[0]);
    }
}

public class SpellFilter{
    private static final LocalizedText ERROR_CONDITION_PARENTHESIS = Global.getLanguageManager().get("ERROR_CONDITION_PARENTHESIS");
    private static final LocalizedText ERROR_CONDITION_UNKNOWN_PROPERTY = Global.getLanguageManager().get("ERROR_CONDITION_UNKNOWN_PROPERTY");
    private static final LocalizedText ERROR_SORT_SYNTAX = Global.getLanguageManager().get("ERROR_SORT_SYNTAX");
    private static final LocalizedText ERROR_UNKNOWN_PROPERTY = Global.getLanguageManager().get("ERROR_UNKNOWN_PROPERTY");
    private static final Pattern sortPattern = Pattern.compile("^ *(?i)([a-z0-9_]+) *(|ASC|DESC) *$");
    public static final Map<String, Function<Spell, TypedProperty>> PROPERTY_RESOLVERS = Map.ofEntries(
        // boolean
        entry("friendly_fire", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getFriendlyFire())),
        entry("has_charges", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getHasCharges())),
        entry("has_projectile_component", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent != null);}),
        entry("has_related_projectile", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getRelatedProjectile() != null)),
        entry("has_velocity_component", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent != null);}),
        entry("multiply_speed", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getMultiplySpeed())),
        entry("never_unlimited", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getNeverUnlimited())),
        entry("projectile_affect_physics_bodies", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent == null ? null : velocityComponent.getAffectPhysicsBodies());}),
        entry("projectile_apply_terminal_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent == null ? null : velocityComponent.getApplyTerminalVelocity());}),
        entry("projectile_collide_with_world", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getCollideWithWorld());}),
        entry("projectile_damage_scaled_by_speed", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getDamageScaledBySpeed());}),
        entry("projectile_die_on_low_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getDieOnLowVelocity());}),
        entry("projectile_displace_liquid", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent == null ? null : velocityComponent.getDisplaceLiquid());}),
        entry("projectile_explosion_dont_damage_shooter", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getExplosionDontDamageShooter());}),
        entry("projectile_friendly_fire", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getFriendlyFire());}),
        entry("projectile_limit_to_max_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent == null ? null : velocityComponent.getLimitToMaxVelocity());}),
        entry("projectile_on_collision_die", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getOnCollisionDie());}),
        entry("projectile_on_death_explode", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getOnDeathExplode());}),
        entry("projectile_on_lifetime_out_explode", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.BOOLEAN, projectileComponent == null ? null : projectileComponent.getOnLifetimeOutExplode());}),
        entry("projectile_update_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.BOOLEAN, velocityComponent == null ? null : velocityComponent.getUpdateVelocity());}),
        entry("recursive", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getRecursive())),
        entry("set_bounce", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetBounce())),
        entry("set_cast_delay", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetCastDelay())),
        entry("set_friendly_fire", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetFriendlyFire())),
        entry("set_gore_particles", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetGoreParticles())),
        entry("set_recharge_time", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetRechargeTime())),
        entry("set_recoil", spell -> new TypedProperty(PropertyType.BOOLEAN, spell.getSetRecoil())),

        // int
        entry("bounce", spell -> new TypedProperty(PropertyType.INT, spell.getBounce())),
        entry("cast_delay", spell -> new TypedProperty(PropertyType.INT, spell.getCastDelay())),
        entry("crit_rate", spell -> new TypedProperty(PropertyType.INT, spell.getCritRate())),
        entry("gore_particles", spell -> new TypedProperty(PropertyType.INT, spell.getGoreParticles())),
        entry("lifetime", spell -> new TypedProperty(PropertyType.INT, spell.getLifetime())),
        entry("mana", spell -> new TypedProperty(PropertyType.INT, spell.getManaCost())),
        entry("material_amount", spell -> new TypedProperty(PropertyType.INT, spell.getMaterialAmount())),
        entry("max_charges", spell -> new TypedProperty(PropertyType.INT, spell.getMaxCharges())),
        entry("pattern", spell -> new TypedProperty(PropertyType.INT, spell.getPattern())),
        entry("price", spell -> new TypedProperty(PropertyType.INT, spell.getPrice())),
        entry("projectile_count", spell -> new TypedProperty(PropertyType.INT, spell.getRelatedProjectileCount())),
        entry("projectile_lifetime_base", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.INT, projectileComponent == null ? null : projectileComponent.getLifetime());}),
        entry("projectile_lifetime_max", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.INT, projectileComponent == null ? null : projectileComponent.getLifetimeMax());}),
        entry("projectile_lifetime_min", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.INT, projectileComponent == null ? null : projectileComponent.getLifetimeMin());}),
        entry("projectile_lifetime_randomness", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.INT, projectileComponent == null ? null : projectileComponent.getLifetimeRandomness());}),
        entry("projectile_liquid_death_threshold", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.INT, velocityComponent == null ? null : velocityComponent.getLiquidDeathThreshold());}),
        entry("recharge_time", spell -> new TypedProperty(PropertyType.INT, spell.getRechargeTime())),
        entry("tier_count", spell -> new TypedProperty(PropertyType.INT, spell.getNbTier())),
        entry("timer_length", spell -> new TypedProperty(PropertyType.INT, spell.getTimerLength())),
        entry("trail_material_amount", spell -> new TypedProperty(PropertyType.INT, spell.getTrailMaterialAmount())),

        // double
        entry("damage_curse", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.CURSE))),
        entry("damage_drill", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.DRILL))),
        entry("damage_electricity", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.ELECTRICITY))),
        entry("damage_explosion", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.EXPLOSION))),
        entry("damage_fire", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.FIRE))),
        entry("damage_healing", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.HEALING))),
        entry("damage_holy", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.HOLY))),
        entry("damage_ice", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.ICE))),
        entry("damage_melee", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.MELEE))),
        entry("damage_overeating", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.OVEREATING))),
        entry("damage_physics_hit", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.PHYSICS_HIT))),
        entry("damage_poison", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.POISON))),
        entry("damage_projectile", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.PROJECTILE))),
        entry("damage_radioactive", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.RADIOACTIVE))),
        entry("damage_slice", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getDamage(DamageType.SLICE))),
        entry("damage_total", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getDamageComponent().getTotal())),
        entry("gravity", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getGravity())),
        entry("projectile_air_friction", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getAirFriction());}),
        entry("projectile_damage_curse", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.CURSE));}),
        entry("projectile_damage_drill", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.DRILL));}),
        entry("projectile_damage_electricity", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.ELECTRICITY));}),
        entry("projectile_damage_explosion", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.EXPLOSION));}),
        entry("projectile_damage_fire", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.FIRE));}),
        entry("projectile_damage_healing", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.HEALING));}),
        entry("projectile_damage_holy", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.HOLY));}),
        entry("projectile_damage_ice", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.ICE));}),
        entry("projectile_damage_melee", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.MELEE));}),
        entry("projectile_damage_overeating", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.OVEREATING));}),
        entry("projectile_damage_physics_hit", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.PHYSICS_HIT));}),
        entry("projectile_damage_poison", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.POISON));}),
        entry("projectile_damage_projectile", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.PROJECTILE));}),
        entry("projectile_damage_radioactive", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.RADIOACTIVE));}),
        entry("projectile_damage_scale_max_speed", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageScaleMaxSpeed());}),
        entry("projectile_damage_slice", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getDamage(DamageType.SLICE));}),
        entry("projectile_damage_total", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDamageComponent().getTotal());}),
        entry("projectile_die_on_low_velocity_limit", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getDieOnLowVelocityLimit());}),
        entry("projectile_formation_degree", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : Math.toDegrees(projectileComponent.getFormationRad()));}),
        entry("projectile_formation_rad", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getFormationRad());}),
        entry("projectile_friction", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getFriction());}),
        entry("projectile_gravity_x", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getGravityX());}),
        entry("projectile_gravity_y", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getGravityY());}),
        entry("projectile_knockback", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getKnockback());}),
        entry("projectile_liquid_drag", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getLiquidDrag());}),
        entry("projectile_mass", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getMass());}),
        entry("projectile_speed_min", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getSpeedMin());}),
        entry("projectile_speed_max", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getSpeedMax());}),
        entry("projectile_spread_degree", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : Math.toDegrees(projectileComponent.getSpreadRad()));}),
        entry("projectile_spread_rad", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return new TypedProperty(PropertyType.DOUBLE, projectileComponent == null ? null : projectileComponent.getSpreadRad());}),
        entry("projectile_terminal_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return new TypedProperty(PropertyType.DOUBLE, velocityComponent == null ? null : velocityComponent.getGravityX());}),
        entry("recoil", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getRecoil())),
        entry("screenshake", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getScreenshake())),
        entry("speed", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpeed())),
        entry("spread", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpread())),
        entry("t0", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[0])),
        entry("t1", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[1])),
        entry("t2", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[2])),
        entry("t3", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[3])),
        entry("t4", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[4])),
        entry("t5", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[5])),
        entry("t6", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[6])),
        entry("t7", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[7])),
        entry("t8", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[8])),
        entry("t9", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[9])),
        entry("t10", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getSpawnProbability()[10])),
        entry("tier_total", spell -> new TypedProperty(PropertyType.DOUBLE, spell.getTierTotal()))
    );

    public static final Map<String, Function<Spell, String>> STRING_PROPERTY_RESOLVERS = Map.ofEntries(
        // string
        entry("alias", Spell::getAliasString),
        entry("charge", Spell::getChargeString),
        entry("damage", spell -> spell.getDamageComponent().toString()),
        entry("material", Spell::getMaterial),
        entry("name", Spell::getName),
        entry("name_code", spell -> spell.getClass().getSimpleName()),
        entry("projectile_damage", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().toString()) : "<null>";}),
        entry("projectile_lifetime", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getLifetimeString()) : "<null>";}),
        entry("projectile_speed", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getSpeedString()) : "<null>";}),
        entry("tier", spell -> spell.getTierString(false)),
        entry("tier_all", spell -> spell.getTierString(true)),
        entry("trail_material", Spell::getTrailMaterial),
        entry("trigger_type", spell -> spell.getTriggerType().toString()),
        entry("type", spell -> spell.getType().toString()),

        // boolean
        entry("friendly_fire", spell -> String.valueOf(spell.getFriendlyFire())),
        entry("has_charges", spell -> String.valueOf(spell.getHasCharges())),
        entry("has_projectile_component", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return String.valueOf(projectileComponent != null);}),
        entry("has_related_projectile", spell -> String.valueOf(spell.getRelatedProjectile() != null)),
        entry("has_velocity_component", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return String.valueOf(velocityComponent != null);}),
        entry("multiply_speed", spell -> String.valueOf(spell.getMultiplySpeed())),
        entry("never_unlimited", spell -> String.valueOf(spell.getNeverUnlimited())),
        entry("projectile_affect_physics_bodies", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getAffectPhysicsBodies()) : "<null>";}),
        entry("projectile_apply_terminal_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getApplyTerminalVelocity()) : "<null>";}),
        entry("projectile_collide_with_world", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getCollideWithWorld()) : "<null>";}),
        entry("projectile_damage_scaled_by_speed", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageScaledBySpeed()) : "<null>";}),
        entry("projectile_die_on_low_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDieOnLowVelocity()) : "<null>";}),
        entry("projectile_displace_liquid", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getDisplaceLiquid()) : "<null>";}),
        entry("projectile_explosion_dont_damage_shooter", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getExplosionDontDamageShooter()) : "<null>";}),
        entry("projectile_friendly_fire", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getFriendlyFire()) : "<null>";}),
        entry("projectile_limit_to_max_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getLimitToMaxVelocity()) : "<null>";}),
        entry("projectile_on_collision_die", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getOnCollisionDie()) : "<null>";}),
        entry("projectile_on_death_explode", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getOnDeathExplode()) : "<null>";}),
        entry("projectile_on_lifetime_out_explode", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getOnLifetimeOutExplode()) : "<null>";}),
        entry("projectile_update_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getUpdateVelocity()) : "<null>";}),
        entry("recursive", spell -> String.valueOf(spell.getRecursive())),
        entry("set_cast_delay", spell -> String.valueOf(spell.getSetCastDelay())),
        entry("set_friendly_fire", spell -> String.valueOf(spell.getSetFriendlyFire())),
        entry("set_gore_paticles", spell -> String.valueOf(spell.getSetGoreParticles())),
        entry("set_recoil", spell -> String.valueOf(spell.getSetRecoil())),

        // int
        entry("bounce", spell -> (spell.getSetBounce() ? "=" : "") + spell.getBounce()),
        entry("cast_delay", spell -> (spell.getSetCastDelay() ? "=" : "") + Global.delayFormat(spell.getCastDelay())),
        entry("crit_rate", spell -> String.valueOf(spell.getCritRate()) + "%"),
        entry("gore_particles", spell -> (spell.getSetGoreParticles() ? "=" : "") + spell.getGoreParticles()),
        entry("lifetime", spell -> Global.delayFormat(spell.getLifetime())),
        entry("mana", spell -> String.valueOf(spell.getManaCost())),
        entry("material_amount", spell -> String.valueOf(spell.getMaterialAmount())),
        entry("max_charges", spell -> String.valueOf(spell.getMaxCharges())),
        entry("pattern", spell -> spell.getPattern() + "°"),
        entry("price", spell -> String.valueOf(spell.getPrice())),
        entry("projectile_count", spell -> String.valueOf(spell.getRelatedProjectileCount())),
        entry("projectile_lifetime_base", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getLifetime()) : "<null>";}),
        entry("projectile_lifetime_max", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getLifetimeMax()) : "<null>";}),
        entry("projectile_lifetime_min", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getLifetimeMin()) : "<null>";}),
        entry("projectile_lifetime_randomness", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getLifetimeRandomness()) : "<null>";}),
        entry("projectile_liquid_death_threshold", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getLiquidDeathThreshold()) : "<null>";}),
        entry("recharge_time", spell -> (spell.getSetRechargeTime() ? "=" : "") + Global.delayFormat(spell.getRechargeTime())),
        entry("tier_count", spell -> String.valueOf(spell.getNbTier())),
        entry("timer_length", spell -> Global.delayFormat(spell.getTimerLength())),
        entry("trail_material_amount", spell -> String.valueOf(spell.getTrailMaterialAmount())),

        // double
        entry("damage_curse", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.CURSE))),
        entry("damage_drill", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.DRILL))),
        entry("damage_electricity", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.ELECTRICITY))),
        entry("damage_explosion", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.EXPLOSION))),
        entry("damage_fire", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.FIRE))),
        entry("damage_healing", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.HEALING))),
        entry("damage_holy", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.HOLY))),
        entry("damage_ice", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.ICE))),
        entry("damage_melee", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.MELEE))),
        entry("damage_overeating", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.OVEREATING))),
        entry("damage_physics_hit", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.PHYSICS_HIT))),
        entry("damage_poison", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.POISON))),
        entry("damage_projectile", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.PROJECTILE))),
        entry("damage_radioactive", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.RADIOACTIVE))),
        entry("damage_slice", spell -> String.valueOf(spell.getDamageComponent().getDamage(DamageType.SLICE))),
        entry("damage_total", spell -> String.valueOf(spell.getDamageComponent().getTotal())),
        entry("gravity", spell -> String.valueOf(spell.getGravity())),
        entry("projectile_air_friction", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getAirFriction()) : "<null>";}),
        entry("projectile_damage_curse", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.CURSE)) : "<null>";}),
        entry("projectile_damage_drill", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.DRILL)) : "<null>";}),
        entry("projectile_damage_electricity", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.ELECTRICITY)) : "<null>";}),
        entry("projectile_damage_explosion", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.EXPLOSION)) : "<null>";}),
        entry("projectile_damage_fire", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.FIRE)) : "<null>";}),
        entry("projectile_damage_healing", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.HEALING)) : "<null>";}),
        entry("projectile_damage_holy", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.HOLY)) : "<null>";}),
        entry("projectile_damage_ice", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.ICE)) : "<null>";}),
        entry("projectile_damage_melee", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.MELEE)) : "<null>";}),
        entry("projectile_damage_overeating", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.OVEREATING)) : "<null>";}),
        entry("projectile_damage_physics_hit", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.PHYSICS_HIT)) : "<null>";}),
        entry("projectile_damage_poison", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.POISON)) : "<null>";}),
        entry("projectile_damage_projectile", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.PROJECTILE)) : "<null>";}),
        entry("projectile_damage_radioactive", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.RADIOACTIVE)) : "<null>";}),
        entry("projectile_damage_scale_max_speed", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageScaleMaxSpeed()) : "<null>";}),
        entry("projectile_damage_slice", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getDamage(DamageType.SLICE)) : "<null>";}),
        entry("projectile_damage_total", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDamageComponent().getTotal()) : "<null>";}),
        entry("projectile_die_on_low_velocity_limit", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getDieOnLowVelocityLimit()) : "<null>";}),
        entry("projectile_formation_degree", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(Math.toDegrees(projectileComponent.getFormationRad())) : "<null>";}),
        entry("projectile_formation_rad", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getFormationRad()) : "<null>";}),
        entry("projectile_friction", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getFriction()) : "<null>";}),
        entry("projectile_gravity_x", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getGravityX()) : "<null>";}),
        entry("projectile_gravity_y", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getGravityY()) : "<null>";}),
        entry("projectile_knockback", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getKnockback()) : "<null>";}),
        entry("projectile_liquid_drag", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getLiquidDrag()) : "<null>";}),
        entry("projectile_mass", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getMass()) : "<null>";}),
        entry("projectile_speed_min", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getSpeedMin()) : "<null>";}),
        entry("projectile_speed_max", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getSpeedMax()) : "<null>";}),
        entry("projectile_spread_degree", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(Math.toDegrees(projectileComponent.getSpreadRad())) : "<null>";}),
        entry("projectile_spread_rad", spell -> {Projectile projectile = spell.getRelatedProjectile(); ProjectileComponent projectileComponent = projectile == null ? null : projectile.getProjectileComponent(); return projectileComponent != null ? String.valueOf(projectileComponent.getSpreadRad()) : "<null>";}),
        entry("projectile_terminal_velocity", spell -> {Projectile projectile = spell.getRelatedProjectile(); VelocityComponent velocityComponent = projectile == null ? null : projectile.getVelocityComponent(); return velocityComponent != null ? String.valueOf(velocityComponent.getTerminalVelocity()) : "<null>";}),
        entry("recoil", spell -> (spell.getSetRecoil() ? "=" : "") + String.valueOf(spell.getRecoil())),
        entry("screenshake", spell -> String.valueOf(spell.getScreenshake())),
        entry("speed", spell -> (spell.getMultiplySpeed() ? "x" : "") +  String.valueOf(spell.getSpeed())),
        entry("spread", spell -> String.valueOf(spell.getSpread()) + "°"),
        entry("t0", spell -> String.valueOf(spell.getSpawnProbability()[0])),
        entry("t1", spell -> String.valueOf(spell.getSpawnProbability()[1])),
        entry("t2", spell -> String.valueOf(spell.getSpawnProbability()[2])),
        entry("t3", spell -> String.valueOf(spell.getSpawnProbability()[3])),
        entry("t4", spell -> String.valueOf(spell.getSpawnProbability()[4])),
        entry("t5", spell -> String.valueOf(spell.getSpawnProbability()[5])),
        entry("t6", spell -> String.valueOf(spell.getSpawnProbability()[6])),
        entry("t7", spell -> String.valueOf(spell.getSpawnProbability()[7])),
        entry("t8", spell -> String.valueOf(spell.getSpawnProbability()[8])),
        entry("t9", spell -> String.valueOf(spell.getSpawnProbability()[9])),
        entry("t10", spell -> String.valueOf(spell.getSpawnProbability()[10])),
        entry("tier_total", spell -> String.valueOf(spell.getTierTotal()))
    );

    public SpellFilter(){
        // nothing
    }

    public Spell[] filter(Spell[] spells, String input){
        Expression expression = new ExpressionParser(input).parse();
        ArrayList<Spell> result = new ArrayList<>();
        Result currentResult;

        if(expression == null){
            throw new LocalizedException(ERROR_CONDITION_PARENTHESIS, input);
        }

        for(int i=0; i < spells.length; i++){
            currentResult = expression.evaluate(spells[i]);
            switch(currentResult.getState()){
                case TRUE -> result.add(spells[i]);
                //case INVALID -> throw new LocalizedException(ERROR_CONDITION, currentResult.getError(), currentResult.getArgs());
                case INVALID -> throw new LocalizedException(currentResult.getError(), currentResult.getArgs());
            }
        }

        return result.toArray(new Spell[0]);
    }

    private static class SortStruct{
        String propertyName;
        public Function<Spell, TypedProperty> resolver;
        public boolean sortType; // ASC = false

        public SortStruct(String propertyName, Function<Spell, TypedProperty> resolver, boolean sortType){
            this.propertyName = propertyName;
            this.resolver = resolver;
            this.sortType = sortType;
        }

        public SortStruct(String propertyName, Function<Spell, TypedProperty> resolver){
            this.propertyName = propertyName;
            this.resolver = resolver;
            this.sortType = false;
        }
    }

    public Spell[] sort(Spell[] spells, String input){
        Matcher m;
        String[] inputs = input.split(",");
        SortStruct[] sortStruct = new SortStruct[inputs.length];
        double[][] comparisonValues = new double[spells.length][inputs.length];
        TypedProperty typedProperty;
        Spell currentSpell;
        double[] currentComparisonValue;

        for(int i=0; i < inputs.length; i++){
            m = sortPattern.matcher(inputs[i]);
            if(m.find()){
                sortStruct[i] = new SortStruct(m.group(1), PROPERTY_RESOLVERS.get(m.group(1)), m.group(2).equalsIgnoreCase("DESC"));
                if(sortStruct[i].resolver == null){
                    throw new LocalizedException(ERROR_CONDITION_UNKNOWN_PROPERTY, new String[]{inputs[i], m.group(1)});
                }
            }else{
                throw new LocalizedException(ERROR_SORT_SYNTAX, inputs[i]);
            }
        }

        // use NaN value as default to avoid multiple calculations of the same property/spell
        for(int i=0; i < comparisonValues.length; i++){
            Arrays.fill(comparisonValues[i], Double.NaN);
        }

        for(int i=0; i < spells.length - 1; i++){
            for(int j=i+1; j < spells.length; j++){
                for(int k=0; k < comparisonValues[i].length; k++){
                    if(Double.isNaN(comparisonValues[i][k])){
                        typedProperty = sortStruct[k].resolver.apply(spells[i]);
                        if(typedProperty.value() != null){
                            comparisonValues[i][k] = switch(typedProperty.type()){
                                case BOOLEAN -> ((Boolean)typedProperty.value()) ? 1.0 : 0.0;
                                case INT -> (Integer)typedProperty.value();
                                case DOUBLE -> (Double)typedProperty.value();
                            };
                        }else{
                            comparisonValues[i][k] = -Double.MAX_VALUE;
                        }
                    }
                    if(Double.isNaN(comparisonValues[j][k])){
                        typedProperty = sortStruct[k].resolver.apply(spells[j]);
                        if(typedProperty.value() != null){
                            comparisonValues[j][k] = switch(typedProperty.type()){
                                case BOOLEAN -> ((Boolean)typedProperty.value()) ? 1.0 : 0.0;
                                case INT -> (Integer)typedProperty.value();
                                case DOUBLE -> (Double)typedProperty.value();
                            };
                        }else{
                            comparisonValues[j][k] = -Double.MAX_VALUE;
                        }
                    }
                    if(comparisonValues[i][k] != comparisonValues[j][k]){
                        if((comparisonValues[i][k] > comparisonValues[j][k]) != sortStruct[k].sortType){ // != to invert sort order depending on the type
                            currentSpell = spells[i];
                            currentComparisonValue = comparisonValues[i];
                            spells[i] = spells[j];
                            comparisonValues[i] = comparisonValues[j];
                            spells[j] = currentSpell;
                            comparisonValues[j] = currentComparisonValue;
                        }
                        break;
                    }
                }
            }
        }
        return spells;
    }

    // I can't just make an array of Function<Spell, String> because java is stupid, so I have to do this...
    private static class StringPropertyStruct{
        public Function<Spell, String> resolver;

        public StringPropertyStruct(Function<Spell, String> resolver){
            this.resolver = resolver;
        }
    }

    public String[][] getStringProperties(Spell[] spells, String input){
        String[] properties = input.trim().equals("*") ? SpellFilter.STRING_PROPERTY_RESOLVERS.keySet().stream().sorted().toArray(String[]::new) : input.split(",");
        String[][] result;
        StringPropertyStruct[] stringPropertyStruct = new StringPropertyStruct[properties.length];

        Arrays.parallelSetAll(properties, (i) -> properties[i].trim().toLowerCase());
        for(int i=0; i < properties.length; i++){
            stringPropertyStruct[i] = new StringPropertyStruct(SpellFilter.STRING_PROPERTY_RESOLVERS.get(properties[i]));
            if(stringPropertyStruct[i].resolver == null){
                throw new LocalizedException(ERROR_UNKNOWN_PROPERTY, properties[i]);
            }
        }

        result = new String[properties.length][spells.length + 1];
        for(int i=0; i < result.length; i++){
            result[i][0] = properties[i];
            for(int j=1; j < result[i].length; j++){
                result[i][j] = stringPropertyStruct[i].resolver.apply(spells[j - 1]);
            }
        }

        return result;
    }
}