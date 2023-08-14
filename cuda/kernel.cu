#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <stdio.h>
#include <chrono>

__global__ void fizzBuzzKernel(int *matrix, int n) {
    int row = blockIdx.y * blockDim.y + threadIdx.y;
    int col = blockIdx.x * blockDim.x + threadIdx.x;
    int tid = row * n + col + 1; // Compute the number for this thread

    if (tid <= n * n) {
        if (tid % 15 == 0) {
            matrix[row * n + col] = 0; // 0 indicates "FizzBuzz"
        } else if (tid % 3 == 0) {
            matrix[row * n + col] = 1; // 1 indicates "Fizz"
        } else if (tid % 5 == 0) {
            matrix[row * n + col] = 2; // 2 indicates "Buzz"
        } else {
            matrix[row * n + col] = tid; // Number itself
        }
    }
}

int main() {
    const int n = 10000; // Set the size of the matrix (n x n)

    // Allocate memory on the host for the matrix
    int *host_matrix = (int*)malloc(n * n * sizeof(int));

    // Allocate memory on the device for the matrix
    int *device_matrix;
    cudaMalloc((void**)&device_matrix, n * n * sizeof(int));

    auto start_time = std::chrono::high_resolution_clock::now();

    // Launch the CUDA kernel with 2D grid and block dimensions
    dim3 block_dim(32, 32);
    dim3 grid_dim((n + block_dim.x - 1) / block_dim.x, (n + block_dim.y - 1) / block_dim.y);
    fizzBuzzKernel<<<grid_dim, block_dim>>>(device_matrix, n);
    auto end_time = std::chrono::high_resolution_clock::now();

    // Copy the matrix from the device to the host
    cudaMemcpy(host_matrix, device_matrix, n * n * sizeof(int), cudaMemcpyDeviceToHost);

    // Display the results
    for (int i = 1; i < 10; i++) {
        if (host_matrix[i] == 0) {
            printf("FizzBuzz\n");
        } else if (host_matrix[i] == 1) {
            printf("Fizz\n");
        } else if (host_matrix[i] == 2) {
            printf("Buzz\n");
        } else {
            printf("%d\n", host_matrix[i]);
        }
    }

    auto duration_ns = std::chrono::duration_cast<std::chrono::nanoseconds>(end_time - start_time).count();

    printf("Execution time ns: %lld\n", duration_ns);

    // Free device and host memory
    cudaFree(device_matrix);
    free(host_matrix);

    return 0;
}