/*
 * Copyright (c) 2001-2023 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package gilberto_teste;


import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;



public class Gilberto extends Robot {

	boolean peek; // Não vire se houver um robô ali
	double moveAmount; //Quanto mover
	/**
	* correr: Mova-se pelas paredes
	*/
	public void run() {
		// Set colors
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.orange);
		setBulletColor(Color.cyan);
		setScanColor(Color.cyan);

		
		// Inicializa moveAmount com o máximo possível para este campo de batalha..
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Initialize peek para falso
		peek = false;

	
		// vire à esquerda para ficar de frente para uma parede.
		// getHeading() % 90 significa o restante de
		//getHeading() dividido por 90.
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Gire a arma para virar 90 graus à direita.
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Olhe antes de virar quando ahead() for concluído.
			peek = true;
			//Subir a parede
			ahead(moveAmount);
			//Não olhe agora
			peek = false;
			//Vira para a próxima parede
			turnRight(90);
		}
	}


		/**
		* onHitRobot: Afaste-se um pouco.
		*/
	public void onHitRobot(HitRobotEvent e) {
		// Se ele estiver na nossa frente, afaste-se um pouco.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // senão ele está atrás de nós, então avance um pouco
		else {
			ahead(100);
		}
	}

	/**
	 * onScannedRobot:  Fogo!
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		// Observe que scan é chamado automaticamente quando o robô está em movimento.
		// Ao chamá-lo manualmente aqui, garantimos que geraremos outro evento de varredura se houver um robô no próximo
		// parede, para que não comecemos a subir até que ela desapareça.
		if (peek) {
			scan();
		}
	}
}
