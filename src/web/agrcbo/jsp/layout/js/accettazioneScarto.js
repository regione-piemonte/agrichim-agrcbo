var singoloClick=true;
function checkDataIncasso(data_i){
	 var espressione = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
     // Effettua il test sulla stringa e
     //    ritorna il risultato con un alert
     if (!espressione.test(data_i))  {
       return "Data Incasso non conforme al formato dd/MM/yyyy\n";
     } else {
       // Recupera dalla stringa i campi anno, mese e giorno
       anno = parseInt(data_i.substr(6),10);
       mese = parseInt(data_i.substr(3, 2),10);
       giorno = parseInt(data_i.substr(0, 2),10);

       // Crea la nuova data
       var data=new Date(anno, mese-1, giorno);

       // Controlla che i parametri della data siano
       //  gli stessi che abbiamo impostato
       if (data.getFullYear()==anno &&
             data.getMonth()+1==mese &&
             data.getDate()==giorno){
          return '';
       } else {
          return "Data Incasso non conforme al formato dd/MM/yyyy\n";
       }
     }
}
function salva(form){
    var msg='';
    form.note.value=form.note.value.trim();
    var pag = form.pagamento.value.trim();
    var d_i = form.data_incasso.value.trim();
    /*if(pag=="S" && (d_i!=null && d_i!="")){
        msg = checkDataIncasso(d_i);
    }else if(pag=="S" && (d_i==null || d_i=="")){
        msg="Inserire Data Incasso\n";
    }else */if(d_i!=null && d_i!=""){
    	msg = checkDataIncasso(d_i);
    }
    if (!form.scarto[0].checked){
      if ( form.note.value=='' )
        msg=msg+'Valorizzare il campo note\n';
    }
    if ( msg != '' ){
        alert("Dati incompleti\n\n"+msg );
        return;
    }else{
      singoloClick=false;
      form.submit();
    }
}

