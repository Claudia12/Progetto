package core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.sourceforge.jpcap.capture.CaptureFileOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;



public class Sniff 
{


	public static void main(String[] args) 
	{
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("f",true,"leggere da file");
		options.addOption("i",true,"Sniff da intefaccia di rete");
		options.addOption("o",true,"Scrittura su file csv");

		try {
		    CommandLine line = parser.parse( options, args );

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
		        try {
						new Sniffer(line.getOptionValue("i"),line.getOptionValue("o"));
					} catch (Exception e) {
						e.printStackTrace();
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
		        try {
						new Sniffer(line.getOptionValue("i"));
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }    
		    
			}
		catch( ParseException exp ) {
		    System.out.println( "Unexpected exception:" + exp.getMessage() );
		}
	}
	
}
