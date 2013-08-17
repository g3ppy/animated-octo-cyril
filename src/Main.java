import org.hypnosaur.icfp2013.brute.BruteForce;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 9:10 PM
 */
public class Main {

    public static void main(String[] args) {
        BruteForce bruteForce = new BruteForce();
        bruteForce.addOperator("not");
        bruteForce.addOperator("and");
        bruteForce.addOperator("xor");


        bruteForce.computeTreeForSize(5);
        bruteForce.printAll(5);



//        bruteForce

    }
}
