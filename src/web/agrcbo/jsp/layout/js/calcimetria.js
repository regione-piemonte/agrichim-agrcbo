function salva(form)
{
    var msg='';
    form.letturaCalcimetro.value=form.letturaCalcimetro.value.trim();
    form.pressioneAtmosferica.value=form.pressioneAtmosferica.value.trim();
    form.temperatura.value=form.temperatura.value.trim();
    form.calcareAttivo.value=form.calcareAttivo.value.trim();
    form.letturaFerroOssalato.value=form.letturaFerroOssalato.value.trim();
    form.diluizioneDeterminaFerro.value=form.diluizioneDeterminaFerro.value.trim();

    var letturaCalcimetro =form.letturaCalcimetro.value.replace(",",".");
    var pressioneAtmosferica =form.pressioneAtmosferica.value.replace(",",".");
    var temperatura =form.temperatura.value.replace(",",".");
    var calcareAttivo =form.calcareAttivo.value.replace(",",".");
    var letturaFerroOssalato =form.letturaFerroOssalato.value.replace(",",".");
    var diluizioneDeterminaFerro =form.diluizioneDeterminaFerro.value.replace(",",".");

    if ((letturaCalcimetro!='') && (!isFloat(letturaCalcimetro,2) || (letturaCalcimetro<-9999999.99) || (letturaCalcimetro>9999999.99)))
        msg=msg+'Inserire un numero nel formato #######,## nel campo "Lettura calcimetro" oppure lasciare il campo vuoto\n';

    if ((pressioneAtmosferica!='') && (!isFloat(pressioneAtmosferica,3) || (pressioneAtmosferica<=0) || (pressioneAtmosferica>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Pressione atmosferica" oppure lasciare il campo vuoto\n';

    if ((temperatura!='') && (!isFloat(temperatura,3) || (temperatura<10) || (temperatura>30)))
        msg=msg+'Inserire un numero maggiore o uguale a 10 e minore o uguale a 30 in "Temperatura in °C" oppure lasciare il campo vuoto\n';

    if ((calcareAttivo!='') && (!isFloat(calcareAttivo,1) || (calcareAttivo<0) || (calcareAttivo>999999.9)))
        msg=msg+'Inserire un numero nel formato ######,# nel campo "Calcare attivo" oppure lasciare il campo vuoto\n';

    if ((letturaFerroOssalato!='') && (!isFloat(letturaFerroOssalato,3) || (letturaFerroOssalato<0) || (letturaFerroOssalato>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura ferro in ossalato (p.p.m.)" oppure lasciare il campo vuoto\n';

    if ((diluizioneDeterminaFerro!='') && (!isFloat(diluizioneDeterminaFerro,3) || (diluizioneDeterminaFerro<0) || (diluizioneDeterminaFerro>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione per determina Ferro in ossalato" oppure lasciare il campo vuoto\n';


    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else
    {
      if (letturaCalcimetro=='' && pressioneAtmosferica=='' && temperatura==''
          && calcareAttivo=='' && letturaFerroOssalato=='' && diluizioneDeterminaFerro=='')
      {
        alert("Attenzione:\nnon è stato inserito nessun valore.");
        return false;
      }
      else return true;
    }
}

