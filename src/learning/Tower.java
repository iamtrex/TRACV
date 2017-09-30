package learning;

public class Tower {
    int pos_x;
    int pos_y;

    public Tower(int hahaX, int hahaY) {
        pos_x = hahaX;
        pos_y = hahaY;


        //Do Something...

    }


    public void fireProjectileIfAvailable(){
        //DO SOMETHING.
        if(offCooldown()){
            //never happens... :(
        }
    }

    public boolean offCooldown(){
        return false;
        //STUB.
    }





}
