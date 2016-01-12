package com.swirepe.proof;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.sun.istack.internal.logging.Logger;
import com.swirepe.proof.ProofOfWork;
import com.swirepe.proof.ProofOfWorkGenerator;
import com.swirepe.proof.ProofOfWorkVerifier;

public class VerificationIsFasterThanWorkTest {
  private static final Logger logger = Logger.getLogger(VerificationIsFasterThanWorkTest.class);
  
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
}
