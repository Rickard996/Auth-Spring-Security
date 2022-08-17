package cl.RicardoC.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.RicardoC.models.User;
import cl.RicardoC.services.UserService;
import cl.RicardoC.validator.UserValidator;

@Controller
public class UserController {
	//importante este autowired sino no anda. Se puede usar el constructor igual
	@Autowired
	private UserService userService;

	private UserValidator userValidator;
	
	
	//no puedo poner mas de un constructor para que sea reconocido por Spring
//	public UserController(UserService userService) {
//		this.userService = userService;
//	}
	
	public UserController(UserService userService, UserValidator userValidator) {
		super();
		this.userService = userService;
		this.userValidator = userValidator;
	}

	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute(value = "user") User user) {
		return "registrationPage.jsp";
	}
	
	
	//Recuerde, cuando las credenciales son incorrectas, Spring Security 
	//redirecciona al cliente a la URL "/login?error". Además, cuando 
	//un cliente cierra sesión por medio del formulario en nuestro archivo
	//homePage.jsp, Spring Security lo redirecciona a la URL "/login?logout".
	
	
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model) {
		
		if(error!=null) {   //si hay parametro error
			model.addAttribute("logoutMessage", "Credenciales invalidas, Please try again");
		}
		if(logout!=null) {
			model.addAttribute("logoutMessage", "Logout Succesful!!");
		}
		return "loginPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute(value = "user") User user,
			BindingResult result, Model model, HttpSession session) {
		
		this.userValidator.validate(user, result);
		
		System.out.print("estoy acaaa");
		if(result.hasErrors()) {
			System.out.println("con errores");
			return "registrationPage.jsp";
		}else {
			
			System.out.println("sin errores");
			this.userService.saveWithUserRole(user);
			
			//this.userService.saveUserWithAdminRole(user);
			
			return "redirect:/login";
		}
	}
	
	//El objeto más básico en Spring Security es el SecurityContextHolder.
	//Aquí es donde Spring Security almacena los detalles del contexto 
	//actual de seguridad de la aplicación, como el principal que actualmente está usando la aplicación.
	
	
	//Nuestro método home acepta solicitudes GET con las URL "/" y "/home". 
	@RequestMapping(value = {"/","/home"})
	public String home(Principal principal, Model model) {
		// En nuestro ejemplo, el principal es el usuario actual conectado. 
		String username = principal.getName();
		//Después de una autenticación exitosa, podemos obtener el nombre de nuestro principal (usuario actual) 
		//a través del método .getName()
		
		
		model.addAttribute("currentUser", this.userService.findByUsername(username));
		return "homePage.jsp";
		
//		//para versiones anteriores de Spring
//		import org.springframework.security.core.context.SecurityContextHolder;
//		import org.springframework.security.core.userdetails.User;
//		// Por defecto, obtenemos el tipo de objeto, por lo que debemos convertirlo en un objeto UserDetails.
//		User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String name = user.getUsername(); 
		
	}
	
	
    // ruta del admin
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "adminPage.jsp";
    }
    
	
	
	
	
	
	
}
