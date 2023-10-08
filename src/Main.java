import statemachine.ConsecutiveSequenceStateMachine;
import statemachine.dao.StateRepository;
import statemachine.dao.impl.InMemoryStateRepository;

public class Main {

    public static void main(String[] args) {
        // create state machine, set max consecutive count
        ConsecutiveSequenceStateMachine<Character> stateMachine
                = new ConsecutiveSequenceStateMachine<>(3);

        // first input
        char[] inputSequence
                = {'1', '0', '1', '0', '1', '1', '1'};

        for (char input : inputSequence) {
            stateMachine.processInput(input);
        }

        // save the state
        StateRepository<Character> repository = new InMemoryStateRepository<>();
        stateMachine.saveState(repository);

        // Reset the state
        ConsecutiveSequenceStateMachine<Character> newStateMachine = new ConsecutiveSequenceStateMachine<>(3);
        newStateMachine.resetState(repository);

        // input continues after reset
        char[] secondInputSequence = {'1', '1', '1', '0', '0', '0', '1', '1', '0', '0'};

        for (char input : secondInputSequence) {
            stateMachine.processInput(input);
        }

        // save the state again
        stateMachine.saveState(repository);

        // Reset the state
        newStateMachine.resetState(repository);

        // input continues after reset
        char[] thirdInputSequence = {'0', '0'};

        for (char input : thirdInputSequence) {
            stateMachine.processInput(input);
        }
    }
}
