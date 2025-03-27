package controller;

import java.util.concurrent.Semaphore;

public class ThreadCozinheiro extends Thread {

	private int id;
	private String prato;
	private int tipoPrato;
	private Semaphore semaphore;

	public ThreadCozinheiro(Semaphore semaphore) {
		this.id = (int) getId();
		if (this.id % 2 == 0) {
			this.prato = "Lasanha a Bolonhesa";
			this.tipoPrato = 0;
		} else {
			this.prato = "Sopa de Cebola";
			this.tipoPrato = 1;
		}
		this.semaphore = semaphore;
	}

	@Override
	public void run() {

		cozinhar();
		try {
			semaphore.acquire();
			entregar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

	}

	public void cozinhar() {
		int tempoCozimento;

		if (tipoPrato == 0) {
			tempoCozimento = (int) (Math.random() * 601) + 600;
		} else {
			tempoCozimento = (int) (Math.random() * 301) + 500;
		}

		int tempoPercorrido = 0;
		
		while(tempoPercorrido < tempoCozimento) {
			
			System.out.println("O prato "+prato+" já está "+String.format("%.2f", ((double)tempoPercorrido / tempoCozimento * 100))+"% cozido");
			
			try {
				
				tempoPercorrido+=100;

				sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("O prato "+prato+" foi finalizado!");
	}

	public void entregar() {
		System.out.println("O jogador está entregando o prato: " + this.prato + "!");
		try {
			sleep(500);
			System.out.println("O jogador entregou o prato: " + this.prato + "!");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
