package org.hypnosaur.icfp2013.client;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

/**
 * A web client for http://icfpc2013.cloudapp.net/
 */
public class ICFPWebClient implements ICFPClient {

    public static final String SECRET = "0294RpfQiaTg5z0OjISr6glSKEMZHEp05IEbIR6k";

    private final WebServiceClient webServiceClient;

    protected ICFPWebClient(WebServiceClient webServiceClient) {
        this.webServiceClient = webServiceClient;
    }

    @Override
    public Problem[] myProblems() {
        try {
            Gson gson = new Gson();
            return gson.fromJson(
                    webServiceClient.call(
                            MethodName.myproblems.toString(), ""),
                    Problem[].class);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public EvalResponse evalProgramId(String id, String[] arguments) {
        return eval(EvalRequest.evalProgramId(id, arguments));
    }

    @Override
    public EvalResponse evalProgramCode(String programCode, String[] arguments) {
        return eval(EvalRequest.evalProgramCode(programCode, arguments));
    }

    @Override
    public GuessResponse guess(String id, String programCode) {
        return guess(new GuessRequest(id, programCode));
    }

    @Override
    public TrainingProblem train(int size, String[] operators) {
        return train(new TrainRequest(size, operators));
    }

    @Override
    public String status() {
        try {
            return webServiceClient.call(MethodName.status.toString(),
                    new StatusRequest().toJson());
        } catch (Exception ex) {
            Throwables.propagate(ex);
        }
        return "nil";
    }

    private EvalResponse eval(EvalRequest evalRequest) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(
                    webServiceClient.call(
                            MethodName.eval.toString(), evalRequest.toJson()),
                    EvalResponse.class);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    private GuessResponse guess(GuessRequest guessRequest) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(
                    webServiceClient.call(
                            MethodName.guess.toString(),
                            guessRequest.toJson()),
                    GuessResponse.class);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    private TrainingProblem train(TrainRequest trainRequest) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(
                    webServiceClient.call(
                            MethodName.train.toString(),
                            trainRequest.toJson()),
                    TrainingProblem.class);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }


    public static ICFPClient create() {
        return ICFPWebClient.create(SECRET);
    }

    public static ICFPClient create(String secret) {
        return new ICFPWebClient(new WebServiceClient(secret));
    }

    /**
     * Available web service methods.
     */
    public static enum MethodName {

        myproblems, eval, guess, train, status
    }
}
