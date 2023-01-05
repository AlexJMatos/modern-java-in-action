package chapter6;

import entities.Dish;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

import static entities.Dish.menu;

public class MultiLevelGrouping {
    public static void main(String[] args) {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = dishesByTypeCaloricLevel();
        System.out.println("Dishes by Type Caloric Level: " + dishesByTypeCaloricLevel);
        Map<Dish.Type, Long> typesCount = typesCount();
        System.out.println("Count of types: " + typesCount);
        Map<Dish.Type, Dish> mostCaloricDishByType = mostCaloricDishByType();
        System.out.println("Most Caloric Dish By Type: " + mostCaloricDishByType);
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = caloricLevelsByType();
        System.out.println("Caloric Levels By Food Type: " + caloricLevelsByType);
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel() {
        return menu.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
            if (dish.getCalories() <= 400) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        })));
    }

    private static Map<Dish.Type, Long> typesCount() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Dish> mostCaloricDishByType() {
        return menu.stream().collect(toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories))));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
            if (dish.getCalories() <= 400) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        }, toSet())));
    }
}
