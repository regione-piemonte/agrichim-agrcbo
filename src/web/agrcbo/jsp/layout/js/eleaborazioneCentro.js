/**
 * 
 */

function elaborazioneCentroVerifiche(form)
{
  var msg='';

  
  if(form.codiceVerificaElab.value=='')
  {
	 	msg=msg+'Inserire il "codice verifica elabrazione"\n';
  }
  if ( msg != '' )
  {
      alert( "Dati incompleti\n\n"+msg );
      return false;
  }
  else
      return true;
}