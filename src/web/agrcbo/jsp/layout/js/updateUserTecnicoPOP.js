function salva(form)
{
    var msg='';
    form.cognomeRagioneSociale.value=form.cognomeRagioneSociale.value.trim();
    form.nome.value=form.nome.value.trim();

    if (form.cognomeRagioneSociale.value == '')
      msg=msg+'Valorizzare la voce "Cognome o Ragione sociale"\n';

    if (form.tipoPersona.value=="Persona fisica" && form.nome.value == '')
      msg=msg+'Valorizzare la voce "Nome"\n';

    if ( (form.cap.value != '') && !((form.cap.value.length == 5) && (isNumber(form.cap.value))) )
      msg=msg+'Valorizzare correttamente la voce "CAP"\n';

    if (form.email.value!='' && controllaMail(form.email.value))
      msg=msg+'Valorizzare correttamente la voce "E.mail"\n';

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else return true;
}

