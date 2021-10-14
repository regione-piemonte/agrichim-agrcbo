package it.csi.cuneo;

import it.csi.solmr.util.services.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.net.ssl.SSLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.poi.util.StringUtil;

/**
 * Title:        Agrichim - Back Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piant�, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public final class Utili
{
  /**
   *  Questo attributo � definito solo per le scritture su log tramite CuneoLogger
   **/
  private static final Utili utiliRef = new Utili(); 

 /**
   *  Questo attributo se vale true indica che all'interno della nostra
   *  applicazione web utilizziamo sempre il forward. Se vale false
   *  indica che dove serve invece del forward usiamo il sendredirect
   **/
  public static final boolean FORWARD=false;

  /**
   *  Questo attributo se vale true indica che la nostra applicazione
   *  web verr� installa su TOMCAT. Se vale false verr� invece installata
   *  su WebLogic. Dato che l'unica diffrenza fra i due � che WebLogic usa
   *  i DataSource mentre TomCat usa PoolMan, facendo cos� rendiamo
   *  trasparente l'utilizzo di un'ambiente rispetto all'altro
   **/
  public static final boolean POOLMAN=false;

  public static final DecimalFormat nf0 = new DecimalFormat("#0");
  public static final DecimalFormat nf1 = new DecimalFormat("#0.0");
  public static final DecimalFormat nf2 = new DecimalFormat("#0.00");
  public static final DecimalFormat nf3 = new DecimalFormat("#0.000");
  public static final DecimalFormat FORMAT_2 = new DecimalFormat("###,##0.00");
  
  public static final String valuta(Object euro) {
    return valuta(Double.parseDouble((String)euro));
  }
  
  public static final String valuta(double euro) {
    return nf2.format(euro);
  }
  
  public static final String valutaSimbolo(double euro) {
    return (new StringBuffer("� ")).append(nf2.format(euro)).toString();
  }

  private static String pattern = "dd/MM/yyyy";
  private static String yearPattern = "yyyy";
  private static Locale itLocale=new Locale("it","IT");
  private static SimpleDateFormat dateTimeFormatter;
  private static SimpleDateFormat yearTimeFormatter;

   public final static String DATA = "dd/MM/yyyy";
   public final static String ORA = "HH:mm:ss";
   public final static String DATA_ORA = "dd/MM/yyyy HH:mm:ss";
 
  static
  {
    dateTimeFormatter = new SimpleDateFormat(pattern,itLocale);
    yearTimeFormatter = new SimpleDateFormat(yearPattern,itLocale);
  }

  public static final String getSystemDate()
  {
      Date now = new Date();
      return dateTimeFormatter.format(now);
  }

  public static final String getYearDate()
  {
      Date now = new Date();
      return yearTimeFormatter.format(now);
  }

  public static void forward(HttpServletRequest request,
                             HttpServletResponse response,
                             String url)
      throws IOException,ServletException
  {
    if (FORWARD)
    {
      RequestDispatcher rd=request.getRequestDispatcher(url);
      rd.forward(request,response);
    }
    else
    {
      response.sendRedirect(request.getContextPath()+url);
    }
  }

  public static String formatCurrency(BigDecimal number)
  {
	  if(number==null)
		  number = BigDecimal.ZERO;
	  
    return formatValue(FORMAT_2, number);
  }
  
  private static String formatValue(DecimalFormat format, BigDecimal number)
  {
    try
    {
      if (number == null)
      {
        return "";
      }
      return format.format(number);
    }
    catch (Exception e)
    {
      return "";
    }
  }
  
  public static void forwardConParametri(HttpServletRequest request,
                             HttpServletResponse response,
                             String url)
      throws IOException,ServletException
  {
      RequestDispatcher rd=request.getRequestDispatcher(url);
      rd.forward(request,response);
  }

  public static void removeAllSessionAttributes(HttpSession session)
  {
    Enumeration attributi;

    attributi = session.getAttributeNames();
    String attrName;
    while (attributi.hasMoreElements())
    {
      attrName = (String)attributi.nextElement();
      CuneoLogger.debug(utiliRef, "Logout in corso - rimozione attributo di sessione '"+attrName+"'");
      session.removeAttribute(attrName);
    }
  }

  public static final int[] idTokenize(String lista)
  {
    return idTokenize(lista,";");
  }

  public static final int[] idTokenize(String lista, String delim)
  {
    if ( lista == null )
        return null;

    StringTokenizer st = new StringTokenizer(lista,delim);
    Vector ids_str = new Vector();
    while (st.hasMoreTokens())
        ids_str.add(st.nextToken());

    int ids[] = new int[ids_str.size()];
    for (int i=0; i<ids.length; i++)
        ids[i]=Integer.parseInt((String)ids_str.get(i));

    return ids;
  }

  public static final boolean[] boolIdTokenize(String lista, String delim)
  {
    if ( lista == null )
        return null;

    StringTokenizer st = new StringTokenizer(lista,delim);
    Vector ids_str = new Vector();
    while (st.hasMoreTokens())
        ids_str.add(st.nextToken());

    boolean ids[] = new boolean[ids_str.size()];
    for (int i=0; i<ids.length; i++)
      if ("true".equals((String)ids_str.get(i)))
        ids[i]=true;
      else  ids[i]=false;

    return ids;
  }

  public static final long[] longIdTokenize(String lista)
  {
    return longIdTokenize(lista,";");
  }

  public static final long[] longIdTokenize(String lista, String delim)
  {
    if ( lista == null )
        return null;

    StringTokenizer st = new StringTokenizer(lista,delim);
    Vector ids_str = new Vector();
    while (st.hasMoreTokens())
        ids_str.add(st.nextToken());

    long ids[] = new long[ids_str.size()];
    for (int i=0; i<ids.length; i++)
        ids[i]=Integer.parseInt((String)ids_str.get(i));

    return ids;
  }

  /**
   * @param cap  cap da controllare
   * @return restituisce true se il cap inserito � corretto (cio� se la sua
   * lunghezza � 5 ed � composto solo da cifre)
   */
  public static boolean controllaCap(String cap)
  {
    if (cap==null) return false;
    if (cap.length()!=5) return false;
    try
    {
      Integer.parseInt(cap);
    }
    catch(Exception e)
    {
      return false;
    }
    return true;
  }


  /**
   *
   * @param mail indirizzo email da controllare
   * @param l numero di caratteri massimo dell'indirizzo email
   * @return restituisce true se l'email inserita � formalmente corretta:
   * contiene una ed una sola @ ed almeno un punto
   */
  public static boolean controllaMail(String mail,int l)
  {
      if (mail==null) return false;
      if (mail.length()<6 || mail.length()>l) return false;
      if ((mail.indexOf('@')==-1) || (mail.indexOf('@')==0)
             || (mail.indexOf('.')==-1)) return false;
      mail=mail.substring(mail.indexOf('@')+1);
      if (mail.indexOf('@')!=-1) return false;
      if ((mail.indexOf('.')==-1) || (mail.indexOf('.')==0)) return false;
      return true;
    }

  /**
   *
   * @param cf codice fiscale da controllare
   * @return restituisce true se il codice fiscale inserito � corretto
   */
  public static boolean controllaCodiceFiscale(String cf)
  {
    try
    {
      String set1, set2, setpari, setdisp;
      int i,s;
      /*String validiNum = "0123456789";
      String validiLet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

      for(i = 0; i <= 5; i ++ )
        if (validiLet.indexOf( cf.charAt(i)) == -1)
          return false;
      for(i = 6; i <= 7; i ++ )
        if (validiNum.indexOf( cf.charAt(i)) == -1)
          return false;
      if (validiLet.indexOf( cf.charAt(i)) == -1)
        return false;
      for(i = 9; i <= 10; i ++ )
        if (validiNum.indexOf( cf.charAt(i)) == -1)
          return false;
      if (validiLet.indexOf( cf.charAt(i)) == -1)
        return false;
      for(i = 12; i <= 14; i ++ )
        if (validiNum.indexOf( cf.charAt(i)) == -1)
          return false;
      if (validiLet.indexOf( cf.charAt(i)) == -1)
        return false;*/

      set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
      setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
      s = 0;
      for(i = 1; i <= 13; i += 2 )
        s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      for(i = 0; i <= 14; i += 2 )
        s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
      if( s%26 != cf.charAt(15)-'A' )
        return false;
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   *
   * @param pi partita iva da controllare
   * @return restituisce true se la partita iva � corretta
   */
  public static boolean controllaPartitaIVA(String pi)
  {
    String validi = "0123456789";
    int c, s = 0;
    for(int i = 0; i < 11; i++ )
    {
      if( validi.indexOf( pi.charAt(i) ) == -1 )
        return false;
    }
    for(int i = 0; i <= 9; i += 2 )
      s += pi.charAt(i) - '0';
    for(int i = 1; i <= 9; i += 2 )
    {
      c = 2*( pi.charAt(i) - '0' );
      if( c > 9 )  c = c - 9;
      s += c;
    }
    if( ( 10 - s%10 )%10 != pi.charAt(10) - '0' )
      return false;
    return true;
  }

  public static final String toVarchar(String in)
  {
    if (in==null) return null;
    StringBuffer out=new StringBuffer();
    char str[]=new char[ in.length()];
    in.getChars(0,str.length,str,0);
    for (int i=0;i<str.length;i++)
    {
      out.append(str[i]);
      if (str[i]=='\'') out.append("'");
    }
    return out.toString();
   }

   public static String strikeSpecialChars(String input)
   {
     StringTokenizer st = new StringTokenizer(input,"\"'",true);
     StringBuffer sb = new StringBuffer("");
     String token = null;
     int ascii=0;
     while (st.hasMoreTokens())
     {
       token = st.nextToken();
       ascii = token.charAt(0);
       switch(ascii)
       {
         case 39: // '
           sb.append("\\'");
           break;
         case 34: // "
           sb.append("&quot;");
           break;
         default:
           sb.append(token);
       }
     }
     return sb.toString();
   }

   public static String strikeSpecialCharsURL(String input)
   {
     return strikeSpecialCharsURL(input,false);
   }
   public static String strikeSpecialCharsURL(String input, boolean isHyperLink)
   {
       String ampersand=(isHyperLink?"%25":"%");
       StringTokenizer st = new StringTokenizer(input," \"%&'/\\",true);
       StringBuffer sb = new StringBuffer("");
       String token = null;
       int ascii=0;
       while (st.hasMoreTokens())
       {
         token = st.nextToken();
         ascii = token.charAt(0);
         switch(ascii)
         {
           case 32: // blank
           case 34: // "
           case 37: // %
           case 38: // &
           case 39: // '
           case 47: // /
           case 92: // \
             sb.append(ampersand).append(Integer.toHexString(ascii));
             break;
           default:
             sb.append(token);
         }
       }
       return sb.toString();
   }
   public static boolean isNumber(String numero)
   {
     try {
    	 double num=Double.parseDouble(numero);
     } catch(Exception e) {
    	 return false;
     }
     return true;
   }
   /**
   * @param numero  numero da controllare
   * @param min  minimo valore che numero pu� assumere
   * @param max  massimo valore che numero pu� assumere
   * @return restituisce true se il numero inserito � corretto (cio� se �
   * un numero intero maggiore (o uguale) di min e minore (o uguale) di max
   */
  public static boolean isNumber(String numero,int min,int max)
  {
    try
    {
      int num=Integer.parseInt(numero);
      if (num<min) return false;
      if (num>max) return false;
    }
    catch(Exception e)
    {
      return false;
    }
    return true;
  }

  /**
   * @param numero  numero da controllare
   * @param min  minimo valore che numero pu� assumere
   * @param max  massimo valore che numero pu� assumere
   * @param precision  massimo numero di cifre decimali che pu� avere il numero
   * @return restituisce true se il numero inserito � corretto (cio� se �
   * un numero reale maggiore (o uguale) di min e minore (o uguale) di max
   * e di precisione massima precision
   */
  public static boolean isDouble(String numero,double min,double max,int precision)
  {
    char characterCheck;
    int counterPunto = 0;
    int posPunto = 0;
    int i;
    try
    {
      numero=numero.replace(',','.');
      double num=Double.parseDouble(numero);
      if (num<min) return false;
      if (num>max) return false;
    }
    catch(Exception e)
    {
      return false;
    }
    for (i=0; i < numero.length(); i++)
    {
      //Assigns the CharacterCheck variable to each character of input in succession
      characterCheck = numero.substring(i, i+1).charAt(0);
      if ('.' == (characterCheck))
      {
         counterPunto++;
         posPunto = i;
         if (counterPunto >1) return false;
      }
      if (! (((characterCheck >= '0') && (characterCheck <= '9')) || (characterCheck=='.') || (characterCheck=='-')))
         return false;
    }

    if (posPunto>0)
        if ( (i-posPunto-1) > precision )
           return false;
        return true;
  }

  /**
   * @param data  data da controllare
   * @return restituisce true se la data inserita � corretto . La data
   * deve essere inserita nel formato dd/mm/aaaa
   */
  public static boolean isDate(String data)
  {
    StringTokenizer st = new StringTokenizer(data,"/");
    String gg,mm,aaaa;
    if (st.hasMoreTokens())
      gg=st.nextToken();
    else return false;
    if (st.hasMoreTokens())
      mm=st.nextToken();
    else return false;
    if (st.hasMoreTokens())
      aaaa=st.nextToken();
    else return false;
    if (gg.length()!=2) gg='0'+gg;
    if (mm.length()!=2) mm='0'+mm;
    if (aaaa.length()!=4) return false;
    String strdata=gg+"/"+mm+"/"+aaaa;
    try
    {
      if (!strdata.equals(dateTimeFormatter.format(dateTimeFormatter.parse(data,new ParsePosition(0))))) return false;
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }
  
  public static String validazioneSQLParam(String data) {
		if(data==null || data.isEmpty())
			return null;
		else {
			data = data.replace("'", "\\'");
			return data;
		}
			
  }

  /**
   * Metodo accediURL(String u)
   *
   * @param u Stringa contenente l'url in formato &quot;http://host.domain/dir/page.htm&quot;
   * @return
   * @throws MalformedURLException
   * @throws IOException
   */
  public static BufferedReader accediURL(String u)
      throws MalformedURLException, IOException
  {
    return accediURL(u, null);
  }

  /**
   * Metodo accediURL(String u, String data)
   *
   * @param u String contenente l'url in formato &quot;http://host.domain/dir/page.htm&quot;
   * @param data String contenente i parametri da passare in post all'URL in formato &quot;parametro1=valore1&parametro2=valore2...&quot;
   * @return
   * @throws MalformedURLException
   * @throws IOException
   */
  public static BufferedReader accediURL(String u, String data)
      throws MalformedURLException, SSLException, IOException
  {
    CuneoLogger.debug(null, "\nUtili.accediURL(\""+u+"\", \""+data+"\") - inizio");
    // Bisogna prima installare JSSE 1.0.1+ e lavorare con il JDK 1.2+
    System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

//    System.getProperties().put("java.protocol.handler.pkgs", "HTTPClient");

//    SSLContext sc = SSLContext.getInstance("SSL");
//    sc.init(km,tma,new java.security.SecureRandom());
//    SSLSocketFactory sf1 = sc.getSocketFactory();
//    HttpsURLConnection.setDefaultSSLSocketFactory(sf1);


//    URL.setURLStreamHandlerFactory(new com.ms.net.wininet.WininetStreamHandlerFactory());
//    URLStreamHandlerFactory usfh = new URLStreamHandlerFactory();
//    URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory());
//    URL.set

    URL url=null;
    try
    {
      url = new URL(u);
    }
    catch (Throwable t)
    {
      CuneoLogger.debug(null, "\n\nAGRCFO - Utili.accediURL() - "+t.getMessage());
    }

    URLConnection conn = url.openConnection();
    conn.setDoOutput(true);
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    if (data!=null)
      wr.write(data);
    wr.flush();
    wr.close();

    // Get the response
    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    // CuneoLogger.debug(this, "Utili.accediURL(...) - fine");
    return rd;
  }

  /**
   * Restituisce una stringa che &egrave; la rappresentazione dello stacktrace associato
   * all'eccezione passata come argomento.
   * Se l'eccezione passata &egrave; <code>null</code>, viene restituito <code>null</code>.
   * @param e
   * @return
   */
  public static String getStackTraceAsString(Exception e)
  {
  	String result = null;

		if (e != null )
		{
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			printWriter.flush();
			result = stringWriter.toString();
		}

		return result;
  }

		/**
			* @param toParse la stringa da trasformare in un oggetto data
			* @param format il formato secondo il quale parsificare la stringa
			* @return l'oggetto data parsificato  secondo il contenuto del parametro format
			* @throws Exception se la parsificazione fallisce
		*/
	 public static Date parse(String toParse, String format) throws Exception {
	   SimpleDateFormat sdf = new SimpleDateFormat(format);
	   return sdf.parse(toParse);
	 }
	
	 /**
		 * @param date la stringa con la data da trasformare in un oggetto data
		 * @return l'oggetto data parsificato secondo il contenuto della costante DATA
		 * @throws Exception se la parsificazione fallisce
	 */
	public static Date parseDate(String date) throws Exception {
	  return parse(date, DATA);
	}

  /**
   * Somme e sottrazioni vengono arrotondate correttamente
   * @param numero
   * @param precisione
   * @return double
   */
  public static double arrotonda(double numero, int precisione)
  {
    return new java.math.BigDecimal(numero).setScale(precisione, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  private static String formatNumber(double val, int numDecimali, boolean separatoreMigliaia, boolean decimaliObbligatori)
  {
    String pattern = new String();

    if (separatoreMigliaia == true)
    {
      pattern = "#,##0";
    }
    else
    {
      pattern = "##0";
    }

    if (numDecimali >0 )
    {
      //Arrotondamento corretto
      val = arrotonda(val, numDecimali);

      //formatto con i decimali
      pattern += ".";
      /** @todo trovare metodo migliore per appendere 0 o # */
      for (int i = 0; i < numDecimali; i++)
      {
        if (decimaliObbligatori) {
          pattern +="0";
        }
        else {
          pattern +="#";
        }
      }
    }
    java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);

    return df.format(val);
  }

  /**
   * Formatta il numero percentuale con due cifre decimali e visualizza il relativo simbolo
   * @param val
   * @return
   */
  public static String formatNumberPercent(Object val, boolean decimali)
  {
    if (val == null || val.equals("")) return "";
    
    return formatNumber(Double.parseDouble(val.toString()), decimali ? 2 : 0, true, true) + "%";
  }
  
   /**
   * Riceve una stringa e restituisce un Long, facendo tutti i controlli del caso
   * Se la stringa � null o "", restituisce null
   */
  public static BigDecimal parseBigDecimal(String s)
  {
	  BigDecimal result = null;
	  if (s!=null && !"".equals(s))
		  result = new BigDecimal(s);
	  return result;
  }

 /**
   * Riceve una stringa in formato ISO-8859-xx
   * e converte le lettere accentate in unicode
   */
  public static String iso2unicode(String iso)
  {
	  iso.replace('�', '\u00E0');
	  iso.replace('�', '\u00E8');
	  iso.replace('�', '\u00E9');
	  iso.replace('�', '\u00EC');
	  iso.replace('�', '\u00F2');
	  iso.replace('�', '\u00F3');
	  iso.replace('�', '\u00F9');
	  return iso;
  }
  
  public static boolean isEmpty(String obj)
  {
  	return (obj == null || "".equals(obj));
  }

  public static String formatElementValue(String elementValue)
  {
  	if (isEmpty(elementValue)) return null;

  	elementValue = elementValue.replace(',','.');
  	elementValue = Utili.nf1.format(Double.parseDouble(elementValue));
  	elementValue = elementValue.replace('.',',');
  	
  	return elementValue;
  }

  public static String checkNull(Object obj)
  {
  	if (obj == null) return "";
  	
  	return obj.toString();
  }

  public static String getDecodificaPagamento(String pagamento)
  {
  	if (Validator.isEmpty(pagamento)) return null;
  	
  	if ("S".equals(pagamento)) return "Analisi pagata";
  	if ("N".equals(pagamento)) return "Analisi da pagare";
  	if ("G".equals(pagamento)) return "Analisi gratuita";

  	return pagamento;
  }

  public static String getDecodificaStatoAnomalia(String statoAnomalia)
  {
  	if (Validator.isEmpty(statoAnomalia)) return null;
  	
  	if ("A".equals(statoAnomalia)) return "Anomalia non bloccante";
  	if ("B".equals(statoAnomalia)) return "Anomalia bloccante";

  	return "No";
  }

  public static String getDecodificaFlagSiNo(String flag)
  {
  	if (Validator.isEmpty(flag)) return null;
  	
  	if ("S".equals(flag)) return "Si";

  	return "No";
  }

  public static String getDecodificaIntestazioneFattura(String fatturare)
  {
  	if (Validator.isEmpty(fatturare)) return null;
  	
  	if ("U".equals(fatturare)) return "Utente";
  	if ("P".equals(fatturare)) return "Proprietario";
  	if ("T".equals(fatturare)) return "Tecnico";
  	if ("A".equals(fatturare)) return "Altri estremi";  	

  	return null;
  }
  
//  public static String getImponibileoIVA(String costo_analisi, boolean iva) {
//	  BigDecimal imp = BigDecimal.ZERO;
//	  BigDecimal b_iva = BigDecimal.ZERO;
//	  if(NumberUtils.isNumber(costo_analisi)) {
//		  BigDecimal x = NumberUtils.createBigDecimal(costo_analisi);
//		  if(x!=BigDecimal.ZERO) {
//			  BigDecimal a = new BigDecimal("1.22"); 
//			  imp = x.divide(a, 2);
//			  b_iva = x.subtract(imp);
//		  }
//		  if(iva)
//			  return String.valueOf(b_iva.doubleValue()).replace(".","/").replace(",", ".").replace("/", ",");
//		  else
//			  return String.valueOf(imp.doubleValue()).replace(".","/").replace(",", ".").replace("/", ",");
//	  }else
//		  return null;
//  }
  
  public static BigDecimal getImponibileoIVA(String costo_analisi, boolean iva) {
	  BigDecimal imp = BigDecimal.ZERO;
	  BigDecimal b_iva = BigDecimal.ZERO;
	  if(NumberUtils.isNumber(costo_analisi)) {
		  BigDecimal x = NumberUtils.createBigDecimal(costo_analisi);
		  if(x!=BigDecimal.ZERO) {
			  BigDecimal a = new BigDecimal("1.22"); 
			  imp = x.divide(a, 2);
			  b_iva = x.subtract(imp);
		  }
		  if(iva)
			  return b_iva;
		  else
			  return imp;
	  }else
		  return null;
  }
}