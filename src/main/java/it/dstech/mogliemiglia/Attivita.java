package it.dstech.mogliemiglia;

import com.opencsv.bean.CsvBindByPosition;

public class Attivita {
	@CsvBindByPosition(position = 0)
	private String azione;
	@CsvBindByPosition(position = 1)
	private int punteggio;
	@CsvBindByPosition(position = 2)
	private int livello;

	public Attivita() {
	}
 
	public Attivita(String azione, int punteggio, int livello) {
		this.azione = azione;
		this.punteggio = punteggio;
		setLivello(livello);
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + (azione == null ? 0 : azione.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attivita other = (Attivita) obj;
		if (azione == null) {
			if (azione != null)
				return false;
		} else if (!azione.equals(azione))
			return false;
		return true;
	}

	public int getLivello() {
		return livello;
	}

	public void setLivello(int livello) {
		this.livello = livello;
	}

	public String toString() {
		return "Attivita [azione=" + azione + ", punteggio=" + punteggio + ", livello=" + livello + "]";
	}
}