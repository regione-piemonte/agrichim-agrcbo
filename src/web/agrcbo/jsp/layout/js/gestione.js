/**
 * Funzione che apre una finestra di popup
 * Nome predefinito della finestra di popup: popUpAgrc
 * Dimensioni predefinite della finestra di popup: 600x400
 */
var hWin;
function pop(str,x,y,nome){
  if (x==null){
    x=600;
    y=400;
  }
  if (nome==null)
    nome="popUpAgrcBO";

  var left=(window.screen.availWidth-x)/2;
  var top=(window.screen.availHeight-y)/2;
  hWin = window.open(str,nome,'scrollbars=yes,resizable=yes,width='+x+',height='+y+',top='+top+',left='+left+',status=yes,location=no,toolbar=no');
  hWin.focus();
}


/**
 * La funzione seguente � utilizzata per la modifica e la cancellazione:
 * se il parametro modo � valorizzato implica cancellazione,
 * in caso contrario modifica
 */
function popGes(str,form,modo,x,y,nome) {
    var valore = null;
    if (form.radiobutton == null) return;
    if (form.radiobutton[0])
    {   // entra qui se il radiobutton � un array
        // (un solo radiobutton NON � un array)
        for (var i=0; i<form.radiobutton.length; i++) {
            if (form.radiobutton[i].checked) {
                valore=form.radiobutton[i].value;
                break;
            }
        }
    }
    else if (form.radiobutton.checked) valore=form.radiobutton.value;

    if (modo!= null) {
        form.cancella.value = valore;
        form.action=str;
        form.submit();
    } else {
        str+="?modifica="+valore;
        pop(str,x,y,nome);
    }
}

/**
 * La funzione seguente � utilizzata per la modifica e la cancellazione dei preventivi:
 * se il parametro modo � valorizzato implica cancellazione,
 * in caso contrario modifica
 */
function popGesPreventivi(str,form,modo,x,y,nome) {
    var valore = null;
    if (form.radiobutton == null) return;
    if (form.radiobutton[0])
    {   // entra qui se il radiobutton � un array
        // (un solo radiobutton NON � un array)
        for (var i=0; i<form.radiobutton.length; i++)  {
            if (form.radiobutton[i].checked) {
                valore=form.radiobutton[i].value;
                break;
            }
        }
    }
    else if (form.radiobutton.checked) 
    	valore=form.radiobutton.value;

    if (modo!= null) {
    	checkUtilizzo = eval('form.checkUtilizzo_'+valore+'.value');
    	if(checkUtilizzo == "true")
    		alert("Il preventivo e' associato a delle richieste. Impossibile eliminare");
    	else{
    		form.cancella.value = valore;
            form.action=str;
            form.submit();
    	}
    } else {
        str+="?modifica="+valore;
        pop(str,x,y,nome);
    }
}

/**
 * La funzione seguente � utilizzata per il dettaglio
 */
function popGesDet(str,form,x,y,param) {
    var valore = null;
    if (form.radiobutton == null) return;
    if (form.radiobutton[0])
    { // entra qui se il radiobutton � un array (un solo radiobutton NON � un array)
        for (var i=0; i<form.radiobutton.length; i++) {
            if (form.radiobutton[i].checked) {
                valore=form.radiobutton[i].value;
                break;
            }
        }
    }
    else if (form.radiobutton.checked) valore=form.radiobutton.value;
    str+="?dettaglio="+valore;
    if (param != null) str+="&sedeScelta="+param;
    pop(str,x,y);
}

/**
 * La funzione seguente � utilizzata per le fatture
 */
function popGesFatture(str,form,x,y) {
    str+="?fatturaSelected="+fatturaSelected+"&annoSelected="+annoSelected+"&dataFattura="+dataFattura;
    pop(str,x,y);
}

/**
 * Le variabili e le funzioni seguenti sono utilizzate
 * per recuperare il comune selezionato nel popup (codice istat e descrizione)
 */
var ctrlCodiceIstat, ctrlDescrizione, ctrlSiglaProvincia;
function popComune(istat,descrizione){
  ctrlCodiceIstat=istat;
  ctrlDescrizione=descrizione;
  var url='../view/comunePOP.jsp';
  if (istat.value != '') url=url+"?istatSearch="+istat.value;
  pop(url,400,200,'comunePOPBO');
}
function popComuneSearch(istat,descrizione, siglaProvincia){
  ctrlCodiceIstat=istat;
  ctrlDescrizione=descrizione;
  ctrlSiglaProvincia = siglaProvincia;
  var url='../view/comunePOPSearch.jsp';
  if (istat.value != '') url=url+"?istatSearch="+istat.value;
  pop(url,400,200,'comunePOPBO');
}
function leggiComune(controllo, provincia){
  var indice=controllo.selectedIndex;
  ctrlCodiceIstat.value=controllo[indice].value;
  ctrlDescrizione.value=controllo[indice].text;
  
  var indiceProvincia = provincia.selectedIndex;
  ctrlSiglaProvincia.value = provincia[indiceProvincia].text;
}

function leggiComuneSearch(){
  ctrlCodiceIstat.value='';
  ctrlDescrizione.value='';
  ctrlSiglaProvincia = '';
}

/**
 * Abilita un campo di testo
 * */
function abilitaText(text){
  text.disabled=false;
}

/**
 * Disabilita un campo di testo
 * */
function disabilitaText(text){
  text.value="";
  text.disabled=true;
}

/**
 * Abilita una combo
 * */
function abilitaCombo(select){
  select.disabled=false;
}

/**
 * Disabilita una combo
 * */
function disabilitaCombo(select){
  select.options.selectedIndex=0;
  select.disabled=true;
}

/**
 * Usata nell'onload di una pagina: a seconda del valore di un radio
 * abilita o disabilita una combo
 * */
function controllaRadioCombo(radio,select){
  if (radio[0].checked) disabilitaCombo(select);
  else abilitaCombo(select);
}

/**
 * Usata nell'onload di una pagina: a seconda del valore di un radio
 * abilita o disabilita un campo di testo
 * */
function controllaRadioText(radio,text,input){
  if (input==null) input=0;
  if (radio[input].checked) abilitaText(text);
  else disabilitaText(text);
}

function submitFormFunzione(form, funzione){
  form.funzione.value = funzione;
  form.submit();
}

function messaggioErrore(errore){
	if (errore!=null && errore != ''){
		var msg = errore.replace("<","").replace(">","").replace("'","");
		alert(msg);
	}
}
function controllaEmail(mail){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(mail);
}