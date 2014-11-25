You need to 
So far I have this compiling on osprey If someone needs it for a given compiler or such please let me know
 
1. Copy objdbc6.jar to osprey

2. in osprey run "export CLASSPATH=$CLASSPATH:/your/path/to/files/ojdbc6.jar"

3. compile with 
$ javac -cp ojdbc6.jar sel_prdct.java

4. run with 
$ java -cp ojdbc6.jar:. sel_prdct

ToDo:
1.make a makefile that works (the one in the repository doesn't. . . yet.)
2.get somone to ask Abassi if he will want a make file or if we can just javac the 
file because of how he has it set up (I doubt he will trust us to compile with a jar included)
