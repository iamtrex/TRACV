package learning.JavaIntro;

//Welcome to Learning Java!

//In Java, code is stored in different files known as 'classes'
//They are the .java files you see.

//As you can probably tell, two slashes denote comments
/*
And these slashes allow us to write
multi-line
comments
between them
 */

//To get started, we look at the declaration of the class:
//We are in the class named LearnJava.
//Note that in all classes, they are denoted like this:
//  public class ClassName {
//   //Class variables go here (by convention)
//
//   //Methods go here! (rmb method = function in Java)
//  }
//In Java, we use the curly brackets {} to denote blocks of code.

/**
 * A quick intro to Java.
 */


public class LearnJava {

    //In Java, we declare variables with a type, and that type cannot just arbitrarily change.
    //For example here we have created a variable of type "String", and we named that variable text.
    //Note we also created the string with a value of Hello World.

    String text = new String("Hello World");

    //Note that String in this case is an OBJECT. Objects are the core of how Java programming works.
    //With the exception of the very basic variable types: int, float, double, boolean, etc. All variables are
    //always objects.
    //In this case we created text as a new String, and gave it the parameter "Hello World". We will see that
    //  this gives it a value "Hello World"

    //Objects in java are creaetd in the form of:
    //Object objName = new Object(Param1, Param2, ...); //Note you can have 0 parameters (@main function)

    //A more common shorthand is to just use:
    // String text = "Hello World". This is an exception for Strings and a few others only!

    //This is an example of a method (aka. function).
    //The method is called printTextToScreen.
    //Methods are in the form of :
    //public RETURN_TYPE functionName(VarType varName, Var2Type var2Name...){
    // ... Code here
    //}
    public void printTextToScreen() {

        //This just prints to the console. In this case, it prints the value of the String named text that we created
        //  earlier;

        System.out.println(text);
        //This means that whenever we call the "printTextToScreen()" function, it will write the value of text.
    }

    //Here is a function that takes parameters, as you can see, it takes 2 integers, named a and b.
    //We also see that the return type is int instead of void. This means we must use the return statement in the functeion
    //Here we have return a+b;, therefore this function just adds the value of a and b and returns it.
    public int addNumbers(int a, int b) {
        return a + b;
    }


    //Here we see a function with NO RETURN STATEMENT! It also seems to have the same name as the File (LearnJava)
    //This is known as the CONSTRUCTOR.
    //Constructors are functions that automatically run when an object of the class is made.
    //  They "Construct" the class...
    //Remember how we created String with a parameter "New World", that means in the String class we have something like:
    //public String(Parameter1 word){
    // ... Create a string with value = word...
    //}

    public LearnJava() {
        printTextToScreen(); //So here you see we run the print to screen function, this will print out the value of text
        //The value of text hasn't changed so it's still "Hello World"

        //Note the shorthand form of creating integers. Again the rule only applies to string, integers, and few other variable types
        int a = 4;
        int b = 5; //Note that here we create what is known as temporary variables.
        //These variables belong to the function, so other functions will NOT be able to see or access these variables


        //We can both pass direct numbers and variables because they are all integers. or a mix.
        int c = addNumbers(1, 5);
        int d = addNumbers(a, b);
        int e = addNumbers(c, -1);

        //NOte that we set the value of c to be the return value of addNumbers(1, 5); Looking back, we can see that
        //This is obviously 6.
        //e is then c + -1, and since c is 6, we know e = -1;

        //Now we will create anotehr Object.
        RandomNumberGenerator RNG = new RandomNumberGenerator();

        //Since we now have an object of the class RandomNumberGenerator, we can use the functions in that classs.
        //This is how Java gets different files to work together.
        int f = RNG.getRandomNumber(5);
        //Go to the RandomNumberGenerator file and see what the function getRandomNumber does ...
        // (The name is pretty intuitive though.)


        //F will tehrefore be a random int between 0 and 5;

        //text = f; //<-- we see if we uncomment this line, it's an error. This is because you can't simply merge
        //      Types in Java. text is a string, f is an integer. they don't just combine simply.

        text = String.valueOf(f); //The function valueOf creates a string from an integer, so it effectively converts
        // f to be a string.

        //Now note we changed the variable text to be something else, so now let's try printing it again:
        printTextToScreen();

        //We will see this time it prints a random number between 1 and 5.


    }


    //Here is a special method. The main method.
    //Don't worry too much about the syntax, it's pretty much alwasy teh same.
    //Effectively , when Java starts, it wants to run this method always,  a method in the form of
    //public static void main(String args[]){
    // ...
    // }
    //So we just need to include it.
    public static void main(String args[]) {

        //To start off our program, we create a LearnJava Object!
        //Note Learnjava is this class! But by creating an object we are able to run the constructor.
        //  So the logic we just had before now initializes and executes.
        LearnJava LJ = new LearnJava();

        LJ.printTextToScreen(); //You see we can also access methods in this class this way :P
        //Now note, what does this line print?
        //So does it execute after the constructor changed text to be a number or is it still "Hello World" ?


    }
}
