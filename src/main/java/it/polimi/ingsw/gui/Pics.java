package it.polimi.ingsw.gui;

import javax.swing.*;
import java.io.File;

/**
 * Enum with all the useful images for tui GUI interface
 */
public enum Pics {

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
            LEVELDOMETEXT("./src/main/images/buildings/" + "levelDomeText.png"), //21
            LEVEL0VALIDMOVE("./src/main/images/buildings/" + "level0ValidMove.png"), //22
            LEVEL1VALIDMOVE("./src/main/images/buildings/" + "level1ValidMove.png"), //23
            LEVEL2VALIDMOVE("./src/main/images/buildings/" + "level2ValidMove.png"), //24
            LEVEL3VALIDMOVE("./src/main/images/buildings/" + "level3ValidMove.png"), //24
            BOARD("./src/main/images/utils/" + "board.png"), //26

            APOLLO("./src/main/images/godcards/" + "apollo.png"),  //0
            ARTEMIS("./src/main/images/godcards/" + "artemis.png"),   //1
            ATHENA("./src/main/images/godcards/" + "athena.png"),  //2
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
            ZEUSTEXT("./src/main/images/godcards/" + "zeusText.png"); //13


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
