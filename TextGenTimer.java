package comprehensive;

import java.util.ArrayList;
import java.util.Random;

public class TextGenTimer extends TimerTemplate{

    /**
     * Create a timer
     *
     * @param problemSizes array of N's to use
     * @param timesToLoop  number of times to repeat the tests
     */

    private ArrayList<String> inputs;
    private MarkovGraph markovGraph;
    public TextGenTimer(int[] problemSizes, int timesToLoop) {
        super(problemSizes, timesToLoop);

        inputs = new ArrayList<>();

    }

    /**
     * Do any work that needs to be done before your code can be timed
     * For example, fill in an array with N elements
     *
     * @param n problem size to be timed
     */
    @Override
    protected void setup(int n) {

        Random random = new Random(49);

        for (int i = 0; i < n; i++){
            inputs.add("0");
            inputs.add("" + random.nextInt(n));
        }

        markovGraph = new MarkovGraph(inputs);
        System.out.println("N: " + n);

    }

    /**
     * The code to be timed
     *
     * @param n the problem size to be timed
     */
    @Override
    protected void timingIteration(int n) {
        markovGraph.findKLargest("0",n);
    }

    /**
     * Any extra work done in timingIteration that should be subtracted out
     * when computing the time of what you actually care about
     *
     * @param n problem size being timed
     */
    @Override
    protected void compensationIteration(int n) {

    }
}
