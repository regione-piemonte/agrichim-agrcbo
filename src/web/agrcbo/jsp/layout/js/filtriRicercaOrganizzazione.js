function ricercaElenco(form)
{
  var msg='';

  form.ragioneSociale.value=form.ragioneSociale.value.trim();
  if ( (form.ragioneSociale.value != '') && (form.ragioneSociale.value.length<3))
    msg=msg+'Inserire almeno 3 caratteri nella voce "Ragione sociale"\n';

  if ( msg != '' )
  {
      alert( "Dati non corretti\n\n"+msg );
  }
  else
  {
      form.submit();
  }
}
