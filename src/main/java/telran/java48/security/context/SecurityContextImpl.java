package telran.java48.security.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import telran.java48.security.model.UserPr;

@Component
public class SecurityContextImpl implements SecurityContext {
	Map<String, UserPr> context = new ConcurrentHashMap<>();

	@Override
	public UserPr addUserSession(String sessionId, UserPr user) {
		return context.put(sessionId, user);
	}

	@Override
	public UserPr removeUserSession(String sessionId) {
		return context.remove(sessionId);
	}

	@Override
	public UserPr getUserBySessionId(String sessionId) {
		return context.get(sessionId);
	}

}
