package xxl.exceptions;

import java.io.Serial;

public class UnrecognizedFunctionException extends Exception{
    
    @Serial
	private static final long serialVersionUID = 202308312359L;

    /** Unrecognized entry specification. */
	private String _functionName;

	/**
	 * @param entrySpecification
	 */
	public UnrecognizedFunctionException(String functionName) {
		_functionName = functionName;
	}

	/**
	 * @param entrySpecification
	 * @param cause
	 */
	public UnrecognizedFunctionException(String functionName, Exception cause) {
		super(cause);
		_functionName = functionName;
	}

	/**
	 * @return the bad entry specification.
	 */
	public String getEntrySpecification() {
		return _functionName;
	}

}
