package learning;

import java.util.ArrayList;
        import java.util.List;

public class GameState {

    List<Tower> loTowers = new ArrayList<Tower>();

    public void buildTower(int x_pos, int y_pos) {
        Tower t = new Tower(x_pos, y_pos);

        t.fireProjectileIfAvailable();

    }
}
