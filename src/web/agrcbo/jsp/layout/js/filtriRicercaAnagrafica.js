function ricercaElenco(form)
{
  var msg='';

  form.ragioneSociale.value=form.ragioneSociale.value.trim();
  if ( (form.ragioneSociale.value != '') && (form.ragioneSociale.value.length<3))
    msg=msg+'Inserire almeno 3 caratteri nella voce "Cognome o Ragione Sociale "\n';


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
