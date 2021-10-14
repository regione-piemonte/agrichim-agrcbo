function ricercaElenco(form)
{
  var msg='';

  if (form.annoFattura.value == ''
      &&
      form.numeroFattura.value==''
      &&
      form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''
      &&
      form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''
      &&
      form.numeroRichiesta.value == ''
      &&
      form.annoCampione.value == ''
      &&
      form.numeroCampione.value == ''
      &&
      form.codFisPIVA.value==''
      &&
      form.statoPagamento.value == ''
      &&
      form.statoFattura.value==''
      )
      {
        alert( "Attenzione: non è stato valorizzato nessun campo di ricerca" );
        return;
      }
  if ( form.annoFattura.value != '' || form.numeroFattura.value!='')
  {
    var anno=true,num=true;
    if ( (form.annoFattura.value != '') && (!isNumber(form.annoFattura.value)
        || (form.annoFattura.value<1900)))
    {
      anno=false;
      msg=msg+'Valorizzare correttamente la voce "Anno fattura"\n';
    }
    if ( (form.numeroFattura.value != '') && !isNumber(form.numeroFattura.value))
    {
      num=false;
      msg=msg+'Valorizzare correttamente la voce "Numero fattura"\n';
    }
    if (anno && num)
     if (form.annoFattura.value == '' || form.numeroFattura.value =='')
       msg=msg+'Le voci "Anno / Numero fattura" devono essere entrambe valorizzate\n';
  }


  if (!(form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''))
  {
    if (!controlla(form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
          msg=msg+'Valorizzare correttamente la voce "Fatture con data dal"\n';
  }
  form.dataA.value=form.dataAGiorno.value+"/"+form.dataAMese.value+"/"+form.dataAAnno.value;
  if ( form.dataA.value == "//" )
    form.dataA.value = "";

  if (!(form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''))
  {
    if (!controlla(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value))
          msg=msg+'Valorizzare correttamente la voce "Fatture con data al"\n';
  }
  form.dataDa.value=form.dataDaGiorno.value+"/"+form.dataDaMese.value+"/"+form.dataDaAnno.value;
  if ( form.dataDa.value == "//" )
    form.dataDa.value = "";


  if ( (form.numeroRichiesta.value != '') && !isNumber(form.numeroRichiesta.value))
  {
    msg=msg+'Valorizzare correttamente la voce "Numero della richiesta"\n';
  }
  if ( (form.annoCampione.value != '') && (!isNumber(form.annoCampione.value)
        || (form.annoCampione.value<1900)))
  {
    anno=false;
    msg=msg+'Valorizzare correttamente la voce "Anno campione"\n';
  }
  if ( (form.numeroCampione.value != '') && !isNumber(form.numeroCampione.value))
  {
    num=false;
    msg=msg+'Valorizzare correttamente la voce "Numero campione"\n';
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
      form.submit();
  }
}
