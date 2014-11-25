import java.sql.*;

//hello world
class sel_prdct
{
   public static void main (String args[])  //throws SQLException
   
   {
      try
      {
         DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      }   
      catch (Exception e)
      {
         System.out.print(e.getStackTrace() +"\n");
         
      }
      
      
      Connection myconnection = DriverManager.getConnection("jdbc:oracle:thin@olympia.unfcsd.unf.edu:1521dworcl", "teama5dm2f14", "team5ghjptw");
      
      Statement mystatment=myconnection.createStatment();
      ResultSet rset = stmt.executeQuery ("select * from Course");   //read javadocs for ResultsSet
      
      while (rset.next())
      {
         System.out.println(rset.getString(1)+" "+rset.getString(2)+". . . \n");
      }
   
   }




}
