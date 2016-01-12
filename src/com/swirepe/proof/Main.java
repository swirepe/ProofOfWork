package com.swirepe.proof;

import java.security.NoSuchAlgorithmException;

public class Main {
  
  public static void main(String[] args) throws NoSuchAlgorithmException {
    byte[] data = "Hello world".getBytes();
    long start = System.currentTimeMillis();
    ProofOfWorkGenerator generator = new ProofOfWorkGenerator();
    ProofOfWork proof = generator.generate(data);
    long end = System.currentTimeMillis();
    System.out.println("Milliseconds to generate proof of work: " + (end - start));
    System.out.println(proof);
    
    start = System.currentTimeMillis();
    boolean verified = ProofOfWorkVerifier.verify(data, proof);
    end = System.currentTimeMillis();
    System.out.println("Milliseconds to verify work: " + (end - start));
    System.out.println("Verification successful: " + verified);
    System.exit(0);
  }
  
}
  
  