package main;


import fileio.Input;
import fileio.UserInputData;

import java.util.Map;

public class Command {
    public static String favorite(final Input input, final String username, final String title) {
        UserInputData user = null;
        for (UserInputData useraux : input.getUsers()) {
            if (useraux.getUsername().equals(username)) {
                user = useraux;
                break;
            }
        }
        if (!(user.getHistory().containsKey(title))) {
            return "error -> " + title + " is not seen";
        }

        if (user.getFavoriteMovies().contains(title)) {
            return "error -> " + title + " is already in favourite list";
        }

        user.getFavoriteMovies().add(title);
        return "success -> " + title + " was added as favourite";
    }

    public static String view(final Input input, final String username, final String title) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                for (Map.Entry<String, Integer> history : user.getHistory().entrySet()) {
                    if (history.getKey().equals(title)) {
                        int nr = history.getValue();
                        nr = nr + 1;
                        history.setValue(nr);
                        return "success -> " + title + " was viewed with total views of " + history.getValue();
                    }
                }
                if (!(user.getHistory().containsKey(title))) {
                    user.getHistory().put(title, 1);
                    return "success -> " + title + " was viewed with total views of " + 1;
                }
            }
        }
        return null;
    }

    public static String rateMovie(final Input input, final String username, final String title, final double grade) {
        boolean ok = false;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                for (Map.Entry<String, Integer> history : user.getHistory().entrySet()) {
                    if (history.getKey().equals(title)) {
                        for (String s : user.getRates()) {
                            if (s.equals(title)) {
                                ok = true;
                                break;
                            }
                        }
                        if (ok) {
                            return "error -> " + title + " has been already rated";
                        } else {
                            user.getRates().add(title);
                            return "success -> " + title + " was rated with " + grade + " by " + username;
                        }
                    }
                }
                return "error -> " + title + " is not seen";
            }
        }
        return null;
    }

    public static String rateShow(final Input input, final String username, final String title, final double grade) {
        boolean ok = false;
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(username)) {
                for (Map.Entry<String, Integer> history : user.getHistory().entrySet()) {
                    if (history.getKey().equals(title)) {
                        for (String s : user.getRates()) {
                            if (s.equals(title))
                                ok = true;
                            break;
                        }
                    }
                    if (ok) {
                        return "error -> " + title + " has been already rated";
                    } else {
                        user.getRates().add(title);
                        return "success -> " + title + " was rated with " + grade + " by " + username;
                    }
                }
                return "error -> " + title + " is not seen";
            }
        }
        return null;
    }
}
