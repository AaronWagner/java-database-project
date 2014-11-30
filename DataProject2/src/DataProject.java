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

   PreparedStatement addUser;
   PreparedStatement addStudentRequest;
   PreparedStatement addTeacherRequest;
   //DriverManager myDriverManager;
   
   
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
        /*
       try
       {
           Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
           Statement myStatment=myConnection.createStatement();
           ResultSet courseResult=myStatment.executeQuery("select course_number from COURSE");

           boolean isNotEmpty=false;
           while (isNotEmpty=courseResult.next())
           {
               System.out.println(courseResult.getString(1));
               if (!isNotEmpty)
               {
                   break;
               }
           }
           //courseResult.close();
           if (!isNotEmpty)
           {
               System.out.println("The results were empty.");
               //System.exit(0);
           }
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

       //myDataProject.displayCourseRequest("COT4461");
       //myDataProject.insertUser(12345678, "Mary Poppins", "Admin", 4);
       */
       myDataProject.initalizeValues();
       myDataProject.displayCourseRequest("COT3100");
       myDataProject.displayDay("COT3100", "mw");
       myDataProject.displayTime("COT3100", "morning");
       System.out.println("Display request completed");
       /*
       //troubleshooting code to test validateCourseNumber
       if (myDataProject.validateCourseNumber("COT4461"))
       {
           System.out.println("Found \"COT4461\"\n");
       }
       else
       {
           System.out.println("Still didn't find it.");
       }
        */
      myDataProject.studentRequest(666983);
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
   void initalizeValues()
   {
        courseNumbers=new ArrayList<String>();
       //ResultSet courseResult=null;
       try
       {
           DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
       }
       catch (Exception e)
       {
           System.out.print(e.getStackTrace() +"\n");

       }


      //initalizes courses ALL user inputed courses should be checked as valid via the testCourseInputMethod
       try
       {
           Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
           Statement myStatment=myConnection.createStatement();
           ResultSet courseResult=myStatment.executeQuery("select course_number from COURSE");

           boolean isNotEmpty=false;
           boolean empty=true;
           while (isNotEmpty=courseResult.next())
           {
               String aCourse=courseResult.getString(1).trim();
               //System.out.println("Course added: /"+aCourse+"/ \n");
               courseNumbers.add(aCourse);
               if (!isNotEmpty)
               {
                   break;
               }
                empty=false;
           }
           //courseResult.close();
           if (empty)
           {
               System.out.println("The results were empty.");
               //System.exit(0);
           }
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
      if (courseNumbers.contains(userInputCourseNumber))
      {
          inputIsACourse=true;
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



   void displayDay (String requestedCourse, String week_day )
   {
       /*Day Listing - The same as course listing except the listing is based on the day and includes the following
        order of information: day, the course information, times with related student information, and times with related
        faculty information. */
       try
       {
           Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
           Statement myStatment=myConnection.createStatement();
           String studentQuery=("Select * from student_request WHERE course_number = '"+requestedCourse+" '");
           ResultSet studentResults=myStatment.executeQuery(studentQuery);
           System.out.println("Student Requests for "+requestedCourse+" on "+week_day+": \n");
           boolean isEmpty=true;
           while (studentResults.next())
           {
               //String 2 is not used it is the course number
               //Todo insert column lables here
               System.out.println(studentResults.getString(1)+"/"+studentResults.getString(3)+"/"+studentResults.getString(4)+"/"+studentResults.getString(5)+"/"+studentResults.getString(7)+"/");
               isEmpty=false;
           }
           if (isEmpty)
           {
               System.out.println("There are no matching requests \n");
           }
           String facultyQuery=("Select * from faculty_request WHERE course_number = '"+requestedCourse+"'");
           ResultSet facultyResults=myStatment.executeQuery(studentQuery);
           System.out.println(" \nFaculty Requests for "+requestedCourse+" on "+week_day+": \n"); //Todo insert column lables here
           isEmpty=true;
           while (studentResults.next())
           {
               //String 2 is not used it is the course number

               System.out.println(facultyResults.getString(1)+"/"+facultyResults.getString(3)+"/"+facultyResults.getString(4)+"/"+facultyResults.getString(5)+"/"+facultyResults.getString(7)+"/");
               isEmpty=false;
           }
           if (isEmpty)
           {
               System.out.println("There are no matching requests \n");
           }
       }
       catch (Exception e)
       {
           System.out.println("Error Reterving course requests");
           System.out.println(e.getMessage());
           System.out.println(e.getStackTrace().toString());
       }
   }

    void displayTime(String course, String time)
    {
        /*c. Time Listing - The same as course listing except the listing is based on the time and includes the following
        order of information: time, the course information, days with related student information, and days with related
        faculty information.  */
        try
        {
            Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
            Statement myStatment=myConnection.createStatement();
            String studentQuery=("Select * from student_request WHERE course_number = '"+course+"'"+" and time_of_day ='"+time+"'");
            ResultSet studentResults=myStatment.executeQuery(studentQuery);
            System.out.println("Student Requests for "+course+"at time "+time+": \n");
            boolean isEmpty=true;
            while (studentResults.next())
            {
                //String 2 is not used it is the course number
                //Todo insert column lables here
                System.out.println(studentResults.getString(1)+"/"+studentResults.getString(3)+"/"+studentResults.getString(4)+"/"+studentResults.getString(5)+"/"+studentResults.getString(6)+"/");
                isEmpty=false;
            }
            if (isEmpty)
            {
                System.out.println("There are no matching requests \n");
            }
            String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"+" and time_of_day ='"+time+"'");
            ResultSet facultyResults=myStatment.executeQuery(studentQuery);
            System.out.println(" \nFaculty Requests for "+course+"at time "+time+": \n"); //Todo insert column lables here
            isEmpty=true;
            while (studentResults.next())
            {
                //String 2 is not used it is the course number

                System.out.println(facultyResults.getString(1)+"/"+facultyResults.getString(3)+"/"+facultyResults.getString(4)+"/"+facultyResults.getString(5)+"/"+facultyResults.getString(6)+"/");
                isEmpty=false;
            }
            if (isEmpty)
            {
                System.out.println("There are no matching requests \n");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Reterving course requests");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace().toString());
        }
    }
    void displayStudent()
    {
        /*d. Student Listing - The listing includes the information entered by the students on the form from area 1
        including all student courses and matches the list with any courses, days, times in the faculty information.
        */
    }
    void displayFaculty()
    {
        /*e. Faculty Listing - The listing includes the information entered by the faculty on the form from area 3 including
        all student courses and matches the list with any courses, days, times in the student information. */
    }
   void displayCourseRequest (String course)
   {
       /*
       a. Course Listing - The listing includes course information, information for students who request to take course,
       student days and times, information for faculty who request to teach course, faculty days and times, any
        additional information as decided on by the team.
        */
      try
      {
          Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
         Statement myStatment=myConnection.createStatement();
         String studentQuery=("Select * from student_request WHERE course_number = '"+course+" '");
         ResultSet studentResults=myStatment.executeQuery(studentQuery);
          System.out.println("Student Requests for "+course+": \n");
		  boolean isEmpty=true;
          while (studentResults.next())
		  {
			 //String 2 is not used it is the course number
			  //Todo insert column lables here
			 System.out.println(studentResults.getString(1)+"/"+studentResults.getString(3)+"/"+studentResults.getString(4)+"/"+studentResults.getString(5)+"/"+studentResults.getString(6)+"/"+studentResults.getString(7)+"/");
                isEmpty=false;
          }
          if (isEmpty)
          {
              System.out.println("There are no matching requests \n");
          }
		  String facultyQuery=("Select * from faculty_request WHERE course_number = '"+course+"'"); 
		  ResultSet facultyResults=myStatment.executeQuery(studentQuery);
          System.out.println(" \nFaculty Requests for "+course+": \n"); //Todo insert column lables here
          isEmpty=true;
          while (studentResults.next())
		  {
			 //String 2 is not used it is the course number

			 System.out.println(facultyResults.getString(1)+"/"+facultyResults.getString(3)+"/"+facultyResults.getString(4)+"/"+facultyResults.getString(5)+"/"+facultyResults.getString(6)+"/"+facultyResults.getString(7)+"/");
              isEmpty=false;
          }
          if (isEmpty)
          {
              System.out.println("There are no matching requests \n");
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

   String setLength (String myInput, int length)
    {
        String output
        if (myInput.length()>length)
        {
            output=myInput.substring(0, length);
        }
        else
        {
            output=new String(myInput);
            for (int i=myInput.length(); i<length; i++)
            {
                output+=" ";
            }
        }
    }

   void studentRequest (int studentNumber ){
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   int optionChoosen = -1;
	   int tryAgain = 1;
	   int userFinished=0;
	   boolean submitRequest=false;
	   boolean isCourse=false;
	   
	   String course_number = null;
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
			   
			   isCourse=validateCourseNumber(course_number.toUpperCase());
			   if(isCourse==false){
				   System.out.println("Course number entered does not exist in database, please try again: ");
			   }
			   else{
				   isCourse=true;
			   }
		   }
		  course_number=course_number.toUpperCase();
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
		   
		   //confirm course request information
		   tryAgain = 1;
		   System.out.println("\n\nIs the following request correct?");
		   System.out.println("Student Number: N00"+studentNumber);
		   System.out.println("Course Number: "+course_number);
		   System.out.println("Semester: "+semester);
		   System.out.println("Request Year: "+request_year);
		   System.out.println("Days of the Week: "+week_day);
		   System.out.println("Time of the Day: "+time_of_day);
		   while(tryAgain == 1){
			   System.out.print("-----Enter\n-----1 for YES\n-----0 for NO\n-----:");
			   try {
				 optionChoosen = Integer.parseInt(br.readLine());
			   } catch (IOException ioe) {
				 System.out.println("IO error trying to read your course number!");
				 System.exit(1);
			   }
			   if(optionChoosen==1){
				   tryAgain = 0;
				   submitRequest = true;
			   }
			   else if(optionChoosen==0){
				   tryAgain = 0;
				   submitRequest = false;
			   }
			   else{
				   System.out.println("------Incorrect input, please try again");
				   tryAgain = 1;
			   }
		   }
		   
		   if(submitRequest == true){
			  try
			  {
				 Connection myConnection = DriverManager.getConnection("jdbc:oracle:thin:@olympia.unfcsd.unf.edu:1521:dworcl", "teama5dm2f14", "team5ghjptw");
				 Statement myStatment=myConnection.createStatement();
				 String input = new String("insert into STUDENT_REQUEST values ( "+studentNumber+", '"+
											course_number+"', '"+request_date +"', '"+semester +"', "+
											request_year +", '"+week_day  +"', '"+time_of_day  +"')");
				 System.out.println("Request successfully submitted");
				 myStatment.executeQuery (input);   //read javadocs for ResultsSet

			  }

			  catch (Exception e)
			  {
				  System.out.println("error inserting STUDENT_REQUEST");
				 System.out.println(e.getMessage());
				 System.out.println(e.getStackTrace().toString());
				 System.exit(0);
			  }
		   }
		   
		   //another course request?
		   tryAgain = 1;
		   while(tryAgain == 1 && submitRequest != false){
			   System.out.print("\nDo you have another course request?\n-----Enter\n-----1 for YES\n-----0 for NO\n-----:");
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
