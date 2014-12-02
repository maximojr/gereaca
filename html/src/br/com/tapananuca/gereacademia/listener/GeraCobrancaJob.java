package br.com.tapananuca.gereacademia.listener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.tapananuca.gereacademia.service.PagamentoService;

public class GeraCobrancaJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("---- executando o job maledeto");
		
		final PagamentoService pagamentoService = new PagamentoService();
		pagamentoService.gerarCobrancaMensal();
		
		System.out.println("---- fim do job maledeto");
	}

}
