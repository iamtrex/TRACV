package learning.JavaIntro;

//Random is not a default class, therefore we have to import it. YOu don't have to memorize this,
// the IDE, namely IntelliJ in this case, will usually let you know you frogot to import and will recommend the import to you

import java.util.Random;

public class RandomNumberGenerator {


    //We create a variable of type Random named rand.
    //Note we have not initalized it, thsu it's constructor has not ran, and the object is pretty much empty rn.
    Random rand;

    public RandomNumberGenerator() {
        //Here in the constructor, we initalize rand to be an object.
        // This initalizes the object and allows us to use it...
        rand = new Random();
    }


    //here we use the rand variable and run one of it's methods. It returns a integer which we directly return to
    //whoever called this method.

    //Note that this takes a parameter max.
    //Also note that it returns an integer.
    public int getRandomNumber(int max) {
        return rand.nextInt(max);
    }


}
