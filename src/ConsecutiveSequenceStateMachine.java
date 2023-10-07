import java.util.HashMap;
import java.util.Map;

public class ConsecutiveSequenceStateMachine {
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

    private final Map<Character, CountAndState> consecutiveCounts = new HashMap<>();
    private final int _maxConsecutiveCount;

    public ConsecutiveSequenceStateMachine(int maxConsecutiveCount) {
        _maxConsecutiveCount = maxConsecutiveCount;
    }

    public void processInput(char input) {
        CountAndState countAndState = consecutiveCounts.getOrDefault(input, new CountAndState());
        int count = countAndState._counter + 1;
        State state = countAndState._state;
        consecutiveCounts.put(input, new CountAndState(count, state));

        if (count >= _maxConsecutiveCount && state != State.ERROR) {
            System.out.println("Error: Consecutive character (" + input + ") detected.");
            consecutiveCounts.put(input, new CountAndState(0, State.ERROR));
        }

        resetNonConsecutive(input);
    }

    private void resetNonConsecutive(char input) {
        for (char character : consecutiveCounts.keySet()) {
            if (character != input) {
                consecutiveCounts.put(character, new CountAndState());
            }
        }
    }

    public static void main(String[] args) {
        ConsecutiveSequenceStateMachine stateMachine = new ConsecutiveSequenceStateMachine(3);

        char[] inputSequence = {'3', '1', '0', '1', '0', '3', '3', '3', '3', '1', '1', '1', '1', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0'};

        for (char input : inputSequence) {
            stateMachine.processInput(input);
        }
    }
}
