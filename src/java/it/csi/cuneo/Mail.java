package it.csi.cuneo;

import it.csi.agrc.AllegatoEmailVO;
import it.csi.agrc.EtichettaCampione;
import it.csi.agrc.EtichettaCampioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.smtp.SMTPMessage;

/**
 * Title:        Agrichim - Back Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

/**
 * Questa classe contiene i metodi necessari a costruire una email e a
 * spedirla
 */
public class Mail
{
      private String host = "<SMTP_SERVER_HOSTNAME>"; // Stringa che contiene l'indirizzo
      // del server che permette di spedire le email

      private String eMailSender;     // contiene l'indirizzo email del mittente
      private String eMailReceiver[]; // contiene gli indirizzi email dei destinatari
      private String subject;         // subject della email
      public String contenutoEmail;  // contiene il corpo della email
      public Vector<AllegatoEmailVO> elencoAllegati = new Vector<AllegatoEmailVO>();

      public String getSubject()
			{
				return subject;
			}

			public void setSubject(String subject)
			{
				this.subject = subject;
			}

			public String getContenutoEmail()
			{
				return contenutoEmail;
			}

			public void setContenutoEmail(String contenutoEmail)
			{
				this.contenutoEmail = contenutoEmail;
			}

			public Vector<AllegatoEmailVO> getElencoAllegati()
			{
				return elencoAllegati;
			}

			public void addAllegati(AllegatoEmailVO allegatoEmailVO)
			{
				if (elencoAllegati == null)
				{
					elencoAllegati = new Vector<AllegatoEmailVO>();
				}
				elencoAllegati.add(allegatoEmailVO);
			}

			/**
       * Unico costruttore della classe, presenta quattro parametri che
       * corrispondono agli indirizzi email del mittente e del destinatario
       * ed al nome ed al cognome del mittente
       */
      public Mail()
      {
      }

      /**
       * Questo metodo compone il corpo della email.
       */
      public void preparaMail(String subject,String query, String contenutoQuery,
                              String eccezioneEmail)
      {
              this.subject=subject;
              if (subject==null) subject="";
              if (query==null) query="";
              if (contenutoQuery==null) contenutoQuery="";
              if (eccezioneEmail==null) eccezioneEmail="";
              this.contenutoEmail=query+"\n\n"+contenutoQuery+"\n\n"+eccezioneEmail;
              //this.contenutoEmail="<HTML><BODY><H1>"+query+"</H1>\n<P>"+contenutoQuery+"\n<P>"+eccezioneEmail+"</BODY></HTML>";
              //CuneoLogger.debug(this, this.contenutoEmail);
      }

      /**
       * Questo metodo compone il corpo della email.
       */
      public void preparaMail(EtichettaCampioni etichettaCampioni,String scarto,String note)
      {

              int size=etichettaCampioni.size();
              StringBuffer contenuto=new StringBuffer();
              if (size==1)
              {
                if ("B".equals(scarto))
                {
                  subject="Campione scartato";
                  contenuto.append("Il seguente campione ricevuto dal laboratorio agrochimico è stato scartato per il seguente motivo: ");
                  contenuto.append(note);
                }
                else
                {
                  if ("A".equals(scarto))
                  {
                    subject="Campione accettato con riserva";
                    contenuto.append("Il seguente campione ricevuto dal laboratorio agrochimico è stato accettato con riserva: ");
                    contenuto.append(note);
                  }
                  else
                  {
                    subject="Campione accettato";
                    contenuto.append("Il seguente campione ricevuto dal laboratorio agrochimico è stato accettato. ");
                    if (note!=null)
                      contenuto.append(note);
                  }
                }
              }
              else
              {
                if ("B".equals(scarto))
                {
                  subject="Campioni scartati";
                  contenuto.append("I seguenti campioni ricevuti dal laboratorio agrochimico sono stati scartati per il seguente motivo: ");
                  contenuto.append(note);
                }
                else
                {
                  if ("A".equals(scarto))
                  {
                    subject="Campioni accettati con riserva";
                    contenuto.append("I seguenti campioni ricevuti dal laboratorio agrochimico sono stati accettati con riserva: ");
                    contenuto.append(note);
                  }
                  else
                  {
                    subject="Campioni accettati";
                    contenuto.append("I seguenti campioni ricevuti dal laboratorio agrochimico sono stati accettati. ");
                    if (note!=null)
                      contenuto.append(note);
                  }
                }
              }
              contenuto.append("\n\n");
              EtichettaCampione e=null;
              for(int i=0;i<size;i++)
              {
                e=etichettaCampioni.get(i);
                contenuto.append("Numero Richiesta: ");
                contenuto.append(e.getIdRichiesta()).append("\n");
                contenuto.append("Materiale/Matrice: ");
                contenuto.append(e.getDescMateriale()).append("\n");
                if (e.getAnnoCampione()==null || e.getNumeroCampione()==null)
                {
                  contenuto.append("Anno/Numero Campione: \n");
                }
                else
                {
                  contenuto.append("Anno/Numero Campione: ");
                  contenuto.append(e.getAnnoCampione()).append("/");
                  contenuto.append(e.getNumeroCampione()).append("\n");
                }
                if (e.getDescrizioneEtichetta()==null)
                {
                  contenuto.append("Etichetta: \n");
                }
                else
                {
                  contenuto.append("Etichetta: ");
                  contenuto.append(e.getDescrizioneEtichetta()).append("\n");
                }
                contenuto.append("Proprietario: ");
                contenuto.append(e.getProprietario()).append("\n");
                contenuto.append("Richiedente: ");
                contenuto.append(e.getRichiedente()).append("\n");
                if (i!=size-1)
                  contenuto.append("--------------------------------------\n\n");
              }
              this.contenutoEmail=contenuto.toString();
      }

      /**
       * Questo metodo compone il corpo della email.
       */
      public void preparaMail(String idRichiesta)
      {
        StringBuffer contenuto = new StringBuffer();
        subject="Analisi terminata";
        contenuto.append("Il referto dell'analisi del campione con numero richiesta ");
        contenuto.append(idRichiesta);
        contenuto.append(" è disponibile online.");
        this.contenutoEmail = contenuto.toString();
      }
      
      public void preparaMail(StringBuffer idRichiesta)
      {
      	boolean multipla=false;
      	if (idRichiesta!=null && idRichiesta.toString().indexOf(',')!=-1)
      		multipla=true;
      		
        StringBuffer contenuto = new StringBuffer();
        
        if (multipla)
        	subject="Analisi terminate";
        else subject="Analisi terminata";
        
        if (multipla)
          contenuto.append("I referti delle analisi dei campioni con numero richiesta ");
        else contenuto.append("Il referto dell'analisi del campione con numero richiesta ");
         
        
        contenuto.append(idRichiesta);
        
        if (multipla)
        	contenuto.append(" sono disponibili online.");
        else contenuto.append(" è disponibile online.");
        this.contenutoEmail = contenuto.toString();
      }

      /**
       * Questo metodo è quello che spedisce l' email. Restituisce true nel
       * caso non ci siano stati problemi, restituisce false se l'email non
       * è stata spedita
       */
      public boolean inviaMail()
      {
              try
              {
                      // create some properties and get the default Session
                      Properties props = new Properties();
                      props.put("mail.smtp.host", host);


                      Session session = Session.getDefaultInstance(props, null);

                      // crea il messaggio
                      Message msg = new MimeMessage(session);
                      msg.setFrom(new InternetAddress(eMailSender));

                      // E' richiesto un vettore di indirizzi perché una generica email
                      // può avere più destinatari!!
                      InternetAddress[] address = new InternetAddress[eMailReceiver.length];
                      for (int i=0;i<address.length;i++)
                        address[i]=new InternetAddress(eMailReceiver[i]);
                      msg.setRecipients(Message.RecipientType.TO, address);
                      /**
                       * E' possibile settare oltre al destinatario anche la copia
                       * carbone e la copia carbone nascosta
                       **/
                      //Message.RecipientType.CC copia carbone
                      //Message.RecipientType.BCC copia carbone nascaosta
                      msg.setSubject(subject);
                      msg.setSentDate(new Date());
                      msg.setText(contenutoEmail);
                      
                      

                      //spedisce il messaggio
                      Transport.send(msg);
              }
              catch(Exception e)
              {
                      /**
                * Se si entra in questo blocco vuol dire che è stata generata
                * una eccezione e che quindi l'email non è stata spedita
                    */
                      CuneoLogger.debug(this, "\nMail.inviaMail(): eccezione!\n"+e.toString());
                      return false;
              }
              return true; // l'email è stata spedita
      }

      public boolean inviaMailConAllegati()
      {
        try
        {
					Properties props = new Properties();
					props.put("mail.smtp.host", host);
					
					Session session = Session.getDefaultInstance(props, null);
					
					Multipart multipart = new MimeMultipart();
					
					//Corpo del messaggio    
			    MimeBodyPart mbp1 = new MimeBodyPart();
			    mbp1.setText(contenutoEmail,"UTF-8");
			    multipart.addBodyPart(mbp1);					
					
					//Allegati
					Vector<AllegatoEmailVO> elencoAllegati = getElencoAllegati();
					if (elencoAllegati != null)
					{
					  for (AllegatoEmailVO allegato : elencoAllegati)
					  {
					    byte[] attachmentData = allegato.getFile();
					    if (attachmentData == null) continue;
					
					    ByteArrayDataSource bds = new ByteArrayDataSource(attachmentData, "application/pdf");
					    DataSource source = bds;
					    MimeBodyPart mbp2 = new MimeBodyPart();
					    mbp2.setDataHandler(new DataHandler(source));
					    mbp2.setFileName(allegato.getNome());
					    multipart.addBodyPart(mbp2);          
					  }
					}
			 
					SMTPMessage msg = new SMTPMessage(session);
					msg.setFrom(new InternetAddress(eMailSender));

			    //Impostazione corpo e allegati nel messaggio      
			    msg.setContent(multipart);

					/**
					 * Destinatari
					 * E' possibile settare oltre al destinatario anche la copia
					 * carbone e la copia carbone nascosta
					 **/
					//Message.RecipientType.CC copia carbone
					//Message.RecipientType.BCC copia carbone nascosta
					msg.setRecipients(Message.RecipientType.CC, mapDestinatariToArrayInternetAddress(eMailReceiver));
					
					msg.setSubject(subject);
					msg.setSentDate(new Date());
					
					//Spedisce il messaggio
					Transport.send(msg);
        }
        catch(Exception e)
        {
					/**
					 * Se si entra in questo blocco vuol dire che è stata generata
					 * una eccezione e che quindi l'email non è stata spedita
					 */
					CuneoLogger.debug(this, "\nMail.inviaMail(): eccezione!\n" + e.toString());
					return false;
        }
        return true; // l'email è stata spedita
      }

    public void setEMailSender(String newEMailSender)
    {
      eMailSender = newEMailSender;
    }

    public void setEMailReceiver(String newEMailReceiver[])
    {
      int count=0;
      for (count=0;count<newEMailReceiver.length;count++)
        if ("".equals(newEMailReceiver[count])) break;
      eMailReceiver = new String [count];
      for (count=0;count<eMailReceiver.length;count++)
        eMailReceiver[count] = newEMailReceiver[count];
    }

    public void setHost(String host)
    {
      this.host = host;
    }

  	public static InternetAddress[] mapDestinatariToArrayInternetAddress(String[] destsMail) throws AddressException
    {
      ArrayList<InternetAddress> destinatari = null;
      if (destsMail != null && destsMail.length > 0)
      {
        destinatari = new ArrayList<InternetAddress>();
        for(int t=0;t<destsMail.length;t++)
        {
          InternetAddress destinatario = new InternetAddress(destsMail[t]);
          destinatari.add(destinatario);
        }
        return destinatari.toArray(new InternetAddress[destinatari.size()]);
      }
      return null;
    }
}
