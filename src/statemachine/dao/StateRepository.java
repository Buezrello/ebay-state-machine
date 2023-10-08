package statemachine.dao;

import statemachine.ConsecutiveSequenceStateMachine;

public interface StateRepository<T> {
    void saveState(ConsecutiveSequenceStateMachine<T> stateMachine);
    ConsecutiveSequenceStateMachine<T> loadState();
}
