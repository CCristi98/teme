package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import database.Actor;
import database.QueryActors;
import database.QueryVideos;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import fileio.MovieInputData;
import fileio.SerialInputData;

import org.json.simple.JSONArray;
import database.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
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


        Writer jsobj = new Writer(Constants.OUT_FILE);

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < input.getUsers().size(); ++i) {
            users.add(new User(input.getUsers().get(i).getUsername(),
                    input.getUsers().get(i).getSubscriptionType(),
                    input.getUsers().get(i).getHistory(),
                    input.getUsers().get(i).getFavoriteMovies()));
            //System.out.println(users.get(i));
        }

        ArrayList<MovieInputData> movies = new ArrayList<>();
        for (int i = 0; i < input.getMovies().size(); ++i) {
            movies.add(new MovieInputData(input.getMovies().get(i).getTitle(),
                    input.getMovies().get(i).getCast(),
                    input.getMovies().get(i).getGenres(),
                    input.getMovies().get(i).getYear(),
                    input.getMovies().get(i).getDuration()));
            //System.out.println(films.get(i));
        }
        ArrayList<SerialInputData> shows = new ArrayList<>();
        for (int i = 0; i < input.getSerials().size(); ++i) {
            shows.add(new SerialInputData(input.getSerials().get(i).getTitle(),
                    input.getSerials().get(i).getCast(),
                    input.getSerials().get(i).getGenres(),
                    input.getSerials().get(i).getNumberSeason(),
                    input.getSerials().get(i).getSeasons(),
                    input.getSerials().get(i).getYear()));
            //System.out.println(shows.get(i));
        }

        ArrayList<Actor> actors = new ArrayList<>();
        for (int i = 0; i < input.getActors().size(); ++i) {
            actors.add(new Actor(input.getActors().get(i).getName(),
                    input.getActors().get(i).getCareerDescription(),
                    input.getActors().get(i).getFilmography(),
                    input.getActors().get(i).getAwards()));
            //System.out.println(actors.get(i));
        }


        for (int i = 0; i < input.getCommands().size(); ++i) {
            for (int j = 0; j < input.getUsers().size(); ++j) {
                if (input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {

                    if (input.getCommands().get(i).getType().equals(Constants.FAVORITE)) {

                        if (users.get(j).getUsername().equals(input.getCommands().get(i)
                                .getUsername())) {

                            users.get(j).favorite(input.getCommands().get(i).getTitle());
                            arrayResult.add(jsobj.writeFile(
                                    input.getCommands().get(i).getActionId(), "message:",
                                    users.get(j).favoriteMessage(
                                            input.getCommands().get(i).getTitle())));
                            break;
                        }
                    } else if (input.getCommands().get(i).getType().equals(Constants.VIEW)) {

                        if (users.get(j).getUsername().equals(input.getCommands().get(i)
                                .getUsername())) {

                            users.get(j).view(input.getCommands().get(i).getTitle());
                            arrayResult.add(jsobj.writeFile(
                                    input.getCommands().get(i).getActionId(), "message:",
                                    users.get(j).viewMessage(
                                            input.getCommands().get(i).getTitle())));
                            break;
                        }

                    } else if (input.getCommands().get(i).getType().equals(Constants.RATING)) {

                        if (users.get(j).getUsername().equals(input.getCommands().get(i)
                                .getUsername())) {

                            arrayResult.add(jsobj.writeFile(input.getCommands().get(i)
                                            .getActionId(), "message:",
                                    users.get(j).ratingMessage(
                                            input.getCommands().get(i), movies, shows)));
                        }
                    }
                }
            }
            if (input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {

                if (input.getCommands().get(i).getObjectType().equals(Constants.ACTORS)) {
                    QueryActors qA = new QueryActors();

                    if (input.getCommands().get(i).getCriteria().equals(Constants.AVERAGE)) {

                        arrayResult.add(jsobj.writeFile(input.getCommands().get(i).getActionId(),
                                "message:", qA.average(input.getCommands().get(i), users, actors)));

                    } else if (input.getCommands().get(i).getCriteria().equals(Constants.AWARDS)) {
                        arrayResult.add(jsobj.writeFile(input.getCommands().get(i).getActionId(),
                                "message:", qA.awards(input.getCommands().get(i), actors)));

                    } else if (input.getCommands().get(i).getCriteria().equals(
                            Constants.FILTER_DESCRIPTIONS)) {

                        arrayResult.add(jsobj.writeFile(input.getCommands().get(i).getActionId(),
                                "message:", qA.filterDescription(
                                        input.getCommands().get(i), actors)));
                    }

                } else if (input.getCommands().get(i).getCriteria().equals(Constants.RATINGS)) {

                    QueryVideos qV = new QueryVideos();
                    qV.addVideos(input.getCommands().get(i), users);

                    if (input.getCommands().get(i).getObjectType().equals(Constants.MOVIES)) {

                        arrayResult.add(jsobj.writeFile(input.getCommands().get(i).getActionId(),
                                "message:", qV.ratingMovie(input.getCommands().get(i))));

                    } else if (input.getCommands().get(i).getObjectType().equals(Constants.SHOWS)) {

                        arrayResult.add(jsobj.writeFile(input.getCommands().get(i).getActionId(),
                                "message:", qV.ratingShow(input.getCommands().get(i))));
                    }
                }
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
