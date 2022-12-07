package chapter3;

import entities.Apple;
import entities.Letter;
import entities.RGB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class LambdaExpressions {
    public static void main(String[] args) {
        // Functional Interfaces
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(List.of("Alex", "Basketball", ""), nonEmptyStringPredicate);
        System.out.println(nonEmpty);

        forEach(List.of(1, 2, 3, 4, 5), System.out::println);

        List<Integer> l = map(List.of("lambdas", "in", "action"), String::length);
        System.out.println(l);

        // Method references
        List<String> str = Arrays.asList("a", "b", "A", "B");
        str.sort(String::compareToIgnoreCase);

        String integer = "2";
        ToIntFunction<String> stringToInt = Integer::parseInt;
        Integer myValue = stringToInt.applyAsInt(integer);
        System.out.println(myValue);

        BiPredicate<List<String>, String> contains = List::contains;
        Boolean containsGivenString = contains.test(List.of("Hello"), "Hello");
        System.out.println(containsGivenString);

        Predicate<String> startsWithNumber = LambdaExpressions::startsWithNumber;
        Boolean startsWithGivenNumber = startsWithNumber.test("3test");

        List<Integer> weights = List.of(7, 3, 4, 10);
        List<Apple> apples = mapApples(weights, Apple::new);
        System.out.println(apples);

        BiFunction<Integer, Apple.Color, Apple> appleFactory = Apple::new;
        Apple apple = appleFactory.apply(234, Apple.Color.RED);
        System.out.println(apple);

        TriFunction<Integer, Integer, Integer, RGB> colorFactory = RGB::new;
        RGB rgb = colorFactory.apply(255, 255, 255);
        System.out.println(rgb);


        Predicate<Apple> weightComparator = (a1) -> a1.getWeight() > 150;
        Predicate<Apple> redApple = (a1) -> a1.getColor() == Apple.Color.RED;
        AppleInventory.inventory.sort(comparing(Apple::getWeight));
        System.out.println(AppleInventory.inventory.stream().filter(weightComparator.and(redApple)).collect(Collectors.toList()));

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        Function<Integer, Integer> e = f.compose(g);
        int result = h.apply(1);
        int result1 = e.apply(1);
        System.out.println(result);
        System.out.println(result1);

        // Transformation pipeline
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);
        System.out.println(transformationPipeline.apply("This is my message"));

        // Integration
        System.out.println(integrate((double x) -> x + 10, 3, 7));
    }

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        return (f.applyAsDouble(a) + f.applyAsDouble(b)) * (b - a) / 2.0;
    }

    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    public static List<Apple> mapApples(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer i : list) {
            result.add(f.apply(i));
        }
        return result;
    }

    public static boolean startsWithNumber(String myText) {
        return (myText.substring(0, 1)).matches("\\d+");
    }

    // Predicates
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                results.add(t);
            }
        }
        return results;
    }

    // Consumer
    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

    // Function
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}
