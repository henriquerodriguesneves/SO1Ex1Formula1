package controller;

import java.util.concurrent.Semaphore;

public class ThreadTreinoFormula1 extends Thread {
	
	private Semaphore semaforoPista;
	private Semaphore semaforoCarro;
	private Semaphore semaforoTempo;
	int numeroEscuderia;
	int numeroCarro;
	private static double tempo[] = new double[15];
	private static int numCarro[] = new int [15];
	private static int numEscuderia[] = new int [15];
	static int indice;
	
	public ThreadTreinoFormula1(int numeroEscuderia, int numeroCarro, Semaphore semaforoPista,
			Semaphore semaforoCarro, Semaphore semaforoTempo) {
				this.numeroCarro = numeroCarro;
				this.numeroEscuderia = numeroEscuderia;
				this.semaforoCarro = semaforoCarro;
				this.semaforoPista = semaforoPista;
				this.semaforoTempo = semaforoTempo;
			}
	
	public void run() {
		
		try {
			semaforoPista.acquire();
			semaforoCarro.acquire();
			treino();
			semaforoTempo.acquire();
			classificacao();
			if (indice == 14) {
				gridLargada();
			}
		} catch (InterruptedException e){
			e.printStackTrace();
		} finally {
			semaforoPista.release();
			semaforoCarro.release();
			semaforoTempo.release();
		}
		
	}
	

	private void treino() {
		int distanciaTotal = 3000;
		int distanciaPercorrida = 0;
		int deslocamento;
		double menorTempo = 1000;
		indice++;
		
		System.out.println("O carro " + numeroCarro + " da Escuderia " + numeroEscuderia + 
				" está na pista agora.");
		for (int volta = 0; volta < 3; volta++) {
			double tempoInicial = System.nanoTime();
			while (distanciaPercorrida < distanciaTotal) {
				deslocamento = (int)((Math.random() * 101) + 200);
				distanciaPercorrida = distanciaPercorrida + deslocamento;
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			double tempoFinal = System.nanoTime();
			double tempoTotal = tempoFinal - tempoInicial;
			tempoTotal = tempoTotal / Math.pow(10, 9);
			System.out.println("O carro " + numeroCarro + " da Escuderia " + numeroEscuderia + 
					" marcou o tempo " + tempoTotal + " na volta " + volta);
			if (menorTempo > tempoTotal) {
				menorTempo = tempoTotal;
			}
		}
		
		System.out.println("A Escuderia --> " + numeroEscuderia + " fez o melhor tempo com o carro " 
				+ numeroCarro + ". Tempo: " + menorTempo + "s");
		tempo[indice] = menorTempo;
		numCarro[indice] = numeroCarro;
		numEscuderia[indice] = numeroEscuderia;
	}
	
	private void classificacao() {
		
	}
	
	private void gridLargada() {
		int carroAuxiliar;
		int escuderiaAuxiliar;
		double tempoAuxiliar;
		int tamanho = tempo.length;
		
		for (int a = 0; a < tamanho; a++) {
			for (int b = a + 1; b < tamanho; b++){
				if (tempo[a] > tempo[b]) {
					carroAuxiliar = numCarro[a];
					escuderiaAuxiliar = numEscuderia[a];
					tempoAuxiliar = tempo[a];
					numCarro[a] = numCarro[b];
					numEscuderia[a] = numEscuderia[b];
					tempo[a] = tempo[b];
					numCarro[b] = carroAuxiliar;
					numEscuderia[b] = escuderiaAuxiliar;
					tempo[b] = tempoAuxiliar;
				}
			}
		}
		
		System.out.println("\n>>>>>>>>>> Grid de Largada <<<<<<<<<<\n");
		
		for (int i = 0; i < 14; i++) {
			System.out.println((i+1) + "° lugar --> Carro: " + numCarro[i] + " da Escuderia: " 
					+ numEscuderia[i] + " fez o tempo: " + tempo[i]);
		}
		
	}

}
