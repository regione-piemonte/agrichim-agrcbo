package it.csi.agrc;

import java.io.Serializable;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Nadia B.
 * @version 1.0.0
 */

public class AllegatoEmailVO implements Serializable
{
	/** serialVersionUID */
	private static final long serialVersionUID = 2511428921432372517L;

	private byte[] file;
  private String nome;

	public AllegatoEmailVO()
	{
		super();
	}

	public AllegatoEmailVO(byte[] file, String nome)
	{
		super();

		this.file = file;
		this.nome = nome;
	}

	public byte[] getFile()
	{
		return file;
	}
	public void setFile(byte[] file)
	{
		this.file = file;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
}