# Price Watcher Version 3

In this assignment, you are to extend your HW2 program to support
multiple items. Focus on the design of the UI. You will learn and
become familar with several Swing widgets such as JDialog, JList (or
JTable), JMenu, JPopupMenu, JMenuBar and JToolBar. Your application
shall meet all the relevant requirements from HW2 as well as the
following new ones.

![Demo gif](https://media.giphy.com/media/Uqe7LXQ8s8v6UBOSap/giphy.gif)

### R1. 
    Provide a way to manage the list of items whose prices are to be
    watched over. The user should be able to add a new item, remove an
    existing item, and change an existing item, e.g., rename the item
    or change its URL (see R3, R4 and R5 below).

### R2. 
    Display all watched items along with their price changes. Consider
    using a JList (or JTable) for this.

### R3. 
    Use custom dialogs (subclasses of JDialog) to add and change an
    item in the watch list.

#### R4. 
    Improve the user interface by proving a menu and a tool bar to (a)
    add a new item and (b) to check the current prices of all
    items. For each menu item, provide an icon, a mnemonic and an
    accelerator. For each tool bar button, use an icon and provide a
    tool tip.

#### R5. 
    Provide a popup menu to manipulate an indiviual item. Your popup
    menu shall include menu items for:

    - Checking the current price
    - Viewing its webpage
    - Editing it (change the name and URL; see R1 above)
    - Removing it (see R1 above)

#### R6. 
    Use JavaDoc to document your classes. Write a Javadoc comment for
    each class/interface, field, constructor and method.

### Requirements
1. (10 points) Design your application and document your design by
   drawing a UML class diagram [Chapter 4 of 1]. You should focus on
   designing those classes that are modified (from your HW2 design) or
   newly introduced; highlight them in your diagram.

   - Your class diagram should show the main components (classes and
     interfaces) and their relationships. 
   - Your model (business logic) classes should be clearly separated 
     from the view/control (UI) classes with no dependencies [2].
   - For each class in your diagram, define key (public) operations
     to show its roles or responsibilities in your application.
   - For each association (aggregate and composite), include at least
     a label, multiplicities and navigation directions.
   - You should provide a short, textual description of each class 
     appearing in your class diagram.

2. (90 points) Code your design by making your code conform to your
   design.

   For R2, use a JList (or JTable) to display multiple items. Note
   that you can reuse the ItemView class from HW2 to display each item
   of the list. However, you need to define a custom renderer for the
   JList; read an online tutorial entitled [JList custom renderer
   example](https://www.codejava.net/java-se/swing/jlist-custom
   -renderer-example).

   For R3, refer to online documents such as [The Java Tutorials: How
   to Make Dialogs](https://docs.oracle.com/javase/tutorial/uiswing/
   components/dialog.html). You can define a subclass of JDialog to
   specify your own UI layout.

   For R4, use JMenuBar and JToolBar; refer to the following two
   online documents:

   - "How to use menus" available from https://docs.oracle.com/
      javase/tutorial/uiswing/components/menu.html
   - "How to use tool bars" available from https://docs.oracle.com/
      javase/tutorial/uiswing/components/toolbar.html

   For R5, you will need to handle mouse events on a JList, i.e.,
   register a mouse listener to show a popup menu. See sample code
   "Java Swing How to - Handle mouse event on JList" 
   (http://www.java2s.com/Tutorials/Java/Swing_How_to/JList/
   Handle_mouse_event_on_JList.htm).

#### HINTS
   
   Reuse your HW2 design and code as much as possible. For this, you
   may need to refactor HW2 classes to open up their features or make
   them extensible.

#### TESTING

   Your code should compile and run correctly under Java 8 or later
   versions.

#### WHAT AND HOW TO TURN IN

   You should submit hard copies of your UML diagrams along with
   accompanying documents before the class on the due date. Your hard
   copy submission should include:

   - design.doc (UML class diagram along with a description)
   - contribution-form.docx (if done in pair)
   - screenshot of Git commits (optional if done individually)

   You should submit your program through the Assignment Submission
   page found in the Homework page of the course website. You should
   submit a single zip file that contains:

   - hw3.jar, a runnable jar containing bytecode and support files 
     (e.g., images and audio clips)
   - src directory of source code files
   
   The submission page will ask you to zip your program and upload a
   single zip file. Your zip file should include only a single
   directory named YourFirstNameLastName containing all your source
   code files and other support files needed to compile and run your
   program. DO NOT INCLUDE BYTECODE (.class) FILES. There is a limit
   on upload file size and the maximum file size is 2MB. You should
   turn in your programs by 11:59 pm on the due date.

#### DEMO

   You will need to make one or two minutes demo of your application
   to the course staff.

#### GRADING

   You will be graded on the quality of the design and how clear your
   code is. Excessively long code will be penalized: don't repeat code
   in multiple places. Your code should be reasonably documented and
   sensibly indented so it is easy to read and understand.

   Be sure your name is in the comments in your code.

#### REFERENCES 

   [1] Martina Seidl, et al., UML@Classroom: An Introduction to
       Object-Oriented Modeling, Springer, 2015. Free ebook through
       UTEP library.

   [2] Holger Gast, How to Use Objects, Addison-Wesley, 2016.
       Sections 9.1 and 9.2. Ebook available from UTEP library.
