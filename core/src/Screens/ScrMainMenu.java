package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import Menu.GamGame1;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrMainMenu extends InputAdapter implements Screen, TextInputListener {

    Sound sndMainMenu;
    GamGame1 game;
    private SpriteBatch batch;
    private boolean isSong, isExit, bSong = true;
    private BitmapFont font, fontRed;
    public static String text = "15"; 

    public ScrMainMenu(GamGame1 _game) {
        game = _game;
    }

    @Override
    public void show() {
        sndMainMenu = (Sound) Gdx.audio.newSound(Gdx.files.internal("menu.mp3"));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        font = new BitmapFont();
        fontRed = new BitmapFont();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        font.setColor(Color.BLACK);
        fontRed.setColor(Color.RED);
        Gdx.input.getTextInput(this, "Enter the length of the initial round you want: ", "", text);
        if (bSong) {
            long id = sndMainMenu.loop(1.0f, 1.0f, 0.0f);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "The fonts will flash           when the location of the colours is about to change!", (Gdx.graphics.getWidth() / 2) - 200, (Gdx.graphics.getHeight() / 2) + 68);
        fontRed.draw(batch, "RED", (Gdx.graphics.getWidth() / 2) - 75, (Gdx.graphics.getHeight() / 2) + 68);
        font.draw(batch, "If restarting (or continuing), the round will last until the 'Enter' button is pressed", (Gdx.graphics.getWidth() / 2) - 200, (Gdx.graphics.getHeight() / 2) - 68);
        font.draw(batch, "Press Enter to Play!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Press Escape to Exit", Gdx.graphics.getWidth() / 2, 375);
        font.draw(batch, text, Gdx.graphics.getWidth() / 2, 280);
        batch.end();
        if (!bSong) {
            sndMainMenu.stop();
        }
        if (isSong) {
            game.nScreen = 1;
            game.updateState();
            dispose();
        } else if (isExit) {
            Gdx.app.exit();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            bSong = false;
            isSong = true;
        } else if (keycode == Input.Keys.ESCAPE) {
            isExit = true;
        }
        return false;
    }

    @Override
    public void input(String text) {
        this.text = text;
    }

    @Override
    public void canceled() {
    }
}