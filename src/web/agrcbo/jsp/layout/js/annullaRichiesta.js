var singoloClick = true;

function salva(form)
{
	var msg = '';
	form.note.value = form.note.value.trim();

	if (form.note.value == '')
	{
		msg = msg + 'Valorizzare il campo motivazione\n';
	}

    if (msg != '')
    {
        alert("Dati incompleti\n\n" + msg);
        return;
    }
    else
    {
      singoloClick = false;
      form.submit();
    }
}