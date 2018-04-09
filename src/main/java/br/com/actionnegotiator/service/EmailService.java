package br.com.actionnegotiator.service;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.Transaction;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private InvestmentRuleService investimentRuleService;
	
	public void sendMailByTransaction(Transaction transaction) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(getText(transaction));
		message.setTo(transaction.getAccount().getEmail());
		message.setSubject(getSubject(transaction.getCreatedIn()));
		message.setFrom("action.negotiator@gmail.com");
		javaMailSender.send(message);
	}
	
	private String getText(Transaction transaction) {
		StringBuilder text = new StringBuilder();
		text.append("Ol�!\n\n");
		
		text.append("Realizamos uma ");
		text.append(transaction.getType());
		text.append(" para voc�, conforme combinado:\n\n");
		
		text.append("-Empresa: ");
		text.append(transaction.getCompany().getName());
		text.append("\n");	
		
		text.append("-Valor requisitado: ");
		text.append(investimentRuleService.getRequestedValue(transaction).setScale(2, RoundingMode.HALF_EVEN));
		text.append("\n");
		
		text.append("-Valor da transa��o: ");
		text.append(transaction.getValue().setScale(2, RoundingMode.HALF_EVEN));
		text.append("\n");
		
		text.append("-Quantidade de a��es negociadas: ");
		text.append(transaction.getQuantity().setScale(2, RoundingMode.HALF_EVEN));
		text.append("\n\n");
		
		text.append("Atenciosamente,\n");
		text.append("Equipe Negociador de A��es");
		return text.toString();
	}
	
	private String getSubject(Calendar createdIn) {
		StringBuilder subject = new StringBuilder();		
		subject.append("Transa��o realizada em ");	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		subject.append(sdf.format(createdIn.getTime()));
		
		return subject.toString();
	}

}
