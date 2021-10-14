function salva(form)
{
    var msg='';
    form.indirizzo.value=form.indirizzo.value.trim();
    form.cap.value=form.cap.value.trim();
    if ( ((form.cap.value.length != 5) || (!isNumber(form.cap.value)))
                  && (form.cap.value.length != 0) )
           msg=msg+'Valorizzare correttamente la voce "CAP" del Proprietario del campione\n';
    if (form.istat.value == '')
      msg=msg+'Selezionare il "Comune" del Proprietario del campione\n';
    if (form.email.value!='' && controllaMail(form.email.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail" del Proprietario del campione\n';
        if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
        return true;
}

