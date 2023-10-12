package telran.java48.security.context;

import telran.java48.security.model.UserPr;

public interface SecurityContext {
	UserPr addUserSession(String sessionId, UserPr user);
	
	UserPr removeUserSession(String sessionId);
	
	UserPr getUserBySessionId(String sessionId);
}
