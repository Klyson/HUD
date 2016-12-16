package Screens;
//CURRENT
import Menu.GamGame1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ScrSongOne extends InputAdapter implements Screen {

    GamGame1 game;
    ScrMainMenu mainmenu;
    private float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
    private SpriteBatch batch;
    private Texture img1, img2, img3, img4;
    private Sprite sprite1, sprite2, sprite3, sprite4, spriteP, sprite1G, sprite2G, sprite3G, sprite4G;
    private boolean isCorrect, isExit, isCirc, isKeyChange, isClick, isDone, bCount = true, isJUp, isRand, isCont, isPause, isCol = true, isColPause = true, isColNoClick;
    private BitmapFont font;
    private Circle circ;
    private Color TL, TR, BL, BR;
    ShapeRenderer shapeRenderer, shapeRend2;
    private Rectangle recTL, recTR, recBL, recBR;
    float fXMid, fYMid, fGood = 1/*number of correct clicks*/, fEff = 0/*% correct so far*/;
    int nJ = 0/**/, nTimeout = 0, nMaxOut = 90, nCount = 0, nNext, nCountSwitch = 0, nRand = 10, nJMax, nCountCol = 0;
    ArrayList<Rectangle> AlRandRect = new ArrayList();
    //0 = TL, 1 = TR, 2 = BL, 3 = BR. 
    //=========================TV INP=============================//
    int nGB = 0, nTextX, nTextY;
    Sprite sprDance;
    Sound sndSong1, sndEnd;
    Texture txSheet, txTemp, txOne;
    BitmapFont textGreat, textBad;
    Animation araniDance[];
    TextureRegion trTemp;
    int fW, fH, fSx, fSy;
    int nFrame, nPos, nTime, z;
    private boolean bDance = true;
    //=========================TV INP=============================//

    public ScrSongOne(GamGame1 _game) {
        this.game = _game;
    }

    @Override
    public void show() {
        fXMid = Gdx.graphics.getWidth() / 2;
        fYMid = Gdx.graphics.getHeight() / 2;
        mainmenu = new ScrMainMenu(game);
        nJMax = Integer.parseInt(mainmenu.text);
        font = new BitmapFont();
        batch = new SpriteBatch();
        sprite1 = new Sprite(new Texture("Red.png"));//TL
        sprite2 = new Sprite(new Texture("Blue.png"));//TR
        sprite3 = new Sprite(new Texture("green.jpg"));//BL
        sprite4 = new Sprite(new Texture("purple.jpg"));//BR
        spriteP = new Sprite(new Texture("pause.jpg"));
        sprite1G = new Sprite(new Texture("GreyTL.png"));//TL
        sprite2G = new Sprite(new Texture("GreyTR.png"));//TLsprite1G = new Sprite(new Texture("GreyTL.png"));//TL
        sprite3G = new Sprite(new Texture("GreyBL.png"));//TLsprite1G = new Sprite(new Texture("GreyTL.png"));//TL
        sprite4G = new Sprite(new Texture("GreyBR.png"));//TLsprite1G = new Sprite(new Texture("GreyTL.png"));//TL
        shapeRenderer = new ShapeRenderer();
        circ = new Circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 150);
        sprite1.setSize(w / 2, h / 2);
        sprite2.setSize(w / 2, h / 2);
        sprite3.setSize(w / 2, h / 2);
        sprite4.setSize(w / 2, h / 2);
        spriteP.setSize(w / 3, h / 3);
        sprite1G.setSize(w / 2, h / 2);
        sprite2G.setSize(w / 2, h / 2);
        sprite3G.setSize(w / 2, h / 2);
        sprite4G.setSize(w / 2, h / 2);
        recTL = new Rectangle(0, 0, w / 2, h / 2); //rectangles representing -
        recTR = new Rectangle(w / 2, 0, w / 2, h / 2); // - the corners
        recBL = new Rectangle(0, h / 2, w / 2, h / 2);
        recBR = new Rectangle(w / 2, h / 2, w / 2, h / 2);
        AlRandRect.add(recTL); //Populating the arraylist with rectangles
        AlRandRect.add(recTR);
        AlRandRect.add(recBL);
        AlRandRect.add(recBR);
        sprite1.setPosition(recTL.x, recTL.y);//setting the sprite positions -
        sprite2.setPosition(recTR.x, recTR.y);// - based on the initial set up of the array
        sprite3.setPosition(recBL.x, recBL.y);
        sprite4.setPosition(recBR.x, recBR.y);
        sprite3G.setPosition(recTL.x, recTL.y);//setting the grey sprite positions -
        sprite4G.setPosition(recTR.x, recTR.y);// - their positions don't change
        sprite1G.setPosition(recBL.x, recBL.y);
        sprite2G.setPosition(recBR.x, recBR.y);
        //=========================TV INP=============================//
        nFrame = 0;
        sndSong1 = Gdx.audio.newSound(Gdx.files.internal("Song1.mp3"));
        long id = sndSong1.play(1.0f, 1.0f, 0.0f);
        nPos = 0;
        araniDance = new Animation[10];
        txSheet = new Texture("dance.png");
        fW = txSheet.getWidth() / 8;
        fH = txSheet.getHeight() / 10;
        for (int i = 0; i < 10; i++) {
            Sprite[] arSprDance = new Sprite[8];
            for (int k = 0; k < 8; k++) {
                nNext = k * fW;
                fSy = i * fH;
                sprDance = new Sprite(txSheet, fSx, fSy, fW, fH);
                arSprDance[k] = new Sprite(sprDance);
            }
            araniDance[i] = new Animation(0.5f, arSprDance);
        }
        FileHandle fontFile = Gdx.files.internal("Woods.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.WHITE;
        textGreat = generator.generateFont(parameter);
        textBad = generator.generateFont(parameter);
//        textNice = generator.generateFont(parameter);
//        textOk = generator.generateFont(parameter);
        generator.dispose();
        //=========================TV INP=============================//
        //nJMa nNext= nNext = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        nNext = ThreadLocalRandom.current().nextInt(0, 3 + 1);//initial randomization of where to click
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override //4 = TL, 5 = TR, 1 = BL, 2 = BR
    public void render(float delta) {
        //=========================TV INP=============================//
        nTime++;
        z++;
        //=========================TV INP=============================//
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!isDone && !isPause) { //Playing Game
            if (isCol) {
                isColNoClick = true;
                nCountCol++;
                if (nCountCol == 110) {
                    isCol = false;
                    nCountCol = 0;
                }
            } else {
                nCountSwitch++;
                isColNoClick = false;
            }
            if (nJ == nJMax) {
                isDone = true;
            }
            isCont = true;
            if ((nRand - 30) >= nCountSwitch) {
                font.setColor(Color.WHITE);
            } else {
                font.setColor(Color.RED);
            }
            if (!isRand) {
                nRand = ThreadLocalRandom.current().nextInt(200, 400 + 1); //randomizes WHEN to change the location of the colours
                isRand = true;
            }
            //System.out.println(rand);
            if (nCountSwitch == nRand) {
                nCountSwitch = 0;
                isRand = false;
                isCol = true;
                Collections.shuffle(AlRandRect);
                sprite1.setPosition(AlRandRect.get(0).x, AlRandRect.get(0).y); //S1 = 0
                sprite2.setPosition(AlRandRect.get(1).x, AlRandRect.get(1).y); //S2 = 1
                sprite3.setPosition(AlRandRect.get(2).x, AlRandRect.get(2).y); //S3 = 2
                sprite4.setPosition(AlRandRect.get(3).x, AlRandRect.get(3).y); //S4 == 3
            }
            shapeRenderer.begin(ShapeType.Filled);
            batch.begin();
            if (isCol) {
                sprite1.draw(batch);
                sprite2.draw(batch);
                sprite3.draw(batch);
                sprite4.draw(batch);
            } else {
                sprite1G.draw(batch);
                sprite2G.draw(batch);
                sprite3G.draw(batch);
                sprite4G.draw(batch);
            }
            if (isExit) {
                Gdx.app.exit();
            }
            if (AlRandRect.get(nNext) == recTL) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recTR) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recBL) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recBR) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            }
            if (isClick && nTimeout != nMaxOut) {
                nCount++;
                shapeRenderer.setColor(Color.WHITE);
                if (nCount == 4) {
                    nCount = 0;
                    isClick = false;
                }
            }
            if (isCol) {
                font.draw(batch, String.valueOf(nCountCol) + " / " + String.valueOf("110"), fXMid + 30, fYMid * 2);
            } else {
            font.draw(batch, String.valueOf(nJ) + " / " + mainmenu.text, 170, fYMid * 2);//amount of pattern remaining
            font.draw(batch, String.valueOf(fGood - 1), 220, fYMid * 2);//how many correct clicks
            font.draw(batch, String.valueOf(fEff) + "%", 300, fYMid * 2);//percentage correct
            font.draw(batch, String.valueOf(nTimeout) + " / " + String.valueOf(nMaxOut), 425, fYMid * 2);//time until middle colour changes
            font.draw(batch, String.valueOf(nCountSwitch) + " / " + String.valueOf(nRand), 500, fYMid * 2);//time until colour locations change
            }
            font.draw(batch, "Escape to exit", fXMid - 50, 30);
            font.draw(batch, "Spacebar to pause or unpause", fXMid - 50, 76);
            font.draw(batch, "Press Enter to show end screen!", fXMid - 50, 52);
            //System.out.println("nCountSwitch: " + nCountSwitch + " nRand: " + nRand);
            batch.end();
            if (bCount && !isCol) {
                nTimeout++;
            }
            if (nTimeout == nMaxOut && !isClick) {
                if (!isJUp) {
                    nJ++;
                    nNext = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                    isJUp = true;
                }
                bCount = false;
                nCount++;
                shapeRenderer.setColor(Color.WHITE);
                if (nCount == 4) {
                    nTimeout = 0;
                    nCount = 0;
                    isJUp = false;
                    bCount = true;
                }
            }
            shapeRenderer.circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 150);
            shapeRenderer.end();
        } else if (!isDone && isPause) { //Paused
            shapeRenderer.begin(ShapeType.Filled);
            batch.begin();
            if (isCol) {
                sprite1.draw(batch);
                sprite2.draw(batch);
                sprite3.draw(batch);
                sprite4.draw(batch);
            } else {
                sprite1G.draw(batch);
                sprite2G.draw(batch);
                sprite3G.draw(batch);
                sprite4G.draw(batch);
            }
            isCirc = false;
            if (isExit) {
                Gdx.app.exit();
            }
            if (AlRandRect.get(nNext) == recTL) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recTR) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recBL) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            } else if (AlRandRect.get(nNext) == recBR) {
                if (nNext == 0) {
                    shapeRenderer.setColor(Color.RED);
                } else if (nNext == 1) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (nNext == 2) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (nNext == 3) {
                    shapeRenderer.setColor(Color.PURPLE);
                }
            }
            font.draw(batch, String.valueOf(nJ), 200, fYMid * 2);
            font.draw(batch, String.valueOf(fGood - 1), 250, fYMid * 2);
            font.draw(batch, String.valueOf(fEff) + "%", 300, fYMid * 2);
            font.draw(batch, String.valueOf(nTimeout) + " / " + String.valueOf(nMaxOut), 425, fYMid * 2);
            font.draw(batch, "Escape to exit", fXMid - 50, 30);
            font.draw(batch, "Spacebar to pause or unpause", fXMid - 50, 76);
            font.draw(batch, "Press Enter to show end screen!", fXMid - 50, 52);
            spriteP.draw(batch);
            batch.end();
            shapeRenderer.circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 150);
            shapeRenderer.end();
        } else { //End Screen
            bDance = false;
            font.setColor(Color.WHITE);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            batch.begin();
            font.draw(batch, "You clicked correctly " + String.valueOf(fGood - 1) + " times out of " + String.valueOf(nJ), 250, fYMid + 100);
            font.draw(batch, "Your efficiency was " + String.valueOf(fEff) + "%", 250, fYMid);
            font.draw(batch, "Press Escape to Exit", 250, fYMid - 200);
            font.draw(batch, "Left Click to Continue", 250, fYMid - 150);
            font.draw(batch, "Right Click to Restart", 250, fYMid - 100);
            batch.end();
            if (isExit) {
                Gdx.app.exit();
            }
        }
        //=========================TV INP=============================//
        if (bDance) {
            if (nGB == 1) {
                if (z % 0.5 == 0) {
                    batch.begin();
                    textGreat.draw(batch, "GREAT", nTextY, nTextX);
                    nTextX += 5;
                    batch.end();
                    //System.out.println("hi " + Gdx.graphics.getHeight());
                    if (nTextX > Gdx.graphics.getHeight()) {
                        nTextX = Gdx.graphics.getHeight() / 2;
                        nTextY = Gdx.graphics.getWidth() / 5 + (int) (Math.random() * (Gdx.graphics.getWidth() / 2));
                        nGB = 0;
                    }
                }
            }
            if (nGB == 2) {
                if (z % 0.5 == 0) {
                    batch.begin();
                    textBad.draw(batch, "bad", nTextY, nTextX += 5);
                    batch.end();
                    //System.out.println("hi " + Gdx.graphics.getHeight());
                    if (nTextX > Gdx.graphics.getHeight()) {
                        nTextX = Gdx.graphics.getHeight() / 2;
                        nTextY = Gdx.graphics.getWidth() / 5 + (int) (Math.random() * (Gdx.graphics.getWidth() / 2));
                        nGB = 0;
                    }
                }
            }
            if (!isPause) {
                if (nTime % 6 == 0) {
                    nFrame++;
                }
                if (nFrame > 8) {
                    nFrame = 0;
                }
            }
            //System.out.println(nPos + " " + nFrame);
            trTemp = araniDance[nPos].getKeyFrame(nFrame, true);
            batch.begin();
            batch.draw(trTemp, Gdx.graphics.getWidth() / 2 - trTemp.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - trTemp.getRegionHeight() / 2);
            batch.end();
        }
    }
    //=========================TV INP=============================//

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isCorrect = false;
        isCirc = false;
        if (isDone && button == Buttons.RIGHT) {
            fGood = 1;
            isCol = true;
            nCountSwitch = 0;
            nCountCol = 0;
            fEff = 0;
            nJ = 0;
            nTimeout = 0;
            isDone = !isDone;
        } else if (isDone && button == Buttons.LEFT) {
            nTimeout = 0;
            isDone = !isDone;
        }
        bDance = true;
        if (!isDone) {
            if (button == Buttons.LEFT && recTL.contains(screenX, screenY)
                    && AlRandRect.get(nNext) == recBL && !circ.contains(screenX, screenY)) {
                isCorrect = true;
            } else if (button == Buttons.LEFT && recTR.contains(screenX, screenY)
                    && AlRandRect.get(nNext) == recBR && !circ.contains(screenX, screenY)) {
                isCorrect = true;
            } else if (button == Buttons.LEFT && recBL.contains(screenX, screenY)
                    && AlRandRect.get(nNext) == recTL && !circ.contains(screenX, screenY)) {
                isCorrect = true;
            } else if (button == Buttons.LEFT && recBR.contains(screenX, screenY)
                    && AlRandRect.get(nNext) == recTR && !circ.contains(screenX, screenY)) {
                isCorrect = true;
            }
            if (!circ.contains(screenX, screenY) && !isPause && !isColNoClick) {
                isCirc = true;
            }
            if (isCirc) {
                bCount = true;
                nNext = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                isClick = true;
                nTimeout = 0;
                nJ++;
                fEff = (fGood / nJ) * 100;
                if (isCorrect) {
                    nGB = 1;
                    fGood++;
                } else if (!isCorrect) {
                    nGB = 2;
                }
            }
            if (fEff == 100) {
                nPos = 6;
            }
            if (fEff < 100 && fEff > 79) {
                nPos = 7;
            }
            if (fEff < 80 && fEff > 59) {
                nPos = 8;
            }
            if (fEff < 60) {
                nPos = 1;
            }
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            isExit = true;
        } else if (keycode == Input.Keys.ENTER) {
            isDone = true;
        } else if (keycode == Input.Keys.SPACE) {
            isPause = !isPause;
            isCirc = false;
        }
        return false;
    }
}