package com.swirepe.proof;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class VerifyCorrectWorkTest {

  @Test
  public void testCorrectWork() throws NoSuchAlgorithmException {
    byte[] data = "Hello world".getBytes();
    ProofOfWorkGenerator generator = new ProofOfWorkGenerator();
    ProofOfWork proof = generator.generate(data);
    assertTrue(ProofOfWorkVerifier.verify(data, proof));
  }
}
