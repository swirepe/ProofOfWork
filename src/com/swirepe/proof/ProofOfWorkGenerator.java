package com.swirepe.proof;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProofOfWorkGenerator {
  private static final int DEFAULT_NUM_THREADS = 16;
  private static final int DEFAULT_LEADING_ZEROS = 5;
  private final ExecutorService threadpool;

  private final int leadingZeros;
  private final int numThreads;
  
  public ProofOfWorkGenerator() {
    this(DEFAULT_NUM_THREADS, DEFAULT_LEADING_ZEROS);
  }

  public ProofOfWorkGenerator(int numTheads, int leadingZeros) {
    this.numThreads = numTheads;
    this.leadingZeros = validateLeadingZeros(leadingZeros);
    threadpool = Executors.newFixedThreadPool(numThreads);
  }

  private int validateLeadingZeros(int leadingZeros) {
    if (leadingZeros <= 0 || leadingZeros > 32) {
      return DEFAULT_LEADING_ZEROS;
    }
    return leadingZeros;
  }
  
  public ProofOfWork generate(final byte[] data) {
    Collection<Callable<ProofOfWork>> tasks = createTasks(data, this.leadingZeros);
    ProofOfWork proof = null;
    try {
      proof = threadpool.invokeAny(tasks);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return proof;
  }

  private Collection<Callable<ProofOfWork>> createTasks(final byte[] data, int numThreads) {
    Collection<Callable<ProofOfWork>> workers = new ArrayList<>(numThreads);
    for (int i = 0; i < numThreads; i++) {
      workers.add(new ProofWorker(data, this.leadingZeros));
    }
    return workers;
  }
}
