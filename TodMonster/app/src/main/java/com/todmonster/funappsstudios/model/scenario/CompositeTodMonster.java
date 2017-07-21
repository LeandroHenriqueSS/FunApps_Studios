package com.todmonster.funappsstudios.model.scenario;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/** A list of todMonsters. */
public class CompositeTodMonster {

    private final LinkedList<TodMonster> todMonsters = new LinkedList<>();
    private final List<TodMonster> safeTodMonsters = Collections.unmodifiableList(todMonsters);

    /** @return immutable list of todMonsters. */
    public List<TodMonster> getTodMonsters() { return safeTodMonsters; }

    /**
     * @param todMonster myMonster
     */
    public void addDot(TodMonster todMonster) {
        todMonsters.add(todMonster);
    }


}
