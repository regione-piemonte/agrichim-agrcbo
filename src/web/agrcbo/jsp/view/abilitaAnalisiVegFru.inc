<%
  int esitoAnalisi[]=new int[2];

  boolean veg=false;

  /*a seconda del tipo di materiale devo fare cose diverse*/
  String codMateriale = aut.getCodMateriale();
  if (Analisi.MAT_ERBACEE.equals(codMateriale) || Analisi.MAT_FOGLIE.equals(codMateriale))
    veg=true;


  it.csi.agrc.Analisi analisiRichieste=new it.csi.agrc.Analisi();
  analisiRichieste.setDataSource(dataSource);
  analisiRichieste.setAut(aut);
  analisiRichieste.setIdRichiesta(aut.getIdRichiestaCorrente());
  analisiRichieste.select();

  /***
   * Leggo le analisi richieste e le memorizzo nel vettore richiestaAnalisi
   */
  java.util.Enumeration enumAnalisiRich=analisiRichieste.getCodiciAnalisi().elements();
  String codiceRichAnal;
  while(enumAnalisiRich.hasMoreElements())
  {
    codiceRichAnal=(String)enumAnalisiRich.nextElement();

    /**
     * Per prima cosa vado a controllare se è stato richiesto qualche paccheto
     * di analisi
     * */
    if (it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codiceRichAnal))
    {
      bFerro=true;
      bManganese=true;
      bZinco=true;
      bRame=true;
      bBoro=true;
      richiestaAnalisi[Analisi.VEGFRU_MACRO_MICRO] = true;
    }
    if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codiceRichAnal))
    {
      bCalcio=true;
      bMagnesio=true;
      bPotassio=true;
      bFosforo=true;
      bAzoto=true;
      richiestaAnalisi[Analisi.VEGFRU_MACRO_MICRO] = true;
    }

    /**
     * umidità del campione
     * */
    if (it.csi.agrc.Analisi.ANA_UMIDITA.equals(codiceRichAnal))
        richiestaAnalisi[Analisi.VEGFRU_UMIDITA] = true;

    /***
     * microelementi
     */
    if(it.csi.agrc.Analisi.ANA_CALCIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_MAGNESIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_POTASSIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_FERRO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_MANGANESE.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_ZINCO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_RAME.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_BORO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_FOSFORO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_AZOTO.equals(codiceRichAnal)
       )
    {
      richiestaAnalisi[Analisi.VEGFRU_MACRO_MICRO] = true;
      if (it.csi.agrc.Analisi.ANA_CALCIO.equals(codiceRichAnal)) bCalcio=true;
      if (it.csi.agrc.Analisi.ANA_MAGNESIO.equals(codiceRichAnal)) bMagnesio=true;
      if (it.csi.agrc.Analisi.ANA_POTASSIO.equals(codiceRichAnal)) bPotassio=true;
      if (it.csi.agrc.Analisi.ANA_FERRO.equals(codiceRichAnal)) bFerro=true;
      if (it.csi.agrc.Analisi.ANA_MANGANESE.equals(codiceRichAnal)) bManganese=true;
      if (it.csi.agrc.Analisi.ANA_ZINCO.equals(codiceRichAnal)) bZinco=true;
      if (it.csi.agrc.Analisi.ANA_RAME.equals(codiceRichAnal)) bRame=true;
      if (it.csi.agrc.Analisi.ANA_BORO.equals(codiceRichAnal)) bBoro=true;
      if (it.csi.agrc.Analisi.ANA_FOSFORO.equals(codiceRichAnal)) bFosforo=true;
      if (it.csi.agrc.Analisi.ANA_AZOTO.equals(codiceRichAnal)) bAzoto=true;
    }
  }

  /**
   * Per prima cosa disabilito tutte le richieste
   * */
  for (int i=0;i<esitoAnalisi.length;i++)
    esitoAnalisi[i]=Analisi.ANALISI_COLORE_DISABILITATO;

  /**
   * umidità del campione
   * */
  if (richiestaAnalisi[Analisi.VEGFRU_UMIDITA])
  {
    UmiditaCampione umiditaCamp= new UmiditaCampione();
    umiditaCamp.setDataSource(dataSource);
    umiditaCamp.setAut(aut);
    umiditaCamp.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (umiditaCamp.select())
      esitoAnalisi[Analisi.VEGFRU_UMIDITA]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.VEGFRU_UMIDITA]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.VEGFRU_UMIDITA])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloUMIDITA","inseriti");
      templ.newBlock("hrefUMIDITA");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloUMIDITA","richiesti");
      templ.newBlock("hrefUMIDITA");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloUMIDITA","disabilitato");
      break;
  }

  /**
   * microelementi
   * */
  if (richiestaAnalisi[Analisi.VEGFRU_MACRO_MICRO])
  {
    if (veg)
    {
      MacroMicroElemIndFoglie macroMicroElemIndFog= new MacroMicroElemIndFoglie();
      macroMicroElemIndFog.setDataSource(dataSource);
      macroMicroElemIndFog.setAut(aut);
      macroMicroElemIndFog.setIdRichiesta(aut.getIdRichiestaCorrente());
      if (macroMicroElemIndFog.select())
        esitoAnalisi[Analisi.VEGFRU_MACRO_MICRO]=Analisi.ANALISI_COLORE_INSERITI;
      else
        esitoAnalisi[Analisi.VEGFRU_MACRO_MICRO]=Analisi.ANALISI_COLORE_RICHIESTI;
    }
    else
    {
      MacroMicroElemIndFrutta macroMicroElemIndFru= new MacroMicroElemIndFrutta();
      macroMicroElemIndFru.setDataSource(dataSource);
      macroMicroElemIndFru.setAut(aut);
      macroMicroElemIndFru.setIdRichiesta(aut.getIdRichiestaCorrente());
      if (macroMicroElemIndFru.select())
        esitoAnalisi[Analisi.VEGFRU_MACRO_MICRO]=Analisi.ANALISI_COLORE_INSERITI;
      else
        esitoAnalisi[Analisi.VEGFRU_MACRO_MICRO]=Analisi.ANALISI_COLORE_RICHIESTI;
    }
  }
  switch(esitoAnalisi[Analisi.VEGFRU_MACRO_MICRO])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloMACROMICRO","inseriti");
      templ.newBlock("hrefMACROMICRO");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloMACROMICRO","richiesti");
      templ.newBlock("hrefMACROMICRO");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloMACROMICRO","disabilitato");
      break;
  }



%>
