package com.swirepe.proof;

import static org.junit.Assert.assertFalse;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.swirepe.proof.ProofOfWork;
import com.swirepe.proof.ProofOfWorkGenerator;
import com.swirepe.proof.ProofOfWorkVerifier;

public class IncorrectVerificationTest {
  private final ProofOfWork correct;
  private final byte[] correctData = "Hello World".getBytes();
  
  public IncorrectVerificationTest() {
    int threads = 2;
    int leadingZeros = 4;
    byte[] data = "Hello World".getBytes();
    correct = new ProofOfWorkGenerator(threads, leadingZeros).generate(data);
  }
  
  @Test
  public void testBadCreationTime() throws NoSuchAlgorithmException {
    long badCreationTime = -1;
    ProofOfWork incorrectTime = new ProofOfWork(
        correct.sha512Hash,
        badCreationTime,
        correct.nonce,
        correct.leadingZeros);
    assertFalse(ProofOfWorkVerifier.verify(correctData, incorrectTime));
  }

  @Test
  public void testExpiredTime() throws NoSuchAlgorithmException {
    // created after the universe has ended.
    long expiredTime = Long.MAX_VALUE;
    ProofOfWork incorrectTime = new ProofOfWork(
        correct.sha512Hash,
        expiredTime,
        correct.nonce,
        correct.leadingZeros);
    assertFalse(ProofOfWorkVerifier.verify(correctData, incorrectTime));
  }
  
  @Test
  public void testWrongNonce() throws NoSuchAlgorithmException {
    ProofOfWork incorrectNonce = new ProofOfWork(
        correct.sha512Hash,
        correct.createdAt,
        correct.nonce + 1,
        correct.leadingZeros);
    assertFalse(ProofOfWorkVerifier.verify(correctData, incorrectNonce));
  }
  
  @Test
  public void testTooFewLeadingZeros() throws NoSuchAlgorithmException {
    ProofOfWork badZeros = new ProofOfWork(
        correct.sha512Hash,
        correct.createdAt,
        correct.nonce,
        correct.leadingZeros + 1);
    assertFalse(ProofOfWorkVerifier.verify(correctData, badZeros));
  }
  
  @Test
  public void testInvalidData() throws NoSuchAlgorithmException {
    byte[] incorrectData = "This isn't the right data.".getBytes();
    assertFalse(ProofOfWorkVerifier.verify(incorrectData, correct));
  }
}
