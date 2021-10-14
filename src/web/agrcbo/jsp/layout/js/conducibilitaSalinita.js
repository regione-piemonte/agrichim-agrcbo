function salva(form)
{
    var msg='';

    if (!isNumber(form.conducibilita.value))
        msg=msg+'Inserire un numero nel formato ##### nel campo "Conducibilità (in micro S/cm)"\n';
    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    return true;
}

