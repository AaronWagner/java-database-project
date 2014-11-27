DataProject: DataProject.class
	java -cp :/usr/lib/oracle/11.2/client64/lib/ojdbc6.jar DataProject DataProject.class
DataProject.class: DataProject.java
	javac -cp :/usr/lib/oracle/11.2/client64/lib/ojdbc6.jar DataProject.java
