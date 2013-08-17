import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hypnosaur.icfp2013.brute.BruteForce;
import org.hypnosaur.icfp2013.client.EvalResponse;
import org.hypnosaur.icfp2013.client.ICFPClient;
import org.hypnosaur.icfp2013.client.ICFPWebClient;
import org.hypnosaur.icfp2013.client.TrainingProblem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 9:10 PM
 */
public class Main {
    public static ICFPClient icfpClient = ICFPWebClient.create("0550bjHM9InBehh47QR4leYJcjMAzvCh2p0G1mWL");

    public static LinkedList<String> readValues() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("values.txt"));

            Gson gson = new Gson();
            LinkedList<String> values = gson.fromJson(br, new TypeToken<LinkedList<String>>() {
            }.getType());

            return values;


        } catch (FileNotFoundException e) {
            System.out.println("Ceva");
        }
        return null;
    }

    public static void main(String[] args) {


        TrainingProblem trainingProblem = icfpClient.train(5, new String[1]);
        LinkedList<String> operators = new LinkedList<>(Arrays.asList(trainingProblem.getOperators()));

        System.out.println("New Training Problem: " + trainingProblem.getId());
        System.out.println("Size:" + trainingProblem.getSize());
        System.out.println("Operators:" + operators);

        LinkedList<String> values = readValues();
        Map<String, String> responses;

        responses = getResponses(trainingProblem.getId(), values);

        System.out.println(values);
        System.out.println(responses);


        BruteForce bruteForce = new BruteForce(responses);
        bruteForce.addOperator("not");
        bruteForce.addOperator("and");
        bruteForce.addOperator("xor");


        bruteForce.computeTreeForSize(5);
        bruteForce.printAll(5);


//        bruteForce

    }

    private static Map<String, String> getResponses(String id, LinkedList<String> values) {
        Map<String, String> responses = Maps.newHashMap();

        String[] inputs = values.toArray(new String[values.size()]);

        EvalResponse evalResponse = icfpClient.evalProgramId(id, inputs);

        String[] outputs = evalResponse.getOutputs();

        for (int i = 0; i < values.size(); i ++) {
            responses.put(values.get(i), outputs[i]);
        }

        return responses;
    }
}
