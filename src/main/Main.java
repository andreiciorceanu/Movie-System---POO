package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        String username = null, title = null, message = null;
        double grade;
        int seasonNumber;

        for (ActionInputData action : input.getCommands()) {

            if (action.getActionType().equals(Constants.COMMAND)) {

                username = action.getUsername();
                title = action.getTitle();

                if (action.getType().equals(Constants.FAVORITE)) {
                    message = Command.favorite(input, username, title);
                    JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), message);
                    arrayResult.add(jsonObject);
                }

                if (action.getType().equals(Constants.VIEW)) {
                    message = Command.view(input, username, title);
                    JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), message);
                    arrayResult.add(jsonObject);
                }

                if (action.getType().equals(Constants.RATING)) {
                    grade = action.getGrade();
                    seasonNumber = action.getSeasonNumber();
                    if (seasonNumber == 0) {
                        message = Command.rateMovie(input, username, title, grade);
                    } else {
                        message = Command.rateShow(input, username, title, grade);
                    }
                    JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), message);
                    arrayResult.add(jsonObject);
                }
            }

            if (action.getActionType().equals(Constants.QUERY)) {
            username = action.getUsername();
            title = action.getTitle();
                if (action.getCriteria().equals(Constants.AWARDS)) {
                message = Query.awards(input, action.getSortType(), action.getFilters().get(3));
                    JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), message);
                    arrayResult.add(jsonObject);
            }
                if (action.getCriteria().equals(Constants.FILTER_DESCRIPTIONS)) {
                    //message = Query.filterdescription(input, action.getSortType(), action.getFilters().get(4));
                    JSONObject jsonObject = fileWriter.writeFile(action.getActionId(), message);
                    arrayResult.add(jsonObject);
                }
            }

        }

        fileWriter.closeJSON(arrayResult);
    }
}
