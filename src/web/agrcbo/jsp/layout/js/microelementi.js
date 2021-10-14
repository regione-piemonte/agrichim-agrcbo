function salva(form,ferro,manganese,zinco,rame,boro)
{
    var msg='';

    /**
     * Faccio questo controllo solo se è stata richeista l'analisi del ferro
     * */
    if (ferro)
    {
      form.letturaFerro.value=form.letturaFerro.value.trim();
      form.diluizioneFerro.value=form.diluizioneFerro.value.trim();
      var letturaFerro =form.letturaFerro.value.replace(",",".");
      var diluizioneFerro =form.diluizioneFerro.value.replace(",",".");
      if (!isFloat(letturaFerro,3) || (letturaFerro<0) || (letturaFerro>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Ferro (p.p.m.)"\n';
      if (!isFloat(diluizioneFerro,3) || (diluizioneFerro<0) || (diluizioneFerro>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Ferro"\n';
    }

    /**
     * Faccio questo controllo solo se è stata richeista l'analisi del manganese
     * */
    if (manganese)
    {
      form.letturaManganese.value=form.letturaManganese.value.trim();
      form.diluizioneManganese.value=form.diluizioneManganese.value.trim();
      var letturaManganese =form.letturaManganese.value.replace(",",".");
      var diluizioneManganese =form.diluizioneManganese.value.replace(",",".");
      if (!isFloat(letturaManganese,3) || (letturaManganese<0) || (letturaManganese>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Manganese (p.p.m.)"\n';
      if (!isFloat(diluizioneManganese,3) || (diluizioneManganese<0) || (diluizioneManganese>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Manganese"\n';
    }

    /**
     * Faccio questo controllo solo se è stata richeista l'analisi del zinco
     * */
    if (zinco)
    {
      form.letturaZinco.value=form.letturaZinco.value.trim();
      form.diluizioneZinco.value=form.diluizioneZinco.value.trim();
      var letturaZinco =form.letturaZinco.value.replace(",",".");
      var diluizioneZinco =form.diluizioneZinco.value.replace(",",".");
      if (!isFloat(letturaZinco,3) || (letturaZinco<0) || (letturaZinco>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Zinco (p.p.m.)"\n';
      if (!isFloat(diluizioneZinco,3) || (diluizioneZinco<0) || (diluizioneZinco>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Zinco"\n';
    }

    /**
     * Faccio questo controllo solo se è stata richeista l'analisi del rame
     * */
    if (rame)
    {
      form.letturaRame.value=form.letturaRame.value.trim();
      form.diluizioneRame.value=form.diluizioneRame.value.trim();
      var letturaRame =form.letturaRame.value.replace(",",".");
      var diluizioneRame =form.diluizioneRame.value.replace(",",".");
      if (!isFloat(letturaRame,3) || (letturaRame<0) || (letturaRame>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Rame (p.p.m.)"\n';
      if (!isFloat(diluizioneRame,3) || (diluizioneRame<0) || (diluizioneRame>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Rame"\n';
    }

    /**
     * Faccio questo controllo solo se è stata richeista l'analisi del boro
     * */
    if (boro)
    {
      form.letturaBoro.value=form.letturaBoro.value.trim();
      form.diluizioneBoro.value=form.diluizioneBoro.value.trim();
      var letturaBoro =form.letturaBoro.value.replace(",",".");
      var diluizioneBoro =form.diluizioneBoro.value.replace(",",".");
      if (!isFloat(letturaBoro,3) || (letturaBoro<0) || (letturaBoro>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Boro (p.p.m.)"\n';
      if (!isFloat(diluizioneBoro,3) || (diluizioneBoro<0) || (diluizioneBoro>999999.999))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Boro"\n';
    }

    if ( msg != '' )
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    else return true;
}

