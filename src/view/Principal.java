package view;

import java.util.concurrent.Semaphore;

import controller.ThreadTreinoFormula1;


public class Principal {

	public static void main(String[] args) {
		int permissaoPista = 5;
		int permissaoTempo = 1;
		Semaphore semaforoPista = new Semaphore(permissaoPista);
		Semaphore[] semaforoCarro = new Semaphore[7];
		Semaphore semaforoTempo = new Semaphore(permissaoTempo);
		int numCarro = 0;
		
		for (int numEscuderia = 0; numEscuderia < 7; numEscuderia++) {
			semaforoCarro[numEscuderia] = new Semaphore(1);
			for (int i = 0; i < 2; i++){
				numCarro++;
				Thread tCarro = new ThreadTreinoFormula1 ((numEscuderia + 1), numCarro, semaforoPista, semaforoCarro[numEscuderia], semaforoTempo);
				tCarro.start();
			}
		}

	}

}
