function salva(form, ferroTotale, manganeseTotale, zincoTotale, rameTotale, piomboTotale, cromoTotale, boroTotale, nichelTotale, cadmioTotale, stronzioTotale, altroMetalloTotale)
{
    var msg = '';

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del ferro totale
     * */
    if (ferroTotale)
    {
      form.ferroTotale.value = form.ferroTotale.value.trim();
      var ferroTotale = form.ferroTotale.value.replace(",",".");
      if (! isFloat(ferroTotale, 3) || (ferroTotale < 0) || (ferroTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Ferro totale (p.p.m.)"\n';
	  }
    }

     /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del manganese totale
     * */
    if (manganeseTotale)
    {
      form.manganeseTotale.value = form.manganeseTotale.value.trim();
      var manganeseTotale = form.manganeseTotale.value.replace(",",".");
      if (! isFloat(manganeseTotale, 3) || (manganeseTotale < 0) || (manganeseTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Manganese totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi dello zinco totale
     * */
    if (zincoTotale)
    {
      form.zincoTotale.value = form.zincoTotale.value.trim();
      var zincoTotale = form.zincoTotale.value.replace(",",".");
      if (! isFloat(zincoTotale, 3) || (zincoTotale < 0) || (zincoTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Zinco totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del rame totale
     * */
    if (rameTotale)
    {
      form.rameTotale.value = form.rameTotale.value.trim();
      var rameTotale = form.rameTotale.value.replace(",",".");
      if (! isFloat(rameTotale, 3) || (rameTotale < 0) || (rameTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Rame totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del piombo totale
     * */
    if (piomboTotale)
    {
      form.piomboTotale.value = form.piomboTotale.value.trim();
      var piomboTotale = form.piomboTotale.value.replace(",",".");
      if (! isFloat(piomboTotale, 3) || (piomboTotale < 0) || (piomboTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Piombo totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del cromo totale
     * */
    if (cromoTotale)
    {
      form.cromoTotale.value = form.cromoTotale.value.trim();
      var cromoTotale = form.cromoTotale.value.replace(",",".");
      if (! isFloat(cromoTotale, 3) || (cromoTotale < 0) || (cromoTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Cromo totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del boro totale
     * */
    if (boroTotale)
    {
      form.boroTotale.value = form.boroTotale.value.trim();
      var boroTotale = form.boroTotale.value.replace(",",".");
      if (! isFloat(boroTotale, 3) || (boroTotale < 0) || (boroTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Boro totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del nichel totale
     * */
    if (nichelTotale)
    {
      form.nichelTotale.value = form.nichelTotale.value.trim();
      var nichelTotale = form.nichelTotale.value.replace(",",".");
      if (! isFloat(nichelTotale, 3) || (nichelTotale < 0) || (nichelTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Nichel totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del cadmio totale
     * */
    if (cadmioTotale)
    {
      form.cadmioTotale.value = form.cadmioTotale.value.trim();
      var cadmioTotale = form.cadmioTotale.value.replace(",",".");
      if (! isFloat(cadmioTotale, 3) || (cadmioTotale < 0) || (cadmioTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Cadmio totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi dello stronzio totale
     * */
    if (stronzioTotale)
    {
      form.stronzioTotale.value = form.stronzioTotale.value.trim();
      var stronzioTotale = form.stronzioTotale.value.replace(",",".");
      if (! isFloat(stronzioTotale, 3) || (stronzioTotale < 0) || (stronzioTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Stronzio totale (p.p.m.)"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi altro metallo totale
     * */
    if (altroMetalloTotale)
    {
      form.altroMetalloTotale.value = form.altroMetalloTotale.value.trim();
      var altroMetalloTotale = form.altroMetalloTotale.value.replace(",",".");
      if (! isFloat(altroMetalloTotale, 3) || (altroMetalloTotale < 0) || (altroMetalloTotale > 999999.999))
	  {
          msg = msg + 'Inserire un numero nel formato ######,### nel campo "Altro metallo totale (p.p.m.)"\n';
	  }
    }

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n" + msg );
        return false;
    }
    else
    {	
        return true;
    }		
}