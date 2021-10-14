function salva(form)
{
    var msg='';
    form.note.value=form.note.value.trim();

    var mail=form.mail[0].checked;
    var firmaReferto=form.firmaReferto[0].checked;

    var i;
	for (i = 0; i < form.pagamento.length; i++)
	{
	  if (form.pagamento[i].checked)
	  {
		break;
	  }
	  if (i == (form.pagamento.length - 1))
	  {
		msg = msg + 'Selezionare lo stato pagamento analisi\n';
	  } 
	}

	var scartoOk = true;
	for (i = 0; i < form.scarto.length; i++)
	{
	  if (form.scarto[i].checked)
	  {
		break;
	  }
	  if (i == (form.scarto.length - 1))
	  {
		msg = msg + 'Selezionare lo Scarto/Sospensione \n';
		scartoOk = false;
	  } 
	}

    if (scartoOk && !form.scarto[0].checked)
    {
      if ( form.note.value=='' )
        msg = msg + 'Valorizzare il campo note\n';
    }

    if (msg != '')
    {
        alert("Dati incompleti\n\n"+msg );
        return;
    }
    
   
    if (form.scarto[2].checked)
        alert('E\' stata inserita un\'anomalia bloccante, non verrà emesso il referto.');

    if (!form.scarto[2].checked)
    {
      msg='Emissione del referto di prova\n\n';

      msg+='Si sta per emettere il referto di prova con il risultato delle analisi: alla conferma il referto sarà disponibile on-line all\'utente.\n\n\n';
      msg+='Il referto verrà emesso ';
      if (firmaReferto) msg+='CON FIRMA\n\n';
      else msg+='SENZA FIRMA\n\n';
      if (mail) msg+='VERRA\' INVIATA UNA E.MAIL DI SEGNALAZIONE ANALISI TERMINATA ALL\'UTENTE\n';
      else msg+='NON VERRA\' INVIATA ALCUNA E.MAIL ALL\'UTENTE \n';
      msg+='\nConfermare l\'emissione del referto?';
      if (confirm(msg)) form.submit();
    }
    else
    {
     form.submit();
    }

}

function back(form)
{
  form.action="../controller/campioniLaboratorioBack.jsp";
  form.submit();
}