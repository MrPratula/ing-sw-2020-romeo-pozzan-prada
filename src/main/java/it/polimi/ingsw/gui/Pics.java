package it.polimi.ingsw.gui;

import javax.swing.*;
import java.io.File;

/**
 * Enum with all the useful images for tui GUI interface
 */
public enum Pics {

        // BUILDINGS
        LEVEL0("./src/main/images/buildings/" + "level0.png"),  //0
        LEVEL1("./src/main/images/buildings/" + "level1.png"),   //1
        LEVEL2("./src/main/images/buildings/" + "level2.png"),  //2
        LEVEL3("./src/main/images/buildings/" + "level3.png"), //3
        LEVEL0DOME("./src/main/images/buildings/" + "level0Dome.png"), //26
        LEVEL1DOME("./src/main/images/buildings/" + "level1Dome.png"), //27
        LEVEL2DOME("./src/main/images/buildings/" + "level2Dome.png"), //28
        LEVEL3DOME("./src/main/images/buildings/" + "level3Dome.png"), //4
        LEVEL0TOKENRED("./src/main/images/buildings/" + "level0tokenRed.png"),  //5
        LEVEL1TOKENRED("./src/main/images/buildings/" + "level1tokenRed.png"), //6
        LEVEL2TOKENRED("./src/main/images/buildings/" + "level2tokenRed.png"),//7
        LEVEL3TOKENRED("./src/main/images/buildings/" + "level3tokenRed.png"),  //8
        LEVEL0TOKENBLUE("./src/main/images/buildings/" + "level0tokenBlue.png"),   //9
        LEVEL1TOKENBLUE("./src/main/images/buildings/" + "level1tokenBlue.png"),  //10
        LEVEL2TOKENBLUE("./src/main/images/buildings/" + "level2tokenBlue.png"),  //11
        LEVEL3TOKENBLUE("./src/main/images/buildings/" + "level3tokenBlue.png"), //12
        LEVEL0TOKENYELLOW("./src/main/images/buildings/" + "level0tokenYellow.png"), //13
        LEVEL1TOKENYELLOW("./src/main/images/buildings/" + "level1tokenYellow.png"), //14
        LEVEL2TOKENYELLOW("./src/main/images/buildings/" + "level2tokenYellow.png"), //15
        LEVEL3TOKENYELLOW("./src/main/images/buildings/" + "level3tokenYellow.png"), //16
        LEVEL0TEXT("./src/main/images/buildings/" + "level0Text.png"),  //17
        LEVEL1TEXT("./src/main/images/buildings/" + "level1Text.png"), //18
        LEVEL2TEXT("./src/main/images/buildings/" + "level2Text.png"),//19
        LEVEL3TEXT("./src/main/images/buildings/" + "level3Text.png"), //20
        LEVEL0DOMETEXT("./src/main/images/buildings/" + "level0DomeText.png"), //21
        LEVEL1DOMETEXT("./src/main/images/buildings/" + "level1DomeText.png"), //21
        LEVEL2DOMETEXT("./src/main/images/buildings/" + "level2DomeText.png"), //21
        LEVEL3DOMETEXT("./src/main/images/buildings/" + "level3DomeText.png"), //21
        LEVEL0VALIDMOVE("./src/main/images/buildings/" + "level0ValidMove.png"), //22
        LEVEL0TOKENREDVALIDMOVE("./src/main/images/buildings/" + "level0tokenRedValidMove.png"), //22
        LEVEL0TOKENBLUEVALIDMOVE("./src/main/images/buildings/" + "level0tokenBlueValidMove.png"), //22
        LEVEL0TOKENYELLOWVALIDMOVE("./src/main/images/buildings/" + "level0tokenYellowValidMove.png"), //22
        LEVEL1VALIDMOVE("./src/main/images/buildings/" + "level1ValidMove.png"), //23
        LEVEL1TOKENREDVALIDMOVE("./src/main/images/buildings/" + "level1TokenRedValidMove.png"), //23
        LEVEL1TOKENBLUEVALIDMOVE("./src/main/images/buildings/" + "level1tokenBlueValidMove.png"), //23
        LEVEL1TOKENYELLOWVALIDMOVE("./src/main/images/buildings/" + "level1tokenYellowValidMove.png"), //23
        LEVEL2VALIDMOVE("./src/main/images/buildings/" + "level2ValidMove.png"), //24
        LEVEL2TOKENREDVALIDMOVE("./src/main/images/buildings/" + "level2tokenRedValidMove.png"), //24
        LEVEL2TOKENBLUEVALIDMOVE("./src/main/images/buildings/" + "level2tokenBlueValidMove.png"), //24
        LEVEL2TOKENYELLOWVALIDMOVE("./src/main/images/buildings/" + "level2tokenYellowValidMove.png"), //24
        LEVEL3VALIDMOVE("./src/main/images/buildings/" + "level3ValidMove.png"), //24
        LEVEL3TOKENREDVALIDMOVE("./src/main/images/buildings/" + "level3tokenRedValidMove.png"), //24
        LEVEL3TOKENBLUEVALIDMOVE("./src/main/images/buildings/" + "level3tokenBlueValidMove.png"), //24
        LEVEL3TOKENYELLOWVALIDMOVE("./src/main/images/buildings/" + "level3tokenYellowValidMove.png"), //24

        // GODCARDS
        APOLLO("./src/main/images/godcards/" + "apollo.png"),  //0
        APOLLOPANEL("./src/main/images/godcards/" + "apolloPanel.png"),  //0
        ARTEMIS("./src/main/images/godcards/" + "artemis.png"),   //1
        ARTEMISPANEL("./src/main/images/godcards/" + "artemisPanel.png"),   //1
        ATHENA("./src/main/images/godcards/" + "athena.png"),  //2
        ATHENAPANEL("./src/main/images/godcards/" + "athenaPanel.png"),  //2
        ATLAS("./src/main/images/godcards/" + "atlas.png"),  //3
        CHRONUS("./src/main/images/godcards/" + "chronus.png"), //4
        DEMETER("./src/main/images/godcards/" + "demeter.png"),  //5
        HEPHAESTUS("./src/main/images/godcards/" + "hephaestus.png"), //6
        HERA("./src/main/images/godcards/" + "hera.png"), //7
        HESTIA("./src/main/images/godcards/" + "hestia.png"),  //8
        LIMUS("./src/main/images/godcards/" + "limus.png"),   //9
        MINOTAUR("./src/main/images/godcards/" + "minotaur.png"),  //10
        PAN("./src/main/images/godcards/" + "pan.png"),  //11
        PROMETHEUS("./src/main/images/godcards/" + "prometheus.png"), //12
        ZEUS("./src/main/images/godcards/" + "zeus.png"), //13
        APOLLOTEXT("./src/main/images/godcards/" + "apolloText.png"),  //+ 0
        ARTEMISTEXT("./src/main/images/godcards/" + "artemisText.png"),   //1
        ATHENATEXT("./src/main/images/godcards/" + "athenaText.png"),  //2
        ATLASTEXT("./src/main/images/godcards/" + "atlasText.png"), //3
        CHRONUSTEXT("./src/main/images/godcards/" + "chronusText.png"), //4
        DEMETERTEXT("./src/main/images/godcards/" + "demeterText.png"),  //5
        HEPHAESTUSTEXT("./src/main/images/godcards/" + "hephaestusText.png"), //6
        HERATEXT("./src/main/images/godcards/" + "heraText.png"), //7
        HESTIATEXT("./src/main/images/godcards/" + "hestiaText.png"),  //8
        LIMUSTEXT("./src/main/images/godcards/" + "limusText.png"),   //9
        MINOTAURTEXT("./src/main/images/godcards/" + "minotaurText.png"),  //10
        PANTEXT("./src/main/images/godcards/" + "panText.png"),  //11
        PROMETHEUSTEXT("./src/main/images/godcards/" + "prometheusText.png"), //12
        ZEUSTEXT("./src/main/images/godcards/" + "zeusText.png"), //13
        NOTHIRDPLAYER("./src/main/images/godcards/" + "nothirdplayer.png"), //13

        // UTILS
        BOARD("./src/main/images/utils/" + "board.png"),
        DONE("./src/main/images/utils/" + "done.png"),
        MESSAGEBG2("./src/main/images/utils/" + "bgMessage2.png"),
        BATTLEFIELDICON("./src/main/images/utils/" + "battlefieldIcon.png"),
        GODICON("./src/main/images/utils/" + "godIcon.png"),
        ERRORICON("./src/main/images/utils/" + "errorIcon.png"),
        INFORMATIONICON("./src/main/images/utils/" + "informationIcon.png"),
        PLAYERICON("./src/main/images/utils/" + "playerIcon.png"),
        GAMEOVERICON("./src/main/images/utils/" + "gameover.png"),
        BUTTON("./src/main/images/utils/" + "btn_purple.png"),
        SANTORINI("./src/main/images/utils/" + "Santorini.png"),
        PLAYBUTTON("./src/main/images/utils/" + "buttonPlay.png"),
        NICKNAME("./src/main/images/utils/" + "nickname.png"),
        NUMBEROFPLAYERS("./src/main/images/utils/" + "howmanyplayers.png"),
        GODPOWER("./src/main/images/utils/" + "godpower.png"),
        ASKFORPROMETHEUSPOWER("./src/main/images/utils/" + "prometheus.png"),

        // MESSAGES
        WELCOMEMESSAGE("./src/main/images/messages/" + "welcomeMessage.png"),
        ASK_FOR_BUILD("./src/main/images/messages/" + "ASK_FOR_BUILD.png"),
        ASK_FOR_SELECT_TOKEN("./src/main/images/messages/" + "ASK_FOR_SELECT_TOKEN.png"),
        ASK_FOR_WHERE_TO_MOVE("./src/main/images/messages/" + "ASK_FOR_WHERE_TO_MOVE.png"),
        PLACE_YOUR_TOKEN("./src/main/images/messages/" + "PLACE_YOUR_TOKEN.png"),
        NOT_YOUR_TURN("./src/main/images/messages/" + "NOT_YOUR_TURN.png"),

        // BACKGROUNDS
        DIALOGBG("./src/main/images/backgrounds/" + "dialogBg.png"),
        MAINBG("./src/main/images/backgrounds/" + "mainBg.png"),
        COLUMNS("./src/main/images/backgrounds/" + "columns.png"),
        OLYMPUS("./src/main/images/backgrounds/" + "olympus.png");


    private String path;

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
