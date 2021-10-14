function salva(form,analisiCalcio,analisiMagnesio,analisiPotassio,analisiFerro,
               analisiAzoto,analisiFosforo,analisiManganese,analisiZinco,
               analisiRame,analisiBoro)
{
  var msg='';

  form.pesoCampione.value=form.pesoCampione.value.trim();
  form.volumeDiluizione.value=form.volumeDiluizione.value.trim();
  var pesoCampione=form.pesoCampione.value.replace(",",".");
  var volumeDiluizione=form.volumeDiluizione.value.replace(",",".");
  if (!isFloat(pesoCampione,3) || (pesoCampione<=0) || (pesoCampione>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Peso campione"\n';
  if (!isFloat(volumeDiluizione,3) || (volumeDiluizione<=0) || (volumeDiluizione>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Volume diluizione"\n';

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del calcio
   * */
  if (analisiCalcio)
  {
    form.letturaCaPpm.value=form.letturaCaPpm.value.trim();
    var letturaCaPpm=form.letturaCaPpm.value.replace(",",".");
    if ((letturaCaPpm!='') && (!isFloat(letturaCaPpm,3) || (letturaCaPpm<=0) || (letturaCaPpm>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Calcio (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del magnesio
   * */
  if (analisiMagnesio)
  {
    form.letturaMgPpm.value=form.letturaMgPpm.value.trim();
    var letturaMgPpm=form.letturaMgPpm.value.replace(",",".");
    if ((letturaMgPpm!='') && (!isFloat(letturaMgPpm,3) || (letturaMgPpm<=0) || (letturaMgPpm>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Magnesio (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del potassio
   * */
  if (analisiPotassio)
  {
    form.letturaKPpm.value=form.letturaKPpm.value.trim();
    var letturaKPpm=form.letturaKPpm.value.replace(",",".");
    if ((letturaKPpm!='') && (!isFloat(letturaKPpm,3) || (letturaKPpm<=0) || (letturaKPpm>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Potassio (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del azoto
   * */
  if (analisiAzoto)
  {
    form.azoto.value=form.azoto.value.trim();
    var azoto=form.azoto.value.replace(",",".");
    if ((azoto!='') && (!isFloat(azoto,3) || (azoto<=0) || (azoto>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Azoto %" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del fosforo
   * */
  if (analisiFosforo)
  {
    form.fosforoPpm.value=form.fosforoPpm.value.trim();
    var fosforoPpm=form.fosforoPpm.value.replace(",",".");
    if ((fosforoPpm!='') && (!isFloat(fosforoPpm,3) || (fosforoPpm<=0) || (fosforoPpm>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Fosforo (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del fosforo
   * */
  if (analisiFerro)
  {
    form.diluizioneFe.value=form.diluizioneFe.value.trim();
    form.letturaFePpm.value=form.letturaFePpm.value.trim();
    var diluizioneFe=form.diluizioneFe.value.replace(",",".");
    var letturaFePpm=form.letturaFePpm.value.replace(",",".");
    if ((diluizioneFe!='') && (!isFloat(diluizioneFe,3) || (diluizioneFe<=0) || (diluizioneFe>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Ferro" oppure lasciare il campo vuoto\n';
    if ((letturaFePpm!='') && (!isFloat(letturaFePpm,3) || (letturaFePpm<=0) || (letturaFePpm>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Ferro (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del manganese
   * */
  if (analisiManganese)
  {
    form.diluizioneMn.value=form.diluizioneMn.value.trim();
    form.letturaMnPpm.value=form.letturaMnPpm.value.trim();
    var diluizioneMn=form.diluizioneMn.value.replace(",",".");
    var letturaMnPpm=form.letturaMnPpm.value.replace(",",".");
    if ((diluizioneMn!='') && (!isFloat(diluizioneMn,3) || (diluizioneMn<=0) || (diluizioneMn>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Manganese" oppure lasciare il campo vuoto\n';
    if ((letturaMnPpm!='') && (!isFloat(letturaMnPpm,3) || (letturaMnPpm<=0) || (letturaMnPpm>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Manganese (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi dello zinco
   * */
  if (analisiZinco)
  {
    form.diluizioneZn.value=form.diluizioneZn.value.trim();
    form.letturaZnPpm.value=form.letturaZnPpm.value.trim();
    var diluizioneZn=form.diluizioneZn.value.replace(",",".");
    var letturaZnPpm=form.letturaZnPpm.value.replace(",",".");
    if ((diluizioneZn!='') && (!isFloat(diluizioneZn,3) || (diluizioneZn<=0) || (diluizioneZn>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Zinco" oppure lasciare il campo vuoto\n';
    if ((letturaZnPpm!='') && (!isFloat(letturaZnPpm,3) || (letturaZnPpm<=0) || (letturaZnPpm>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Zinco (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del rame
   * */
  if (analisiRame)
  {
    form.diluizioneCu.value=form.diluizioneCu.value.trim();
    form.letturaCuPpm.value=form.letturaCuPpm.value.trim();
    var diluizioneCu=form.diluizioneCu.value.replace(",",".");
    var letturaCuPpm=form.letturaCuPpm.value.replace(",",".");
    if ((diluizioneCu!='') && (!isFloat(diluizioneCu,3) || (diluizioneCu<=0) || (diluizioneCu>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Rame" oppure lasciare il campo vuoto\n';
    if ((letturaCuPpm!='') && (!isFloat(letturaCuPpm,3) || (letturaCuPpm<=0) || (letturaCuPpm>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Rame (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  /**
   * Faccio questo controllo solo se è stata richiesta l'analisi del boro
   * */
  if (analisiBoro)
  {
    form.diluizioneB.value=form.diluizioneB.value.trim();
    form.letturaBPpm.value=form.letturaBPpm.value.trim();
    var diluizioneB=form.diluizioneB.value.replace(",",".");
    var letturaBPpm=form.letturaBPpm.value.replace(",",".");
    if ((diluizioneB!='') && (!isFloat(diluizioneB,3) || (diluizioneB<=0) || (diluizioneB>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Diluizione Boro" oppure lasciare il campo vuoto\n';
    if ((letturaBPpm!='') && (!isFloat(letturaBPpm,3) || (letturaBPpm<=0) || (letturaBPpm>999999.999)))
          msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura Boro (p.p.m.)" oppure lasciare il campo vuoto\n';
  }

  if ( msg != '' )
  {
      alert( "Dati incompleti\n\n"+msg );
      return false;
  }
  else return true;

}

