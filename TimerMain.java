package comprehensive;

import assign10.Question3Timing;

public class TimerMain {

    public static void main(String[] args){

        int[] nSizes = new int[20];
        int index = 0;
        for(int i = 10000; i <= 200000; i+=10000){
            nSizes[index] = i;
            index++;
        }

        var timer = new TextGenTimer(nSizes, 100);
        var results = timer.run();

        System.out.println("n, time");

        for(var result: results){
            System.out.println(result.n() + ", " + result.avgNanoSecs());
        }














    }
}
