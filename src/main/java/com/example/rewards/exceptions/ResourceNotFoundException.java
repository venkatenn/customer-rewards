package com.example.rewards.exceptions;
/**
 * An exception class which can be used when a resource is not found in the db.
 *
 * @author Venkat E
 */
public class ResourceNotFoundException extends RuntimeException {

  /** @param message */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
