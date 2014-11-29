import java.sql.*;
import java.util.ArrayList;

//Please note for ResultSet.getString() the first colum is colum 1 not 0

//hello world
class DataProject
{
   ArrayList<String> courseNumbers;






   public static void main (String args[])  //throws SQLException
   
   {
      DataProject myDataProject=new DataProject();
      
      try
      {
         DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      }   
      catch (Exception e)
      {
         System.out.print(e.getStackTrace() +"\n");
       
      }
      myDataProject.initalizeValues();
      //myDataProject.insertUser( 15236, "Aaron Wagner", "Student", 1);
       
   }

   //this method gives leading zeros to a integer it takes in a string because
   //I intend to use Result.getString(columNumber) for all parameters
   String displayNNumber (String number)
   {
      String output="";
      //String rawNumber=Integer.toString(number);
      for (i=0; i<(8-number.length()); i++)
      {
         output+="0";
      }
      output+=rawNumber;
   }

   void insertUser(int studentNumber, String name, String userType, int permission)
   {
        String password=Integer.toString(studentNumber);
      try
      {
         Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");

         Statement mystatment=myconnection.createStatement();
         String input = new String("insert into USERS values ( "+studentNumber+", '"+userType+"', '"+password+"', '"+name+"', "+permission+")");
         mystatment.executeQuery (input);   //read javadocs for ResultsSet

      }

      catch (Exception e)
      {
         System.out.println(e.getMessage());
         System.out.println(e.getStackTrace().toString());
         System.exit(0);
      }


       

   }
   void initalizeValues()
   {
      //initalizes courses ALL user inputed courses should be checked as valid via the testCourseInputMethod
      try
      {
          ResultSet courseResult=makeQuery("select course_number from course");
		  while (courseResult.next())
		  {
			 courseNumbers.add(courseResult.getString(1));
		  }  
		}
		catch (Exception e)
		{
			 System.out.println(e.getMessage());
			 System.out.println(e.getStackTrace().toString());
		}
   }
   
   boolean validateCourseNumber(String userInputCourseNumber)
   {
      boolean inputIsACourse=false;
      for (String courseNumber: courseNumbers)
      {
         if (courseNumber.equals(userInputCourseNumber.trim())) {inputIsACourse=true; break;}
      } 
      return  inputIsACourse;  
   }
   
   
      //Todo make makeQuery blocks handel SQLException.
   ResultSet makeQuery(String query)throws SQLException
   {
      
   

         Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         
         Statement mystatment=myconnection.createStatement();
         ResultSet output = mystatment.executeQuery ("select * from Course");   //read javadocs for ResultsSet
		  return output;				





   }

   void displayCourseRequest (String course)
   {
      try
      {
         String studentQuery=("Select * from student_request WHERE course_number = '"+course+"'");
         ResultSet studentResults=makeQuery(studentQuery);

		  while (studentResults.next())
		  {
			 //String 2 is not used it is the course number
			 System.out.println(""); //Todo insert column lables here
			 System.out.println(studentResults.getString(1)+"/"+studentResults.getString(3)+"/"+studentResults.getString(4)+"/"+studentResults.getString(5)+"/"+studentResults.getString(6)+"/"+studentResults.getString(7)+"/"+studentResults.getString(8)+"/");
		  }
		  String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"); 
		  ResultSet facultyResults=makeQuery(studentQuery);
		  while (studentResults.next())
		  {
			 //String 2 is not used it is the course number
			 System.out.println(""); //Todo insert column lables here
			 System.out.println(facultyResults.getString(1)+"/"+facultyResults.getString(3)+"/"+facultyResults.getString(4)+"/"+facultyResults.getString(5)+"/"+facultyResults.getString(6)+"/"+facultyResults.getString(7)+"/"+facultyResults.getString(8)+"/");
		  }
	  }
	  catch (Exception e)
	  {
		 System.out.println(e.getMessage());
         System.out.println(e.getStackTrace().toString());
	  }
      //dispalyResults(studentResults);
   }
   
   
}
