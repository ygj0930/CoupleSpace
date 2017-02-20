package com.ygj.error;

public class MyException extends RuntimeException {
	    protected String key;
	    protected Object[] args;
	
	    public MyException(String key)
	    {
	        super(key);
	        this.key = key;
	    }
		
	    public  MyException(String key, Throwable cause)
	    {
	        super(key, cause);
	        this.key = key;
	    }
		

	    public  MyException(String key,  Object ... args)
	    {
	        super(key);
	        this.key = key;
	        this.args = args;
	    }
		
	
	    public  MyException(String key, Throwable cause, Object ... args)
	    {
	        super(key, cause);
	        this.key = key;
	        this.args = args;
	    }

	    public String getKey()
	    {
	        return key;
	    }

	    public Object[] getArgs()
	    {
	        return args;
	    }

}
