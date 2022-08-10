package chapter1;

import entities.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red")
        );

        /**
         * Return red apples
         */
        List<Apple> redApples = filterApples(inventory, FilteringApples::isRedApple);
        System.out.println("Red Apples: \n\t" + redApples);

        /**
         * Return lightweight apples (less than 150g)
         */
        List<Apple> lightApples = filterApples(inventory, FilteringApples::isLightweight);
        System.out.println("Apples that weight less than or equal 150g: \n\t" + lightApples);

        /**
         * Return lightweight green apples
         */
        List<Apple> lightAndGreenApples = filterApples(inventory, apple -> "green".equals(apple.getColor()) && apple.getWeight() <= 150);
        System.out.println("Apples that are green and weight less than 150g: \n\t" + lightAndGreenApples);
    }

    public static boolean isRedApple(Apple apple) {
        return "red".equals(apple.getColor());
    }

    public static boolean isLightweight(Apple apple) {
        return apple.getWeight() <= 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}
