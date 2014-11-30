import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Please note for ResultSet.getString() the first colum is colum 1 not 0

//hello world
class DataProject
{
   ArrayList<String> courseNumbers;
   Connection myConnection = null;
   Statement myStatment =null;
   PreparedStatement addUser;
   PreparedStatement addStudentRequest;
   PreparedStatement addTeacherRequest;
   DriverManager myDriverManager;

   public static void main (String args[])  //throws SQLException
   
   {
      DataProject myDataProject=new DataProject();
      


       myDataProject.initalizeValues();
      /*
       if (myDataProject.validateCourseNumber("COT4461"))
       {
           System.out.println("Found \"COT4461\"\n");
       }
      */
     // myDataProject.studentRequest(666982);
      //myDataProject.insertUser( 15236, "Aaron Wagner", "Student", 1);
       
   }

   //this method gives leading zeros to a integer it takes in a string because
   //I intend to use Result.getString(columNumber) for all parameters
   /*String displayNNumber (String number)
   {
      String output="";
      int i;
      //String rawNumber=Integer.toString(number);
      for (i=0; i<(8-number.length()); i++)
      {
         output+="0"; //
      }
      output+=rawNumber;
   }*/

   void insertUser(int studentNumber, String name, String userType, int permission)
   {
        String password=Integer.toString(studentNumber);
      try
      {
         myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         myStatment=myConnection.createStatement();
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
   void initalizeValues()
   {

       ResultSet courseResult=null;
       try
       {
           DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
       }
       catch (Exception e)
       {
           System.out.print("Driver error"+ e.getStackTrace() +"\n");

       }
      //initalizes courses ALL user inputed courses should be checked as valid via the testCourseInputMethod
      try
      {
          Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
          myStatment=myconnection.createStatement();
          courseResult=myStatment.executeQuery("select * from COURSE");
            /*if (courseResult.wasNull())
                {System.out.println("Courseresult is null");}
            //SQL exception: no data read
            */
          courseNumber=courseResult.getObject("COURSE_NUMBER", String);

            /*while (courseResult.next())
            {
                String aCourse=courseResult.getString(1);
                courseNumbers.add(aCourse);
            }
            courseResult.close();
            */
            System.out.println("Done loading courses");
        }
        catch (SQLException g)
        {
            System.out.println("SQLError in the result set:"+g.getCause()+"\n"+g.getMessage()+"\n"+g.getStackTrace());
        }
        catch (Exception f)
        {
            System.out.println("Error in the result set:"+f.getCause()+"\n"+f.getMessage()+"\n"+f.getStackTrace());
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
   /*ResultSet makeQuery(String query)throws SQLException
   {
      
   

         Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         
         Statement mystatment=myconnection.createStatement();
         ResultSet output = mystatment.executeQuery ("select * from Course");   //read javadocs for ResultsSet
		  return output;				





   }
    */

   void displayCourseRequest (String course)
   {
      try
      {
         String studentQuery=("Select * from student_request WHERE course_number = '"+course+"'");
         ResultSet studentResults=myStatment.executeQuery(studentQuery);

		  while (studentResults.next())
		  {
			 //String 2 is not used it is the course number
			 System.out.println(""); //Todo insert column lables here
			 System.out.println(studentResults.getString(1)+"/"+studentResults.getString(3)+"/"+studentResults.getString(4)+"/"+studentResults.getString(5)+"/"+studentResults.getString(6)+"/"+studentResults.getString(7)+"/"+studentResults.getString(8)+"/");
		  }
		  String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"); 
		  ResultSet facultyResults=myStatment.executeQuery(studentQuery);
		  while (studentResults.next())
		  {
			 //String 2 is not used it is the course number
			 System.out.println(""); //Todo insert column lables here
			 System.out.println(facultyResults.getString(1)+"/"+facultyResults.getString(3)+"/"+facultyResults.getString(4)+"/"+facultyResults.getString(5)+"/"+facultyResults.getString(6)+"/"+facultyResults.getString(7)+"/"+facultyResults.getString(8)+"/");
		  }
	  }
	  catch (Exception e)
	  {
          System.out.println("Error Reterving course requests");
		 System.out.println(e.getMessage());
         System.out.println(e.getStackTrace().toString());
	  }
      //dispalyResults(studentResults);
   }
   
   void studentRequest (int studentNumber ){
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   int optionChoosen = -1;
	   int tryAgain = 1;
	   int userFinished=0;
	   
	   String course_number = null;
	   boolean isCourse=false;
	   String request_date = null;
	   String semester = null;
	   int request_year = 0;
	   String week_day = null;
	   String time_of_day = null;
	   
	   System.out.println("Welcome to the student course request form");
	   
	   while(userFinished==0){
		   System.out.println("Please enter the following information\n");
		   
		   //get course number
		   isCourse=false;
		   while(isCourse==false){
			   System.out.print("Course number (IE. COP2220): ");
			   try {
				 course_number = br.readLine();
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   
			   isCourse=validateCourseNumber(course_number);
			   if(isCourse==false){
				   System.out.println("Course number entered does not exist in database, please try again: ");
			   }
		   }
		  
		  //get request date (no user input reqiured)
		   DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		   Date date = new Date();
		   request_date = dateFormat.format(date);
		   
		   //get semester
		   while(tryAgain == 1){
			   System.out.print("\nSelect a semester\nEnter 1 for Fall, 2 for Spring, or 3 for Summer: ");
			   try {
				 optionChoosen = Integer.parseInt(br.readLine());
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   
			   if(optionChoosen==1){
				   semester = "fall";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==2){
				   semester = "spring";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==3){
				   semester = "summer";
				   tryAgain = 0;
			   }
			   else{
				   System.out.println("------Incorrect input, please try again");
				   tryAgain = 1;
			   }
		   }
		   tryAgain = 1;
		   
		   //get request year
		   System.out.print("\nEnter the year you wish to take the requested course: ");
		   try {
			 request_year = Integer.parseInt(br.readLine());
		   } catch (IOException ioe) {
			 System.out.println("IO error trying to read your course number!");
			 System.exit(1);
		   }
		   
		   //get weekday
		   while(tryAgain == 1){
			   System.out.print("\nSelect the days you would prefer to take this course\n"
						+"Enter 1 for MW, 2 for TR, 3 for MTWR, or 4 for TRF: ");
			   try {
				 optionChoosen = Integer.parseInt(br.readLine());
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   
			   if(optionChoosen==1){
				   week_day = "mw";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==2){
				   week_day = "tr";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==3){
				   week_day = "mtwr";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==4){
				   week_day = "trf";
				   tryAgain = 0;
			   }
			   else{
				   System.out.println("------Incorrect input, please try again");
				   tryAgain = 1;
			   }
		   }
		   tryAgain = 1;
		   
		   //get time of day
		   while(tryAgain == 1){
			   System.out.print("\nSelect a time of day\nEnter 1 for morning, 2 for afternoon, or 3 for evening: ");
			   try {
				 optionChoosen = Integer.parseInt(br.readLine());
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   
			   if(optionChoosen==1){
				   time_of_day = "morning";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==2){
				   time_of_day = "afternoon";
				   tryAgain = 0;
			   }
			   else if(optionChoosen==3){
				   time_of_day = "evening";
				   tryAgain = 0;
			   }
			   else{
				   System.out.println("------Incorrect input, please try again");
				   tryAgain = 1;
			   }
		   }
		   tryAgain = 1;
		   /*id NUMBER(8) NOT NULL, course_number CHAR(8) NOT NULL, request_date DATE NOT NULL, semester VARCHAR(6) NOT NULL, request_year 
			NUMBER(4) NOT NULL, week_day VARCHAR(4), time_of_day VARCHAR(9),
			*/
		   System.out.println("Please confirm that the follow request is correct:");
		   System.out.println("Student Number: 00"+studentNumber);
		   System.out.println("Course Number: "+course_number);
		   System.out.println("Semester: "+semester);
		   System.out.println("Request Year: "+request_year);
		   System.out.println("Days of the Week: "+week_day);
		   System.out.println("Time of the Day: "+time_of_day);
		   
		   //another course request?
		   while(tryAgain == 1){
			   System.out.print("Do you have another course request? Enter 1 for YES, 0 for NO: ");
			   try {
				 optionChoosen = Integer.parseInt(br.readLine());
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   
			   if(optionChoosen==1){
				   userFinished=0;
				   tryAgain = 0;
			   }
			   else if(optionChoosen==0){
				   userFinished = 1;
				   tryAgain = 0;
			   }
			   else{
				   System.out.println("------Incorrect input, please try again");
				   tryAgain = 1;
			   }
		   }
	   }
   }
   
}
