package foo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Hello world!
 *
 */
public class Activator implements BundleActivator {

	public void start(BundleContext bundleContext) 
	{
		int a,b,c;
		a=1;
		b=5; 
		c=a+b;
		System.out.println("c="+c);
		
		MySecondAgent msa = new MySecondAgent();
		msa.setup();
   
   
   
   }
	public void stop(BundleContext bundleContext) 
   {System.out.println("stop");}
}
