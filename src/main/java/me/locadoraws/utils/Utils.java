/*
 * Classe criada para inserir métodos utilitários e comuns nas classes
 */
package me.locadoraws.utils;

/**
 * @author Alex Junior Rambo 
 */

public class Utils { 

	//Contrutor privado para evitar instanciamentos desnecessários
    private Utils() {
    }

    //Verifica se uma string é nula ou vazia
	public static boolean isNullOrEmpty(String value) {
		if (value == null || value.trim().isEmpty()) {
			return true; 
		}
		
		return false;
	}
	
	//Verifica se uma string pode ser convertida para int
	public static boolean isInteger(String value) {
		//Validação utilizando regex
		if (value.matches("\\d+")) {
			return true; 
		}
		
		return false;
	}
}