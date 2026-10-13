package org.example.GUI.state;

import org.example.main.Global;
import org.example.spells.Spell;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stable spell identity for persistence (categories, saved builds). Keys by fully-qualified
 * class name rather than alias string: a spell's declared aliases aren't guaranteed to include
 * its class name (only the no-explicit-alias fallback in {@code Spell()} adds it), so alias
 * lookup can silently miss. The class name always exists and is unambiguous.
 */
public final class SpellRegistry{
    private static final Map<String, Spell> templatesByKey = build();

    private SpellRegistry(){}

    private static Map<String, Spell> build(){
        Map<String, Spell> map = new LinkedHashMap<>();
        for(Spell spell : Global.getSpellList().getSpells(false)){
            map.put(keyOf(spell), spell);
        }
        return map;
    }

    public static String keyOf(Spell spell){
        return spell.getClass().getName();
    }

    /** Returns a fresh clone of the spell registered under {@code key}, or null if unknown. */
    public static Spell byKey(String key){
        Spell template = templatesByKey.get(key);
        return template == null ? null : template.clone();
    }
}
