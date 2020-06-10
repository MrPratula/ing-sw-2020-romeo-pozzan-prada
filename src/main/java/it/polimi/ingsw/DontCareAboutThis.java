package it.polimi.ingsw;

import java.util.Arrays;
import java.util.Scanner;

public class DontCareAboutThis {

    public static void main(String[] args) {


        boolean inputs = askYesOrNot();

        System.out.println((inputs));


    }





    public static boolean askYesOrNot(){

        Scanner in = new Scanner(System.in);
        String input = in.nextLine().toUpperCase();

        return input.equals("Y") || input.equals("YE") || input.equals("YES") || input.equals("TRUE") || input.equals("SI");
    }
}