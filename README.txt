Student name: Kevin Niland
Student number: G00342279
Student email: G00342279@gmit.ie

N.B: To run the program successfully, there must be a folder of subject files to read in from
and a query file present. I have included a test folder and test query file in my upload folder.

The project uses seven main classes, with an inner class used inside the 'FileHandler' class to add words to the queue. The classes are: Calculator, FileHandler, Menu, QueryHandler, Runner, Word, and WordInstance.

Calculator
============
This class takes in the words contained inside the subject files and query file and stores them onto their own sepearate maps. Based on this, it passes these maps into a function and calculates the cosine distance between the various subject files and the query files.

FileHandler
=============
Takes in the subject files and puts them onto the blocking queue. This class also contains the class 'QueueCreator'. This takes a word from the queue and passes it to the 'Word' class.

Menu
======
Creates a primitive UI. Allows the user to read in the subject files and query file and view the cosine distances relative to each file

QueryHandler
============
Similar to 'FileHandler', this class takes in the query file and passes it to the 'Calculator' class.

Runner
=======
This class runs the application.

Word
=====
Used to get the file, word contained in file, and the file count.