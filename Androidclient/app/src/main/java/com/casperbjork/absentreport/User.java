package com.casperbjork.absentreport;

public class User {
     private String username;
     private String full_name;
     private Boolean authenticated;
     private String status;
     private String returnDate;
     private String office;
     private int userID;

     private static final User instance = new User();

     private User(){
         this.full_name = "";
         this.username = "";
         this.authenticated = false;
         this.userID = 0;
         this.status = "";
         this.returnDate = "";
         this.office = "";
     }

     public static User getInstance(){
         return instance;
     }

     public void authenticate(String username, boolean authenticated, int userID){
         this.username = username;
         this.authenticated = authenticated;
         this.userID = userID;
     }

     //GETTERS
     public String getUsername(){
         return this.username;
     }

     public boolean isAuthenticated(){
         return this.authenticated;
     }

     public int getUserID(){
         return this.userID;
     }

     public String getFull_name(){
         return this.full_name;
     }

     public String getStatus() { return this.status; }

     public String getReturnDate() { return this.returnDate; }

     public String getOffice() { return this.office; }
}
