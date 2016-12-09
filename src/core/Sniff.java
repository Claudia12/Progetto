package core;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureFileOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.PacketCapture;



public class Sniff 
{


	public static void main(String[] args) throws CaptureDeviceLookupException 
	{

		ArrayList<String> schedeDiRete= new ArrayList<String>();
		String[] devs = PacketCapture.lookupDevices(); 
		for(int i = 0; i < devs.length ; i++)
		{
			schedeDiRete.add(devs[i].trim().split("\\s")[0]);
		}

		CommandLineParser parser = new DefaultParser();

		Options options = new Options();
		options.addOption("f",true,"Analisi da file pcap");
		options.addOption("i",true,"Sniff da intefaccia di rete");
		options.addOption("o",true,"Scegliere nome file csv su cui salvare l'output");
		options.addOption("h",false,"Schermata di aiuto");

		HelpFormatter formatter = new HelpFormatter();



		try {
			CommandLine line = parser.parse( options, args );

			if(line.hasOption("h") && !line.hasOption("f")  && !line.hasOption("i") && !line.hasOption("o"))
			{
				formatter.printHelp("Opzioni disponibili", options);
			}

			if( line.hasOption( "f" ) && line.hasOption( "i" ))
			{

				System.out.println("non e' possibile entrambe le opzioni");
				return;
			}
			if( line.hasOption( "f" ) && line.hasOption( "o" ))//
			{
				try {
					new SnifferPcap(line.getOptionValue("f"),line.getOptionValue("o"));
				} catch (CaptureFileOpenException e) {

					e.printStackTrace();
				} catch (CapturePacketException e) {
					e.printStackTrace();
				}
			}
			if( line.hasOption( "i" ) && line.hasOption( "o" ))
			{
				if(schedeDiRete.contains(line.getOptionValue("i")))
				{
					try {
						new Sniffer(line.getOptionValue("i"),line.getOptionValue("o"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (line.getOptionValue("i").equals("list"))
				{
					System.out.println("Usa una tra queste :");
					for(int i=0;i<schedeDiRete.size();i++)
						System.out.println(schedeDiRete.get(i));
				}
	 
				else
				{
					System.out.println("Nome scheda di rete non valida");
					System.out.println("Usa una tra queste :");
					for(int i=0;i<schedeDiRete.size();i++)
						System.out.println(schedeDiRete.get(i));
				}
			}
			if( line.hasOption( "f" ) && !line.hasOption( "o" ))//
			{
				try {
					new SnifferPcap(line.getOptionValue("f"));
				} catch (CaptureFileOpenException e) {
					e.printStackTrace();
				} catch (CapturePacketException e) {
					e.printStackTrace();
				}
			}
			if( line.hasOption( "i" ) && !line.hasOption( "o" ))//
			{
				if(schedeDiRete.contains(line.getOptionValue("i")))
				{
					try {
						new Sniffer(line.getOptionValue("i"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (line.getOptionValue("i").equals("list"))
				{
					System.out.println("Usa una tra queste :");
					for(int i=0;i<schedeDiRete.size();i++)
						System.out.println(schedeDiRete.get(i));
				}
				else
				{
					System.out.println("Nome scheda di rete non valida");
					System.out.println("Usa una tra queste :");
					for(int i=0;i<schedeDiRete.size();i++)
						System.out.println(schedeDiRete.get(i));
	
				}
			}   	

		}
		catch( ParseException exp ) {
			System.out.println( "Unexpected exception:" + exp.getMessage() );
		}
	}

}
