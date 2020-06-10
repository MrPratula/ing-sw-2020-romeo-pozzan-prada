package it.polimi.ingsw;

import java.util.Scanner;

public class DontCareAboutThis {

    public static void main(String[] args) {


        System.out.println("1 to use Cli, 2 to use Gui");
        Scanner scanner = new Scanner(System.in);
        int number;
        try{
            number = scanner.nextInt();
            while(number != 1 && number != 2){
                System.out.println("Wrong number, try again: ");
                System.out.println("1 per Cli, 2 per Gui");
                number = scanner.nextInt();
            }
        } catch (Exception e){
            number = 1;
        }

        System.out.println(number);


    }






}