package Menu;

import com.badlogic.gdx.Game;
import Screens.ScrMainMenu;
import Screens.ScrSongOne;

//import Screens.ScrEndScreen;

public class GamGame1 extends Game {

    ScrMainMenu scrMenu;
    ScrSongOne scrSong1;

    //ScrEndScreen scrEnd;
    public int nScreen; // 0 for mainmenu, 1 for song1, 2 for endscreen

    public void updateState() {
        if (nScreen == 0) {
            setScreen(scrMenu);
        } else if (nScreen == 1) {
            setScreen(scrSong1);
        } 
       //else if (nScreen == 2) {
            //setScreen(scrEnd);
        //}
    }

    @Override
    public void create() {
        nScreen = 0;
        scrMenu = new ScrMainMenu(this);
        scrSong1 = new ScrSongOne(this);

        //scrEnd = new ScrEndScreen(this);
        updateState();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}