function salva(form)
{
    var msg='';
    form.letturaSostanzaOrganica.value=form.letturaSostanzaOrganica.value.trim();
    form.pesoCampione.value=form.pesoCampione.value.trim();
    form.carbonioOrganicoMetodoAna.value=form.carbonioOrganicoMetodoAna.value.trim();
    var letturaSostanzaOrganica =form.letturaSostanzaOrganica.value.replace(",",".");
    var pesoCampione=form.pesoCampione.value.replace(",",".");
    var carbonioOrganicoMetodoAna=form.carbonioOrganicoMetodoAna.value.replace(",",".");

    /**
     * Almeno uno dei due gruppi di campi deve essere valorizzato ed
     * essere maggiore di zero
     * */
    if (letturaSostanzaOrganica!='' && (!isFloat(letturaSostanzaOrganica,3) ||
        (letturaSostanzaOrganica<=0) || (letturaSostanzaOrganica>999999.999)) )
      msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura sostanza organica %"\n';
    if (pesoCampione!='' && (!isFloat(pesoCampione,3) ||
        (pesoCampione<=0) || (pesoCampione>999999.999)) )
      msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso campione (grammi)"\n';
    if (carbonioOrganicoMetodoAna!='' && (!isFloat(carbonioOrganicoMetodoAna,3) ||
        (carbonioOrganicoMetodoAna<=0) || (carbonioOrganicoMetodoAna>999999.999)) )
      msg=msg+'Inserire un numero nel formato ######,### nel campo "Carbonio organico %"\n';

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
    {
      if (!((letturaSostanzaOrganica!='' ^ carbonioOrganicoMetodoAna!='') &&
           (pesoCampione!='' ^ carbonioOrganicoMetodoAna!='')))
        msg=msg+'Valorizzare correttamente i campi "Lettura sostanza organica %" e "Peso campione (grammi)" oppure il campo "Carbonio organico %"\n';
      if ( msg != '' )
      {
          alert( "Dati non corretti\n\n"+msg );
          return false;
      }
      else return true;
    }



    return true;
}

