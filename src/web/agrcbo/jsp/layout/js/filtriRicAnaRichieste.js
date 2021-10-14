function ricercaElenco(form)
{
  var msg='';
  if ( (form.idRichiestaDa.value != '') && !isNumber(form.idRichiestaDa.value))
        msg=msg+'Valorizzare correttamente la voce "Numero della richiesta da"\n';
  if ( (form.idRichiestaA.value != '') && !isNumber(form.idRichiestaA.value))
        msg=msg+'Valorizzare correttamente la voce "Numero della richiesta a"\n';
  if (!(form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''))
  {
    if (!controlla(form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
          msg=msg+'Valorizzare correttamente la "Data di richiesta analisi precedente al"\n';
  }
  form.dataA.value=form.dataAGiorno.value+"/"+form.dataAMese.value+"/"+form.dataAAnno.value;
  if ( form.dataA.value == "//" )
    form.dataA.value = "";

  if (!(form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''))
  {
    if (!controlla(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value))
          msg=msg+'Valorizzare correttamente la "Data di richiesta analisi posteriore  al"\n';
  }
  form.dataDa.value=form.dataDaGiorno.value+"/"+form.dataDaMese.value+"/"+form.dataDaAnno.value;
  if ( form.dataDa.value == "//" )
    form.dataDa.value = "";

  form.cognomeProprietario.value=form.cognomeProprietario.value.trim();
  form.nomeTecnico.value=form.nomeTecnico.value.trim();
  form.cognomeTecnico.value=form.cognomeTecnico.value.trim();
  form.nomeProprietario.value=form.nomeProprietario.value.trim();
  form.etichetta.value=form.etichetta.value.trim();

  if ( (form.cognomeProprietario.value != '') && (form.cognomeProprietario.value.length<2))
    msg=msg+'Inserire almeno 2 caratteri nella voce "Cognome o Ragione sociale" del proprietario\n';
  if ( (form.cognomeTecnico.value != '') && (form.cognomeTecnico.value.length<2))
    msg=msg+'Inserire almeno 2 caratteri nella voce "Cognome" del tecnico\n';
  if ( (form.etichetta.value != '') && (form.etichetta.value.length<4))
    msg=msg+'Inserire almeno 4 caratteri nella voce "Etichetta"\n';
  if ( msg != '' )
  {
      alert( "Dati non corretti\n\n"+msg );
      return false;
  }
  else
  {
    if (form.tipoOrganizzazione.value!='-1' && form.organizzazione.value=='-1')
    {
      msg=msg+'Se si seleziona un tipo di organizzazione, è necessario selezionare un\' Organizzazione\n';
    }
  }
  if ( msg != '' )
  {
      alert( "Dati non completi\n\n"+msg );
      return false;
  }
  return true;
}
