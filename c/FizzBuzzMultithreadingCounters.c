#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <time.h>
#include <sys/time.h>

#define THREAD_COUNT 24

struct thread_data
{
    int start;
    int end;
    char **output_array;
};

char fizzBuzzArray[9];
char fizzArray[8];
char buzzArray[8];
char xArr[2];

void *fizzbuzz(void *arg)
{
    struct thread_data *data = (struct thread_data *)arg;
    int i;

    short ct_3 = data->start % 3;
    short ct_5 = data->start % 5;

    for (i = data->start; i < data->end; i++)
    {

        if (ct_3 == 3)
        {
            data->output_array[i - 1] = fizzArray;
            ct_3 = 0;
        }

        if (ct_5 == 5)
        {
            data->output_array[i - 1] = buzzArray;
            ct_5 = 0;
        }

        if (ct_5 == 0 && ct_3 == 0)
        {
            data->output_array[i - 1] = fizzBuzzArray;
            ct_3 = 1;
            ct_5 = 1;
            continue;
        } 

        if (ct_5 != 0 && ct_3 != 0)
        {
            data->output_array[i - 1] = xArr;
        }

        ct_3++;
        ct_5++;
    }
    pthread_exit(NULL);
}

long currentTimeMillis()
{
    struct timeval time;
    gettimeofday(&time, NULL);

    return time.tv_sec * 1000 + time.tv_usec / 1000;
}

int main(int argc, char *argv[])
{

    int n = 100000000;
    int block_size = n / THREAD_COUNT;
    int blocks[THREAD_COUNT];
    pthread_t threads[THREAD_COUNT];
    struct thread_data thread_data_array[THREAD_COUNT];
    char **output_array = (char **)malloc(n * sizeof(char *));

    strcpy(fizzBuzzArray, "FizzBuzz");
    strcpy(fizzArray, "Fizz");
    strcpy(buzzArray, "Buzz");
    strcpy(xArr, "x");

    long startMillis = currentTimeMillis();

    for (int i = 0; i < THREAD_COUNT; i++)
    {
        blocks[i] = i * block_size + 1;
    }
    blocks[THREAD_COUNT - 1] = n + 1;

    for (int i = 0; i < THREAD_COUNT; i++)
    {
        thread_data_array[i].start = blocks[i];
        thread_data_array[i].end = blocks[i + 1];
        thread_data_array[i].output_array = output_array;
        int rc = pthread_create(&threads[i], NULL, fizzbuzz, (void *)&thread_data_array[i]);
        if (rc)
        {
            printf("Error: return code from pthread_create() is %d\n", rc);
            exit(-1);
        }
    }

    for (int i = 0; i < THREAD_COUNT; i++)
    {
        pthread_join(threads[i], NULL);
    }

    long millis = currentTimeMillis() - startMillis;
    printf("Wall time in millis: %ld\n", millis);

    
    free(output_array);

    return 0;
}
