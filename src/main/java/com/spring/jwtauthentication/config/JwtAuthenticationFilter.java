package com.spring.jwtauthentication.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String requestHeader =  request.getHeader("Authorization");
		String username = null;
		String token = null;
		
		if(requestHeader!=null && requestHeader.startsWith("Bearer"))
		{
			token = requestHeader.substring(7);
			try 
			{
				username = jwtTokenUtil.getUsernameFromToken(token);
			} 
			catch (IllegalArgumentException e) 
			{
				System.out.println("Unable to get JWT Token");
			} 
			catch (ExpiredJwtException e)
			{
				System.out.println("JWT Token has expired");
			}
			catch (SignatureException e) 
			{
				String errorMessage = "Bad Request Access Denied";
		        response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        response.setContentType("application/json");
		        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
		        return;
		    }
			catch(Exception e)
			{
				System.out.println("Something went wrong");
			}
		}
		else
		{
			logger.warn("Invalid Header Value");
		}

		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			if(jwtTokenUtil.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	
			}
			else
			{
				logger.info("validation fails");
			}
		}
		
		filterChain.doFilter(request, response);
	}
}
