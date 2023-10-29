package com.andersen.orange.util;

import com.andersen.orange.exception.NoMorePairException;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
    private final UserRepository userRepository;

    public Algorithm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Pair findPair(List<User> presentUsers) {
        List<User> notInterviewed = interviewedCheck(presentUsers);
        if (notInterviewed.size() <= 1) {
            throw new NoMorePairException("All the students already answered today");
        }

        System.out.println("=====================AFTER DATE CHECK========================");
        for (User user : notInterviewed) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getPairs());
        }

        List<User> startUserList = getSortedUsers(notInterviewed);

        int lowerPriorityUser = startUserList.get(0).getPairs().size();
        List<User> samePriorityStart = findMinValueFromList(lowerPriorityUser, startUserList);

        Random random = new Random();

        System.out.println("=====================OUR MAIN USER========================");
        User mainUser = startUserList.get(random.nextInt(samePriorityStart.size()));
        System.out.println(mainUser.getName() + " " + mainUser.getPairs());

        List<User> opponentList = startUserList.stream()
                .filter(Predicate.not(user -> user.getTeam().equals(mainUser.getTeam())))
                .sorted(Comparator.comparingInt(user -> user.getPairs().size()))
                .toList();

        Map<User, Integer> opponentPriorityMap = createOpponentPriorityMap(mainUser, opponentList);

        int minValue = Collections.min(opponentPriorityMap.values());


        List<User> priorityUsers = findMinValueFromMap(minValue, opponentPriorityMap);

        System.out.println("=====================OUR PRIORITY USERS AFTER USING MAP========================");
        for (User user : priorityUsers){
            System.out.println(user.getName() + " " + user.getPairs());
        }

        User opponentUser = priorityUsers.get(random.nextInt(priorityUsers.size()));

        return Pair.builder()
                .user(mainUser)
                .opponent(opponentUser)
                .build();
    }

    private List<User> getSortedUsers(List<User> presentUsers) {
        return presentUsers.stream()
                .sorted(Comparator.comparingInt(user -> user.getPairs().size()))
                .toList();
    }

    private Map<User, Integer> createOpponentPriorityMap(User firstUser, List<User> OpponentList) {
        List<Pair> userPairs = firstUser.getPairs();
        System.out.println("=====================MY RECENT PAIRS========================");
        for (Pair pair : userPairs) {
            System.out.println("MY OPPONENT WAS USER WITH ID " + pair.getOpponent());
        }

        Map<User, Integer> opponentMeetingsCount = new HashMap<>();
        for (User opponent : OpponentList) {
            int count = 0;
            boolean hasMet = false;
            for (Pair userPair : userPairs) {
                if (userPair.getOpponent().equals(opponent)) {
                    hasMet = true;
                    break;
                }
            }
            if (hasMet) {
                count++;
            }
            opponentMeetingsCount.put(opponent, count);
        }
        System.out.println("=====================AFTER CREATING OPPONENTS PRIORITY MAP========================");
        for (Map.Entry<User, Integer> user : opponentMeetingsCount.entrySet()) {
            System.out.println(user.getKey().getName() + " " + user.getValue());
        }

//        List<User> opponentPriorityList = new ArrayList<>(OpponentList);
//        opponentPriorityList.sort(Comparator
//                .<User>comparingLong(opponentMeetingsCount::get)
//                .thenComparing(User::getName, Comparator.naturalOrder()));

        return opponentMeetingsCount;
    }

    private List<User> interviewedCheck(List<User> presentUsers) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());

        System.out.println("+++++++++++++++++++++TODAY ALREADY DUELS++++++++++++++++++++++++++++");
        List<User> already = presentUsers.stream()
                .filter(user -> user.getPairs().stream()
                        .anyMatch(duel -> duel.getDate().toString().equals(formattedDate)))
                .toList();
        for (User user : already) {
            System.out.println(user.getName() + " " + user.getLastname() + " " + user.getPairs());
        }
        return presentUsers.stream()
                .filter(Predicate.not(user -> user.getPairs().stream()
                        .anyMatch(duel -> duel.getDate().toString().equals(formattedDate))))
                .toList();
    }

    private List<User> findMinValueFromList(int minValue, List<User> starterList){
        List<User> resultList = new ArrayList<>();
        for (User user : starterList) {
            if (minValue == user.getPairs().size()) {
                resultList.add(user);
            }
        }
        return resultList;
    }

    private List<User> findMinValueFromMap(int minValue, Map<User, Integer> map){
        List<User> resultList = new ArrayList<>();
        for (Map.Entry<User, Integer> userMap : map.entrySet()) {
            if (minValue == userMap.getValue()){
                resultList.add(userMap.getKey());
            }
        }
        return resultList;
    }
}

