package statemachine.dao.impl;

import statemachine.ConsecutiveSequenceStateMachine;
import statemachine.dao.StateRepository;

public class InMemoryStateRepository<T> implements StateRepository<T> {

    private ConsecutiveSequenceStateMachine<T> savedState;

    @Override
    public void saveState(ConsecutiveSequenceStateMachine<T> stateMachine) {
        savedState = new ConsecutiveSequenceStateMachine<>(stateMachine.get_maxConsecutiveCount());
        savedState.set_consecutiveCounts(stateMachine.get_consecutiveCounts());
        savedState.set_maxConsecutiveCount(stateMachine.get_maxConsecutiveCount());
    }

    @Override
    public ConsecutiveSequenceStateMachine<T> loadState() {
        return savedState;
    }
}
