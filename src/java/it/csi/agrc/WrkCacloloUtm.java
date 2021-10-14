package it.csi.agrc;

import java.io.Serializable;

public class WrkCacloloUtm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idCalcolo;
	private String codiceIstat;
	private String sezione;
	private String particella;
	private String foglio;
	private String coordinataNord;
	private String coordinataEst;
	private String dataElaborazione;
	private String esitoElaborazione;
	private String codErr;
	private String msgErr;
	
	public WrkCacloloUtm() {
		// TODO Auto-generated constructor stub
	}
	
	public long getIdCalcolo() {
		return idCalcolo;
	}

	public void setIdCalcolo(long idCalcolo) {
		this.idCalcolo = idCalcolo;
	}

	public String getCodiceIstat() {
		return codiceIstat;
	}
	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getCoordinataNord() {
		return coordinataNord;
	}
	public void setCoordinataNord(String coordinataNord) {
		this.coordinataNord = coordinataNord;
	}
	public String getCoordinataEst() {
		return coordinataEst;
	}
	public void setCoordinataEst(String coordinataEst) {
		this.coordinataEst = coordinataEst;
	}
	public String getDataElaborazione() {
		return dataElaborazione;
	}
	public void setDataElaborazione(String dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}
	public String getEsitoElaborazione() {
		return esitoElaborazione;
	}
	public void setEsitoElaborazione(String esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}

	public String getCodErr() {
		return codErr;
	}

	public void setCodErr(String codErr) {
		this.codErr = codErr;
	}

	public String getMsgErr() {
		return msgErr;
	}

	public void setMsgErr(String msgErr) {
		this.msgErr = msgErr;
	}

	
	
	
	

}
