package support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import object.Connessione;
import object.Flusso;

import com.csvreader.CsvWriter;

public class WriterCSV
{

	public WriterCSV()
	{
		
	}
	
	public void writeInCSV(Connessione c,Flusso f,String indicatore, double risultato,double tempoarrivo)
	{
		boolean alreadyExists =new File(c.getNomeFile()).exists();
		CsvWriter csvOutput;
		try {
			csvOutput = new CsvWriter(new FileWriter(c.getNomeFile(), true), ';');
			if (!alreadyExists)
			{
				
				csvOutput.write("TimeStamp");
				csvOutput.write("Indicatore");
				csvOutput.write("IDFlusso");
				csvOutput.write("Risultato ottenuto");

				csvOutput.endRecord();
				csvOutput.flush();
			}
			if(indicatore.equals("RWIN") || indicatore.equals("Byte/sec"))
			{
				csvOutput.write(String.format("%.7f",tempoarrivo));
				csvOutput.write(indicatore);
				csvOutput.write(f.IDFlusso());
				csvOutput.write(Integer.toString((int) risultato));
			}
			else
			{
				csvOutput.write(String.format("%.7f",tempoarrivo));
				csvOutput.write(indicatore);
				csvOutput.write(f.IDFlusso());
				csvOutput.write(String.format( "%.7f",risultato));

			}

			csvOutput.endRecord();
			csvOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
