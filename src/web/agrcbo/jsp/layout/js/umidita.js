function salva(form)
{
    var msg='';
    form.tara.value=form.tara.value.trim();
    form.pesoNettoUmido.value=form.pesoNettoUmido.value.trim();
    form.pesoLordoSecco.value=form.pesoLordoSecco.value.trim();
    var tara =form.tara.value.replace(",",".");
    var pesoNettoUmido=form.pesoNettoUmido.value.replace(",",".");
    var pesoLordoSecco=form.pesoLordoSecco.value.replace(",",".");
    if (!isFloat(tara,3) || (tara<0) || (tara>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Tara (in grammi)"\n';
    if (!isFloat(pesoNettoUmido,3) || (pesoNettoUmido<0) || (pesoNettoUmido>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso netto umido (in grammi)"\n';
    if (!isFloat(pesoLordoSecco,3) || (pesoLordoSecco<0) || (pesoLordoSecco>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso lordo secco (in grammi)"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
    {
      if (parseFloat(pesoLordoSecco) <= parseFloat(tara) )
      {
        msg=msg+'Il peso lordo secco deve essere maggiore della tara\n';
      }
      if (parseFloat(pesoLordoSecco) > parseFloat(tara)+parseFloat(pesoNettoUmido))
      {
        msg=msg+'Il peso lordo secco non può essere maggiore di peso netto umido più tara\n';
      }
      if ( msg != '' )
      {
          alert( "Dati non corretti\n\n"+msg );
          return false;
      }
      else return true;
    }

}

