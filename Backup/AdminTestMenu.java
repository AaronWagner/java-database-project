import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AdminTestMenu 
{
   public static void main(String[] args) 
   {
      String adminID = "00006914";
      String facultyID = "00005631";
      String studentID = "00607916";
      String inputID;
      String inputPass;
      String option;
      Scanner input = new Scanner(System.in);
      
      System.out.println("Welcome to the UNF School of Computing Course Management System:"); 
      System.out.print("Please enter your 8-digit UNF ID, e.g. '00781354': ");
   
      inputID = input.nextLine();
      
      while(inputID.length() != 8)
      {
         System.out.print("\nError: Invalid UNF ID, please re-enter your 8-digit UNF ID: ");
         inputID = input.nextLine();
      }
      
      System.out.print("Please enter your password: ");
      inputPass = input.nextLine();
      
      System.out.println("\nAdministrator Menu");
      System.out.print("Options: ");
      System.out.println("1. Add User");
      System.out.println("         2. Delete User");
      System.out.println("         3. Reset User Password");
      System.out.println("         4. View Entries\n");
      System.out.print("Please select option 1-4, or type 'exit' to return to the main menu: ");
      
      option = input.nextLine();
      
      
      while(!option.equals("exit"))
      {
         if(option.equals("1"))
         {
            System.out.print("Please enter new user's 8-digit UNF ID: ");
            String stringID = input.nextLine();
            int newID = Integer.parseInt(stringID);
         
            while(stringID.length() != 8)
            {
               System.out.print("\nError: Invalid UNF ID, please re-enter new user's 8-digit UNF ID: ");
               stringID = input.nextLine();
               newID = Integer.parseInt(stringID);
            }            
            
            System.out.print("Please enter new user's account type, e.g. 'Student': ");
            String newType = input.nextLine();
            
            while(!newType.equals("Student") && !newType.equals("Faculty") && !newType.equals("Admin"))
            {
               System.out.print("Invalid new user type, please try again: ");
               newType = input.nextLine();
            }
                     
         
            System.out.print("Please enter new user's name: ");
            String newName = input.nextLine();
            
            System.out.print("Please enter new user's permissions level, e.g. '1': ");
            String stringPerm = input.nextLine();
            int newPerm = Integer.parseInt(stringPerm);
            
            insertUser(newID, newType, newName, newPerm);
            //System.out.println("Option 1 Selected.");
         }
         
         if(option.equals("2"))
         {
            System.out.println("Option 2 Selected.");
         }
         if(option.equals("3"))
         {
            System.out.println("Option 3 Selected.");
         }
         if(option.equals("4"))
         {
            System.out.println("Option 4 Selected.");
         }                 
         if(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"))
         {
            System.out.println("Invalid selection.");
         }
         
         option = input.nextLine();          
      }
     

   }
   
static  void insertUser(int studentNumber, String name, String userType, int permission)
   {
        String password=Integer.toString(studentNumber);
      try
      {
         Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         Statement myStatment=myConnection.createStatement();
         String input = new String("insert into USERS values ( "+studentNumber+", '"+userType+"', '"+password+"', '"+name+"', "+permission+")");
         myStatment.executeQuery (input);   //read javadocs for ResultsSet

      }

      catch (Exception e)
      {
          System.out.println("error inserting user");
         System.out.println(e.getMessage());
         System.out.println(e.getStackTrace().toString());
         System.exit(0);
      }


       

   }   
}