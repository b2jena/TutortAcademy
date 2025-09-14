package com.tutort.assignments.Multithreading.interviews;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Module 15: FAANG Interview Problems
 * Classic multithreading problems with optimal solutions
 */
public class Module15_InterviewProblems {

    public static void main(String[] args) throws Exception {
        oddEvenPrinting();
        fizzBuzzMultithreaded();
        printInOrder();
        buildingH2O();
    }

    // Problem 1: Print odd and even numbers alternately
    private static void oddEvenPrinting() throws InterruptedException {
        System.out.println("=== Odd-Even Printing ===");

        OddEvenPrinter printer = new OddEvenPrinter(10);

        Thread oddThread = new Thread(() -> {
            try {
                printer.printOdd();
            } catch (InterruptedException e) {
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                printer.printEven();
            } catch (InterruptedException e) {
            }
        });

        oddThread.start();
        evenThread.start();
        oddThread.join();
        evenThread.join();
        System.out.println();
    }

    // Problem 2: FizzBuzz Multithreaded (LeetCode 1195)
    private static void fizzBuzzMultithreaded() throws InterruptedException {
        System.out.println("=== FizzBuzz Multithreaded ===");

        FizzBuzz fizzBuzz = new FizzBuzz(15);

        Thread[] threads = {
                new Thread(() -> {
                    try {
                        fizzBuzz.fizz();
                    } catch (InterruptedException e) {
                    }
                }),
                new Thread(() -> {
                    try {
                        fizzBuzz.buzz();
                    } catch (InterruptedException e) {
                    }
                }),
                new Thread(() -> {
                    try {
                        fizzBuzz.fizzbuzz();
                    } catch (InterruptedException e) {
                    }
                }),
                new Thread(() -> {
                    try {
                        fizzBuzz.number();
                    } catch (InterruptedException e) {
                    }
                })
        };

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println();
    }

    // Problem 3: Print in Order (LeetCode 1114)
    private static void printInOrder() throws InterruptedException {
        System.out.println("=== Print in Order ===");

        Foo foo = new Foo();

        Thread[] threads = {
                new Thread(() -> {
                    try {
                        foo.third();
                    } catch (InterruptedException e) {
                    }
                }),
                new Thread(() -> {
                    try {
                        foo.first();
                    } catch (InterruptedException e) {
                    }
                }),
                new Thread(() -> {
                    try {
                        foo.second();
                    } catch (InterruptedException e) {
                    }
                })
        };

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println();
    }

    // Problem 4: Building H2O (LeetCode 1117)
    private static void buildingH2O() throws InterruptedException {
        System.out.println("=== Building H2O ===");

        H2O h2o = new H2O();

        // Create H and O threads
        Thread[] threads = new Thread[9]; // 6H + 3O = 3 H2O molecules

        for (int i = 0; i < 6; i++) {
            threads[i] = new Thread(() -> {
                try {
                    h2o.hydrogen();
                } catch (InterruptedException e) {
                }
            });
        }

        for (int i = 6; i < 9; i++) {
            threads[i] = new Thread(() -> {
                try {
                    h2o.oxygen();
                } catch (InterruptedException e) {
                }
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println();
    }

    static class OddEvenPrinter {
        private final int max;
        private final Object lock = new Object();
        private int current = 1;

        public OddEvenPrinter(int max) {
            this.max = max;
        }

        public void printOdd() throws InterruptedException {
            synchronized (lock) {
                while (current <= max) {
                    while (current % 2 == 0 && current <= max) {
                        lock.wait();
                    }
                    if (current <= max) {
                        System.out.print(current + " ");
                        current++;
                        lock.notify();
                    }
                }
            }
        }

        public void printEven() throws InterruptedException {
            synchronized (lock) {
                while (current <= max) {
                    while (current % 2 == 1 && current <= max) {
                        lock.wait();
                    }
                    if (current <= max) {
                        System.out.print(current + " ");
                        current++;
                        lock.notify();
                    }
                }
            }
        }
    }

    static class FizzBuzz {
        private final int n;
        private final Object lock = new Object();
        private int current = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        public void fizz() throws InterruptedException {
            synchronized (lock) {
                while (current <= n) {
                    while (current <= n && (current % 3 != 0 || current % 15 == 0)) {
                        lock.wait();
                    }
                    if (current <= n) {
                        System.out.print("fizz ");
                        current++;
                        lock.notifyAll();
                    }
                }
            }
        }

        public void buzz() throws InterruptedException {
            synchronized (lock) {
                while (current <= n) {
                    while (current <= n && (current % 5 != 0 || current % 15 == 0)) {
                        lock.wait();
                    }
                    if (current <= n) {
                        System.out.print("buzz ");
                        current++;
                        lock.notifyAll();
                    }
                }
            }
        }

        public void fizzbuzz() throws InterruptedException {
            synchronized (lock) {
                while (current <= n) {
                    while (current <= n && current % 15 != 0) {
                        lock.wait();
                    }
                    if (current <= n) {
                        System.out.print("fizzbuzz ");
                        current++;
                        lock.notifyAll();
                    }
                }
            }
        }

        public void number() throws InterruptedException {
            synchronized (lock) {
                while (current <= n) {
                    while (current <= n && (current % 3 == 0 || current % 5 == 0)) {
                        lock.wait();
                    }
                    if (current <= n) {
                        System.out.print(current + " ");
                        current++;
                        lock.notifyAll();
                    }
                }
            }
        }
    }

    static class Foo {
        private final CountDownLatch firstDone = new CountDownLatch(1);
        private final CountDownLatch secondDone = new CountDownLatch(1);

        public void first() throws InterruptedException {
            System.out.print("first ");
            firstDone.countDown();
        }

        public void second() throws InterruptedException {
            firstDone.await();
            System.out.print("second ");
            secondDone.countDown();
        }

        public void third() throws InterruptedException {
            secondDone.await();
            System.out.print("third ");
        }
    }

    static class H2O {
        private final Semaphore hydrogenSemaphore = new Semaphore(2);
        private final Semaphore oxygenSemaphore = new Semaphore(0);
        private final CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.print("H2O ");
            oxygenSemaphore.release();
            hydrogenSemaphore.release(2);
        });

        public void hydrogen() throws InterruptedException {
            hydrogenSemaphore.acquire();
            System.out.print("H");
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
            }
        }

        public void oxygen() throws InterruptedException {
            oxygenSemaphore.acquire();
            System.out.print("O");
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
            }
        }
    }
}