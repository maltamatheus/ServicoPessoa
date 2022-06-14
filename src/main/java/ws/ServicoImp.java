package ws;

import javax.jws.WebService;

@WebService(endpointInterface = "ws.Servico")
public class ServicoImp implements Servico{

	@Override
	public Pessoa servico01(Pessoa pessoa) {
		Pessoa p = pessoa;
		return p;
	}

}
