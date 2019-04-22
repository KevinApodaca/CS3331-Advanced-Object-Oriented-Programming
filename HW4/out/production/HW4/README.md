# Price Watcher Version 4

In this assignment, you are to extend your HW3 code and create the
ultimate version of the Price Watcher application that supports
network and data persistence. Your app shall meet all the relevant
requirements from the previous homework assignments as well as the
following new ones.

### R1. 
    The application shall find the price of a watched item from the
      item's Web page. Remember that the URL of an item is provided by
      the user when the item is added to the watch list.

        a. It shall inform the user if the price of an item can't be found
           (e.g., malformed or non-existing URL).

        b. It shall support item pages from at least three different
           online stores.

### R2. 
    The application shall persist watched items. The items should be
        stored in an external storage to so that they can be available
        when the application is closed and launched later.

### R3. 
    You should separate network and database operations into separate
        modules (or classes) to decouple them from the rest of the code.
        Consider introducing new subclasses of the PriceFinder and
        ItemManager classes.
### Requirements
1. (10 points) Design your application and document your design by
   drawing a UML class diagram [Chapter 4 of 1]. You should focus on
   designing those classes that are modified (from your HW3 design) or
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

3. (bonus points) Introduce other interesting features, e.g.,

   (10 points) Use a JProgressBar to show the progress of updating the
      prices of all items, as it may take a while because of network
      operations.

   (10+ points) Provide a way to filter displayed items, e.g., all
      items from a selected store, or to sort displayed items.

#### HINTS
   
   Reuse your HW3 design and code as much as possible. For example,
   define HW4 Main as a subclass of HW3 Main.

   For R1, introduce a subclass of the PriceFinder class from HW0, say
   WebPriceFinder, and override the method that, given a URL, finds
   the price of an item. This will minimize the changes needed to the
   rest of the program.

   For R2, introduce a subclass of the ItemManager class from HW3, say
   FileItemManager, and override all mutation methods such as addItem,
   changeItem, and removeItem to store the watched items in a file.
   Consider using the JavaScript Object Notation (JSON), a lightweight
   data-interchange format (see www.json.org) [3] to encode the items
   and store them in a file. You can find many open-source JSON
   libraries written in Java (e.g, JSON-java) from
   www.json.org. Define the following two methods in the Item class for
   the JSON encoding/decoding.

    public JSONObject toJson() {
    	Map<String, Object> map = new HashMap<>();
    	map.put("name", name);
    	map.put("currentPrice", currentPrice);
    	...
    	return new JSONObject(map);
    }
    // item.toJson().toString() will give a JSON string of the form:
    // { "name": "LED Monitor", "currentPrice": 30.99, ... }

    public static Item fromJson(JSONObject obj) {
        String name = obj.getString("name");
        float currentPrice = (float) obj.getDouble("currentPrice");
        ...
        Item item = new Item(name, ...);
        ...
        return item;
    }

    To save/restore a list of items, use a JSONArray.

   To test price changes, use the following URL that shows a fake
   item: (http://www.cs.utep.edu/cheon/cs3331/homework/hw4/).

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

   - hw4.jar, a runnable jar containing bytecode and support files 
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

   [3] Ben Smith, Beginning JSON, Apress, 2015. Ebook.
