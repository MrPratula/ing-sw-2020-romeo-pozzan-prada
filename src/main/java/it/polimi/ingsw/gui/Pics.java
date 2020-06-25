package it.polimi.ingsw.gui;

import javax.swing.*;
import java.io.File;

/**
 * Enum with all the useful images for tui GUI
 */
public enum Pics {

        // BUILDINGS
        LEVEL0("/level0.png"),  //0
        LEVEL1("/level1.png"),   //1
        LEVEL2("/level2.png"),  //2
        LEVEL3("/level3.png"), //3
        LEVEL0DOME("/level0Dome.png"), //26
        LEVEL1DOME("/level1Dome.png"), //27
        LEVEL2DOME("/level2Dome.png"), //28
        LEVEL3DOME("/level3Dome.png"), //4
        LEVEL0TOKENRED("/level0tokenRed.png"),  //5
        LEVEL1TOKENRED("/level1tokenRed.png"), //6
        LEVEL2TOKENRED("/level2tokenRed.png"),//7
        LEVEL3TOKENRED("/level3tokenRed.png"),  //8
        LEVEL0TOKENBLUE("/level0tokenBlue.png"),   //9
        LEVEL1TOKENBLUE("/level1tokenBlue.png"),  //10
        LEVEL2TOKENBLUE("/level2tokenBlue.png"),  //11
        LEVEL3TOKENBLUE("/level3tokenBlue.png"), //12
        LEVEL0TOKENYELLOW("/level0tokenYellow.png"), //13
        LEVEL1TOKENYELLOW("/level1tokenYellow.png"), //14
        LEVEL2TOKENYELLOW("/level2tokenYellow.png"), //15
        LEVEL3TOKENYELLOW("/level3tokenYellow.png"), //16
        LEVEL0TEXT("/level0Text.png"),  //17
        LEVEL1TEXT("/level1Text.png"), //18
        LEVEL2TEXT("/level2Text.png"),//19
        LEVEL3TEXT("/level3Text.png"), //20
        LEVEL0DOMETEXT("/level0DomeText.png"), //21
        LEVEL1DOMETEXT("/level1DomeText.png"), //21
        LEVEL2DOMETEXT("/level2DomeText.png"), //21
        LEVEL3DOMETEXT("/level3DomeText.png"), //21
        LEVEL0VALIDMOVE("/level0ValidMove.png"), //22
        LEVEL0TOKENREDVALIDMOVE("/level0tokenRedValidMove.png"), //22
        LEVEL0TOKENBLUEVALIDMOVE("/level0tokenBlueValidMove.png"), //22
        LEVEL0TOKENYELLOWVALIDMOVE("/level0tokenYellowValidMove.png"), //22
        LEVEL1VALIDMOVE("/level1ValidMove.png"), //23
        LEVEL1TOKENREDVALIDMOVE("/level1tokenRedValidMove.png"), //23
        LEVEL1TOKENBLUEVALIDMOVE("/level1tokenBlueValidMove.png"), //23
        LEVEL1TOKENYELLOWVALIDMOVE("/level1tokenYellowValidMove.png"), //23
        LEVEL2VALIDMOVE("/level2ValidMove.png"), //24
        LEVEL2TOKENREDVALIDMOVE("/level2tokenRedValidMove.png"), //24
        LEVEL2TOKENBLUEVALIDMOVE("/level2tokenBlueValidMove.png"), //24
        LEVEL2TOKENYELLOWVALIDMOVE("/level2tokenYellowValidMove.png"), //24
        LEVEL3VALIDMOVE("/level3ValidMove.png"), //24
        LEVEL3TOKENREDVALIDMOVE("/level3tokenRedValidMove.png"), //24
        LEVEL3TOKENBLUEVALIDMOVE("/level3tokenBlueValidMove.png"), //24
        LEVEL3TOKENYELLOWVALIDMOVE("/level3tokenYellowValidMove.png"), //24

        // GODCARDS
        APOLLO("/apollo.png"),  //0
        APOLLOPANEL("/apolloPanel.png"),  //0
        ARTEMIS("/artemis.png"),   //1
        ARTEMISPANEL("/artemisPanel.png"),   //1
        ATHENA("/athena.png"),  //2
        ATHENAPANEL("/athenaPanel.png"),  //2
        ATLAS("/atlas.png"),  //3
        ATLASPANEL("/atlasPanel.png"),  //3
        CHRONUS("/chronus.png"), //4
        CHRONUSPANEL("/chronusPanel.png"), //4
        DEMETER("/demeter.png"),  //5
        DEMETERPANEL("/demeterPanel.png"),  //5
        HEPHAESTUS("/hephaestus.png"), //6
        HEPHAESTUSPANEL("/hephaestusPanel.png"), //6
        HERA("/hera.png"), //7
        HERAPANEL("/heraPanel.png"), //7
        HESTIA("/hestia.png"),  //8
        HESTIAPANEL("/hestiaPanel.png"),  //8
        LIMUS("/limus.png"),   //9
        LIMUSPANEL("/limusPanel.png"),   //9
        MINOTAUR("/minotaur.png"),  //10
        MINOTAURPANEL("/minotaurPanel.png"),  //10
        PAN("/pan.png"),  //11
        PANPANEL("/panPanel.png"),  //11
        PROMETHEUS("/prometheus.png"), //12
        PROMETHEUSPANEL("/prometheusPanel.png"), //12
        ZEUS("/zeus.png"), //13
        ZEUSPANEL("/zeusPanel.png"), //13
        APOLLOTEXT("/apolloText.png"),  //+ 0
        ARTEMISTEXT("/artemisText.png"),   //1
        ATHENATEXT("/athenaText.png"),  //2
        ATLASTEXT("/atlasText.png"), //3
        CHRONUSTEXT("/chronusText.png"), //4
        DEMETERTEXT("/demeterText.png"),  //5
        HEPHAESTUSTEXT("/hephaestusText.png"), //6
        HERATEXT("/heraText.png"), //7
        HESTIATEXT("/hestiaText.png"),  //8
        LIMUSTEXT("/limusText.png"),   //9
        MINOTAURTEXT("/minotaurText.png"),  //10
        PANTEXT("/panText.png"),  //11
        PROMETHEUSTEXT("/prometheusText.png"), //12
        ZEUSTEXT("/zeusText.png"), //13
        NOTHIRDPLAYER("/nothirdplayer.png"), //13

        // UTILS
        BOARD("/board.png"),
        DONE("/done.png"),
        MESSAGEBG2("/bgMessage2.png"),
        BATTLEFIELDICON("/battlefieldIcon.png"),
        GODICON("/godIcon.png"),
        ERRORICON("/errorIcon.png"),
        INFORMATIONICON("/informationIcon.png"),
        PLAYERICON("/playerIcon.png"),
        GAMEOVERICON("/gameover.png"),
        GAMELOST("/gameLost.png"),
        GAMEWON("/gameWon.png"),
        BUTTON("/btn_purple.png"),
        SANTORINI("/Santorini.png"),
        PLAYBUTTON("/buttonPlay.png"),
        NICKNAME("/nickname.png"),
        NUMBEROFPLAYERS("/howmanyplayers.png"),
        GODPOWER("/godpower.png"),
        ASKFORPROMETHEUSPOWER("/prometheusPower.png"),

        // MESSAGES
        WELCOMEMESSAGE("/welcomeMessage.png"),
        ASK_FOR_BUILD("/ASK_FOR_BUILD.png"),
        ASK_FOR_SELECT_TOKEN("/ASK_FOR_SELECT_TOKEN.png"),
        ASK_FOR_WHERE_TO_MOVE("/ASK_FOR_WHERE_TO_MOVE.png"),
        PLACE_YOUR_TOKEN("/PLACE_YOUR_TOKEN.png"),
        NOT_YOUR_TURN("/NOT_YOUR_TURN.png"),
        GAMEENDED("/gameEnded.png"),
        ANOTHERPLAYERISBUILDING("/another chosing build.png"),
        SECOND_BUILD("/SECOND_BUILD.png"),

        // BACKGROUNDS
        DIALOGBG("/dialogBg.png"),
        MAINBG("/mainBg.png"),
        COLUMNS("/columns.png"),
        OLYMPUS("/olympus.png");


    private final String path;

    Pics(String path) {
        this.path = path;
    }

    public ImageIcon getImageIcon(){
        return new ImageIcon(new File(this.getPath()).getAbsolutePath());
    }

    public String getPath(){
        return path;
    }

    @Override
    public String toString(){
        return path;
    }
}
