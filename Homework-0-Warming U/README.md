# CS 3331: Advanced Object-Oriented Programming :small_blue_diamond:

## HOMEWORK: Warming Up

### Due: February 4, 2019

This homework shall be done individually. 

The purpose of this assignment is to refresh your memory on Java
programming and become familiar with a Java IDE (Eclipse or IntellJ
Idea). You will also produce baseline code that you will be extending
throughout this semester.

In this course, you are to develop a price tracking application, named
Price Watcher, in a series of homework assignments. The application
will help a user to figure out the best time to buy something by
watching over fluctuating online prices. It behaves like Amazon's wish
list (www.amazon.com). You will start with a very simple application
and refine it to an ultimate version in several iterations.

In this assignment, you are to write the first version of the Price
Watcher application that tracks "simulated" prices of a single, fixed
item. Your application shall meet the following functional and
non-functional requirements.

**R1.** The application shall display the name of an item, its initial and
    current prices, and the percentage change of the prices (see R3 and
    R4 below). You may assume that the application knows a single item
    to watch over the price.

**R2.** The application shall provide a way to find the current price of
    the item and calculate a new price change (see R3 below to
    simulate the price of an item).

**R3.** The application shall include a class, say PriceFinder, to
    simulate the price of an item. Given the URL of an item, the class
    returns a "simulated" price of the item, e.g., by generating a
    random, or normally-distributed, price. The idea is to apply the
    Strategy design pattern [1] in later assignments by introducing a
    subclass that actually downloads and parses the web document of
    the given URL to find the current price.

**R4.** The application shall provide a console-based user interface (UI)
    to communicate with a user. It shall take all user inputs from
    System.in and display all outputs to System.out. It shall provide
    a way for the user to quit the application, e.g., using a special
    input value. Below is a sample UI.

  
      Welcome to Price Watcher!

    Name:   LED monitor
    URL:    https://www.bestbuy.com/site/samsung-ue590-series-28-led
            -4k-uhd-monitor-black/5484022.p?skuId=5484022
    Price:  $61.13
    Change: 0.00%
    Added:  08/25/2018 ($61.13)

    Enter 1 (to check price), 2 (to view page), or -1 to quit? 
    

**R5.** Optionally, the application shall provide a way to view the Web
    page of the item. Learn how to launch a default web browser
    programmatically in Java.

---
### TESTING

   Your code should compile and run correctly under Java 8 or later
   versions. 

### WHAT AND HOW TO TURN IN

   Submit your program through the Assignment Submission page located
   in the Homework section of the course website. Your program
   submission should include the following:

   - hw0.jar, a runnable jar containing bytecode
   - src directory of source code files
   
   The submission page will ask you to zip your program and upload a
   single zip file. Your zip file should include only a single
   directory named YourFirstNameLastName containing all your source
   code files and other support files needed to compile and run your
   program. DO NOT INCLUDE BYTECODE (.class) FILES. There is a limit
   on upload file size and the maximum file size is 2MB. You should
   turn in your programs by midnight on the due date.

### DEMO

   You will need to make one or two minutes demo of your application
   to the course staff.

### GRADING

   You will be graded on the quality of the design and how clear your
   code is. Excessively long code will be penalized: don't repeat code
   in multiple places. Your code should be reasonably documented and
   sensibly indented so it is easy to read and understand.

   Be sure your name is in the comments in your code.

--- 
### REFERENCES 

   [1] Wikipedia, Strategy pattern, https://en.wikipedia.org/wiki/
       Strategy_pattern.
