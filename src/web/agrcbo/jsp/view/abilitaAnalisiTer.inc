<%
  it.csi.agrc.Analisi analisiRichieste=new it.csi.agrc.Analisi();
  analisiRichieste.setDataSource(dataSource);
  analisiRichieste.setAut(aut);
  analisiRichieste.setIdRichiesta(aut.getIdRichiestaCorrente());
  analisiRichieste.select();

  int esitoAnalisi[]=new int[12];



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
      ferro=true;
      manganese=true;
      zinco=true;
      rame=true;
      richiestaAnalisi[Analisi.TER_MICROELEMENTI] = true;
    }
    if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codiceRichAnal))
    {
      richiestaAnalisi[Analisi.TER_REAZIONE_SUOLO] = true; // PH
      richiestaAnalisi[Analisi.TER_SOSTANZA_ORGANICA] = true;
      richiestaAnalisi[Analisi.TER_FOSFORO_OLSEN] = true;
      richiestaAnalisi[Analisi.TER_AZOTO] = true;
      richiestaAnalisi[Analisi.TER_COMPLESSO_SCAMBIO] = true;
      calcio=true;
      magnesio=true;
      potassio=true;
      csc=true;
    }
    if (it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codiceRichAnal))
    {
      richiestaAnalisi[Analisi.TER_COMPLESSO_SCAMBIO] = true;
      calcio=true;
      magnesio=true;
      potassio=true;
      csc=true;
    }
  /*  if (it.csi.agrc.Analisi.ANA_PACCHETTO_METALLI_PESANTI.equals(codiceRichAnal))
    {
      ferroTotale = true;
      manganeseTotale = true;
      zincoTotale = true;
      rameTotale = true;
      piomboTotale = true;
      cromoTotale = true;
      boroTotale = true;
      nichelTotale = true;
      cadmioTotale = true;
      stronzioTotale = true;
      altroMetalloTotale = true;
      richiestaAnalisi[Analisi.TER_METALLI_PESANTI] = true;
    }*/

    /**
     * granulometria metodo automatico
     * granulometria metodo Bojoucos
     * */
    if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_STANDARD.equals(codiceRichAnal))
    {
      richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO] = true;
      richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_BOJOUCOS] = true;
      if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(codiceRichAnal)) fra4=true;
      if (it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(codiceRichAnal)) fra5=true;
      if (it.csi.agrc.Analisi.ANA_STANDARD.equals(codiceRichAnal)) std=true;
    }

    /**
     * umidità del campione
     * */
    if (it.csi.agrc.Analisi.ANA_UMIDITA.equals(codiceRichAnal))
        richiestaAnalisi[Analisi.TER_UMIDITA] = true;

    /**
     * reazione del suolo
     * */
    if (it.csi.agrc.Analisi.ANA_PH.equals(codiceRichAnal))
        richiestaAnalisi[Analisi.TER_REAZIONE_SUOLO] = true;

    /**
     * calcimetria
     * */
    if (it.csi.agrc.Analisi.ANA_CALCAREATTIVO.equals(codiceRichAnal)
                              ||
        it.csi.agrc.Analisi.ANA_CALCARETOTALE.equals(codiceRichAnal)
                              ||
        it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codiceRichAnal)
        )
        richiestaAnalisi[Analisi.TER_CALCIMETRIA] = true;

    /**
     * sostanza organica
     * */
    if (it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA.equals(codiceRichAnal))
        richiestaAnalisi[Analisi.TER_SOSTANZA_ORGANICA] = true;

    /**
     * azoto
     * */
    if (it.csi.agrc.Analisi.ANA_AZOTO.equals(codiceRichAnal))
      richiestaAnalisi[Analisi.TER_AZOTO] = true;

    /***
     * complesso di scambio
     */
    if(it.csi.agrc.Analisi.ANA_CALCIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_MAGNESIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_POTASSIO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codiceRichAnal)
       )
    {
      richiestaAnalisi[Analisi.TER_COMPLESSO_SCAMBIO] = true;
      if (it.csi.agrc.Analisi.ANA_CALCIO.equals(codiceRichAnal)) calcio=true;
      if (it.csi.agrc.Analisi.ANA_MAGNESIO.equals(codiceRichAnal)) magnesio=true;
      if (it.csi.agrc.Analisi.ANA_POTASSIO.equals(codiceRichAnal)) potassio=true;
      if (it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codiceRichAnal)) csc=true;
    }
    /***
     * fosforo OLSEN
     */
    if (it.csi.agrc.Analisi.ANA_FOSFORO.equals(codiceRichAnal))
      richiestaAnalisi[Analisi.TER_FOSFORO_OLSEN] = true;

    /**
     * microelementi
     * */
    if (it.csi.agrc.Analisi.ANA_FERRO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_MANGANESE.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_ZINCO.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_RAME.equals(codiceRichAnal)
                        ||
       it.csi.agrc.Analisi.ANA_BORO.equals(codiceRichAnal)
       )
    {
      if (it.csi.agrc.Analisi.ANA_FERRO.equals(codiceRichAnal)) ferro=true;
      if (it.csi.agrc.Analisi.ANA_MANGANESE.equals(codiceRichAnal)) manganese=true;
      if (it.csi.agrc.Analisi.ANA_ZINCO.equals(codiceRichAnal)) zinco=true;
      if (it.csi.agrc.Analisi.ANA_RAME.equals(codiceRichAnal)) rame=true;
      if (it.csi.agrc.Analisi.ANA_BORO.equals(codiceRichAnal)) boro=true;
      richiestaAnalisi[Analisi.TER_MICROELEMENTI] = true;
    }

    /**
     * conducibilità o salinità
     * */
    if (it.csi.agrc.Analisi.ANA_SALINITA.equals(codiceRichAnal))
      richiestaAnalisi[Analisi.TER_CONDUCIBILITA_SALINITA] = true;

    /**
     * Metalli pesanti
     */
    if (it.csi.agrc.Analisi.ANA_FERRO_TOTALE.equals(codiceRichAnal) || it.csi.agrc.Analisi.ANA_MANGANESE_TOTALE.equals(codiceRichAnal) ||
    		it.csi.agrc.Analisi.ANA_ZINCO_TOTALE.equals(codiceRichAnal) || it.csi.agrc.Analisi.ANA_RAME_TOTALE.equals(codiceRichAnal) ||
				it.csi.agrc.Analisi.ANA_PIOMBO_TOTALE.equals(codiceRichAnal) || it.csi.agrc.Analisi.ANA_CROMO_TOTALE.equals(codiceRichAnal) ||
				//it.csi.agrc.Analisi.ANA_BORO_TOTALE.equals(codiceRichAnal) || 
				it.csi.agrc.Analisi.ANA_NICHEL_TOTALE.equals(codiceRichAnal) ||
				it.csi.agrc.Analisi.ANA_CADMIO_TOTALE.equals(codiceRichAnal) 
				/*|| 
				it.csi.agrc.Analisi.ANA_STRONZIO_TOTALE.equals(codiceRichAnal) ||
				it.csi.agrc.Analisi.ANA_ALTRO_METALLO_TOTALE.equals(codiceRichAnal)*/
       )
    {
      if (it.csi.agrc.Analisi.ANA_FERRO_TOTALE.equals(codiceRichAnal)) ferroTotale = true;
      if (it.csi.agrc.Analisi.ANA_MANGANESE_TOTALE.equals(codiceRichAnal)) manganeseTotale = true;
      if (it.csi.agrc.Analisi.ANA_ZINCO_TOTALE.equals(codiceRichAnal)) zincoTotale = true;
      if (it.csi.agrc.Analisi.ANA_RAME_TOTALE.equals(codiceRichAnal)) rameTotale = true;
      if (it.csi.agrc.Analisi.ANA_PIOMBO_TOTALE.equals(codiceRichAnal)) piomboTotale = true;
      if (it.csi.agrc.Analisi.ANA_CROMO_TOTALE.equals(codiceRichAnal)) cromoTotale = true;
     // if (it.csi.agrc.Analisi.ANA_BORO_TOTALE.equals(codiceRichAnal)) boroTotale = true;
      if (it.csi.agrc.Analisi.ANA_NICHEL_TOTALE.equals(codiceRichAnal)) nichelTotale = true;
      if (it.csi.agrc.Analisi.ANA_CADMIO_TOTALE.equals(codiceRichAnal)) cadmioTotale = true;
     // if (it.csi.agrc.Analisi.ANA_STRONZIO_TOTALE.equals(codiceRichAnal)) stronzioTotale = true;
      //if (it.csi.agrc.Analisi.ANA_ALTRO_METALLO_TOTALE.equals(codiceRichAnal)) altroMetalloTotale = true;
      
      richiestaAnalisi[Analisi.TER_METALLI_PESANTI] = true;  
    }
  }


  /**
   * Per prima cosa disabilito tutte le richieste
   * */
  for (int i=0;i<esitoAnalisi.length;i++)
    esitoAnalisi[i]=Analisi.ANALISI_COLORE_DISABILITATO;

  /**
   * Vado a vedere se è stata richiesta l'analisi. In caso positivo
   * vado a vedere se sono già stati inseriti dei dati:
   * se questo risulta vero imposto il colore a ANALISI_COLORE_INSERITI
   * altrimenti a ANALISI_COLORE_RICHIESTI
   * */

  /**
   * umidità del campione
   * */
  if (richiestaAnalisi[Analisi.TER_UMIDITA])
  {
    UmiditaCampione umiditaCamp= new UmiditaCampione();
    umiditaCamp.setDataSource(dataSource);
    umiditaCamp.setAut(aut);
    umiditaCamp.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (umiditaCamp.select())
      esitoAnalisi[Analisi.TER_UMIDITA]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_UMIDITA]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_UMIDITA])
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
   * granulometria metodo automatico
   * */
  if (richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO])
  {
    GranulometriaA4Frazioni granulometriaA4Fraz= new GranulometriaA4Frazioni();
    granulometriaA4Fraz.setDataSource(dataSource);
    granulometriaA4Fraz.setAut(aut);
    granulometriaA4Fraz.setIdRichiesta(aut.getIdRichiestaCorrente());

    GranulometriaA5Frazioni granulometriaA5Fraz= new GranulometriaA5Frazioni();
    granulometriaA5Fraz.setDataSource(dataSource);
    granulometriaA5Fraz.setAut(aut);
    granulometriaA5Fraz.setIdRichiesta(aut.getIdRichiestaCorrente());

    GranulometriaStandard granulometriaStd= new GranulometriaStandard();
    granulometriaStd.setDataSource(dataSource);
    granulometriaStd.setAut(aut);
    granulometriaStd.setIdRichiesta(aut.getIdRichiestaCorrente());

    if (granulometriaA4Fraz.select() || granulometriaA5Fraz.select()
         || granulometriaStd.select())
      esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloGRANAUTOMATICO","inseriti");
      templ.newBlock("hrefGRANAUTOMATICO");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloGRANAUTOMATICO","richiesti");
      templ.newBlock("hrefGRANAUTOMATICO");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloGRANAUTOMATICO","disabilitato");
      break;
  }


  /**
   * granulometria metodo Bojoucos
   * */
  if (richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_BOJOUCOS])
  {
    GranulometriaMetodoBojoucos granulometriaMetBojou= new GranulometriaMetodoBojoucos();
    granulometriaMetBojou.setDataSource(dataSource);
    granulometriaMetBojou.setAut(aut);
    granulometriaMetBojou.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (granulometriaMetBojou.select())
      esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_BOJOUCOS]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_BOJOUCOS]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_GRANULOMETRIA_METODO_BOJOUCOS])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloGRANBOJOUCOS","inseriti");
      templ.newBlock("hrefGRANBOJOUCOS");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloGRANBOJOUCOS","richiesti");
      templ.newBlock("hrefGRANBOJOUCOS");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloGRANBOJOUCOS","disabilitato");
      break;
  }

  /**
   * reazione del suolo
   * */
  if (richiestaAnalisi[Analisi.TER_REAZIONE_SUOLO])
  {
    ReazioneSuolo reazSuolo= new ReazioneSuolo();
    reazSuolo.setDataSource(dataSource);
    reazSuolo.setAut(aut);
    reazSuolo.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (reazSuolo.select())
      esitoAnalisi[Analisi.TER_REAZIONE_SUOLO]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_REAZIONE_SUOLO]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_REAZIONE_SUOLO])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloREAZIONESUOLO","inseriti");
      templ.newBlock("hrefREAZIONESUOLO");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloREAZIONESUOLO","richiesti");
      templ.newBlock("hrefREAZIONESUOLO");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloREAZIONESUOLO","disabilitato");
      break;
  }

  /**
   * calcimetria
   * */
  if (richiestaAnalisi[Analisi.TER_CALCIMETRIA])
  {
    Calcimetria calc= new Calcimetria();
    calc.setDataSource(dataSource);
    calc.setAut(aut);
    calc.setIdRichiesta(aut.getIdRichiestaCorrente());
    calc.select();
    if (calc.isCompletata())
      esitoAnalisi[Analisi.TER_CALCIMETRIA]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_CALCIMETRIA]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_CALCIMETRIA])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloCALCIMETRIA","inseriti");
      templ.newBlock("hrefCALCIMETRIA");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloCALCIMETRIA","richiesti");
      templ.newBlock("hrefCALCIMETRIA");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloCALCIMETRIA","disabilitato");
      break;
  }

  /**
   * sostanza organica
   * */
  if (richiestaAnalisi[Analisi.TER_SOSTANZA_ORGANICA])
  {
    SostanzaOrganica sostanzaOrg= new SostanzaOrganica();
    sostanzaOrg.setDataSource(dataSource);
    sostanzaOrg.setAut(aut);
    sostanzaOrg.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (sostanzaOrg.select())
      esitoAnalisi[Analisi.TER_SOSTANZA_ORGANICA]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_SOSTANZA_ORGANICA]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_SOSTANZA_ORGANICA])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloSOSTANZAORGANICA","inseriti");
      templ.newBlock("hrefSOSTANZAORGANICA");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloSOSTANZAORGANICA","richiesti");
      templ.newBlock("hrefSOSTANZAORGANICA");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloSOSTANZAORGANICA","disabilitato");
      break;
  }

  /**
   * azoto
   * */
  if (richiestaAnalisi[Analisi.TER_AZOTO])
  {
    Azoto azo= new Azoto();
    azo.setDataSource(dataSource);
    azo.setAut(aut);
    azo.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (azo.select())
      esitoAnalisi[Analisi.TER_AZOTO]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_AZOTO]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_AZOTO])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloAZOTO","inseriti");
      templ.newBlock("hrefAZOTO");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloAZOTO","richiesti");
      templ.newBlock("hrefAZOTO");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloAZOTO","disabilitato");
      break;
  }

  /**
   * complesso di scambio
   * */
  if (richiestaAnalisi[Analisi.TER_COMPLESSO_SCAMBIO])
  {
    ComplessoScambio compScambio= new ComplessoScambio();
    compScambio.setDataSource(dataSource);
    compScambio.setAut(aut);
    compScambio.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (compScambio.select())
      esitoAnalisi[Analisi.TER_COMPLESSO_SCAMBIO]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_COMPLESSO_SCAMBIO]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_COMPLESSO_SCAMBIO])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloCOMPLESSOSCAMBIO","inseriti");
      templ.newBlock("hrefCOMPLESSOSCAMBIO");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloCOMPLESSOSCAMBIO","richiesti");
      templ.newBlock("hrefCOMPLESSOSCAMBIO");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloCOMPLESSOSCAMBIO","disabilitato");
      break;
  }

  /**
   * fosforo OLSEN
   * */
  if (richiestaAnalisi[Analisi.TER_FOSFORO_OLSEN])
  {
    FosforoMetodoOLSEN fosforoMetOLSEN= new FosforoMetodoOLSEN();
    fosforoMetOLSEN.setDataSource(dataSource);
    fosforoMetOLSEN.setAut(aut);
    fosforoMetOLSEN.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (fosforoMetOLSEN.select())
      esitoAnalisi[Analisi.TER_FOSFORO_OLSEN]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_FOSFORO_OLSEN]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_FOSFORO_OLSEN])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloFOSFOROOLSEN","inseriti");
      templ.newBlock("hrefFOSFOROOLSEN");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloFOSFOROOLSEN","richiesti");
      templ.newBlock("hrefFOSFOROOLSEN");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloFOSFOROOLSEN","disabilitato");
      break;
  }

  /**
   * microelementi
   * */
  if (richiestaAnalisi[Analisi.TER_MICROELEMENTI])
  {
    MicroelementiMetodoDTPA microelemMetDTPA= new MicroelementiMetodoDTPA();
    microelemMetDTPA.setDataSource(dataSource);
    microelemMetDTPA.setAut(aut);
    microelemMetDTPA.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (microelemMetDTPA.select())
      esitoAnalisi[Analisi.TER_MICROELEMENTI]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_MICROELEMENTI]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_MICROELEMENTI])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloMICROELEMENTI","inseriti");
      templ.newBlock("hrefMICROELEMENTI");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloMICROELEMENTI","richiesti");
      templ.newBlock("hrefMICROELEMENTI");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloMICROELEMENTI","disabilitato");
      break;
  }

  /**
   * conducibilità o salinità
   * */
  if (richiestaAnalisi[Analisi.TER_CONDUCIBILITA_SALINITA])
  {
    ConducibilitaSalinita condSalinita= new ConducibilitaSalinita();
    condSalinita.setDataSource(dataSource);
    condSalinita.setAut(aut);
    condSalinita.setIdRichiesta(aut.getIdRichiestaCorrente());
    if (condSalinita.select())
      esitoAnalisi[Analisi.TER_CONDUCIBILITA_SALINITA]=Analisi.ANALISI_COLORE_INSERITI;
    else
      esitoAnalisi[Analisi.TER_CONDUCIBILITA_SALINITA]=Analisi.ANALISI_COLORE_RICHIESTI;
  }
  switch(esitoAnalisi[Analisi.TER_CONDUCIBILITA_SALINITA])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloCONDUCIBILITASALINITA","inseriti");
      templ.newBlock("hrefCONDUCIBILITASALINITA");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloCONDUCIBILITASALINITA","richiesti");
      templ.newBlock("hrefCONDUCIBILITASALINITA");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloCONDUCIBILITASALINITA","disabilitato");
      break;
  }

  //Metalli pesanti
  if (richiestaAnalisi[Analisi.TER_METALLI_PESANTI])
  {
    MetalliPesanti metalliP = new MetalliPesanti();
    metalliP.setDataSource(dataSource);
    metalliP.setAut(aut);
    metalliP.setIdRichiesta(aut.getIdRichiestaCorrente());

    if (metalliP.select())
    {
      esitoAnalisi[Analisi.TER_METALLI_PESANTI] = Analisi.ANALISI_COLORE_INSERITI;
    }
    else
    {
      esitoAnalisi[Analisi.TER_METALLI_PESANTI] = Analisi.ANALISI_COLORE_RICHIESTI;
    }
  }
  switch (esitoAnalisi[Analisi.TER_METALLI_PESANTI])
  {
    case Analisi.ANALISI_COLORE_INSERITI:
      templ.bset("coloreTitoloMETALLIPESANTI", "inseriti");
      templ.newBlock("hrefMETALLIPESANTI");
      break;
    case Analisi.ANALISI_COLORE_RICHIESTI:
      templ.bset("coloreTitoloMETALLIPESANTI", "richiesti");
      templ.newBlock("hrefMETALLIPESANTI");
      break;
    case Analisi.ANALISI_COLORE_DISABILITATO:
      templ.bset("coloreTitoloMETALLIPESANTI", "disabilitato");
      break;
  }
%>