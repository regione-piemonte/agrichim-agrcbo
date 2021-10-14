function salva(form)
{
    var msg='';
    form.ragioneSociale.value=form.ragioneSociale.value.trim();
    form.indirizzo.value=form.indirizzo.value.trim();
    form.sedeTerritoriale.value=form.sedeTerritoriale.value.trim();

    if (form.idTipoOrganizzazione.value == '-1')
      msg=msg+'Selezionare una voce in "Tipo organizzazione"\n';


    lung= form.cfPartitaIva.value.length;
    form.cfPartitaIva.value = form.cfPartitaIva.value.toUpperCase();
    if (!(lung==11 || lung==16 || lung==0))
    {
      msg=msg+'Inserire una Partita IVA o un Codice Fiscale oppure lasciare il campo "Codice Fiscale / Partita IVA" vuoto\n';
    }
    else
    {
      if (lung==11)
      {
        msgTmp = controllaPartitaIVA(form.cfPartitaIva.value);
        if (msgTmp!='')
            msg=msg+'"Codice Fiscale / Partita IVA" - '+msgTmp+'\n';
      }
      if (lung==16)
      {
        msgTmp = controllaCodiceFiscale(form.cfPartitaIva.value);
        if (msgTmp!='')
            msg=msg+'"Codice Fiscale / Partita IVA" - '+msgTmp+'\n';
      }
    }

    if (form.ragioneSociale.value == '')
      msg=msg+'Valorizzare la voce "Ragione Sociale"\n';
    if (form.indirizzo.value == '')
      msg=msg+'Valorizzare la voce "Indirizzo"\n';
    if ( (form.cap.value != '') && !((form.cap.value.length == 5) && (isNumber(form.cap.value))) )
      msg=msg+'Valorizzare correttamente la voce "CAP"\n';
    if (form.comune.value == '')
      msg=msg+'Selezionare il "Comune"\n';

    if (form.email.value!='' && controllaMail(form.email.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail"\n';


    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

