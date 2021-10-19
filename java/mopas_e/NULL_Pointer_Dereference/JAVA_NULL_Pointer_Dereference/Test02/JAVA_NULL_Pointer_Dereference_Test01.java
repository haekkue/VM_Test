public class TestAspect {
	private Log log = LogFactory.getLog(TestAspect.class);
	private StopWatch sw = null;
	
	public void beforeprinteMessage() {
		sw = new StopWatch();
		sw.start();      		
		log.debug("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}

	public void afterprintMessage() {
		sw.stop();
	
		log.debug("  실행 시간 : "+sw.getTime());
		log.debug("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
	public void foo1() {
	    String a = null;
	    bar(a);
	}
	
	public void bar(String text) {
	    text.trim();
	    
	    text.mid(5);
	}
}
