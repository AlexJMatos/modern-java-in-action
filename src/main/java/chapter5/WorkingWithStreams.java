package chapter5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorkingWithStreams {
    private static final Trader raoul = new Trader("Raoul", "Cambridge");
    private static final Trader mario = new Trader("Mario", "Milan");
    private static final Trader alan = new Trader("Alan", "Cambridge");
    private static final Trader brian = new Trader("Brian", "Cambridge");
    private static final List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String[] args) {
        List<Transaction> transactionsIn2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.printf("All transactions in the year 2011 and sorted by value (small to high): %s%n", transactionsIn2011);

        List<String> uniqueCities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.printf("Unique cities where the traders work: %s%n", uniqueCities);

        List<Trader> tradersFromCambridgeOrderedByName = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.printf("Traders from Cambridge and sorted by name.: %s%n", tradersFromCambridgeOrderedByName);

        String tradersOrderedAlphabetically = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .map(Trader::getName)
                .collect(Collectors.joining(", "));
        System.out.printf("String of all traders names sorted alphabetically: %s%n", tradersOrderedAlphabetically);

        Boolean anyTraderInMilan = transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        System.out.printf("Are any traders based in Milan?: %s%n", anyTraderInMilan);

        System.out.printf("Values for transactions done in Cambridge: %s%n", transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> String.valueOf(transaction.getValue()))
                .sorted(Comparator.comparing(Integer::valueOf))
                .collect(Collectors.joining(", ")));

        System.out.printf("Highest value of all the transactions: %s%n", transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .map(Transaction::getValue).orElse(0));

        System.out.printf("Transaction with smallest value: %s%n", transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .orElse(null));

        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println("Count even numbers from 1 to 100: " + evenNumbers.count());

        // Pythagorean Triples
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                                IntStream.rangeClosed(a, 100)
                                        .mapToObj(
                                                b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                                        .filter(t -> t[2] % 1 == 0));

        pythagoreanTriples.limit(5)
                .forEach(t ->
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\Ksquare\\OneDrive - The Ksquare Group\\Desktop\\Java\\modern-java-in-action\\src\\main\\resources\\data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Stream.iterate(0, n -> n + 2)
//                .limit(10)
//                .forEach(System.out::println);

        String fib = Stream.iterate(new int[]{0, 1},
                        t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> String.valueOf(t[0]))
                .collect(Collectors.joining(", "));
        System.out.println("Fibonacci: " + fib);

        IntSupplier fibonacci = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        String fibValues = IntStream.generate(fibonacci).limit(10).mapToObj(String::valueOf).collect(Collectors.joining(", "));
        System.out.println("Fibonacci: " + fibValues);
    }
}
