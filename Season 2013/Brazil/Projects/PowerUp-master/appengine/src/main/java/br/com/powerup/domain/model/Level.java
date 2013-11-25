package br.com.powerup.domain.model;

public enum Level {
	
	ZERO(Long.MIN_VALUE, 0l),
    ONE(0l, 100l),
    TWO(101l, 500l),
    THREE(501l, 1000l),
    FOUR(1001l, 5000l),
    FIVE(5001l, 15000l),
    SIX(15001l, 30000l),
    SEVEN(30001l, Long.MAX_VALUE)
    ;

    private Long from;
    private Long to;
	
    private Level(Long from, Long to) {
		this.from = from;
		this.to = to;
	}
	
    public static Level fit(Long candidate) {
    	for (Level l : Level.values()) {
    		  if (candidate <= l.getTo() && candidate >= l.getFrom()){
    			  return l;
    		  }
    	}
    	throw new IllegalStateException("Could not fit candidate : "  + candidate + "to any level");
    }

	public Long getFrom() {
		return from;
	}

	public Long getTo() {
		return to;
	}
    
    
}
