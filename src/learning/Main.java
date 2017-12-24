package learning;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * A quick intro to Java.
 */

//Here is the declaration of the class's name. We can ignore the extends Application right now.
    // Classes are just individual files in Java. We will see how we can communicate between different classes
    //  using objects below.

//It should be noted that classes are declared with the followingn syntax:
    //public class ClassName{
    //    variables and
    //    code go here...
    //} <- note we use curly brackets to start and close blocks of code. Note we use the same for each method (function)
public class Main extends Application {


    //In Java, we must specify what type of variable variables are. In this case we create a variable of type
    //     "Group".
    //In Java, besides the basic variables (boolean, int, double, float, etc.) all variables are OBJECTS
    // So Group is a TYPE of OBJECT.
    // And we have created a variable root of type Group.
    private Group root;


    //we can ignore the @Override annotation for now.
    //This below is a METHOD (you may know them as functions).
    //This method is named "start", and takes 1 parameter (Stage primaryStage).
    //The parameter is therefore a variable of type Stage.
    @Override
    public void start(Stage primaryStage) throws Exception{

        //Creates a basic button and stores that to the variable named 'btn'
        //Note that Button, Stage, Group are built in, if you scroll to the top, you shoudl see that we import
        //  these because they are part of what we call the "Java Library." Aka it's a lot of code people already
        //  wrote for us that we can borrow to make coding faster.

        //In this case, someone wrote a "Button" class. So when we create a Button, we are creating a variable of that
        //   type and we can access it.
        Button btn = new Button("This is the button's text");

        new Group();
        root.getChildren().add(btn);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
