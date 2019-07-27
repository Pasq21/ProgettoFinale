package it.dstech.mogliemiglia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GestioneMoglieMiglia {
	private List<Attivita> wifeActivity;
	private List<Attivita> husbandActivity;

	public static void main (String [] args) throws URISyntaxException, IOException {
		GestioneMoglieMiglia g = new GestioneMoglieMiglia();
		System.out.println(g.getListaAzioniMarito());
	}
	public GestioneMoglieMiglia() throws URISyntaxException, IOException {
		wifeActivity = new ArrayList<Attivita>();
		husbandActivity = new ArrayList<Attivita>();
		//URI uri = getClass().getClassLoader().getResource("src/main/resources/rewards.csv").toURI();
		//Path path = Paths.get(uri);
		//Path path = Paths.get(getClass().getClassLoader().getResource("src/main/resources/rewards.csv").toURI());
	
		File f = new File("C:\\Users\\Acer\\eclipse-workspace2\\ProgettoFinale\\src\\main\\resources\\rewards.csv");
		BufferedReader br = new BufferedReader(new FileReader(f));
		List<Attivita> lista = new ArrayList<Attivita>();
		String riga = br.readLine();
		while(riga != null) {
			String[] splitted = riga.split(",");
			Attivita toInsert = new Attivita(splitted[0].trim(), Integer.parseInt(splitted[1].trim()), Integer.parseInt(splitted[2].trim()));
			lista.add(toInsert);
			riga = br.readLine();
		}
		br.close();
		System.out.println(lista);
		//new CsvToBeanBuilder(new FileReader(f)).withType(Attivita.class).build()
		//		.parse();
		for (Attivita attivita : lista) {
			if (attivita.getPunteggio() > 0) {
				wifeActivity.add(attivita);
			} else {
				husbandActivity.add(attivita);
			}
		}
	}

	public List<Attivita> getListaAzioniMarito() {
		return husbandActivity;
	}

	public List<Attivita> getListaAzioniMoglie() {
		return wifeActivity;
	}

	public int checkValoreAzione(String azione) {
		int punteggio = 0;
		Attivita o = new Attivita(azione, punteggio, 1);
		if (wifeActivity.contains(o)) {
			punteggio = ((Attivita) wifeActivity.get(wifeActivity.indexOf(o))).getPunteggio();
		} else {
			punteggio = ((Attivita) husbandActivity.get(husbandActivity.indexOf(o))).getPunteggio();
		}
		return punteggio;
	}
}