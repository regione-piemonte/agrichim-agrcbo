function salva(form)
{
    var msg='';
    var analisi = form.analisi.value;
    if (analisi=='Std')
    {
      form.argilla.value=form.argilla.value.trim();
      form.limoTotale.value=form.limoTotale.value.trim();
      var argilla =form.argilla.value.replace(",",".");
      var limoTotale=form.limoTotale.value.replace(",",".");
      if (!isFloat(argilla,2) || (argilla<0) || (argilla>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Argilla"\n';
      if (!isFloat(limoTotale,2) || (limoTotale<0) || (limoTotale>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Limo totale"\n';
      if ( msg != '' )
      {
          alert( "Dati incompleti\n\n"+msg );
          return false;
      }
      else
      {
        if (parseFloat(argilla) + parseFloat(limoTotale)>100)
        {
          msg=msg+'L\'argilla più il limo totale deve essere minore o uguale a 100\n';
        }
        if ( msg != '' )
        {
            alert( "Dati non corretti\n\n"+msg );
            return false;
        }
        else return true;
      }
    }
    if (analisi=='4Fra')
    {
      form.argilla.value=form.argilla.value.trim();
      form.limoTotale.value=form.limoTotale.value.trim();
      form.limoFine.value=form.limoFine.value.trim();
      var argilla =form.argilla.value.replace(",",".");
      var limoTotale=form.limoTotale.value.replace(",",".");
      var limoFine=form.limoFine.value.replace(",",".");
      if (!isFloat(argilla,2) || (argilla<0) || (argilla>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Argilla"\n';
      if (!isFloat(limoTotale,2) || (limoTotale<0) || (limoTotale>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Limo totale"\n';
      if (!isFloat(limoFine,2) || (limoFine<0) || (limoFine>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Limo fine"\n';
      if ( msg != '' )
      {
          alert( "Dati incompleti\n\n"+msg );
          return false;
      }
      else
      {
        if (parseFloat(limoFine) > parseFloat(limoTotale))
        {
          msg=msg+'Il limo fine non può essere maggiore del limo totale\n';
        }
        if (parseFloat(argilla) + parseFloat(limoTotale)>100)
        {
          msg=msg+'L\'argilla più il limo totale deve essere minore o uguale a 100\n';
        }
        if ( msg != '' )
        {
            alert( "Dati non corretti\n\n"+msg );
            return false;
        }
        else return true;
      }
    }
    if (analisi=='5Fra')
    {
      form.argilla.value=form.argilla.value.trim();
      form.limoTotale.value=form.limoTotale.value.trim();
      form.limoFine.value=form.limoFine.value.trim();
      form.sabbiaTotale.value=form.sabbiaTotale.value.trim();
      form.sabbiaGrossa.value=form.sabbiaGrossa.value.trim();
      var argilla =form.argilla.value.replace(",",".");
      var limoTotale=form.limoTotale.value.replace(",",".");
      var limoFine=form.limoFine.value.replace(",",".");
      var sabbiaTotale=form.sabbiaTotale.value.replace(",",".");
      var sabbiaGrossa=form.sabbiaGrossa.value.replace(",",".");
      if (!isFloat(argilla,2) || (argilla<0) || (argilla>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Argilla"\n';
      if (!isFloat(limoTotale,2) || (limoTotale<0) || (limoTotale>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Limo totale"\n';
      if (!isFloat(limoFine,2) || (limoFine<0) || (limoFine>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Limo fine"\n';
      if (!isFloat(sabbiaTotale,2) || (sabbiaTotale<0) || (sabbiaTotale>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Sabbia totale"\n';
      if (!isFloat(sabbiaGrossa,2) || (sabbiaGrossa<0) || (sabbiaGrossa>999.99))
          msg=msg+'Inserire un numero nel formato ###,## nel campo "% Sabbia grossa"\n';
      if ( msg != '' )
      {
          alert( "Dati incompleti\n\n"+msg );
          return false;
      }
      else
      {
        if (parseFloat(sabbiaGrossa) > parseFloat(sabbiaTotale))
        {
          msg=msg+'La sabbia grossa non può essere maggiore della sabbia totale\n';
        }
        if (parseFloat(limoFine) > parseFloat(limoTotale))
        {
          msg=msg+'Il limo fine non può essere maggiore del limo totale\n';
        }
        if (parseFloat(argilla) +
            parseFloat(sabbiaTotale) +
            parseFloat(limoTotale)!=100)
        {
          msg=msg+'L\'argilla più il limo totale più la sabbia totale deve essere uguale a 100\n';
        }
        if ( msg != '' )
        {
            alert( "Dati non corretti\n\n"+msg );
            return false;
        }
        else return true;
      }
    }
}

