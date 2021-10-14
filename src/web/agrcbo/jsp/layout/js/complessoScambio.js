function salva(form,calcio,magnesio,potassio,csc,vBACl2)
{
    var msg='';

    form.pesoTerreno.value=form.pesoTerreno.value.trim();
    var pesoTerreno =form.pesoTerreno.value.replace(",",".");
    if (!isFloat(pesoTerreno,3) || (pesoTerreno<=0) || (pesoTerreno>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso terreno"\n';

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del Ca
     * o Mg o K
     * */
    if (vBACl2)
    {
      form.vbacl2PerEstrazione.value=form.vbacl2PerEstrazione.value.trim();
      var vbacl2PerEstrazione =form.vbacl2PerEstrazione.value.replace(",",".");
      if (!isFloat(vbacl2PerEstrazione,3) || (vbacl2PerEstrazione<=0) || (vbacl2PerEstrazione>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "VBaCL2 per estrazione"\n';
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del CSC
     * */
    if (csc)
    {
      form.pesoSeccoProvetta.value=form.pesoSeccoProvetta.value.trim();
      form.pesoSeccoAcquaProvetta.value=form.pesoSeccoAcquaProvetta.value.trim();
      form.letturaMagnesioEdta.value=form.letturaMagnesioEdta.value.trim();
      form.letturaBiancoEdta.value=form.letturaBiancoEdta.value.trim();
      var pesoSeccoProvetta =form.pesoSeccoProvetta.value.replace(",",".");
      var pesoSeccoAcquaProvetta =form.pesoSeccoAcquaProvetta.value.replace(",",".");
      var letturaMagnesioEdta =form.letturaMagnesioEdta.value.replace(",",".");
      var letturaBiancoEdta =form.letturaBiancoEdta.value.replace(",",".");
      if (!isFloat(pesoSeccoProvetta,3) || (pesoSeccoProvetta<=0) || (pesoSeccoProvetta>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso secco + provetta (in grammi)"\n';
      if (!isFloat(pesoSeccoAcquaProvetta,3) || (pesoSeccoAcquaProvetta<=0) || (pesoSeccoAcquaProvetta>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso secco + acqua + provetta (in grammi)"\n';
      else
      {
        if (parseFloat(pesoSeccoAcquaProvetta)<parseFloat(pesoSeccoProvetta))
           msg+='"Peso secco + acqua + provetta (in grammi)" deve eserre maggiore o uguale di "Peso secco + provetta (in grammi)"\n';
      }

      if (!isFloat(letturaMagnesioEdta,3) || (letturaMagnesioEdta<=0) || (letturaMagnesioEdta>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lett Magnesio ppm/Titol EDTA campione (in ml)"\n';

      if (!isFloat(letturaBiancoEdta,3) || (letturaBiancoEdta<=0) || (letturaBiancoEdta>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lett Bianco ppm/titol EDTA bianco"\n';
      else
      {
        if (parseFloat(letturaMagnesioEdta) >156.0)
        {
          if (!(parseFloat(letturaBiancoEdta)>parseFloat(letturaMagnesioEdta)
                           &&
             (parseFloat(letturaBiancoEdta)>156.0)))
             {
               msg+='"Lett Bianco ppm/titol EDTA bianco" deve eserre maggiore di "Lett Magnesio ppm/Titol EDTA campione (in ml)"\n';
          }
        }
        else
        {
          if (!(parseFloat(letturaBiancoEdta)>parseFloat(letturaMagnesioEdta)
                           &&
             (parseFloat(letturaBiancoEdta)<156.0)))
             {
               msg+='"Lett Bianco ppm/titol EDTA bianco" deve eserre maggiore di "Lett Magnesio ppm/Titol EDTA campione (in ml)" ma minore di 156.0\n';
          }
        }
      }
    }

    form.letturaSodio.value=form.letturaSodio.value.trim();
    form.diluizioneSodio.value=form.diluizioneSodio.value.trim();


    var letturaSodio =form.letturaSodio.value.replace(",",".");
    var diluizioneSodio =form.diluizioneSodio.value.replace(",",".");

    if ((letturaSodio!='') && (!isFloat(letturaSodio,3) || (letturaSodio<0) || (letturaSodio>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Sodio (p.p.m.)" oppure lasciare il campo vuoto\n';
    if ((diluizioneSodio!='') && (!isFloat(diluizioneSodio,3) || (diluizioneSodio<0) || (diluizioneSodio>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Sodio" oppure lasciare il campo vuoto\n';
    else
    {
      if ((letturaSodio!='') && parseFloat(letturaSodio)==0.0)
      {
        if ((diluizioneSodio=='') || parseFloat(diluizioneSodio)!=0.0)
          msg+='"Diluizione Sodio" deve essere uguale a 0.0 perchè  "Lettura Sodio (p.p.m.)" è uguale a 0.0';
      }
      if ((letturaSodio!='') && parseFloat(letturaSodio)>0.0)
      {
        if ((diluizioneSodio=='') || parseFloat(diluizioneSodio)<=0.0)
          msg+='"Diluizione Sodio" deve essere maggiore di 0.0 perchè  "Lettura Sodio (p.p.m.)" è maggiore di  0.0';
      }
    }


    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del Ca
     * */
    if (calcio)
    {
      form.letturaCalcio.value=form.letturaCalcio.value.trim();
      form.diluizioneCalcio.value=form.diluizioneCalcio.value.trim();
      var letturaCalcio =form.letturaCalcio.value.replace(",",".");
      var diluizioneCalcio =form.diluizioneCalcio.value.replace(",",".");
      if (diluizioneCalcio != '' && (! isFloat(letturaCalcio,3) || (letturaCalcio<0) || (letturaCalcio>999999.999)))
      {
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Calcio (p.p.m.)"\n';
      }
      if (letturaCalcio != '' && (! isFloat(diluizioneCalcio,3) || (diluizioneCalcio<=0) || (diluizioneCalcio>999999.999)))
	  {
    	msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Calcio"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del Mg
     * */
    if (magnesio)
    {
      form.letturaMagnesio.value=form.letturaMagnesio.value.trim();
      form.diluizioneMagnesio.value=form.diluizioneMagnesio.value.trim();
      var letturaMagnesio =form.letturaMagnesio.value.replace(",",".");
      var diluizioneMagnesio =form.diluizioneMagnesio.value.replace(",",".");
      if (diluizioneMagnesio != '' && (! isFloat(letturaMagnesio,3) || (letturaMagnesio<0) || (letturaMagnesio>999999.999)))
	  {
    	  msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Magnesio (p.p.m.)"\n';
	  }
      if (letturaMagnesio != '' && (! isFloat(diluizioneMagnesio,3) || (diluizioneMagnesio<=0) || (diluizioneMagnesio>999999.999)))
	  {
	  	msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Magnesio"\n';
	  }
    }

    /**
     * Faccio questo controllo solo se è stata richiesta l'analisi del k
     * */
    if (potassio)
    {
      form.letturaPotassio.value=form.letturaPotassio.value.trim();
      form.diluizionePotassio.value=form.diluizionePotassio.value.trim();
      var letturaPotassio =form.letturaPotassio.value.replace(",",".");
      var diluizionePotassio =form.diluizionePotassio.value.replace(",",".");   
      if (diluizionePotassio != '' && (! isFloat(letturaPotassio,3) || (letturaPotassio<0) || (letturaPotassio>999999.999)))
	  {
    	  msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Potassio (p.p.m.)"\n';
	  }
      if (letturaPotassio != '' && (! isFloat(diluizionePotassio,3) || (diluizionePotassio<=0) || (diluizionePotassio>999999.999)))
      {
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Potassio"\n';
	  }        
    }


    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else return true;
}

