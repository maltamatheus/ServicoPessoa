package servidor;

import javax.xml.ws.Endpoint;

import ws.ServicoImp;

public class Servidor {

	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost:8080/Servico", new ServicoImp());
		
		System.out.println("Tá rodando o servidor");
	}

}
