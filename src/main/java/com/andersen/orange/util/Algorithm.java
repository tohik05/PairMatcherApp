package com.andersen.orange.util;

import com.andersen.orange.exception.NoMorePairException;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

@Component
public class Algorithm {

    public Pair findPair(List<User> presentUsers) {
        List<User> notInterviewed = interviewedCheck(presentUsers);
        if (notInterviewed.size() <= 1) {
            throw new NoMorePairException("All the students have already answered today");
        }

        List<User> startUserList = getSortedUsers(presentUsers);

        int lowerPriorityUser = startUserList.get(0).getPairs().size();
        List<User> samePriorityStartList = findMinValueFromList(lowerPriorityUser, startUserList);

        Random random = new Random();
        User mainUser = startUserList.get(random.nextInt(samePriorityStartList.size()));

        List<User> opponentList = startUserList.stream()
                .filter(Predicate.not(user -> user.getTeam().equals(mainUser.getTeam())))
                .sorted(Comparator.comparingInt(user -> user.getPairs().size()))
                .toList();

        Map<User, Integer> opponentPriorityMap = createOpponentPriorityMap(mainUser, opponentList);

        int minValue = Collections.min(opponentPriorityMap.values());
        List<User> priorityUsers = findMinValueFromMap(minValue, opponentPriorityMap);

        User opponentUser = priorityUsers.get(random.nextInt(priorityUsers.size()));
        List<User> pair = new ArrayList<>();
        pair.add(mainUser);
        pair.add(opponentUser);
        return Pair.builder()
                .users(pair)
                .build();
    }

    private List<User> getSortedUsers(List<User> presentUsers) {
        return presentUsers.stream()
                .sorted(Comparator.comparingInt(user -> user.getPairs().size()))
                .toList();
    }

    private Map<User, Integer> createOpponentPriorityMap(User firstUser, List<User> OpponentList) {
        Map<User, Integer> opponentMeetingsCount = new HashMap<>();
        for (User opponent : OpponentList) {
            int count = 0;
            for (Pair userPair : firstUser.getPairs()) {
                for (User user : userPair.getUsers()) {
                    if (opponent.equals(user)) {
                        count++;
                        break;
                    }
                }
            }
            opponentMeetingsCount.put(opponent, count);
        }
        return opponentMeetingsCount;
    }

    private List<User> interviewedCheck(List<User> presentUsers) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        return presentUsers.stream()
                .filter(Predicate.not(user -> user.getPairs().stream()
                        .anyMatch(duel -> duel.getDate().toString().equals(formattedDate))))
                .toList();
    }

    private List<User> findMinValueFromList(int minValue, List<User> starterList) {
        List<User> resultList = new ArrayList<>();
        for (User user : starterList) {
            if (minValue == user.getPairs().size()) {
                resultList.add(user);
            }
        }
        return resultList;
    }

    private List<User> findMinValueFromMap(int minValue, Map<User, Integer> map) {
        List<User> resultList = new ArrayList<>();
        for (Map.Entry<User, Integer> userMap : map.entrySet()) {
            if (minValue == userMap.getValue()) {
                resultList.add(userMap.getKey());
            }
        }
        return resultList;
    }
}

