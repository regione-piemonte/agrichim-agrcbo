package it.csi.agrc;
//import it.csi.cuneo.*;
//import java.util.*;
//import java.sql.*;
//import it.csi.jsf.web.pool.*;
import java.io.Serializable;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class BeanRicerca implements Serializable
{
  private static final long serialVersionUID = -2788561524951301448L;

  private String idRichiestaDa;
  private String idRichiestaA;
  
  private String dataDaGiorno;
  private String dataDaMese;
  private String dataDaAnno;
  private String dataAGiorno;
  private String dataAMese;
  private String dataAAnno;
  
  private String dataDaGiornoPag;
  private String dataDaMesePag;
  private String dataDaAnnoPag;
  private String dataAGiornoPag;
  private String dataAMesePag;
  private String dataAAnnoPag;
  
  private String dataEmissioneRefertoDaGiorno;
  private String dataEmissioneRefertoDaMese;
  private String dataEmissioneRefertoDaAnno;
  private String dataEmissioneRefertoAGiorno;
  private String dataEmissioneRefertoAMese;
  private String dataEmissioneRefertoAAnno;
  private String tipoMateriale;
  private String cognomeProprietario;
  private String nomeProprietario;
  private String etichetta;
  private String cognomeTecnico;
  private String nomeTecnico;
  private String annoCampione;
  private String numeroCampioneDa;
  private String numeroCampioneA;
  private String organizzazione;
  private String tipoOrganizzazione;
  private String labConsegna;
  private String stato;
  private String statoPagamento;
  private String labAnalisi;
  private boolean tutti=true;
  private String analisiFattura;
  private String spedizioneFattura;
  private String istat;
  private String tipoUtente;
  private String codFisPIVA;
  private String ragioneSociale;
  private String annoFattura;
  private String numeroFattura;
  private String annoFatturaA;
  private String numeroFatturaA;
  private String numeroRichiesta;
  private String numeroCampione;
  private String statoFattura;
  private String iuv;

  public BeanRicerca(){}

  public String getIdRichiestaDa()
  {
    return idRichiestaDa;
  }
  public String getIdRichiestaA()
  {
    return idRichiestaA;
  }
  public String getDataDaGiorno()
  {
    return dataDaGiorno;
  }
  public String getDataDaMese()
  {
    return dataDaMese;
  }
  public String getDataDaAnno()
  {
    return dataDaAnno;
  }
  public String getDataAGiorno()
  {
    return dataAGiorno;
  }
  public String getDataAMese()
  {
    return dataAMese;
  }
  public String getDataAAnno()
  {
    return dataAAnno;
  }
  public String getDataEmissioneRefertoDaGiorno()
	{
		return dataEmissioneRefertoDaGiorno;
	}
	public void setDataEmissioneRefertoDaGiorno(String dataEmissioneRefertoDaGiorno)
	{
		if (dataEmissioneRefertoDaGiorno == null) dataEmissioneRefertoDaGiorno = "";
		this.dataEmissioneRefertoDaGiorno = dataEmissioneRefertoDaGiorno;
	}
	public String getDataEmissioneRefertoDaMese()
	{
		return dataEmissioneRefertoDaMese;
	}
	public void setDataEmissioneRefertoDaMese(String dataEmissioneRefertoDaMese)
	{
		if (dataEmissioneRefertoDaMese == null) dataEmissioneRefertoDaMese = "";
		this.dataEmissioneRefertoDaMese = dataEmissioneRefertoDaMese;
	}
	public String getDataEmissioneRefertoDaAnno()
	{
		return dataEmissioneRefertoDaAnno;
	}
	public void setDataEmissioneRefertoDaAnno(String dataEmissioneRefertoDaAnno)
	{
		if (dataEmissioneRefertoDaAnno == null) dataEmissioneRefertoDaAnno = "";
		this.dataEmissioneRefertoDaAnno = dataEmissioneRefertoDaAnno;
	}
	public String getDataEmissioneRefertoAGiorno()
	{
		return dataEmissioneRefertoAGiorno;
	}
	public void setDataEmissioneRefertoAGiorno(String dataEmissioneRefertoAGiorno)
	{
		if (dataEmissioneRefertoAGiorno == null) dataEmissioneRefertoAGiorno = "";
		this.dataEmissioneRefertoAGiorno = dataEmissioneRefertoAGiorno;
	}
	public String getDataEmissioneRefertoAMese()
	{
		return dataEmissioneRefertoAMese;
	}
	public void setDataEmissioneRefertoAMese(String dataEmissioneRefertoAMese)
	{
		if (dataEmissioneRefertoAMese == null) dataEmissioneRefertoAMese = "";
		this.dataEmissioneRefertoAMese = dataEmissioneRefertoAMese;
	}
	public String getDataEmissioneRefertoAAnno()
	{
		return dataEmissioneRefertoAAnno;
	}
	public void setDataEmissioneRefertoAAnno(String dataEmissioneRefertoAAnno)
	{
		if (dataEmissioneRefertoAAnno == null) dataEmissioneRefertoAAnno = "";
		this.dataEmissioneRefertoAAnno = dataEmissioneRefertoAAnno;
	}  
  public String getTipoMateriale()
  {
    return tipoMateriale;
  }
  public String getCognomeProprietario()
  {
    return cognomeProprietario;
  }
  public String getNomeProprietario()
  {
    return nomeProprietario;
  }
  public String getEtichetta()
  {
    return etichetta;
  }
  public String getNomeTecnico()
  {
    return nomeTecnico;
  }
  public String getCognomeTecnico()
  {
  return cognomeTecnico;
  }
  public String getNumeroCampioneA()
  {
    return numeroCampioneA;
  }
  public String getNumeroCampioneDa()
  {
    return numeroCampioneDa;
  }
  public String getAnnoCampione()
  {
    return annoCampione;
  }
  public String getStatoPagamento()
  {
    return statoPagamento;
  }
  public String getStato()
  {
    return stato;
  }
  public String getLabConsegna()
  {
    return labConsegna;
  }
  public String getTipoOrganizzazione()
  {
    return tipoOrganizzazione;
  }
  public String getOrganizzazione()
  {
    return organizzazione;
  }
  public String getLabAnalisi()
  {
    return labAnalisi;
  }
  public boolean isTutti()
  {
    return tutti;
  }
  public String getSpedizioneFattura()
  {
    return spedizioneFattura;
  }
  public String getAnalisiFattura()
  {
    return analisiFattura;
  }
  public String getIstat()
  {
    return istat;
  }
  public String getCodFisPIVA()
  {
    return codFisPIVA;
  }
  public String getTipoUtente()
  {
    return tipoUtente;
  }
  public String getRagioneSociale()
  {
    return ragioneSociale;
  }
  public String getAnnoFattura()
  {
    return annoFattura;
  }
  public String getNumeroFattura()
  {
    return numeroFattura;
  }
  public String getAnnoFatturaA()
  {
    return annoFatturaA;
  }
  public String getNumeroFatturaA()
  {
    return numeroFatturaA;
  }
  public String getNumeroRichiesta()
  {
    return numeroRichiesta;
  }
  public String getNumeroCampione()
  {
    return numeroCampione;
  }
  public String getStatoFattura()
  {
    return statoFattura;
  }

  public void setNonValido()
  {
    setCognomeProprietario("");
    setNomeProprietario("");
    setNomeTecnico("");
    setCognomeTecnico("");
    setDataAAnno("");
    setDataAGiorno("");
    setDataAMese("");
    setDataDaAnno("");
    setDataDaGiorno("");
    setDataDaMese("");
    setDataAAnnoPag("");
    setDataAGiornoPag("");
    setDataAMesePag("");
    setDataDaAnnoPag("");
    setDataDaGiornoPag("");
    setDataDaMesePag("");
    setDataEmissioneRefertoAAnno("");
    setDataEmissioneRefertoAGiorno("");
    setDataEmissioneRefertoAMese("");
    setDataEmissioneRefertoDaAnno("");
    setDataEmissioneRefertoDaGiorno("");
    setDataEmissioneRefertoDaMese("");
    setEtichetta("");
    setIdRichiestaA("");
    setIdRichiestaDa("");
    setTipoMateriale("");
    setAnnoCampione("");
    setNumeroCampioneA("");
    setNumeroCampioneDa("");
    setOrganizzazione("");
    setTipoOrganizzazione("");
    setLabConsegna("");
    setStato("");
    setStatoPagamento("");
    setLabAnalisi("");
    setTutti(true);
    setAnalisiFattura("");
    setSpedizioneFattura("");
    setIstat("");
    setCodFisPIVA("");
    setTipoUtente("");
    setRagioneSociale("");
    setAnnoFattura("");
    setNumeroFattura("");
    setAnnoFatturaA("");
    setNumeroFatturaA("");
    setNumeroRichiesta("");
    setNumeroCampione("");
    setStatoFattura("");
    setIuv("");
  }

  public void setTipoMateriale(String tipoMateriale) {
    if (tipoMateriale==null) tipoMateriale="";
    this.tipoMateriale = tipoMateriale;
  }
  public void setNomeProprietario(String nomeProprietario) {
    if (nomeProprietario==null) nomeProprietario="";
    this.nomeProprietario = nomeProprietario;
  }
  public void setIdRichiestaDa(String idRichiestaDa) {
    if (idRichiestaDa==null) idRichiestaDa="";
    this.idRichiestaDa = idRichiestaDa;
  }
  public void setIdRichiestaA(String idRichiestaA) {
    if (idRichiestaA==null) idRichiestaA="";
    this.idRichiestaA = idRichiestaA;
  }
  public void setEtichetta(String etichetta) {
    if (etichetta==null) etichetta="";
    this.etichetta = etichetta;
  }
  public void setDataDaMese(String dataDaMese) {
    if (dataDaMese==null) dataDaMese="";
    this.dataDaMese = dataDaMese;
  }
  public void setDataDaGiorno(String dataDaGiorno) {
    if (dataDaGiorno==null) dataDaGiorno="";
    this.dataDaGiorno = dataDaGiorno;
  }
  public void setDataDaAnno(String dataDaAnno) {
    if (dataDaAnno==null) dataDaAnno="";
    this.dataDaAnno = dataDaAnno;
  }
  public void setDataAMese(String dataAMese) {
    if (dataAMese==null) dataAMese="";
    this.dataAMese = dataAMese;
  }
  public void setDataAGiorno(String dataAGiorno) {
    if (dataAGiorno==null) dataAGiorno="";
    this.dataAGiorno = dataAGiorno;
  }
  public void setDataAAnno(String dataAAnno) {
    if (dataAAnno==null) dataAAnno="";
    this.dataAAnno = dataAAnno;
  }
  public void setCognomeProprietario(String cognomeProprietario) {
    if (cognomeProprietario==null) cognomeProprietario="";
    this.cognomeProprietario = cognomeProprietario;
  }
  public void setCognomeTecnico(String cognomeTecnico) {
    if (cognomeTecnico==null) cognomeTecnico="";
    this.cognomeTecnico = cognomeTecnico;
  }
  public void setNomeTecnico(String nomeTecnico) {
    if (nomeTecnico==null) nomeTecnico="";
    this.nomeTecnico = nomeTecnico;
  }
  public void setAnnoCampione(String annoCampione) {
    if (annoCampione==null) annoCampione="";
    this.annoCampione = annoCampione;
  }
  public void setNumeroCampioneDa(String numeroCampioneDa) {
    if (numeroCampioneDa==null) numeroCampioneDa="";
    this.numeroCampioneDa = numeroCampioneDa;
  }
  public void setNumeroCampioneA(String numeroCampioneA) {
    if (numeroCampioneA==null) numeroCampioneA="";
    this.numeroCampioneA = numeroCampioneA;
  }
  public void setOrganizzazione(String organizzazione) {
    if (organizzazione==null) organizzazione="";
    this.organizzazione = organizzazione;
  }
  public void setTipoOrganizzazione(String tipoOrganizzazione) {
    if (tipoOrganizzazione==null) tipoOrganizzazione="";
    this.tipoOrganizzazione = tipoOrganizzazione;
  }
  public void setLabConsegna(String labConsegna) {
    if (labConsegna==null) labConsegna="";
    this.labConsegna = labConsegna;
  }
  public void setStato(String stato) {
    if (stato==null) stato="";
    this.stato = stato;
  }
  public void setStatoPagamento(String statoPagamento) {
    if (statoPagamento==null) statoPagamento="";
    this.statoPagamento = statoPagamento;
  }
  public void setLabAnalisi(String labAnalisi)
  {
    if (labAnalisi==null) labAnalisi="";
    this.labAnalisi = labAnalisi;
  }
  public void setTutti(boolean tutti)
  {
    this.tutti = tutti;
  }
  public void setAnalisiFattura(String analisiFattura)
  {
    if (analisiFattura==null) analisiFattura="";
    this.analisiFattura = analisiFattura;
  }
  public void setSpedizioneFattura(String spedizioneFattura)
  {
    if (spedizioneFattura==null) spedizioneFattura="";
    this.spedizioneFattura = spedizioneFattura;
  }
  public void setIstat(String istat)
  {
    if (istat==null) istat="";
    this.istat = istat;
  }
  public void setTipoUtente(String tipoUtente)
  {
    if (tipoUtente==null) tipoUtente="";
    this.tipoUtente = tipoUtente;
  }
  public void setCodFisPIVA(String codFisPIVA)
  {
    if (codFisPIVA==null) codFisPIVA="";
    this.codFisPIVA = codFisPIVA;
  }
  public void setRagioneSociale(String ragioneSociale)
  {
    if (ragioneSociale==null) ragioneSociale="";
    this.ragioneSociale = ragioneSociale;
  }
  public void setAnnoFattura(String annoFattura)
  {
    if (annoFattura==null) annoFattura="";
    this.annoFattura = annoFattura;
  }
  public void setNumeroFattura(String numeroFattura)
  {
    if (numeroFattura==null) numeroFattura="";
    this.numeroFattura = numeroFattura;
  }
  public void setAnnoFatturaA(String annoFatturaA)
  {
    if (annoFatturaA==null) annoFatturaA="";
    this.annoFatturaA = annoFatturaA;
  }
  public void setNumeroFatturaA(String numeroFatturaA)
  {
    if (numeroFatturaA==null) numeroFatturaA="";
    this.numeroFatturaA = numeroFatturaA;
  }
  public void setNumeroRichiesta(String numeroRichiesta)
  {
    if (numeroRichiesta==null) numeroRichiesta="";
    this.numeroRichiesta = numeroRichiesta;
  }
  public void setNumeroCampione(String numeroCampione)
  {
    if (numeroCampione==null) numeroCampione="";
    this.numeroCampione = numeroCampione;
  }
  public void setStatoFattura(String statoFattura)
  {
    if (statoFattura==null) statoFattura="";
    this.statoFattura = statoFattura;
  }

	public String getIuv() {
		return iuv;
	}
	
	public void setIuv(String iuv) {
		if(iuv==null) iuv="";
		this.iuv = iuv;
	}

	public String getDataDaGiornoPag() {
		return dataDaGiornoPag;
	}

	public void setDataDaGiornoPag(String dataDaGiornoPag) {
		if (dataDaGiornoPag==null) dataDaGiornoPag="";
	    this.dataDaGiornoPag = dataDaGiornoPag;
	}

	public String getDataDaMesePag() {
		return dataDaMesePag;
	}

	public void setDataDaMesePag(String dataDaMesePag) {
		if (dataDaMesePag==null) dataDaMesePag="";
	    this.dataDaMesePag = dataDaMesePag;
	}

	public String getDataDaAnnoPag() {
		return dataDaAnnoPag;
	}

	public void setDataDaAnnoPag(String dataDaAnnoPag) {
		if (dataDaAnnoPag==null) dataDaAnnoPag="";
	    this.dataDaAnnoPag = dataDaAnnoPag;
	}

	public String getDataAGiornoPag() {
		return dataAGiornoPag;
	}

	public void setDataAGiornoPag(String dataAGiornoPag) {
		if (dataAGiornoPag==null) dataAGiornoPag="";
	    this.dataAGiornoPag = dataAGiornoPag;
	}

	public String getDataAMesePag() {
		return dataAMesePag;
	}

	public void setDataAMesePag(String dataAMesePag) {
		if (dataAMesePag==null) dataAMesePag="";
	    this.dataAMesePag = dataAMesePag;
	}

	public String getDataAAnnoPag() {
		return dataAAnnoPag;
	}

	public void setDataAAnnoPag(String dataAAnnoPag) {
		if (dataAAnnoPag==null) dataAAnnoPag="";
	    this.dataAAnnoPag = dataAAnnoPag;
	}
}