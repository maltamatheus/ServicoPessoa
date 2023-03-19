package servidor;

import javax.xml.ws.Endpoint;

import ws.CalculadoraServerImpl;

public class CalculadoraServerPublisher {

	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:8080/calc", new CalculadoraServerImpl());
		System.out.println("Rodando a Calculadora");
		
	}

}
