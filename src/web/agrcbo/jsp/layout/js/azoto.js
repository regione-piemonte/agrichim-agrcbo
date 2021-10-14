function salva(form)
{
    var msg='';
    form.azotoTotaleMetodoAna.value=form.azotoTotaleMetodoAna.value.trim();
    form.azotoKjeldahl.value=form.azotoKjeldahl.value.trim();
    var azotoTotaleMetodoAna =form.azotoTotaleMetodoAna.value.replace(",",".");
    var azotoKjeldahl=form.azotoKjeldahl.value.replace(",",".");
    var errore=false;
    msg='Valorizzare correttamente il campo "Azoto totale (metodo ANA) %" oppure il campo "Azoto Kjeldahl %"\n';

    /**
     * Solo uno dei due campi deve essere valorizzato ed essere maggiore di zero
     * */
    if ((azotoTotaleMetodoAna == '' ) && (azotoKjeldahl == '' ))
      errore=true;
    else
    {
      if (azotoTotaleMetodoAna != '' && azotoKjeldahl!='')
      {
        if (!(azotoTotaleMetodoAna == 0 ^ azotoKjeldahl==0))
          errore=true;
      }
      else
      {
        if (((azotoTotaleMetodoAna != '') &&
          (!isFloat(azotoTotaleMetodoAna,3) || (azotoTotaleMetodoAna<0) || (azotoTotaleMetodoAna>999999.999)))
          ||
          ((azotoKjeldahl != '') &&
          (!isFloat(azotoKjeldahl,3) || (azotoKjeldahl<0) || (azotoKjeldahl>999999.999))) )
            errore=true;
        if ((azotoTotaleMetodoAna=='' && azotoKjeldahl==0) || (azotoTotaleMetodoAna==0 && azotoKjeldahl==''))
          errore=true;
      }
    }
    if (errore)
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    return true;
}

