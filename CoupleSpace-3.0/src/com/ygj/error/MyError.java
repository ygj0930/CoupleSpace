package com.ygj.error;

public class MyError extends Error {
	 protected String key;
	 protected Object[] args;
	 
	 public MyError(String key)
	    {
	        super(key);
	        this.key = key;
	    }
		
	 
	    public MyError(String key, Throwable cause)
	    {
	        super(key, cause);
	        this.key = key;
	    }
	
	    public MyError(String key,  Object ... args)
	    {
	        super(key);
	        this.key = key;
	        this.args = args;
	    }
		

	    public MyError(String key, Throwable cause, Object ... args)
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
