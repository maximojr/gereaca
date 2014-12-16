package br.com.tapananuca.gereacademia.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.tapananuca.gereacademia.job.GeraCobrancaJob;
import br.com.tapananuca.gereacademia.service.PagamentoService;
import br.com.tapananuca.gereacademia.service.Service;

@WebListener
public class InicialListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println("----------- Atencao Kreuzeback, vai comecar a putaria.");
		
		try {
			
			Service.iniciarPool(3);
			
			final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			scheduler.start();
			
			final JobDetail jobDetail = JobBuilder.newJob(GeraCobrancaJob.class).build();
			
			final Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(1, 0, 0))
                //.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
                .endAt(null)
                .build();
			
			scheduler.scheduleJob(jobDetail, trigger);
			
			//just to be sure xD
			new PagamentoService().gerarCobrancaMensal();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		Service.fecharConexoes();
		
		System.out.println("----------- FLWS!");
	}

}
