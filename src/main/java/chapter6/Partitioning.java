package chapter6;

import entities.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static entities.Dish.menu;
import static java.util.stream.Collectors.*;

public class Partitioning {
    public static void main(String[] args) {
        System.out.println("Vegetarian and No Vegetarian Menu: " + vegetarianPartitionedMenu());
        System.out.println("Vegetarian Dishes By Type: " + vegetarianDishesByType());
        System.out.println("Most Caloric Partitioned By Vegetarian: " + mostCaloricPartitionedByVegetarian());
        Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(100);
        System.out.println("Prime numbers from 0 to 100: " + partitionPrimes.get(true));
        System.out.println("Non Prime number from 0 to 100: " + partitionPrimes.get(false));
    }

    private static Map<Boolean, List<String>> vegetarianPartitionedMenu() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, mapping(Dish::getName, toList())));
    }

    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    }

    private static Map<Boolean, Dish> mostCaloricPartitionedByVegetarian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
    }

    private static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(partitioningBy(Partitioning::isPrime));
    }

    private static Boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}
