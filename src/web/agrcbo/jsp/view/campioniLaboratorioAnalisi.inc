<%
  /**
   * Se è valorizzato il parametro idRichiesta significa che questa pagina è
   * stata ricaricata perchè è stata modificata la selezione della combo.
   * Quindi devo leggere il nuovo valore di idRichiesta.
   * */
   String idRichiesta=request.getParameter("idRichiesta");
   long longRichiesta=0;
   try
   {
     longRichiesta=Long.parseLong(idRichiesta);
     aut.setIdRichiestaCorrente(longRichiesta);
     session.setAttribute("aut",aut);
   }
   catch(Exception e)
   {
     longRichiesta=0;
   }

   /**
    * Carico la combo con le etichetteCampione selezionate dall'utente
    **/
   etichettaCampioni.selectAnnoNumero(aut.getIdRichieste());
   int size=etichettaCampioni.size();
   EtichettaCampione e;
   for(int i=0;i<size;i++)
   {
     e=etichettaCampioni.get(i);
     templ.newBlock("annoNumero");
     if (longRichiesta!=0)
     {
       if ((""+aut.getIdRichiestaCorrente()).equals(e.getIdRichiesta()))
         templ.set("annoNumero.selected","selected");
       else
         templ.set("annoNumero.selected","");
     }
     else
     {
       if (i==0)
       {
         templ.set("annoNumero.selected","selected");
         try
         {
          aut.setIdRichiestaCorrente(Long.parseLong(e.getIdRichiesta()));
          session.setAttribute("aut",aut);
         }
         catch(Exception eNum){eNum.printStackTrace();}
       }
       else
           templ.set("annoNumero.selected","");
     }
     templ.set("annoNumero.idRichiesta",e.getIdRichiesta());
     templ.set("annoNumero.annoNumero",e.getNumeroCampione()+"/"+e.getAnnoCampione());
   }
   etichettaCampione.selectForCampioniLab();
   templ.newBlock("etichetta");
   templ.set("etichetta.idRichiesta",etichettaCampione.getIdRichiesta());
   templ.set("etichetta.descMateriale",etichettaCampione.getDescMateriale());
   templ.set("etichetta.etichetta",etichettaCampione.getDescrizioneEtichetta());
   templ.set("etichetta.labAnalisi",etichettaCampione.getDescLabAnalisi());

   /**
    * Il codice seguente mi serve per visualizzare correttamente il pdf di
    * anagraficaCampione
    * */
   templ.bset("idRichiesta",aut.getIdRichiestaCorrente()+"");
   templ.bset("codiceMateriale",aut.getCodMateriale());

     /**
    * Il codice seguente mi serve per visualizzare correttamente il pdf con
    * l'elenco delle analisi richieste
    * */
   templ.bset("idRichiestePDF",aut.getIdRichieste());
   
   //Identificativo richiesta necessario per il menu
   if (longRichiesta > 0)
   {
   	templ.bset("idRichiestaSearch", idRichiesta);
   }
   else
   {
  	 templ.bset("idRichiestaSearch", "" + aut.getIdRichiestaCorrente());
   }
%>
