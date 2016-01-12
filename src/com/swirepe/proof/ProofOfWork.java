package com.swirepe.proof;

public class ProofOfWork {
  public final String sha512Hash;
  public final long nonce;
  public final int leadingZeros;
  public final long createdAt;
  
  public ProofOfWork(String sha512Hash, // 128 character string
      long createdAt, // seconds since epoch
      long nonce,     // random value that forces the hash collision
      int leadingZeros) {  // number of leading zeros we should expect, not used in creating the hash
    this.sha512Hash = sha512Hash;
    this.nonce = nonce;
    this.createdAt = createdAt;
    this.leadingZeros = leadingZeros;
  }

  @Override
  public String toString() {
    return "ProofOfWork [sha512Hash=" + sha512Hash
        + ", createdAt=" + createdAt
        + ", nonce=" + nonce
        + ", leadingZeros=" + leadingZeros + "]";
  } 
}
