package bootcamp.micsrv.decprg.order.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import feign.FeignException;

public class FeignClientExceptionHandler {
	   private static final String productExceptionLiteral = "Product";
	   private static final String userExceptionLiteral = "User";
	   private static final String extactRegex = ".+(\\[.+\\])$";
	   public static ResponseEntity<?> handleFeignException(FeignException ex) {
	        if (ex.status() == HttpStatus.NOT_FOUND.value()) {
	        	String extractedCustomMsg = extractCustomMessage(ex.getMessage(),extactRegex);
	            if(extractedCustomMsg != null && (extractedCustomMsg.contains(productExceptionLiteral) || extractedCustomMsg.contains(userExceptionLiteral)))
	            {
	                return new ResponseEntity<>(extractedCustomMsg,HttpStatus.NOT_FOUND);
	            }
	            else
	            {
	            	return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
	            } 
	        } else if (ex.status() == HttpStatus.BAD_REQUEST.value()) {
            		return new ResponseEntity<>("Bad Request",HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>("Internal Server Error",HttpStatus.NOT_FOUND);
	    }
	   
	   private static String extractCustomMessage(String fullMessage,String regex) {
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(fullMessage);
	        if (matcher.find()) {
	            return matcher.group(1).trim();
	        } else {
	            return null;
	        }
	    }
}
