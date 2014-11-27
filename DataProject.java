import java.sql.*;

//Please note for ResultSet.getString() the first colum is colum 1 not 0

//hello world
class DataProject
{
   ArrayList<String> courseNumbers;






   public static void main (String args[])  //throws SQLException
   
   {
      DataProject myDataProject=newDataProject();
      
      try
      {
         DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      }   
      catch (Exception e)
      {
         System.out.print(e.getStackTrace() +"\n");
       
      }
      initalizeValues();
      displayCourseRequest("*"); 
       
   }      
   
   void initalizeValues()
   {
      //initalizes courses ALL user inputed courses should be checked as valid via the testCourseInputMethod
      ResultSet courseResult=makeQuery("select course_number from course");          
      while (courseResult.next())
      {
         courseNumbers.add(courseResult.getString(1);
      }  
      
   }
   
   String validateCourseNumber(String userInputCourseNumber)
   {
      boolean inputIsACourse=false;
      for (String courseNumber: courseNumbers)
      {
         if (courseNumber.equals(userInputCourseNumber.trim()) {output=true; break;}
      } 
      return  inputIsACourse;  
   }
   
   
      
   ResultSet makeQuery(String query)
   {
      ResultSet output;
   
      try
      {
         Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         
         Statement mystatment=myconnection.createStatement();
         ResultSet rset = mystatment.executeQuery ("select * from Course");   //read javadocs for ResultsSet
                     
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
         System.out.println(e.getStackTrace().toString());
      }
      
   }

   void displayCourseRequest (String course)
   {
      String studentQuery=("Select * from student_request WHERE course_number = '"+course+"'"; 
      ResultSet studentResults=makeQuery(studentQuery);
      
      while (studentResults.next())
      {
         //String 2 is not used it is the course number
         System.out.println(""); //Todo insert column lables here
         System.out.println(studentResults.getstring(1)+"/"+studentResults.getstring(3)+"/"+studentResults.getstring(4)+"/"+studentResults.getstring(5)+"/"+studentResults.getstring(6)+"/"+studentResults.getstring(7)+"/"+studentResults.getstring(8)+"/");
      }
      String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"; 
      ResultSet facultyResults=makeQuery(studentQuery);
      while (studentResults.next())
      {
         //String 2 is not used it is the course number
         System.out.println(""); //Todo insert column lables here
         System.out.println(facultyResults.getstring(1)+"/"+facultyResults.getstring(3)+"/"+facultyResults.getstring(4)+"/"+facultyResults.getstring(5)+"/"+facultyResults.getstring(6)+"/"+facultyResults.getstring(7)+"/"+facultyResults.getstring(8)+"/");
      }
      //dispalyResults(studentResults);
   }
   
   
}
