package com.itachi1706.minecrafttools.PingingUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.AsyncTask;

public class PingServer16 {
	
	private InetSocketAddress host;
    private int timeout = 7000;
    
    //Set Minecraft Server Address
    public void setAddress(InetSocketAddress host) {
        this.host = host;
    }
 
    //Get Minecraft Server Address
    public InetSocketAddress getAddress() {
        return this.host;
    }
 
    //Set Connect Timeout
    void setTimeout(int timeout) {
        this.timeout = timeout;
    }
 
    //Get Connect Timeout
    int getTimeout() {
        return this.timeout;
    }
    
    public StatusResponse fetchData() throws IOException {
    	
    	Socket socket = new Socket();
    	OutputStream outputStream;
        DataOutputStream dataOutputStream;
        InputStream inputStream;
 
        socket.setSoTimeout(this.timeout);
 
        socket.connect(host, timeout);
        
        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
 
        inputStream = socket.getInputStream();
 
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(b);
        
        /**
         * Sends following data
         * FE - Server Ping Packet ID
         * 01 - Server List Ping Payload
         * FA - Packet ID for Plugin Message
         * 00 0B - Length of string in short 2 bytes (Always 11)
         * 00 4D 00 43 00 7C 00 50 00 69 00 6E 00 67 00 48 00 6F 00 73 00 74 - the string "MC|PingHost" encoded as a UTF-16BE string
         * XX XX - length of the rest of the data, as a short. Compute as 7 + 2*len(hostname)
         * XX - protocol version - currently 73 (decimal)
         * XX XX - length of following string, in characters, as a short
         * ... - hostname the client is connecting to, encoded the same way as "MC|PingHost"
         * XX XX XX XX - port the client is connecting to, as an int.
         */
        
        handshake.writeByte(0xFE);
        handshake.writeByte(0x01);
        handshake.writeByte(0xFA);
        
        //Encoded "MC|PingHost" in UTF-16BE format
        byte[] mcphByteArray = {0x00,0x4D,0x00,
        		0x43,0x00,0x7C,0x00,0x50,0x00,
        		0x69,0x00,0x6E,0x00,0x67,0x00,
        		0x48,0x00,0x6F,0x00,0x73,0x00,0x74};
        handshake.writeShort(11);
        handshake.write(mcphByteArray);
        
        String hostnamer = new PingServer16Host().onPostExecute(this.host);
        //Length of rest of data
        handshake.writeShort(7+(2 * hostnamer.length()));
        
        //Write protocol version (73)
        handshake.writeByte(73);
        
        handshake.writeShort(hostnamer.length());
        handshake.write(hostnamer.getBytes("UTF-16BE"));
        
        //Write port number
        handshake.writeInt(this.host.getPort());
        
        //Send to server as a packet
        //dataOutputStream.writeShort(b.size());
        dataOutputStream.write(b.toByteArray());
        /**
         * Recieves following data
         * FF - Kick Packet
         * 00 23 - Length (short) of follwing packet (Ignore it)
         * 00 a7 00 31 00 00 - a UTF16 string
         * The remainder is null character (that is 00 00) delimited fields:
		 * Protocol version (e.g. 47)
		 * Minecraft server version (e.g. 1.4.2)
		 * Message of the day (e.g. A Minecraft Server)
		 * Current player count
		 * Max players
         */
        
        //Get Server Response
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        
        int packetID = dataInputStream.read();// Get packet ID
        
        //Check that Packet ID received is 0xFF
        if (packetID == -1) {
        	socket.close();
            throw new IOException("Premature end of stream.");
        }
        
        if (packetID != 0xFF) { //we want a Kick Packet
        	socket.close();
            throw new IOException("Invalid packetID");
        }
        
        //Get the length and ignore it
        short length = dataInputStream.readShort();
        
        byte[] bes = new byte[length*2];
        dataInputStream.readFully(bes);
        String decryptedResult = new String(bes, "UTF-16BE");
        String[] decryptedResults = decryptedResult.split("\u0000");
        dataOutputStream.close();
        outputStream.close();
        inputStream.close();
        socket.close();
        
        StatusResponse response = new StatusResponse(decryptedResults[3], Integer.parseInt(decryptedResults[4]), Integer.parseInt(decryptedResults[5]), decryptedResults[2], decryptedResults[1]);
		return response;
    }
    
    public class PingServer16Host extends AsyncTask<InetSocketAddress, Void, String>{
    	// Do the long-running work in here
		@Override
		protected  String doInBackground(InetSocketAddress... addr) {
			// Get Length
			return addr[0].getHostName();
		}

		public String onPostExecute(InetSocketAddress host) {
			// TODO Auto-generated method stub
			return host.getHostName();
		}
    }
    
  //Status Response Object Class
    public class StatusResponse {
        private String motds;
        private int onlineplayers;
        private int maxplayers;
        private Version versions;
 
        public StatusResponse(String motd, int onlineplayer, int maxplayer, String name, String protocol){
        	motds = motd;
        	onlineplayers = onlineplayer;
        	maxplayers = maxplayer;
        	versions = new Version(name, protocol);
        }
        
        public String getMOTD() {
            return motds;
        }
 
        public int getOnlinePlayers() {
            return onlineplayers;
        }
        
        public int getMaxPlayers() {
            return maxplayers;
        }
 
        public Version getVersion() {
            return versions;
        }   
        
    }
    
    
    //Version Number Object Class
    public class Version {
        private String name;
        private String protocol;
        
        public Version (String n, String p){
        	name = n;
        	protocol = p;
        }
 
        public String getName() {
            return name;
        }
 
        public String getProtocol() {
            return protocol;
        }
    }

}
