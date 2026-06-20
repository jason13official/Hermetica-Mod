package io.github.jason13official.hermetica.impl.common.world.level.aura;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FloatStressTest {

  static final int READERS = 8;
  static final int RUNS = 100;
  static final int WRITE_COUNT = 5_000_000;

  // --- subjects ---
  static final AtomicInteger atomicBits = new AtomicInteger(Float.floatToRawIntBits(1.0f));
  static final AtomicReference<Float> atomicRef = new AtomicReference<>(1.0f);
  static volatile float volatileFloat = 1.0f;

  public static void main(String[] args) throws Exception {

    System.out.printf("%-20s %8s %8s %8s%n", "Run", "volatile", "AtomicBits", "AtomicRef");
    System.out.println("-".repeat(50));

    double sumV = 0, sumB = 0, sumR = 0;

    for (int run = 1; run <= RUNS; run++) {
      double tv = bench(run, Mode.VOLATILE) / 1e6;
      double tb = bench(run, Mode.ATOMIC_BITS) / 1e6;
      double tr = bench(run, Mode.ATOMIC_REF) / 1e6;
      sumV += tv;
      sumB += tb;
      sumR += tr;
      System.out.printf("%-20s %8.1f %10.1f %9.1f%n", "Run " + run, tv, tb, tr);
    }

    System.out.println("-".repeat(50));
    System.out.printf("%-20s %8.1f %10.1f %9.1f%n", "Avg", sumV / RUNS, sumB / RUNS, sumR / RUNS);
    System.out.println("-".repeat(50));
    System.out.println("times in ms | lower = faster");
  }

  private static long bench(int run, Mode mode) throws Exception {
    // reset
    volatileFloat = 1.0f;
    atomicBits.set(Float.floatToRawIntBits(1.0f));
    atomicRef.set(1.0f);

    CountDownLatch ready = new CountDownLatch(READERS + 1);
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(READERS + 1);

    // readers — spin-read as fast as possible, accumulate to prevent dead-code elim
    Thread[] readers = new Thread[READERS];
    long[] sums = new long[READERS]; // prevent elim

    for (int i = 0; i < READERS; i++) {
      final int idx = i;
      readers[idx] = new Thread(() -> {
        long acc = 0;
        ready.countDown();
        try {
          start.await();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        switch (mode) {
          case VOLATILE:
            for (int j = 0; j < WRITE_COUNT; j++) {
              acc += (long) volatileFloat;
            }
            break;
          case ATOMIC_BITS:
            for (int j = 0; j < WRITE_COUNT; j++) {
              acc += (long) Float.intBitsToFloat(atomicBits.get());
            }
            break;
          case ATOMIC_REF:
            for (int j = 0; j < WRITE_COUNT; j++) {
              acc += (long) atomicRef.get().floatValue();
            }
            break;
        }
        sums[idx] = acc;
        done.countDown();
      });
      readers[idx].start();
    }

    // writer
    Thread writer = new Thread(() -> {
      ready.countDown();
      try {
        start.await();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      for (int j = 0; j < WRITE_COUNT; j++) {
        float val = j * 0.001f;
        switch (mode) {
          case VOLATILE:
            volatileFloat = val;
            break;
          case ATOMIC_BITS:
            atomicBits.set(Float.floatToRawIntBits(val));
            break;
          case ATOMIC_REF:
            atomicRef.set(val);
            break;
        }
      }
      done.countDown();
    });
    writer.start();

    ready.await();
    long t0 = System.nanoTime();
    start.countDown();
    done.await();
    long elapsed = System.nanoTime() - t0;

    // prevent dead-code elimination
    long total = 0;
    for (long s : sums) {
      total += s;
    }
    if (total == Long.MAX_VALUE) {
      System.out.print(""); // sink
    }

    return elapsed;
  }

  enum Mode {VOLATILE, ATOMIC_BITS, ATOMIC_REF}
}
