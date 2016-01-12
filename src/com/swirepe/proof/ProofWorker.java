package com.swirepe.proof;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.xml.bind.DatatypeConverter;

public class ProofWorker implements Callable<ProofOfWork> {
  private final Random random = new Random();
  private final int leadingZeros;
  private final int dataLength;
  private final long createdAt;
  private ByteBuffer buffer;
  private long nonce;

  public ProofWorker(final byte[] data, int leadingZeros) {
    this.dataLength = data.length;
    this.leadingZeros = leadingZeros;
    createdAt = Instant.now().getEpochSecond();
    buffer = ByteBuffer.allocate(dataLength 
        + Long.BYTES   // timestamp
        + Long.BYTES); // nonce
    buffer.put(data, 0, dataLength);
    buffer.putLong(dataLength, createdAt);
  }
  
  public ProofOfWork call() {
    try {
      return work();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public ProofOfWork work() throws NoSuchAlgorithmException {
    String leading = "";
    for (int i = 0; i < this.leadingZeros; i++) {
      leading += "0";
    }
    final MessageDigest digest = MessageDigest.getInstance("SHA-512");
    
    while (true) {
      nonce = random.nextLong();
      buffer.putLong(dataLength + Long.BYTES, nonce);
      byte[] hash = digest.digest(buffer.array());
      String hashString = DatatypeConverter.printHexBinary(hash);
      if (hashString.startsWith(leading)) {
        
        return new ProofOfWork(hashString, createdAt, nonce, leadingZeros);
      }
    }
  }

}
