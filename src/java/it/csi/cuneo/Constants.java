package it.csi.cuneo;

/**
 * <p>
 * Classe delle costanti applicative.
 * </p>
 * 
 */

public final class Constants
{
  public static final String LOGGER_BASE = "agrichimbo";
  public static final String SIGLA_PROVINCIA_STATO_ESTERO="EE";
  public static final String DESC_PROVINCIA_STATO_ESTERO="STATO ESTERO";
  public static final int ID_PROCEDIMENTO_AGCRBO = 999;
  
  //costanti usate nella chiamate dei metodi sigmater
  public static final int SRID=32632;
  public static final double METRI_BUFFER=50;

  public static class CODICE_TRACCIATURA
  {
  	public static final String CAMPIONE_IN_LABORATORIO = "20";
  	public static final String REFERTO_EMESSO = "40";
    public static final String RICHIESTA_ANNULLATA = "50";
  }

  public static class REGIONE
  {
    public static final String PIEMONTE = "01";
  }

  public static class MATERIALE
  {
    public static final String CODICE_MATERIALE_TERRENI = "TER";
    public static final String CODICE_MATERIALE_ALTRE_MATRICI = "ZMA";
  }
}