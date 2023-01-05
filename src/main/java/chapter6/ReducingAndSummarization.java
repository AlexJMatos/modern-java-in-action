package chapter6;

import entities.Dish;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

import static entities.Dish.menu;
import static java.util.stream.Collectors.*;

public class ReducingAndSummarization {
    public static void main(String[] args) {
        System.out.println("Number of dishes in the menu: " + (Long) (long) menu.size());

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCaloricDish = menu.stream().max(dishCaloriesComparator);
        Optional<Dish> lessCaloricDish = menu.stream().min(dishCaloriesComparator);

        System.out.println("Most Caloric Dish: " + mostCaloricDish);
        System.out.println("LessCaloricDish: " + lessCaloricDish);

        int totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("Total Calories in the whole menu: " + totalCalories);

        double averageCaloriesInMenu = menu.stream().collect(averagingDouble(Dish::getCalories));
        System.out.printf("Average Calories for dish in the menu: %.2f%n", averageCaloriesInMenu);

        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("Statistics for the menu: " + menuStatistics);

        String allDishesNames = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println("All dishes in the menu: " + allDishesNames);
    }
}
