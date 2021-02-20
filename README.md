# CSVParser

A program that reads and parses a csv file then inserts its valid data to an SQLite database.

## Steps for getting this app running

   Clone repository:

   git clone https://github.com/kenzobanaag/CSVParser.git
   
   ##### Command Prompt:
   1. Open command prompt in cloned repo location
   2. Type: 

    java -jar CSVParser.jar input/ms3Interview.csv

   &nbsp;&nbsp;or no args, program will ask for file path.
   
   &nbsp;&nbsp;Find output in output folder
   
   ##### IntelliJ Idea
   1. Open IntelliJ, File -> Open -> open pom.xml
   2. Open as Project
   3. Run ParserMain
   4. Find output in output folder

## Overview of approach, design choices, and assumptions

   ### Approach
   
   Main approach was splitting the program into 2 main components, parsing and database functions.
   The main function of the driver was to call the parser, then the database functions asynchronously.
   
   ###### Optimization
   To optimize the program, I needed to know which component runs slow. I created classes
   that timed the execution of each component. After execution, it will write the durations paired
   with the size of the csv, which could be found in the output folder under runTimes.log.
   
   Database optimization was done by using transactions. My first approach was using single insert
   queries but that took too much time. Running inserts with this approach took 1600-2000 ms when tested.
   Optimizing with transactions, running inserts on the given input file only took 20 ms.
   
   I tried to optimize parsing, the only way to do this was to increase the performance of splitting
   each line into its appropriate column. The data was too dirty, and the most efficient way to parse the 
   data into the right column was to just use the split() method. 

   ### Design Choices
   
   Programming to interfaces and not implementations. This was my main approach when creating components
   since software always changes. I went with a file parser interface to allow the possibility of extending
   the program to not only read csv files but, other extension types as well.
   
   IDatabase interface with a DAO interface was also used to allow the usage of other databases, making database
   migration easy since only the implementation of these other databases need to be added. Upon more research
   I noticed that the only thing that's differs from database implementations are the connection url strings, so 
   using a different database won't be too much of a problem.
   
   Transactions for insert queries, instead of single sql insert queries. This was the main optimization
   that I did for this program. The use of transactions exponentially decreased insertion times by at least
   98 percent.
   
   ### Assumptions
   * Since there was no given schema for the data, I did not force a type on each data column eg: boolean for true or 
   false, all data columns were considered as strings.
   * Data is considered "valid" when it has 10 data columns, not counting trailing commas. If the data
   line has 10 columns, I considered it as valid, regardless of some empty columns in between.
   * Columns could be empty, but not all. There was only one line that had all the columns filled, which was
   a line that was similar to the header. Having just 1 valid record did not make sense for the
   challenge, so this assumption was made.
   * The order of the data does not matter. Upon reviewing the parsed data, I noticed that there were some 
   true, false columns that did not match other columns.