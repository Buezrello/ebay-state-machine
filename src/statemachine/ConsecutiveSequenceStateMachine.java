package statemachine;

import statemachine.dao.StateRepository;

import java.util.HashMap;
import java.util.Map;

public class ConsecutiveSequenceStateMachine<T> {
    private enum State {
        WORKING, ERROR
    }

    private static class CountAndState {
        private final int _counter;
        private final State _state;

        public CountAndState() {
            this(0, State.WORKING);
        }

        private CountAndState(int counter, State state) {
            _counter = counter;
            _state = state;
        }
    }

    private Map<T, CountAndState> _consecutiveCounts = new HashMap<>();
    private int _maxConsecutiveCount;

    public Map<T, CountAndState> get_consecutiveCounts() {
        return _consecutiveCounts;
    }

    public int get_maxConsecutiveCount() {
        return _maxConsecutiveCount;
    }

    public void set_consecutiveCounts(Map<T, CountAndState> consecutiveCounts) {
        _consecutiveCounts = consecutiveCounts;
    }

    public void set_maxConsecutiveCount(int maxConsecutiveCount) {
        _maxConsecutiveCount = maxConsecutiveCount;
    }

    public ConsecutiveSequenceStateMachine(int maxConsecutiveCount) {
        _maxConsecutiveCount = maxConsecutiveCount;
    }

    public ConsecutiveSequenceStateMachine(ConsecutiveSequenceStateMachine<T> other) {
        _maxConsecutiveCount = other._maxConsecutiveCount;
        _consecutiveCounts = other._consecutiveCounts;
    }

    public void processInput(T input) {
        CountAndState countAndState = _consecutiveCounts.getOrDefault(input, new CountAndState());
        int count = countAndState._counter + 1;
        State state = countAndState._state;
        _consecutiveCounts.put(input, new CountAndState(count, state));

        if (count > _maxConsecutiveCount && state != State.ERROR) {
            System.out.println("Error: Consecutive element (" + input.toString() + ") detected.");
            _consecutiveCounts.put(input, new CountAndState(0, State.ERROR));
        }

        resetNonConsecutive(input);
    }

    private void resetNonConsecutive(T input) {
        for (T element : _consecutiveCounts.keySet()) {
            if (!element.equals(input)) {
                _consecutiveCounts.put(element, new CountAndState());
            }
        }
    }

    public void saveState(StateRepository<T> repository) {
        repository.saveState(this);
    }

    public void resetState(StateRepository<T> repository) {
        ConsecutiveSequenceStateMachine<T> savedState = repository.loadState();
        if (savedState != null) {
            _consecutiveCounts = savedState._consecutiveCounts;
            _maxConsecutiveCount = savedState._maxConsecutiveCount;
        }
    }
}
