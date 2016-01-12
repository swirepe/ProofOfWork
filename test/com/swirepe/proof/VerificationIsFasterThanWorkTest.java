package com.swirepe.proof;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.junit.Test;

public class VerificationIsFasterThanWorkTest {
  private static final Logger logger = Logger.getLogger(VerificationIsFasterThanWorkTest.class.getName());
  
  @Test
  public void testVerificationIsFasterThanWork() throws NoSuchAlgorithmException {
    byte[] data = "Hello world".getBytes();
    long start = System.currentTimeMillis();
    ProofOfWorkGenerator generator = new ProofOfWorkGenerator();
    ProofOfWork proof = generator.generate(data);
    long generationTime = System.currentTimeMillis() - start;
    logger.info("Time to complete work: " + generationTime);
    
    start = System.currentTimeMillis();
    assertTrue(ProofOfWorkVerifier.verify(data, proof));
    long verificationTime = System.currentTimeMillis() - start;
    logger.info("Time to complete verification: " + verificationTime);
    
    assertTrue(generationTime > verificationTime);
  }
  
  @Test
  public void testAverageOverTenTrials() throws NoSuchAlgorithmException {
    averageOverTrials(10);
  }
  
  @Test
  public void testAverageOverOneHundredTrials() throws NoSuchAlgorithmException {
    averageOverTrials(100);
  }
  
  public void averageOverTrials(int trials) throws NoSuchAlgorithmException {
    double generationTime = 0.0;
    double verificationTime = 0.0;
    byte[] data = "Hello, trials!".getBytes();
    ProofOfWork proof = null;
    ProofOfWorkGenerator generator = new ProofOfWorkGenerator(4, 3); // three leading zeros, or this will take all day
    long start = 0;
    for (int i = 0; i < trials; i++) {
      start = System.currentTimeMillis();
      proof = generator.generate(data);
      generationTime += System.currentTimeMillis() - start;
      start = System.currentTimeMillis();
      assertTrue(ProofOfWorkVerifier.verify(data, proof));
      verificationTime += System.currentTimeMillis() - start;
    }
    generationTime /= trials;
    verificationTime /= trials;
    logger.info("Trials: " + trials 
        + ", Average generation time: " + generationTime + "ms"
        + ", Average verification time: " + verificationTime + "ms");
    assertTrue(generationTime > verificationTime);
  }
}
