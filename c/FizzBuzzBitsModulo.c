#include <stdio.h>
#include <time.h>

int modulo5(int x) {
        int divider = 5;
        int diff = x;

        while (diff > 5) {

            while (divider < x) {
                divider = divider << 1;
            }
            divider = divider >> 1;

            diff = x - divider;
            x = diff;

            divider = 5;
        }

        return diff == 5;
}

int modulo3(int x) {
        int divider = 3;
        int diff = x;

        while (diff > 3) {

            while (divider < x) {
                divider = divider << 1;
            }
            divider = divider >> 1;

            diff = x - divider;
            x = diff;

            divider = 3;
        }

        return diff == 3;
}

int main(void) {
    clock_t t;
    t = clock();

    for (int i = 1; i <= 15000000; i++) {
        if (modulo3(i)) {
            if (modulo5(i)) {
                printf("FizzBuzz\n");
            } else {
                printf("Fizz\n");
            }
        } else if (modulo5(i)) {
            printf("Buzz\n");
        } else {
            printf("%d\n", i);
        }
    }

    t = clock() - t;
    double time_taken = ((double)t)/CLOCKS_PER_SEC * 1000;
    printf("Time in millis: %f\n", time_taken);

    return 0;
}