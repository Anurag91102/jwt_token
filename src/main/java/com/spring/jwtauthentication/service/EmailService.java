package com.spring.jwtauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String toEmail,String otp)
	{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("OTP for verification of account");
		mailMessage.setText("Your OTP is:"+otp);
		mailMessage.setFrom("anurag091102@gmail.com");
		javaMailSender.send(mailMessage);
	}
	
	public String generateOtp() 
    {	
    	 // Generate a random OTP (6-digit number)
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
