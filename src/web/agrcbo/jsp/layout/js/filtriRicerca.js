function ricercaElenco(form)
{
  var msg='';
  var corretti=true;
  if ( (form.annoCampione.value != '') &&
         ( !isNumber(form.annoCampione.value) || (form.annoCampione.value<1900)))
        msg=msg+'Valorizzare correttamente la voce "Anno"\n';
  if ( (form.numeroCampioneDa.value != '') && !isNumber(form.numeroCampioneDa.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero campione da"\n';
  }
  if ( (form.numeroCampioneA.value != '') && !isNumber(form.numeroCampioneA.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero campione a"\n';
  }
  if (corretti)
  {
    if (parseInt(form.numeroCampioneDa.value) > parseInt(form.numeroCampioneA.value))
      msg=msg+'"Numero campione da" non deve essere maggiore di "Numero campione a"\n';
  }


  corretti=true;
  if ( (form.idRichiestaDa.value != '') && !isNumber(form.idRichiestaDa.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero della richiesta da"\n';
  }
  if ( (form.idRichiestaA.value != '') && !isNumber(form.idRichiestaA.value))
  {
    corretti=false;
    msg=msg+'Valorizzare correttamente la voce "Numero della richiesta a"\n';
  }
  if (corretti)
  {
    if (parseInt(form.idRichiestaDa.value) > parseInt(form.idRichiestaA.value))
      msg=msg+'"Numero della richiesta da" non deve essere maggiore di "Numero della richiesta a"\n';
  }

  corretti=true;
  if (!(form.dataAGiorno.value=='' && form.dataAMese.value=='' && form.dataAAnno.value==''))
  {
    if (!controlla(form.dataAGiorno.value,form.dataAMese.value,form.dataAAnno.value))
    {
      msg=msg+'Valorizzare correttamente la "Data di richiesta analisi precedente al"\n';
      corretti=false;
    }
  }
  else corretti=false;
  form.dataA.value=form.dataAGiorno.value+"/"+form.dataAMese.value+"/"+form.dataAAnno.value;
  if ( form.dataA.value == "//" )
    form.dataA.value = "";

  if (!(form.dataDaGiorno.value=='' && form.dataDaMese.value=='' && form.dataDaAnno.value==''))
  {
    if (!controlla(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value))
    {
      corretti=false;
      msg=msg+'Valorizzare correttamente la "Data di richiesta analisi sucessiva al"\n';
    }
  }
  else corretti=false;
  form.dataDa.value=form.dataDaGiorno.value+"/"+form.dataDaMese.value+"/"+form.dataDaAnno.value;
  if ( form.dataDa.value == "//" )
    form.dataDa.value = "";
  
  if (corretti &&  !confronta(form.dataDaGiorno.value,form.dataDaMese.value,form.dataDaAnno.value,form.dataAGiorno.value,
		  form.dataAMese.value,form.dataAAnno.value))
     msg=msg+'"Data di richiesta analisi sucessiva al" non deve essere maggiore di "Data di richiesta analisi precedente al"\n';

  if(typeof form.dataAGiornoPag!= "undefined"){
	  if (!(form.dataAGiornoPag.value=='' && form.dataAMesePag.value=='' && form.dataAAnnoPag.value==''))
	  {
		  if (!controlla(form.dataAGiornoPag.value,form.dataAMesePag.value,form.dataAAnnoPag.value))
		  {
			  msg=msg+'Valorizzare correttamente la "Data di pagamento precedente al"\n';
			  corretti=false;
		  }
	  }
	  else corretti=false;
	  form.dataAPag.value=form.dataAGiornoPag.value+"/"+form.dataAMesePag.value+"/"+form.dataAAnnoPag.value;
	  if ( form.dataAPag.value == "//" )
		  form.dataAPag.value = "";
	  
	  if (!(form.dataDaGiornoPag.value=='' && form.dataDaMesePag.value=='' && form.dataDaAnnoPag.value=='')){
		  if (!controlla(form.dataDaGiornoPag.value,form.dataDaMesePag.value,form.dataDaAnnoPag.value)){
			  corretti=false;
			  msg=msg+'Valorizzare correttamente la "Data di pagamento sucessiva al"\n';
		  }
	  }
	  else corretti=false;
	  form.dataDaPag.value=form.dataDaGiornoPag.value+"/"+form.dataDaMesePag.value+"/"+form.dataDaAnnoPag.value;
	  if ( form.dataDaPag.value == "//" )
		  form.dataDaPag.value = "";
	
	  if (corretti &&  !confronta(form.dataDaGiornoPag.value,form.dataDaMesePag.value,form.dataDaAnnoPag.value,form.dataAGiornoPag.value,
			  form.dataAMesePag.value,form.dataAAnnoPag.value))
	     msg=msg+'"Data di pagamento sucessiva al" non deve essere maggiore di "Data di pagamento precedente al"\n';
  }
  if (typeof form.dataEmissioneRefertoAGiorno != "undefined")
  {
	  corretti=true;
	  if (!(form.dataEmissioneRefertoAGiorno.value=='' && form.dataEmissioneRefertoAMese.value=='' && form.dataEmissioneRefertoAAnno.value==''))
	  {
	    if (!controlla(form.dataEmissioneRefertoAGiorno.value,form.dataEmissioneRefertoAMese.value,form.dataEmissioneRefertoAAnno.value))
	    {
	      msg=msg+'Valorizzare correttamente la "Data di emissione del referto precedente al"\n';
	      corretti=false;
	    }
	  }
	  else
		  corretti=false;

	  form.dataEmissioneRefertoA.value=form.dataEmissioneRefertoAGiorno.value+"/"+form.dataEmissioneRefertoAMese.value+"/"+form.dataEmissioneRefertoAAnno.value;

	  if ( form.dataEmissioneRefertoA.value == "//" )
	    form.dataEmissioneRefertoA.value = "";

	  if (!(form.dataEmissioneRefertoDaGiorno.value=='' && form.dataEmissioneRefertoDaMese.value=='' && form.dataEmissioneRefertoDaAnno.value==''))
	  {
	    if (!controlla(form.dataEmissioneRefertoDaGiorno.value,form.dataEmissioneRefertoDaMese.value,form.dataEmissioneRefertoDaAnno.value))
	    {
	      corretti=false;
	      msg=msg+'Valorizzare correttamente la "Data di emissione del referto sucessiva al"\n';
	    }
	  }
	  else corretti=false;

	  form.dataEmissioneRefertoDa.value=form.dataEmissioneRefertoDaGiorno.value+"/"+form.dataEmissioneRefertoDaMese.value+"/"+form.dataEmissioneRefertoDaAnno.value;
	  if ( form.dataEmissioneRefertoDa.value == "//" )
	    form.dataEmissioneRefertoDa.value = "";

	  if (corretti &&  !confronta(form.dataEmissioneRefertoDaGiorno.value,form.dataEmissioneRefertoDaMese.value,form.dataEmissioneRefertoDaAnno.value,form.dataEmissioneRefertoAGiorno.value,form.dataEmissioneRefertoAMese.value,form.dataEmissioneRefertoAAnno.value))
	     msg=msg+'"Data di emissione del referto sucessiva al" non deve essere maggiore di "Data di emissione del referto precedente al"\n';
  }

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
    if (form.annoCampione.value != '')
    {
      if (form.tipoMateriale.value=='')
      {
        msg=msg+'Se si inserisce l\'anno, � necessario selezionare una voce in Tipo di materiale/matrice"\n';
      }
    }
    if (form.numeroCampioneDa.value != '' || form.numeroCampioneA.value != '')
    {
      if (form.annoCampione.value == '')
      {
        msg=msg+'Se si inserisce il numero del campione, � necessario inserire anche l\'anno ed il tipo di materiale\n';
      }
      else
      {
        if (form.tipoMateriale.value=='')
        {
          msg=msg+'Se si inserisce il numero del campione, � necessario inserire anche l\'anno ed il tipo di materiale\n';
        }
      }
    }
    if (form.tipoOrganizzazione.value!='-1' && form.organizzazione.value=='-1')
    {
      msg=msg+'Se si seleziona un tipo di organizzazione, � necessario selezionare un\' Organizzazione\n';
    }
  }
  if ( msg != '' )
  {
      alert( "Dati non completi\n\n"+msg );
      return false;
  }
  return true;
}
