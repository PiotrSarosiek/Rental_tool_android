package com.highcode.Rental_tool.aspect;

import com.highcode.Rental_tool.persistance.entity.Apartment;
import com.highcode.Rental_tool.persistance.entity.User;
import com.highcode.Rental_tool.persistance.enumerated.UserRole;
import com.highcode.Rental_tool.web.exception.ExceptionType;
import com.highcode.Rental_tool.web.exception.RentalToolException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizeApartmentAspect {

    @Before("execution(* com.highcode.Rental_tool.web.controller.StableBillController.*(..)) ||" +
            "execution(* com.highcode.Rental_tool.web.controller.TenantController.*(..))")
    public void authorizeApartment(JoinPoint joinPoint){
        User user = (User) joinPoint.getArgs()[0];
        Apartment apartment = (Apartment) joinPoint.getArgs()[1];

        if(!apartment.getLandlord().getId().equals(user.getId()) && !user.getRole().equals(UserRole.ADMIN))
            throw new RentalToolException(ExceptionType.ACCESS_FORBIDDEN_EXCEPTION, HttpStatus.FORBIDDEN);
    }
}
