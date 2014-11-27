import java.sql.*;



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
      initalizeValues() 
       
   }      
   
   void initalizeValues()
   {
      //initalizes courses ALL user inputed courses should be checked as valid via the testCourseInputMethod
      ResultSet courseResult=makeQuery("select course_number from course");
      while (courseResult.next())
      
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
      //dispalyResults(studentResults);
      String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"; 
      ResultSet facultyResults=makeQuery(studentQuery);
      //dispalyResults(studentResults);
   }
   
   
}
