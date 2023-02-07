package com.example.rewards.exceptions;

/** @author Venkat E */
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message){
    super(message);
  }
}
