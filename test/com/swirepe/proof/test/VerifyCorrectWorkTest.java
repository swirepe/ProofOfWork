package com.swirepe.proof.test;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.swirepe.proof.ProofOfWork;
import com.swirepe.proof.ProofOfWorkGenerator;
import com.swirepe.proof.ProofOfWorkVerifier;

public class VerifyCorrectWorkTest {

  @Test
  public void test() throws NoSuchAlgorithmException {
    byte[] data = "Hello world".getBytes();
    ProofOfWorkGenerator generator = new ProofOfWorkGenerator();
    ProofOfWork proof = generator.generate(data);
    assertTrue(ProofOfWorkVerifier.verify(data, proof));
  }

}
