function ricercaElenco(form)
{
  var msg='';
  if (form.idRichiestaDa.value == '')
       return false;
  if ( (form.idRichiestaDa.value != '') && !isNumber(form.idRichiestaDa.value))
        msg=msg+'Valorizzare correttamente la voce "Numero della richiesta"\n';

  if ( msg != '' ) alert( "Dati non corretti\n\n"+msg );
  else form.submit();
  return true;
}
