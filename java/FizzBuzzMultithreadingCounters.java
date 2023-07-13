import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FizzBuzzMultithreadingCounters {
    private int n;

    public FizzBuzzMultithreadingCounters(int n) {
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
                int ct_3 = start % 3;
                int ct_5 = start % 5;
                for (int j = start; j<end; j++) {
                    if (ct_3 == 3) {
                        result[j - 1] = "Fizz";
                        ct_3 = 0;
                    }

                    if (ct_5 == 5) {
                        result[j - 1] = "Buzz";
                        ct_5 = 0;
                    }

                    if (ct_5 == 0 && ct_3 == 0) {
                        result[j - 1] = "FizzBuzz";
                        ct_3 = 1;
                        ct_5 = 1;
                        continue;
                    }

                    if (ct_5 != 0 && ct_3 != 0) {
                        result[j - 1] = "x";
                    }

                    ct_3++;
                    ct_5++;
                }

            }, executorService);
        }

        CompletableFuture.allOf(futures).join();
        executorService.shutdown();

        long endTime = System.nanoTime();
        System.out.println("Time in millis: " + (endTime - startTime)/1000000);

            
    }

    public static void main(String[] args) {
        FizzBuzzMultithreadingCounters fb = new FizzBuzzMultithreadingCounters(100_000_000);
        fb.runFizzBuzz();
    }
}
