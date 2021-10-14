function salva(form)
{
    var msg='';
    form.phAcqua.value=form.phAcqua.value.trim();
    form.phCloruroPotassio.value=form.phCloruroPotassio.value.trim();
    form.phTampone.value=form.phTampone.value.trim();
    var phAcqua =form.phAcqua.value.replace(",",".");
    var phCloruroPotassio=form.phCloruroPotassio.value.replace(",",".");
    var phTampone=form.phTampone.value.replace(",",".");


    if ((phAcqua == '') && (phCloruroPotassio == '') && (phTampone==''))
        msg=msg+'Valorizzare correttamente uno solo dei 3 campi\n';
    else
    {
      if ((!isFloat(phAcqua,1) || (phAcqua<=0) || (phAcqua>14)) && phAcqua!='')
          msg=msg+'Inserire un numero maggiore di 0 e  minore o uguale 15 in "pH in acqua 1:2,5"\n';
      if ((!isFloat(phCloruroPotassio,1) || (phCloruroPotassio<0) || (phCloruroPotassio>14)) && phCloruroPotassio!='')
          msg=msg+'Inserire un numero positivo minore o uguale 15 in "pH in cloruro di potassio"\n';
      if ((!isFloat(phTampone,1) || (phTampone<0) || (phTampone>14)) && phTampone!='')
          msg=msg+'Inserire un numero positivo minore o uguale 15 in "pH tampone"\n';
    }

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
    {
      if ( ( (phAcqua != '') && ( (phCloruroPotassio != '') || (phTampone != '') ) ) ||
           ( (phCloruroPotassio != '') && (phTampone != '') ) )
        msg=msg+'Valorizzare uno solo dei 3 campi\n';

      if ( msg != '' )
      {
          alert( "Dati non corretti\n\n"+msg );
          return false;
      }
      else return true;
    }
    return true;
}

