package jchart2dTests;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;


public class PortReader {
		SerialPort[] portList;
	  public PortReader(){
		 portList = SerialPort.getCommPorts();
		 for(final SerialPort sp:portList){
			 System.out.println(sp.getSystemPortName());
			 if(sp.getSystemPortName().equals("COM2")){
				 try{
				 System.out.println(sp.openPort());
				// sp.setComPortParameters(9600, 8, 1, 0); //(baudrate,databits,stopbits,parity)
				 sp.addDataListener(new SerialPortDataListener(){

					@Override
					public int getListeningEvents() {
						return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
					}

					@Override
					public void serialEvent(SerialPortEvent event) {
						if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
					         return;
					      byte[] newData = new byte[sp.bytesAvailable()];
					      int numRead = sp.readBytes(newData, newData.length);
					      ByteBuffer bb = ByteBuffer.wrap(newData);
					      System.out.println("Read " + new String( newData, Charset.forName("UTF-8")) + " bytes.");
					}
					 
				 });
				 System.out.println(sp.bytesAvailable());
				// sp.closePort();
				 } catch(Exception e){
					 System.out.println(e.getStackTrace());
				 }
			 }
		 }
	  }
}
