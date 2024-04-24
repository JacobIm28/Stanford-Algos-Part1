/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String word = "";
        String champion = word;
        double counter = 0;

        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            counter += 1;

            if (StdRandom.bernoulli(1 / counter)) {
                champion = word;
            }
        }

        System.out.println(champion);
    }
}
