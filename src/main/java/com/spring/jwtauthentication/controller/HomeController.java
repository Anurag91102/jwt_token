package com.spring.jwtauthentication.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;	
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.spring.jwtauthentication.config.CustomUserDetailsService;
import com.spring.jwtauthentication.config.JwtTokenUtil;
import com.spring.jwtauthentication.exception.TokenGetExpired;
import com.spring.jwtauthentication.model.Employee;
import com.spring.jwtauthentication.model.EmployeeDTO;
import com.spring.jwtauthentication.model.RefreshToken;
import com.spring.jwtauthentication.payloads.JwtReponse;
import com.spring.jwtauthentication.payloads.JwtRequest;
import com.spring.jwtauthentication.payloads.Response;
import com.spring.jwtauthentication.repo.RefreshTokenRepo;
import com.spring.jwtauthentication.service.EmailService;
import com.spring.jwtauthentication.service.EmployeeService;
import com.spring.jwtauthentication.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class HomeController 
{
	@Value("${uploadDir}")
	private String uploadDirectory;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping({"/home","/"})
	public String home()
	{
		return "Welcome to first api";
	}
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody Employee employee,HttpSession session)
	{
		System.out.println(employee.getPassword());
//		Employee DataEmail = employeeService.findByEmail(employee.getEmail());
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRole("ROLE_USER");
		employee.setIsVerified(0);
		employee.setImagePath("default.png");
		
		String otp = emailService.generateOtp();
		
		emailService.sendEmail(employee.getEmail(),otp);
		
		session.setAttribute("generatedOtp", otp);
		session.setAttribute("employeeDetails", employee);
		employeeService.save(employee);
		Response response = new Response(1,"OTP send! Please verify your otp");
		return  new ResponseEntity<>(response,HttpStatus.OK);	
	}
	
	@PostMapping("/verify")
	public ResponseEntity<Response> verifyAccount(@RequestBody Map<String, String> requestBody ,HttpSession session)
	{
		
		String generatedOtp = (String) session.getAttribute("generatedOtp");
		Employee employeeDetails = (Employee) session.getAttribute("employeeDetails");	
		System.out.println(generatedOtp);
		if(requestBody.get("otp").equals(generatedOtp))
		{
			employeeDetails.setIsVerified(1);
			employeeService.save(employeeDetails);
			session.invalidate();	
			Response response = new Response(1, "OTP verified successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else
		{
			 Response response = new Response(0, "Invalid OTP");
			 return new ResponseEntity<>(response, HttpStatus.OK);
		 
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody JwtRequest jwtRequest) throws Exception
	{
		Employee employeeDetails = employeeService.findByEmail(jwtRequest.getEmail());
		if(employeeDetails==null) 
		{
			Response response = new Response(0,"Invalid Email or Password",new ArrayList<>());
			return  new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		else if(employeeDetails.getIsVerified() == 1)
		{
			authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
			UserDetails userDetails =  customUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
			String token = jwtTokenUtil.generateToken(userDetails);
			System.out.println(jwtRequest.getEmail());
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(jwtRequest.getEmail());
			Employee employee = employeeService.findByEmail(jwtRequest.getEmail());
			
			 Map<String, Object> responseData = new HashMap<>();
			 responseData.put("email", employee.getEmail());
		     responseData.put("fname", employee.getFname());
		     responseData.put("lname", employee.getLname());
		     responseData.put("phone", employee.getPhone());
			return ResponseEntity.ok(new JwtReponse(1,"Logged In Successfully",responseData,token,refreshToken.getRefreshToken()));	
		}
		else
		{
			Response response = new Response(0,"You are not verified User",new ArrayList<>());
			return  new ResponseEntity<Response>(response,HttpStatus.OK);
		}
	}
	
	@PostMapping("/profile")
	public String userdashboard(Principal p)
	{
		Employee employee = employeeService.findByEmail(p.getName());
		return "Welcome, "+employee.getFname();
	}

	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshJwtToken(@RequestBody Map<String, String> requestBody) throws TokenGetExpired
	{
		String refreshToken = requestBody.get("refreshToken");
		if(refreshToken == null || refreshToken == "")
		{
			Response response = new Response(0,"Please enter value for the field",new ArrayList<>());
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		RefreshToken  refreshtoken = refreshTokenService.verifyRefreshToken(refreshToken);
		Employee employee = refreshtoken.getEmployee();
		UserDetails userDetails =  customUserDetailsService.loadUserByUsername(employee.getEmail());
		String token = jwtTokenUtil.generateToken(userDetails);
		 Map<String, Object> responseData = new HashMap<>();
		 responseData.put("email", employee.getEmail());
	     responseData.put("fname", employee.getFname());
	     responseData.put("lname", employee.getLname());
	     responseData.put("phone", employee.getPhone());
	     return ResponseEntity.ok(new JwtReponse(1,"JWT Token Generated Successfully",responseData,token,refreshtoken.getRefreshToken()));	
//		return ResponseEntity.ok(new JwtReponse(1,"JWT Token Generated Successfully",token,refreshtoken.getRefreshToken(),employee.getEmail(),employee.getFname(),employee.getLname(),employee.getPhone()));	
	}
	
	public void authenticate(String email,String password) throws Exception
	{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		try 
		{
			authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e)
		{
			throw new BadCredentialsException("Invalid Credentials");
		}
	}

	
	@PostMapping("/view")
	public ResponseEntity<?> viewById(HttpServletRequest request,Principal p)
	{
		Optional<Employee> employee = Optional.of(employeeService.findByEmail(p.getName()));
		if(employee.isPresent())
		{
			Employee employeeDetails = employeeService.findByEmail(p.getName());
			String url = "http://localhost:8090/images/"+employeeDetails.getImagePath();
			employeeDetails.setImagePath(url);
			
			Response response = new Response(1,"Record Found",employeeDetails);
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		else
		{
			Response response = new Response(0,"No Such Record Found",new ArrayList<>());
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateById(@Valid @ModelAttribute EmployeeDTO employeeDTO,Principal p) throws IllegalStateException, IOException 
	{
		Employee employeeDetails = employeeService.findByEmail(p.getName());
		if(employeeDTO.getProfileimage()!=null) 
		{
			MultipartFile image = employeeDTO.getProfileimage();	
			if (image.isEmpty()) 
			{
				Response response = new Response(0,"Please select an image to upload.",new ArrayList<>());
	            return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	        }
	        // Create the upload directory if it doesn't exist
	        File uploadDir = new File(uploadDirectory);
	        if (!uploadDir.exists()) 
	        {
	            uploadDir.mkdirs();
	        }
	        String originalName = image.getOriginalFilename();
	        String fileExtension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
	        if(!fileExtension.equals(".jpg") && !fileExtension.equals(".png") && !fileExtension.equals(".jpeg"))
	        {
	        	Response response = new Response(0, "Invalid file format. Only jpg ,png and jpeg are allowed.", new ArrayList<>());
	        	return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	        }
	        String timestamp = String.valueOf(System.currentTimeMillis());
	        String newFileName = "user_"+employeeDetails.getId()+"_"+timestamp+fileExtension;
	        String filePath = uploadDirectory + File.separator + newFileName;
	        System.out.println(filePath);
	        image.transferTo(new File(filePath));
	        employeeDetails.setImagePath(newFileName);
		}
		if(employeeDTO.getFname() != null)
		{
			employeeDetails.setFname(employeeDTO.getFname());
		}
		if(employeeDTO.getLname() !=null)
		{
			employeeDetails.setLname(employeeDTO.getLname());
		}
		employeeService.save(employeeDetails);
		employeeDetails.setImagePath("http://localhost:8090/images/"+employeeDetails.getImagePath());
		Response response = new Response(1,"Updated Successfully",employeeDetails);
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}

//	dummy image checker
//	@PostMapping("/image")
//	public String imageUpload(@RequestParam("image") MultipartFile image) throws IllegalStateException, IOException
//	{
//		if (image.isEmpty()) 
//		{
//            return "Please select a file to upload.";
//        }
//        // Create the upload directory if it doesn't exist
//        File uploadDir = new File(uploadDirectory);
//        if (!uploadDir.exists()) 
//        {
//            uploadDir.mkdirs();
//        }
//        
//        String userId = "user123";
//        String fileName = image.getOriginalFilename();
//        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        
//        String newFileName = "user_"+userId+"_"+timestamp+fileExtension;
//        
//        String filePath = uploadDirectory + File.separator + newFileName;
//        image.transferTo(new File(filePath));
//        System.out.println(filePath);
//        return "http://localhost:8090/images/"+newFileName;
//	}
	
	
	
}


