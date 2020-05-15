package it.polimi.ingsw.gui;

// QUESTA CLASSE VIENE USATA PER DARCI DELLE INFORMAZIONI.
// NON APPENA AVREMMO TROVATO TUTTE LE SOLUZIONI LA CANCELLEREMO.

/*
    1) Il path per la grafica di "santorini" e "playBotton" dipende dal nostro pc,
    quindi classe SWINGVIEW righe 44 e 49 bisogna scriverle così:
    C:\Users\******\IdeaProjects\ing-sw-2020-romeo-pozzan-prada\src\main\java\it\polimi\ingsw\gui\graphics
    dove al posto di ****** ci va il nome della cartella propria
    ovviamente in fondo al path ci va il nome dell'immagine.png

    2) La grandezze dei bottoni o delle jlabel dipende dall'immagine che mettiamo non da setSize().
    Quando si aggiungono immagini il discorso vale anche per i frame/panel ecc.
    Il bottone di Play l'ho ridimensionato. ( per ridimensionare basta modificare con paint,
    ovviamente però si perde qualità ). Nel caso valuteremo anche questo più avanti, adesso l'importante
    è che tutto funzioni.

    3) Nel setupDialog ho stratificato i panel perchè al primo start mi venivano tutte le cose sovrapposte.
    ho tenuto comunque il codice vecchio tra commenti.
 */
