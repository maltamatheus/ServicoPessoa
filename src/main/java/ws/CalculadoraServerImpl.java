package ws;

import javax.jws.WebService;

@WebService(endpointInterface = "ws.CalculadoraServer")
public class CalculadoraServerImpl implements CalculadoraServer{

	@Override
	public Long somar() {
		return 1l + 2l;
	}

}
