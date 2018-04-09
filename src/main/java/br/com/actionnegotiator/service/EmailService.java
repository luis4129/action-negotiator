package br.com.actionnegotiator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.actionnegotiator.model.InvestmentRule;
import br.com.actionnegotiator.model.Transaction;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setText(text);
        message.setTo(to);
        message.setSubject(subject);
        message.setFrom("action.negotiator@gmail.com");

        /*try {
        	javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
	
	public void notify(Transaction transaction) {		
		String email = transaction.getAccount().getEmail();
		String subject = getSubject(transaction.getCreatedIn());
		String text = getText(transaction);		
		this.sendMail(email, subject, text);
	}
	
	public String getText(Transaction transaction) {
		StringBuilder text = new StringBuilder();
		text.append("Ol�!\n\n");
		
		text.append("Realizamos uma ");
		text.append(transaction.getType());
		text.append(" para voc�, conforme combinado:\n\n");
		
		text.append("-Empresa: ");
		text.append(transaction.getCompany().getName());
		text.append("\n");	
		
		text.append("-Valor requisitado: ");
		text.append(getRequestedValue(transaction).setScale(2, RoundingMode.HALF_EVEN));
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
	
	public String getSubject(Calendar createdIn) {
		StringBuilder subject = new StringBuilder();		
		subject.append("Transa��o realizada em ");	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		subject.append(sdf.format(createdIn.getTime()));
		
		return subject.toString();
	}
	
	public BigDecimal getRequestedValue(Transaction transaction) {
		for (InvestmentRule investmentRule : transaction.getAccount().getInvestmentRules()) {
			if (investmentRule.getCompany().getId().equals(transaction.getCompany().getId())) {
				return transaction.getType().getValue(investmentRule);
			}
		}
		return BigDecimal.ZERO;
	}

}
