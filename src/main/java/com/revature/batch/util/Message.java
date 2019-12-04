package com.revature.batch.util;

public class Message {
	 private String infoMessage;
     private String errorMessage;
     public String getInfoMessage() {
         return infoMessage;
     }
     public void setErrorMessage(String error) {
         this.errorMessage= error;
     }
     public String getErrorMessage() {
         return errorMessage;
     }
     public Message(String error) {
         super();
         this.errorMessage = error;
}}
