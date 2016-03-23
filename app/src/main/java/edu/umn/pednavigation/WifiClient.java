/*
 * WifiClient.java
 * Wireless communication with DB server & traffic controller
 * Created on Friday, May 28, 2010, 10:19:32 AM
 */

/*
 * PI: Chen-Fu Liao
 * University of Minnesota
 * Department of Civil Engineering
 * Minnesota Traffic Observatory (MTO)
 * 500 Pillsbury Drive SE
 * Minneapolis, MN 55455
 */

package edu.umn.pednavigation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WifiClient {

	/* Stores the response received from the web-server */
	private String response = "" ;

	/* Stores the phase of the traffic signal at the current intersection */
	private short phase;

	/* Indicates whether the user can cross or not */
	private char status;

	private short signalInfo;

	/* time left in the current phase */
	private int timeleft;

	/* indicates whether response from web-server has been received or not */
	protected boolean rcvd;

	// Chen-Fu 4/10/12
	private String signalState_Timestamp ;
	
	/* Get the response received from the web-server in text format */
	public static String GetText(InputStream in) {
		String text = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			text = sb.toString();
		} catch (Exception ex) {

		} finally {
			try {

				in.close();
			} catch (Exception ex) {
			}
		}
		return text;
	}

	/* CODE CHANGE START- Sowmya: Send http post request to server*/
	/*
	 * Initializing the Wificlient for a particular website, it gets all the
	 * fields required by calling the parser
	 */
	/* CODE CHANGE by niveditha: 04/04/2012
	 * Added another parameter (press_push_button) to the function press_push_button
	 */
	public void httpConnect(String url, String bluetoothId, String dir, boolean press_push_button) {
//TODO:Update API
/*		Log.e("NVG", "WIFI");
		Log.e("ffff", "WIFI here");
		rcvd = false;
		response = "";
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 60000);
		HttpConnectionParams.setSoTimeout(httpParameters, 60000);

		HttpConnectionParams.setTcpNoDelay(httpParameters, false);
		HttpClient client = new DefaultHttpClient(httpParameters);
		Log.e("NVG", "send req");
		HttpPost httppost = new HttpPost(url);

		// Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("BLUETOOTHID", bluetoothId));
		nameValuePairs.add(new BasicNameValuePair("DIR", dir));
		
		*//* Following pair added by Niveditha - 04/04/2012 *//*
		nameValuePairs.add(new BasicNameValuePair("PRESSPUSHBUTTON",
				new Boolean(press_push_button).toString()));

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Log.e("NVG", "get resp");
			HttpResponse resp = client.execute(httppost);
			Log.e("RESP", "resp: " + resp.getStatusLine().getStatusCode());
			*//* process response *//*
			if (resp.getStatusLine().getStatusCode() == 200) {
				Log.e("SUCCESS", "getStatusCode 200\n");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resp.getEntity().getContent(),
								"UTF-8"));

				String line = null;
				String[] temp;
				String[] temp1;
				String delimiter = ",";

				// Chen-Fu 4/10/12
				//LogData myLog = new LogData();
				//myLog.WriteDataLogtoFile("--- Wi-Fi Client ---");

				signalState_Timestamp = reader.readLine(); // date & timestamp, sample output "Date = 2012-04-10 14:08:15.7"

				//myLog.WriteDataLogtoFile("--- Wi-Fi Client: " + signalState_Timestamp) ;
				
				line = reader.readLine();
				line.replaceAll("^\\s+", "");
				line.replaceAll("\\s+$", "");

				if (line != null) {
					temp = line.split(delimiter);
					for (int index = 0; index < temp.length; index++) {
						temp1 = temp[index].split("=");

						if (temp1.length >= 2) {
							temp1[0].replaceAll(" ", "");
							temp1[1].replaceAll(" ", "");

							if (temp1[0].contains("SignalState"))
								signalInfo = Short.valueOf(temp1[1]);

							if (temp1[0].contains("TimeLeft"))
								timeleft = Integer.valueOf(temp1[1]);

							if (temp1[0].contains("Phase"))
								phase = Short.valueOf(temp1[1]);
						}
					}
					Log.e("DEBUG", "signalInfo: " + signalInfo);
					Log.e("DEBUG", "timeleft: " + timeleft);
					Log.e("DEBUG", "phase: " + phase);
				}
				if (phase == 0) {
					status = 'N';
				} else {
					short BITCHK = 0x01;
					BITCHK = (short) (BITCHK << (phase - 1));
					
					if ((signalInfo & BITCHK) != 0) {
						status = 'W';// walk
					} else {
						status = 'D';// Don't walk
					}
				}
			} else {
				Log.e("ERROR", "" + "Invalid HTTP response: "
						+ resp.getStatusLine().getStatusCode());
				status = 'N';
				phase = 0;
			}
		} catch (ClientProtocolException e) {
			response = "no";
			status = 'N';
			Log.e("foundd", "a");
			e.printStackTrace();
		} catch (IOException e) {
			response = "no";
			Log.e("foundd", "b");
			status = 'N';
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		rcvd = true;*/
	}
	/* CODE CHANGE END*/

	public char getStatus() {
		return status;
	}

	public int getTime() {
		return timeleft;
	}
	
	// Chen-Fu added 4/10/12
	public String getSignalState_Timestamp() {
		String retStr ;
		int idx = signalState_Timestamp.indexOf("Date = ") ;
		if (idx>=0) {
			retStr = signalState_Timestamp.substring(7) ;
		} else {
			retStr = signalState_Timestamp ;
		}
		return retStr ;
	}
}