package guru.springframework.services.security;

import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 1/6/16.
 */
@Aspect
@Component // register this class as a spring component (a bean).
public class LoginAspect {

    // take in any data type (via the wild card).
    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate(){

    }

    // capture on the way in the authentication object that is being passed in (it will be inspected).
    @Before("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logBefore(Authentication authentication){

        System.out.println("This is before the Authenticate Method: authentication: " + authentication.isAuthenticated());
    }

    // runs after the doAuthenticate method.
    // returning: pass back an authenticated object.
    @AfterReturning(value = "guru.springframework.services.security.LoginAspect.doAuthenticate()",
            returning = "authentication")
    public void logAfterAuthenticate( Authentication authentication){
        System.out.println("This is after the Authenticate Method authentication: " + authentication.isAuthenticated());
    }

    @AfterThrowing("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenicationException(Authentication authentication){
        String userDetails = (String) authentication.getPrincipal();
        // log the user name.
        System.out.println("Login failed for user: " + userDetails);
    }
}
