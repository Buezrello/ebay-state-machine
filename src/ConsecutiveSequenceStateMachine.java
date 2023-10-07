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

    private final Map<T, CountAndState> consecutiveCounts = new HashMap<>();
    private final int _maxConsecutiveCount;

    public ConsecutiveSequenceStateMachine(int maxConsecutiveCount) {
        _maxConsecutiveCount = maxConsecutiveCount;
    }

    public void processInput(T input) {
        CountAndState countAndState = consecutiveCounts.getOrDefault(input, new CountAndState());
        int count = countAndState._counter + 1;
        State state = countAndState._state;
        consecutiveCounts.put(input, new CountAndState(count, state));

        if (count >= _maxConsecutiveCount && state != State.ERROR) {
            System.out.println("Error: Consecutive element (" + input.toString() + ") detected.");
            consecutiveCounts.put(input, new CountAndState(0, State.ERROR));
        }

        resetNonConsecutive(input);
    }

    private void resetNonConsecutive(T input) {
        for (T element : consecutiveCounts.keySet()) {
            if (!element.equals(input)) {
                consecutiveCounts.put(element, new CountAndState());
            }
        }
    }

    public static void main(String[] args) {
        ConsecutiveSequenceStateMachine<Character> stateMachine
                = new ConsecutiveSequenceStateMachine<>(3);

        char[] inputSequence
                = {'3', '1', '0', '1', '0', '3', '3', '3', '3', '1', '1', '1', '1', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0'};

        for (char input : inputSequence) {
            stateMachine.processInput(input);
        }
    }
}
