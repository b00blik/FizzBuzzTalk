# FizzBuzz Talk source code samples

## Summary

This repository contains few implementations of FizzBuzz problem in C and Java for my public talk: [Youtube](https://youtu.be/hA2RsVVHPbE)

Here you can find [slides](https://b00blik.ru/pres/FizzBuzzTalk_en_FCC.pdf)

### FizzBuzzMultithreading

Multithreading naive implementation of FizzBuzz

### FizzBuzzBitsModulo

Manually implemented algorithm of checking given number for multiplicity

### FizzBuzzMultithreadingCounters

Multithreading implementation of FizzBuzz using counters instead of % operation

## Compilation

For C examples, GNU C Compiler has been used:

```
gcc FizzBuzzMultithreading.c -o multitreading_naive.out
```

or, if it's necessary to use compiler opitimazion, a special flag can be added:

```
gcc -Os FizzBuzzMultithreading.c -o multitreading_naive.out
```

For getting assembly code file, following way can be used:

```
gcc -Os -S FizzBuzzMultithreading.c -o multithreading.asm
```

For compilation Java source code examples, we can use _javac_ as usual:

```
javac FizzBuzzMultithreadingCounters.java
```
