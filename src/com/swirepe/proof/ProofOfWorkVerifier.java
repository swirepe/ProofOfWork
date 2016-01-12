package com.swirepe.proof;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

public class ProofOfWorkVerifier {
  public static final long EXPIRATION_IN_HOURS = 2;
  
  public static boolean verify(byte[] data,
      ProofOfWork proof,
      long expiration,
      TimeUnit expirationUnits) throws NoSuchAlgorithmException {
    long now = Instant.now().getEpochSecond();
    if (proof.sha512Hash.length() != 128
        || now - proof.createdAt > expirationUnits.toSeconds(expiration)
        || proof.leadingZeros < 1
        || proof.sha512Hash.length() < proof.leadingZeros) {
      return false;
    }
    String leading = "";
    for (int i = 0; i < proof.leadingZeros; i++) {
      leading += "0";
    }
    if (!proof.sha512Hash.startsWith(leading)) {
      return false;
    }
    
    ByteBuffer buffer = ByteBuffer.allocate(data.length + Long.BYTES + Long.BYTES);
    buffer.put(data);
    buffer.putLong(data.length, proof.createdAt);
    buffer.putLong(data.length + Long.BYTES, proof.nonce);
    
    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    byte[] hash = digest.digest(buffer.array());
    String hashString = DatatypeConverter.printHexBinary(hash);
    return hashString.equals(proof.sha512Hash);
  }
  
  public static boolean verify(byte[] data, ProofOfWork proof) throws NoSuchAlgorithmException {
    return verify(data, proof, EXPIRATION_IN_HOURS, TimeUnit.HOURS);
  }
}
