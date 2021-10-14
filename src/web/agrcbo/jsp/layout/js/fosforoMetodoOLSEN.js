function salva(form)
{
    var msg='';
    form.letturaFosforo.value=form.letturaFosforo.value.trim();
    var letturaFosforo =form.letturaFosforo.value.replace(",",".");

    if (!isFloat(letturaFosforo,3) || (letturaFosforo<=0) || (letturaFosforo>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Fosforo (p.p.m.)"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    return true;
}

