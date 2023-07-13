import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FizzBuzzMultithreading {
    private int n;

    public FizzBuzzMultithreading(int n) {
        this.n = n;
    }

    public void runFizzBuzz() {

        long startTime = System.nanoTime();

        int block_size = n / 8;
        int[] blocks = {1, block_size + 1, 2 * block_size + 1, 3 * block_size + 1, 4 * block_size + 1, 5 * block_size + 1, 6 * block_size + 1, 7 * block_size + 1};
        String[] result = new String[100_000_000];

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CompletableFuture[] futures = new CompletableFuture[8];

        for (int i = 0; i < 8; i++) {
            final int start = blocks[i];
            final int end = i == 7 ? n + 1 : blocks[i + 1];
            futures[i] = CompletableFuture.runAsync(() -> {
                    for (int j = start; j < end; j++) {
                        if (j % 3 == 0 && j % 5 == 0) {
                            result[j-1] = "FizzBuzz";
                        } else if (j % 3 == 0) {
                            result[j-1] = "Fizz";
                        } else if (j % 5 == 0) {
                            result[j-1] = "Buzz";
                        } else {
                            result[j-1] = "x";
                        }
                    }
            }, executorService);
        }

        CompletableFuture.allOf(futures).join();
        executorService.shutdown();

        long endTime = System.nanoTime();
        System.out.println("Time in millis: " + (endTime - startTime)/1000000);

            
    }

    public static void main(String[] args) {
        FizzBuzzMultithreading fb = new FizzBuzzMultithreading(100_000_000);
        fb.runFizzBuzz();
    }
}
