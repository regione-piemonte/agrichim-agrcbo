function ricercaElenco(form)
{
  var msg='';

  if (!(form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''))
  {
    if (!controlla(form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
      msg=msg+'Valorizzare correttamente la voce "Fatture con data compresa da"\n';
  }
  form.dataA.value=form.dataAGiorno.value+"/"+form.dataAMese.value+"/"+form.dataAAnno.value;
  if ( form.dataA.value == "//" )
    form.dataA.value = "";

  if (!(form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''))
  {
    if (!controlla(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value))
      msg=msg+'Valorizzare correttamente la voce "Fatture con data compresa a"\n';
  }
  form.dataDa.value=form.dataDaGiorno.value+"/"+form.dataDaMese.value+"/"+form.dataDaAnno.value;
  if ( form.dataDa.value == "//" )
    form.dataDa.value = "";

  if (msg=='')
  {
    if (form.dataA.value=='' ^ form.dataDa.value=='')
      msg+='Valorizzare tutte le voci di "Fatture con data compresa tra" oppure lasciarle tutte vuote\n';
  }

  var annoDa=false,numDa=false,annoA=false,numA=false;

  if ( form.annoFatturaDa.value != '' || form.numeroFatturaDa.value!='')
  {
    annoDa=true;
    numDa=true;
    if ( (form.annoFatturaDa.value != '') && (!isNumber(form.annoFatturaDa.value)
        || (form.annoFatturaDa.value<1900)))
    {
      annoDa=false;
      msg=msg+'Valorizzare correttamente la voce "Fatture con Anno/Numero compresi da"\n';
    }
    if ( (form.numeroFatturaDa.value != '') && !isNumber(form.numeroFatturaDa.value))
    {
      numDa=false;
      msg=msg+'Valorizzare correttamente la voce "Fatture con Anno/Numero compresi da"\n';
    }
  }

  if ( form.annoFatturaA.value != '' || form.numeroFatturaA.value!='')
  {
    annoA=true;
    numA=true;
    if ( (form.annoFatturaA.value != '') && (!isNumber(form.annoFatturaA.value)
        || (form.annoFatturaA.value<1900)))
    {
      annoA=false;
      msg=msg+'Valorizzare correttamente la voce "Fatture con Anno/Numero compresi a"\n';
    }
    if ( (form.numeroFatturaA.value != '') && !isNumber(form.numeroFatturaA.value))
    {
      numA=false;
      msg=msg+'Valorizzare correttamente la voce "Fatture con Anno/Numero compresi a"\n';
    }
  }

  if ((annoA && numA) || (annoDa && numDa))
  {
    if (form.annoFatturaA.value == '' || form.numeroFatturaA.value ==''
        || form.annoFatturaDa.value == '' || form.numeroFatturaDa.value =='')
      msg=msg+'Valorizzare tutte le voci di "Anno / Numero fattura tra" oppure lasciarle tutte vuote\n';
  }



  lung= form.codFisPIVA.value.length;
  form.codFisPIVA.value = form.codFisPIVA.value.toUpperCase();
  if (!(lung==11 || lung==16 || lung==0))
  {
    msg=msg+'Inserire una Partita IVA o un Codice Fiscale oppure lasciare il campo "Codice Fiscale o Partita IVA" vuoto\n';
  }
  else
  {
    if (lung==11)
    {
      msgTmp = controllaPartitaIVA(form.codFisPIVA.value);
      if (msgTmp!='')
          msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
    }
    if (lung==16)
    {
      msgTmp = controllaCodiceFiscale(form.codFisPIVA.value);
      if (msgTmp!='')
          msg=msg+'"Partita IVA o Codice fiscale" - '+msgTmp+'\n';
    }
  }

  if ( msg != '' )
  {
      alert( "Dati non corretti\n\n"+msg );
  }
  else
  {
      hWin = window.open('','stampaFatture','scrollbars=yes,resizable=yes,width=647,height=480,status=yes,location=no,toolbar=no');
      hWin.focus();
      form.submit();
  }
}
