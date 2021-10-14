function salva(form){
    var msg='';
    form.numero_preventivo.value=form.numero_preventivo.value.trim();
    form.codice_fiscale.value=form.codice_fiscale.value.trim();
    form.codice_fiscale.value = form.codice_fiscale.value.toUpperCase();
    form.importo.value=form.importo.value.trim();

    if (form.numero_preventivo == null || form.numero_preventivo.value == '')
      msg=msg+'Scrivere un numero preventivo\n';

    lung= form.codice_fiscale.value.length;
    
    if (!(lung==16)) {
      msg=msg+'Inserire un Codice Fiscale valido\n';
    } else {
      if (lung==16) {
        msgTmp = controllaCodiceFiscale(form.codice_fiscale.value);
        if (msgTmp!='')
            msg=msg+'"Codice Fiscale" - '+msgTmp+'\n';
      }
    }
    var pattern=/^[1-9]\d{0,2}(\.?\d{3})*(,\d+)?$/;
    if (form.importo == null || form.importo.value == '')
      msg=msg+'Valorizzare la voce "Importo"\n';
    else if(!pattern.test(form.importo.value))
    	msg+='Numero non corretto';
    
    if(form.note_aggiuntive != null && form.note_aggiuntive.length > 4000)
    	msg=msg+'Campo note troppo lungo';

    if ( msg != '' ) {
        alert( "Dati errati o incompleti\n\n"+msg );
        return false;
    }else
        return true;
}

function submitPreventivo(form){
	form.importo.value = form.importo.value.replace(",",".");
	form.submit();
}