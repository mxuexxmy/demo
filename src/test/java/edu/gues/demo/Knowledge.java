package edu.gues.demo;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/10/20 1:33
 * @version: 1.0
 */
public class Knowledge {

    public static final Integer ANSWER = 42;

    public static final String CREW_CODE = "00011";

    public String askQuestion(String question) {
        return "The answer to '" + question + "' is: " + ANSWER;
    }

    public String askQuestion1(String question) {
        return "The answer to '" + question + "' is: " + CREW_CODE;
    }


}
