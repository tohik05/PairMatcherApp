package com.andersen.orange.pair.service;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PairMatcherAlgorithm {
    private final PairService pairService;

    public PairMatcherAlgorithm(PairService service) {
        this.pairService = service;
    }

    public List<Pair> pairMatcher(List<User> users) {
        List<Pair> pairs = new ArrayList<>();
        List<User> sortedUserWithPairs = getUsersPairsAndSort(users);

        for (User user : sortedUserWithPairs) {
            pairs.add(createPair(user, sortedUserWithPairs));
        }
        return pairs;
    }

    private Pair createPair(User userForPair, List<User> users) {
        List<User> usersOpponents = filterUsersFromSameTeam(users, userForPair);

        if (userForPair.getPairs().size() == 0) {
            Pair pair = new Pair(usersOpponents.get(0).getId(), new Date(), userForPair);
            return pair;
        } else {
           return userPairsHandler(userForPair, users);
        }
    }

    private Pair userPairsHandler(User userForPair, List<User> opponents) {
        Map<Long, Integer> opponentPairCount = new HashMap<>();

        for (User opponent : opponents) {
            opponentPairCount.put(opponent.getId(), 0);
        }

        for (Pair pair : userForPair.getPairs()) {
            Long opponentID = pair.getOpponentId();
            opponentPairCount.put(opponentID, opponentPairCount.get(opponentID) + 1);
        }

        Long nextOpponentID = Collections
                .min(opponentPairCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        User opponent = opponents.stream()
                .filter(user -> user.getId() == nextOpponentID)
                .findFirst().get();

        return new Pair(opponent.getId(), new Date(), userForPair);
    }

    private List<User> filterUsersFromSameTeam(List<User> users, User user) {
        return users.stream()
                .filter(u -> !u.getTeam().equals(user.getTeam()))
                .toList();
    }

    private List<User> getUsersPairsAndSort(List<User> users) {
        for (User user : users) {
            user.setPairs(pairService.getAllUserPairs(user));
        }

        return users.stream()
                .sorted(Comparator.comparingInt(u -> u.getPairs().size()))
                .toList();
    }
}
