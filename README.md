# Proof of Work 
Peter Swire - swirepe@swirepe.com

## What is it?

Given some data, prove that your machine put some thought into it.  This is for systems where anonymous robots are allowed, but I don't want them posting too quickly.

## How does it work?

It forces the client to find a SHA512 hash collision.  Given some data, find a random nonce that, when appended to that data, produces a SHA512 hash string with a certain number of leading zeros.

I think bitmessage does something similar.

Each proof of work is valid for, by default, two hours.

## How can I use it?

Client:

    ProofOfWorkGenerator generator = new ProofOfWorkGenerator();
    ProofOfWork proof = generator.generate(myByteArrayOfData);
		
Server:

    boolean verified = ProofOfWorkVerifier.verify(data, proof);

## Other Suggestions
This code uses just plain Java 8, with JUnit for the tests.  It uses threading to make the generation a little faster, but that's pretty much the only fancy thing going on here.  Feel free to change the code to better fit your needs.

For example, that ProofOfWork class looks like it would fit in a protocol buffer pretty well.

    message ProofOfWork {
      required string sha512hex = 1;
      required int64 createdAt = 2;
      required int64 nonce = 3;
    }

    message ProvenWork {
      required bytes data = 1;
      required ProofOfWork proof = 2;
    }

From there it's pretty trivial to send this across the wire.  I left this as a POJO for illustrative purposes.

