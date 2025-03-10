package com.base.api.api_base_security.config.security.authorization;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.persistence.entity.security.Operation;
import com.base.api.api_base_security.persistence.repository.security.OperationRepository;
import com.base.api.api_base_security.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OperationRepository operationRepository;
    @Autowired UserService userService;

    @Override
    @Nullable
    public AuthorizationDecision check(Supplier<Authentication> authentication, 
    RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url,httpMethod);
        if(isPublic){
            return new AuthorizationDecision(true);                 
        }
        boolean isGranted = isGranted(url,httpMethod,authentication.get());
        return new AuthorizationDecision(isGranted);     
    }
        
    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if(authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)){
            throw new AuthenticationCredentialsNotFoundException("User not logged in.");
        }
        List<Operation> operations = obtainedOperations(authentication);
        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));
        log.info("IsGranted: "+isGranted);
        return isGranted;
    }

    private boolean isPublic(String url,String httpMethod){
        List<Operation> publicAccessEndpoints = operationRepository.findByPublicAccess();
        boolean isPublic = publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));
        log.info("IsPublic: "+isPublic);
        return isPublic;
    }
       
    private List<Operation> obtainedOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken =(UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        User user =  userService.findOneByUserName(username)
            .orElseThrow(()-> new ObjectNotFoundException("User not found. Username:"+username));
        return user.getRole().getPermissions().stream()
            .map(grantedPermission -> grantedPermission.getOperation())
            .collect(Collectors.toList());     
    }
        

    private String extractUrl(HttpServletRequest request){
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");
        return url;
    }

    private Predicate<? super Operation> getOperationPredicate(String url, String httpMethod) {
        return operationItem->{            
            String basePath = operationItem.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operationItem.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operationItem.getHttpMethod().equals(httpMethod);
        };
    }
    
}
