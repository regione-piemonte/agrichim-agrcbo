function testLength(controllo,maxLength,doTrunc)
{
    var msg="Lunghezza massima raggiunta\n(" + maxLength + " caratteri)";

    if (doTrunc=="false" && controllo.value.length>maxLength)
    {
        alert(msg);
        return false;
    }
    if (doTrunc=="true" && controllo.value.length>maxLength)
    {
        msg=msg+"\n\nIl contenuto del campo � stato troncato.";
        controllo.value=controllo.value.substring(0,maxLength);
        alert(msg);
        return false;
    }
    else
        return true;
}