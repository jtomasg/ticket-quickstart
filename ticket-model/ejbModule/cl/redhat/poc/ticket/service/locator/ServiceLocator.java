package cl.redhat.poc.ticket.service.locator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceLocator {

	private Map<String, Object> cache;
    private InitialContext ic;
    private static ServiceLocator me;
    
    static {
        try {
            me = new ServiceLocator();
        } catch (Exception se) {
            se.printStackTrace(System.err);
        }
    }

    private ServiceLocator() {
        try {
            ic = new InitialContext();
            cache = Collections.synchronizedMap(new HashMap<String, Object>());
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public static final ServiceLocator getInstance() {
        return me;
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    /**
     * Conecta con un EJB Remoto 
     * @param jndiHomeName
     * @return
     * @throws ServiceLocatorException
     */
    public Object doRemoteLookUp(String app, String module, String distinct, String bean, String interfaz)throws ServiceLocatorException{
    	Object ejbRemote = null;    
    	final String distinctName = "";
    	
    	String strJndiLookup = "ejb:" + app + "/" + module + "/" + distinctName + "/" + bean + "!" + interfaz;
    	
    	try {
            if (cache.containsKey(strJndiLookup)) {
                ejbRemote = (Object) cache.get(strJndiLookup);
            } else {            	      	            	            	                      	       
            	final Hashtable jndiProperties = new Hashtable();
            	
            	jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                Context ctx = new InitialContext(jndiProperties);
               // System.out.println("Got context");                
                ejbRemote = (Object) ctx.lookup(strJndiLookup);             	            	            	            	            	            	
                cache.put(strJndiLookup, ejbRemote);
            }
        } catch (NamingException ex) {
        	ex.printStackTrace();
            throw new ServiceLocatorException("JNDI "+strJndiLookup+ " incorrecto", ex.getCause());
        } catch (Exception e) {
        	e.printStackTrace();
            throw new ServiceLocatorException(e.getCause());
        }
        return ejbRemote;    	
    }

	
}
