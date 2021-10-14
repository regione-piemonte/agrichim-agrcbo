function ricercaElenco(form){
  var msg='';
  if (form.annoCampione.value== '' && form.numeroCampioneDa.value == ''
      && form.idRichiestaDa.value == '' && form.tipoMateriale.value == '')
       return false;
  if ( (form.annoCampione.value != '') &&
         ( !isNumber(form.annoCampione.value) || (form.annoCampione.value<1900)))
        msg=msg+'Valorizzare correttamente la voce "Anno"\n';
  if ( (form.numeroCampioneDa.value != '') && !isNumber(form.numeroCampioneDa.value))
        msg=msg+'Valorizzare correttamente la voce "Numero campione"\n';
  if ( (form.idRichiestaDa.value != '') && !isNumber(form.idRichiestaDa.value))
        msg=msg+'Valorizzare correttamente la voce "Numero della richiesta"\n';

  if ( msg != '' ) 
      alert( "Dati non corretti\n\n"+msg );
  else {
    if (form.annoCampione.value != '')
      if (form.tipoMateriale.value=='') 
        msg=msg+'Se si inserisce l\'anno, e\' necessario selezionare una voce in Tipo di materiale/matrice"\n';
    if (form.numeroCampioneDa.value != '' || form.numeroCampioneDa.value != '') 
      if (form.annoCampione.value == '') 
        msg=msg+'Se si inserisce il numero del campione, e\' necessario inserire anche l\'anno ed il tipo di materiale\n';
      else 
        if (form.tipoMateriale.value=='')
          msg=msg+'Se si inserisce il numero del campione, e\' necessario inserire anche l\'anno ed il tipo di materiale\n';
    }
    if ( msg != '' ) 
        alert( "Dati non completi\n\n"+msg );
  if ( msg == '' ){
      form.submit();
  }
  return true;
}
